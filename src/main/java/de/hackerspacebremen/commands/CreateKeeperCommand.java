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

import de.hackerspacebremen.deprecated.presentation.WebCommand;
import de.hackerspacebremen.deprecated.validation.ValidationException;
import de.hackerspacebremen.domain.api.DoorKeyKeeperService;
import de.hackerspacebremen.exceptions.AlreadyExistError;

public class CreateKeeperCommand extends WebCommand{

//	/**
//     * static attribute used for logging.
//     */
//    private static final Logger logger = Logger.getLogger(CreateKeeperCommand.class.getName());
	
	@Inject
	private DoorKeyKeeperService keeperService;
	
	@Override
	public void process() throws ServletException, IOException {
//		final DoorKeyKeeperService keeperService = Factory.createDoorKeyKeeperService();
		this.registerService(keeperService);
		try {
			final boolean admin = keeperService.amount()==0;
			
			keeperService.createNewKeeper(req.getParameter("name"), req.getParameter("pass"), 
					admin, req.getParameter("fullName"), req.getParameter("email"));
			
			this.handleSuccess("Door key keeper was successfully created!", null);
		} catch (AlreadyExistError e) {
			this.handleError(2);
		} catch(ValidationException ve){
			this.handleError(ve);
		}
		
		// close services
		super.process();
	}

	
}
