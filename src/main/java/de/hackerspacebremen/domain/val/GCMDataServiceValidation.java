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
package de.hackerspacebremen.domain.val;

import java.io.IOException;

import de.hackerspacebremen.deprecated.business.validation.BasicServiceValidation;
import de.hackerspacebremen.deprecated.validation.ValidationException;
import de.hackerspacebremen.domain.api.GCMDataService;

public class GCMDataServiceValidation extends BasicServiceValidation implements GCMDataService{

	public GCMDataServiceValidation(final GCMDataService service){
		this.basicService = service;
	}
	
	@Override
	public void register(final String deviceId, final String registrationId) throws ValidationException{
		this.validateIfEmpty(deviceId, 7);
		this.validateIfEmpty(registrationId, 8);
		((GCMDataService)this.basicService).register(deviceId, registrationId);
	}

	@Override
	public void sendMessageToDevices(final String authToken, final String message)
			throws IOException, ValidationException {
		this.validateIfEmpty(authToken, 9);
		this.validateIfEmpty(message, 10);
		((GCMDataService)this.basicService).sendMessageToDevices(authToken, message);
	}

	@Override
	public void unregister(final String deviceId)
			throws ValidationException {
		this.validateIfEmpty(deviceId, 7);
		((GCMDataService)this.basicService).unregister(deviceId);
	}

}
