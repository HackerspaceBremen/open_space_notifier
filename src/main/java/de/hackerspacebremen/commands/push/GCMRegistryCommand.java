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
package de.hackerspacebremen.commands.push;

import java.io.IOException;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.hackerspacebremen.commands.WebCommand;
import de.hackerspacebremen.commands.resultobjects.BasicResultObject;
import de.hackerspacebremen.domain.GcmDataService;
import de.hackerspacebremen.domain.val.ValidationException;

@Component
public class GCMRegistryCommand extends WebCommand{
	
	@Autowired
	private GcmDataService gcmService;

	@Override
	public void process() throws ServletException, IOException {
		try{
			gcmService.register(req.getParameter("deviceId"), req.getParameter("registrationId"));
			this.handleSuccess(new BasicResultObject("Your registry was successful"));
		}catch(ValidationException e){
			this.handleError(e);
		}
		
		// close services
		super.process();
	}
}
