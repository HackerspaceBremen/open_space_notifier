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

public class ActivateCommand extends WebCommand{

	@Inject
	private DoorKeyKeeperService keeperService;
	
	@Override
	public void process() throws ServletException, IOException {
		this.registerService(keeperService);
		
		boolean failed = true;
		try{
			failed = keeperService.activateKeeper(req.getParameter("key"), req.getParameter("nick"));
		}catch(ValidationException ve){
			this.handleError(ve);
		}
		
		keeperService.close();
		if(failed)
			this.resp.sendRedirect("/activateError.html");
		else
			this.resp.sendRedirect("/activate.html");
	}
}
