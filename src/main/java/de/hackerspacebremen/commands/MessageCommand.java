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

import de.hackerspacebremen.commands.helper.StatusTimeFormat;
import de.hackerspacebremen.common.AppConstants;
import de.hackerspacebremen.data.entities.SpaceStatus;
import de.hackerspacebremen.domain.api.LDAPService;
import de.hackerspacebremen.domain.api.SpaceStatusService;
import de.hackerspacebremen.domain.val.ValidationException;
import de.hackerspacebremen.format.FormatException;
import de.hackerspacebremen.format.FormatFactory;
import de.hackerspacebremen.modules.binding.annotations.Proxy;

public class MessageCommand extends WebCommand{

	/**
     * static attribute used for logging.
     */
    private static final Logger logger = Logger.getLogger(MessageCommand.class.getName());
	
    @Inject
    @Proxy
	private SpaceStatusService statusService;
    
    @Inject
    @Proxy
	private LDAPService ldapService;
    
    
	@Override
	public void process() throws ServletException, IOException {
		
		try{
			final String name = this.req.getParameter("name");
			final String pass = this.req.getParameter("pass");
			final String message = this.req.getParameter("message");
			final String format = this.req.getParameter("format");
			final String time = this.req.getParameter("time");
			
			logger.info("message: " + message);
			logger.info("format: " + format);
			logger.info("time: " + time);
			
			if(ldapService.authenticate(name, pass)){
				final SpaceStatus status = statusService.currentStatus();
				final String timeOfCurrent;
				if(format==null || format.isEmpty()){
					timeOfCurrent = "" + status.getTime();
				}else{
					final String kind = req.getParameter("format");
					timeOfCurrent = new StatusTimeFormat(FormatFactory.getFormatter(kind).format(status, AppConstants.LEVEL_VIEW), status).createTimeFormat(format);
				}
				if(time != null && timeOfCurrent.equals(time)){
					statusService.changeMessage(status, message);
					this.handleSuccess("Statusmessage was changed", null);
				}else{
					this.handleError(19);
				}
			}else{
				this.handleError(18);
			}
		}catch(ValidationException ve){
			this.handleError(ve);
		} catch (FormatException e) {
			this.handleError(77);
		}
		
		//closing all
		super.process();
	}
}