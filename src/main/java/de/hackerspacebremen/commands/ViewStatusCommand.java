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
import org.json.JSONObject;

import com.google.inject.Inject;

import de.hackerspacebremen.commands.helper.StatusTimeFormat;
import de.hackerspacebremen.common.AppConstants;
import de.hackerspacebremen.data.entities.SpaceStatus;
import de.hackerspacebremen.deprecated.format.FormatException;
import de.hackerspacebremen.deprecated.presentation.WebCommand;
import de.hackerspacebremen.deprecated.validation.ValidationException;
import de.hackerspacebremen.domain.api.SpaceStatusService;
import de.hackerspacebremen.format.MessageFormat;
import de.hackerspacebremen.util.Constants;

public class ViewStatusCommand extends WebCommand{

	/**
     * static attribute used for logging.
     */
    private static final Logger logger = Logger.getLogger(ViewStatusCommand.class.getName());
	
    @Inject
	private SpaceStatusService statusService;
    
    @Override
    protected void handleSuccess(String message, String result) {
    	super.handleSuccess(message, result);
    	try{
	    	this.result.addValue("api", "0.12");
			this.result.addValue("space", "RevSpace");
			this.result.addValue("url", "https:\\/\\/www.hackerspace-bremen.de");
			final JSONObject iconJSON = new JSONObject();
			iconJSON.put("open", "TODO");
			iconJSON.put("closed", "TODO");
			this.result.addValue("icon", iconJSON);
			this.result.addValue("address", "Bornstrasse 14/15, 28195 Bremen, Germany");
			final JSONObject contactJSON = new JSONObject();
			contactJSON.put("phone", "+49 421 14 62 92 15");
			contactJSON.put("twitter", "@hspacehb");
			contactJSON.put("email", "info@hackerspace-bremen.de");
			this.result.addValue("contact", contactJSON);
			this.result.addValue("logo", "TODO");
			this.result.addValue("lat", 53.08178f);
			this.result.addValue("lon", 8.805831f);
			final JSONObject status = new JSONObject(result);
			this.result.addValue("open", status.getString("ST3").equals("OPEN"));
			this.result.addValue("status", status.getString("ST5"));
			this.result.addValue("lastchange", Long.valueOf(status.getString("ST2")));
    	}catch(JSONException e){
    		logger.warning("JSONException occured: " + e.getMessage());
    	}
    }
    
	@Override
	public void process() throws ServletException, IOException {
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