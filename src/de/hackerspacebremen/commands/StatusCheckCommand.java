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

import de.hackerspacebremen.Factory;
import de.hackerspacebremen.data.entities.DoorKeyKeeper;
import de.hackerspacebremen.data.entities.SpaceStatus;
import de.hackerspacebremen.domain.api.DoorKeyKeeperService;
import de.hackerspacebremen.domain.api.SpaceStatusService;
import de.hackerspacebremen.email.ForgotToCloseEmail;
import de.hackerspacebremen.util.PropertyHelper;
import de.liedtke.presentation.WebCommand;
import de.liedtke.validation.ValidationException;

/**
 * @author Steve
 *
 */
public class StatusCheckCommand extends WebCommand{

	/**
     * static attribute used for logging.
     */
    private static final Logger logger = Logger.getLogger(StatusCheckCommand.class.getName());
	
	/* (non-Javadoc)
	 * @see de.liedtke.presentation.WebCommand#process()
	 */
	@Override
	public void process() throws ServletException, IOException {
		final SpaceStatusService statusService = Factory.createSpaceStatusService();
		final DoorKeyKeeperService keeperService = Factory.createDoorKeyKeeperService();
		this.registerService(statusService, keeperService);
		
		try{
			final SpaceStatus currentStatus = statusService.currentStatus();
			if(currentStatus.getStatus()!=null && currentStatus.getStatus().equals("OPEN")){
				logger.info("The space wasn't closed - START closing space!");
				final DoorKeyKeeper keeper = keeperService.findById(currentStatus.getOpenedBy());
				if(keeper == null){
					this.handleError(99);
				}else{
					statusService.closeSpace(keeper, PropertyHelper.getConstantsPropertyValue("constants.automatic.close"));
					new ForgotToCloseEmail(keeper).send();
					this.handleSuccess("The space is now closed - An email was sent to the email " + keeper.getEmail(), null);
				}
			}else{
				logger.info("The space was correctly closed");
			}
		}catch(ValidationException ve){
			this.handleError(ve);
		}
		super.process();
	}
}
