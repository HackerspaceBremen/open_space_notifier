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

import com.google.appengine.api.datastore.KeyFactory;

import de.hackerspacebremen.Factory;
import de.hackerspacebremen.common.AppConstants;
import de.hackerspacebremen.data.entities.GCMAuth;
import de.hackerspacebremen.data.entities.SpaceStatus;
import de.hackerspacebremen.domain.api.GCMAuthService;
import de.hackerspacebremen.domain.api.GCMDataService;
import de.hackerspacebremen.domain.api.SpaceStatusService;
import de.hackerspacebremen.format.MessageFormat;
import de.liedtke.common.Constants;
import de.liedtke.format.FormatException;
import de.liedtke.presentation.WebCommand;
import de.liedtke.util.Encryption;
import de.liedtke.validation.ValidationException;

public class GCMCommand extends WebCommand{

	/**
     * static attribute used for logging.
     */
    private static final Logger logger = Logger.getLogger(GCMCommand.class.getName());
	
	@Override
	public void process() throws ServletException, IOException {
		final SpaceStatusService statusService = Factory.createSpaceStatusService();
		final GCMDataService gcmDataService = Factory.createGCMDataService();
		final GCMAuthService gcmAuthService = Factory.createGCMAuthService();
		this.registerService(gcmDataService, gcmAuthService, statusService);
		
		try{
			final GCMAuth authToken = gcmAuthService.getAuthToken();
			if(authToken == null){
				this.handleSuccess("Messages couldn't be sent, because no authToken was found!", null);
			}else{
				SpaceStatus status = statusService.currentStatus();
				final String token = req.getParameter("token");
				if(token != null && token.equals(Encryption.encryptSHA256(authToken.getToken()+KeyFactory.keyToString(status.getKey())))){
					MessageFormat.fitMessageSize(status);
					gcmDataService.sendMessageToDevices(authToken.getToken(), this.formatter.format(status, AppConstants.LEVEL_VIEW));
					this.handleSuccess("Messages were sent to the GCM server!", null);
				}else{
					this.handleSuccess("The given token couldn't be 'decrypted! The message couldn't be send ...'", null);
				}
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
