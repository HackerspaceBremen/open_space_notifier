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

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import de.hackerspacebremen.data.entities.DoorKeyKeeper;
import de.hackerspacebremen.data.entities.SpaceStatus;
import de.hackerspacebremen.deprecated.business.validation.BasicServiceValidation;
import de.hackerspacebremen.deprecated.validation.ValidationException;
import de.hackerspacebremen.domain.api.SpaceStatusService;

@Service
public class SpaceStatusServiceValidation extends BasicServiceValidation 
										  implements SpaceStatusService{

	@Resource(name="spaceStatusServiceImpl")
	private SpaceStatusService spaceStatusService;
	
	// needed for di
	public SpaceStatusServiceValidation(){
		this.basicService = this.spaceStatusService;
	}
	
	public SpaceStatusServiceValidation(final SpaceStatusService service){
		this.basicService = service;
		this.spaceStatusService = (SpaceStatusService) basicService;
	}
	
	@Override
	public SpaceStatus openSpace(final DoorKeyKeeper keeper, final String message) throws ValidationException{
		this.validateNull(keeper, 16);
//		return ((SpaceStatusService)this.basicService).openSpace(keeper, message);
		return spaceStatusService.openSpace(keeper, message);
	}

	@Override
	public SpaceStatus closeSpace(final DoorKeyKeeper keeper, final String message) throws ValidationException{
		this.validateNull(keeper, 16);
//		return ((SpaceStatusService)this.basicService).closeSpace(keeper, message);
		return spaceStatusService.closeSpace(keeper, message);
	}

	@Override
	public SpaceStatus currentStatus() throws ValidationException{
//		return ((SpaceStatusService)this.basicService).currentStatus();
		return spaceStatusService.currentStatus();
	}

	@Override
	public SpaceStatus changeMessage(final SpaceStatus status, final String message) throws ValidationException {
		this.validateNull(status, 20);
//		return ((SpaceStatusService)this.basicService).changeMessage(status, message);
		return spaceStatusService.changeMessage(status, message);
	}
}