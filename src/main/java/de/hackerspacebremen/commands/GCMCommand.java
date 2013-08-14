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

import com.google.inject.Inject;

import de.hackerspacebremen.common.AppConstants;
import de.hackerspacebremen.data.entities.SpaceStatus;
import de.hackerspacebremen.domain.api.GCMDataService;
import de.hackerspacebremen.domain.api.SpaceStatusService;
import de.hackerspacebremen.domain.val.ValidationException;
import de.hackerspacebremen.format.FormatException;
import de.hackerspacebremen.format.FormatFactory;
import de.hackerspacebremen.format.MessageFormat;
import de.hackerspacebremen.modules.binding.annotations.Proxy;
import de.hackerspacebremen.util.Constants;

public class GCMCommand extends WebCommand{

	/**
     * static attribute used for logging.
     */
    private static final Logger logger = Logger.getLogger(GCMCommand.class.getName());
	
    @Inject
    @Proxy
	private SpaceStatusService statusService;
    
    @Inject
    @Proxy
	private GCMDataService gcmDataService;
    
	@Override
	public void process() throws ServletException, IOException {
		try{
			SpaceStatus status = statusService.currentStatus();
			final String statusId = req.getParameter("statusId");
			if(statusId != null && status.getId().equals(Long.valueOf(statusId))){
				status = statusService.currentCopyStatus();
				MessageFormat.fitMessageSize(status);
				final String kind = req.getParameter("format");
				
				gcmDataService.sendMessageToDevices(FormatFactory.getFormatter(kind).format(status, AppConstants.LEVEL_VIEW));
				this.handleSuccess("Messages were sent to the GCM server!", null);
			}else{
				this.handleSuccess("The given status id is not valid anymore! The message couldn't be send ...'", null);
			}
		}catch(ValidationException ve){
			this.handleError(ve);
		} catch (FormatException e) {
			this.handleError(77);
			logger.warning(Constants.FORMAT_EXCEPTION_OCCURED + e.getMessage());
		}
		
		super.process();
	}
}
