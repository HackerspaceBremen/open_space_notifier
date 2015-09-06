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
import java.net.URLDecoder;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.hackerspacebremen.commands.helper.StatusTaskStarter;
import de.hackerspacebremen.commands.resultobjects.BasicResultObject;
import de.hackerspacebremen.common.AppConstants;
import de.hackerspacebremen.data.entities.SpaceStatus;
import de.hackerspacebremen.domain.AuthAttemptService;
import de.hackerspacebremen.domain.BasicHTTPAuthenticationService;
import de.hackerspacebremen.domain.SpaceStatusService;
import de.hackerspacebremen.domain.val.ValidationException;
import de.hackerspacebremen.util.Constants;

@Component
public class CloseSpaceCommand extends WebCommand {

	private SpaceStatusService statusService;
	private BasicHTTPAuthenticationService authService;
	private StatusTaskStarter statusTaskStarter;
	private AuthAttemptService authAttemptService;

	@Autowired
	public CloseSpaceCommand(SpaceStatusService statusService, BasicHTTPAuthenticationService authService,
			StatusTaskStarter statusTaskStarter, AuthAttemptService authAttemptService) {
		this.statusService = statusService;
		this.authService = authService;
		this.statusTaskStarter = statusTaskStarter;
		this.authAttemptService = authAttemptService;
	}

	@Override
	public void process() throws ServletException, IOException {
		try {
			final String name = this.req.getParameter("name");
			final String encoded = this.req.getParameter("encoded");

			final String pass;
			final String message;

			if (encoded != null && encoded.equals("true")) {
				pass = URLDecoder.decode(this.req.getParameter("pass"), Constants.UTF8);
				if (this.req.getParameter("message") != null) {
					message = URLDecoder.decode(this.req.getParameter("message"), Constants.UTF8);
				} else {
					message = null;
				}
			} else {
				pass = this.req.getParameter("pass");
				message = this.req.getParameter("message");
			}

			if (authAttemptService.checkAttemptMax(name)) {
				this.handleError(55);
			} else if (authService.authenticate(name, pass)) {
				SpaceStatus status = statusService.currentCopyStatus();
				if (status == null || status.getStatus().equals(AppConstants.OPEN)) {
					status = statusService.closeSpace(name, message);
					this.statusTaskStarter.startTasks(status);
					this.handleSuccess(new BasicResultObject("Space was closed"));
				} else {
					this.handleError(4);
				}
			} else {
				this.handleError(1);
			}
		} catch (ValidationException ve) {
			this.handleError(ve);
		}

		super.process();
	}
}
