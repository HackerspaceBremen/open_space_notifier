/*
 * Hackerspace Bremen Backend - An Open-Space-Notifier
 * 
 * Copyright (C) 2012 Steve Liedtke <sliedtke57@gmail.com>
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the 
 * GNU General Public License as published by the Free Software Foundation; either version 3 of 
 * the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See 
 * the GNU General Public License for more details.
 * 
 * You can find a copy of the GNU General Public License on http://www.gnu.org/licenses/gpl.html.
 * 
 * Contributors:
 *     Steve Liedtke <sliedtke57@gmail.com>
 */
package de.hackerspacebremen.controller;

import javax.annotation.Resource;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import de.hackerspacebremen.common.AppConstants;
import de.hackerspacebremen.common.PresentationMode;
import de.hackerspacebremen.data.entities.SpaceStatus;
import de.hackerspacebremen.domain.api.SpaceStatusService;
import de.hackerspacebremen.format.LanguageFormat;
import de.hackerspacebremen.format.MessageFormat;
import de.hackerspacebremen.transformer.SpeakingDateTransformer;
import de.liedtke.validation.ValidationException;

/**
 * @author Steve Liedtke
 */
@Controller
@RequestMapping("/status")
public final class StatusController extends BasicController{

	@Resource(name="spaceStatusServiceValidation")
	private SpaceStatusService statusService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<String> jsonGetStatus(
			@RequestParam(value="htmlEncoded",required=false, defaultValue="false") boolean htmlEncoded,
			@RequestParam(value="format",required=false) String format) {
		final HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		try{
			final SpaceStatus status = statusService.currentStatus();
			if(status == null){
				return this.handleJsonError(17);
			}else{
				if(htmlEncoded){
					MessageFormat.htmlEncode(status);
				}
				return this.handleJsonSuccess("Status found", 
						status, PresentationMode.VIEW, format);
			}
		} catch(ValidationException ve){
			return this.handleJsonError(ve);
		}
	}
	
	private ResponseEntity<String> handleJsonSuccess(final String message, final SpaceStatus status, final PresentationMode mode, final String format){
		serializer.transform(
				new SpeakingDateTransformer(LanguageFormat.createInstance(format), status.getStatus().equals(AppConstants.OPEN)), "RESULT.time");
		return super.handleJsonSuccess(message, status, mode);
	}
}