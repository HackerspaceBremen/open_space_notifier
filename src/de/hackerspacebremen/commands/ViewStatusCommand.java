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
package de.hackerspacebremen.commands;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;

import org.json.JSONException;

import de.hackerspacebremen.Factory;
import de.hackerspacebremen.commands.helper.StatusTimeFormat;
import de.hackerspacebremen.common.AppConstants;
import de.hackerspacebremen.data.entities.SpaceStatus;
import de.hackerspacebremen.domain.api.SpaceStatusService;
import de.hackerspacebremen.format.MessageFormat;
import de.liedtke.common.Constants;
import de.liedtke.format.FormatException;
import de.liedtke.presentation.WebCommand;
import de.liedtke.validation.ValidationException;

public class ViewStatusCommand extends WebCommand{

	/**
     * static attribute used for logging.
     */
    private static final Logger logger = Logger.getLogger(ViewStatusCommand.class.getName());
	
	@Override
	public void process() throws ServletException, IOException {
		final SpaceStatusService statusService = Factory.createSpaceStatusService();
		this.registerService(statusService);
		
		final boolean htmlEncoded = (req.getParameter("htmlEncoded")!=null && req.getParameter("htmlEncoded").equals("true"));
		try {
			final SpaceStatus status = statusService.currentStatus();
			if(status == null){
				this.handleError(17);
			}else{
				if(htmlEncoded){
					MessageFormat.htmlEncode(status);
				}
				String resultWithFormat = this.formatter.format(status, AppConstants.LEVEL_VIEW);
				resultWithFormat = new StatusTimeFormat(resultWithFormat, status).createOtherFormat(req.getParameter("format"));
				this.handleSuccess("Status found", 
						resultWithFormat);
			}
		} catch (FormatException e) {
			this.handleError(77);
			logger.warning(Constants.FORMAT_EXCEPTION_OCCURED + e.getMessage());
		} catch (JSONException e) {
			this.handleError(88);
			logger.warning(Constants.JSON_EXCEPTION_OCCURED + e.getMessage());
		} catch(ValidationException ve){
			this.handleError(ve);
		}
		
		//closing all services
		super.process();
	}

	
}
