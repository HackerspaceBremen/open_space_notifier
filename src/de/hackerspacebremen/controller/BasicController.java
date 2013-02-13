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

import java.util.logging.Logger;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;

import de.hackerspacebremen.MyErrorMessages;
import de.hackerspacebremen.common.PresentationMode;
import de.hackerspacebremen.transformer.AppEngineKeyTransformer;
import de.hackerspacebremen.transformer.AppEngineTextTransformer;
import de.hackerspacebremen.transformer.EntityTransformer;
import de.liedtke.data.entity.BasicEntity;
import de.liedtke.validation.ValidationException;
import flexjson.JSONSerializer;

/**
 * @author Steve Liedtke
 *
 */
public class BasicController {

	private static final Logger logger = Logger.getLogger(StatusController.class.getName());
	
	// TODO @Autowired
	private MyErrorMessages errorMessages = new MyErrorMessages();
	
	protected final JSONSerializer serializer = new JSONSerializer().exclude("*.class")
			.transform(new EntityTransformer(PresentationMode.VIEW), BasicEntity.class)
			.transform(new AppEngineKeyTransformer(), Key.class)
			.transform(new AppEngineTextTransformer(), Text.class);
	
	protected ResponseEntity<String> handleJsonError(ValidationException ve) {
		return this.handleJsonError(ve.getErrorCode());
	}

	protected ResponseEntity<String> handleJsonError(final int errorCode) {
		final HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		final String error;
		if (this.errorMessages == null) {
			error = "";
		} else {
			error = this.errorMessages.getMessage(errorCode);
		}
		
		final ResponseEntity<String> result = new ResponseEntity<String>(new JSONSerializer().exclude("*.class").serialize(
				new ResultObject(errorCode, error)), headers, HttpStatus.BAD_REQUEST);
		
		logger.info(result.getBody());
		
		return result;
	}
	
	protected ResponseEntity<String> handleJsonSuccess(final String message, final BasicEntity result, final PresentationMode mode) {
		final HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		
		final ResponseEntity<String> successResult = new ResponseEntity<String>(serializer.serialize(
				new ResultObject(message, result)), headers, HttpStatus.OK);
		
		logger.info(successResult.getBody());
		
		return successResult;
	}
	
	@ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleAllExceptions(Exception ex) {
		final String message = ex.getClass().getName() + "; Message: " + ex.getMessage();
		logger.severe(message);
        return new JsonError(message).asModelAndView();
    }
}