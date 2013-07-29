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

import com.google.inject.Inject;

import de.hackerspacebremen.data.entities.SpaceStatus;
import de.hackerspacebremen.domain.api.SpaceStatusService;

public class SpaceStatusServiceValidation extends Validation 
										  implements SpaceStatusService{

	private SpaceStatusService spaceStatusService;

	@Inject
	public SpaceStatusServiceValidation(final SpaceStatusService service){
		this.spaceStatusService = service;
	}
	
	@Override
	public SpaceStatus openSpace(final String changedBy, final String message) throws ValidationException{
		this.validateNull(changedBy, 16);
		return spaceStatusService.openSpace(changedBy, message);
	}

	@Override
	public SpaceStatus closeSpace(final String changedBy, final String message) throws ValidationException{
		this.validateNull(changedBy, 16);
		return spaceStatusService.closeSpace(changedBy, message);
	}

	@Override
	public SpaceStatus currentStatus() throws ValidationException{
		return spaceStatusService.currentStatus();
	}

	@Override
	public SpaceStatus changeMessage(final SpaceStatus status, final String message) throws ValidationException {
		this.validateNull(status, 20);
		return spaceStatusService.changeMessage(status, message);
	}
}