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

import javax.servlet.ServletException;

import de.hackerspacebremen.Factory;
import de.hackerspacebremen.domain.api.GCMDataService;
import de.liedtke.presentation.WebCommand;
import de.liedtke.validation.ValidationException;

public class GCMUnregistryCommand extends WebCommand{

	@Override
	public void process() throws ServletException, IOException {
		final GCMDataService gcmService = Factory.createGCMDataService();
		this.registerService(gcmService);
		try{
			gcmService.unregister(req.getParameter("deviceId"));
			this.handleSuccess("You successfully unregistered", null);
		}catch(ValidationException e){
			this.handleError(e);
		}
		
		// close services
		super.process();
	}
}
