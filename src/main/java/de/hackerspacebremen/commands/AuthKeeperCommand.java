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

import com.google.inject.Inject;

import de.hackerspacebremen.data.entities.DoorKeyKeeper;
import de.hackerspacebremen.deprecated.presentation.WebCommand;
import de.hackerspacebremen.deprecated.validation.ValidationException;
import de.hackerspacebremen.domain.api.DoorKeyKeeperService;
import de.hackerspacebremen.exceptions.DoesntExistError;

public class AuthKeeperCommand extends WebCommand{

	@Inject
	private DoorKeyKeeperService keeperService;
	
	@Override
	public void process() throws ServletException, IOException {
		//final DoorKeyKeeperService keeperService = Factory.createDoorKeyKeeperService();
		this.registerService(keeperService);
		
		try {
			final DoorKeyKeeper adminKeeper = keeperService.findKeyKeeper(req.getParameter("name"), req.getParameter("pass"));
			if(adminKeeper != null && adminKeeper.isAdmin()){
				final boolean admin = (req.getParameter("admin") != null && req.getParameter("admin").equals("true"));
				final boolean active = (req.getParameter("active") != null && req.getParameter("active").equals("true"));
				
				keeperService.authKeeper(req.getParameter("nick"), admin, active, req.getParameter("complete"));
				
				this.handleSuccess("Successfully changed the state of the keeper!", null);
			}else{
				this.handleError(5);
			}
		} catch (DoesntExistError e) {
			this.handleError(6);
		} catch(ValidationException e){
			this.handleError(e);
		}
		
		// close services
		super.process();
	}
}
