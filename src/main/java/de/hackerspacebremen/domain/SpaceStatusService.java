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
package de.hackerspacebremen.domain;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.appengine.api.datastore.Text;

import de.hackerspacebremen.common.AppConstants;
import de.hackerspacebremen.data.entities.SpaceStatus;
import de.hackerspacebremen.data.objectify.SpaceStatusDao;
import de.hackerspacebremen.domain.val.SpaceStatusServiceValidation;
import de.hackerspacebremen.domain.val.ValidationException;

@Service
public class SpaceStatusService {
	
	private SpaceStatusDao spaceStatusDao;
	private SpaceStatusServiceValidation validation;
	
	@Autowired
	public SpaceStatusService(final SpaceStatusDao spaceStatusDao, SpaceStatusServiceValidation validation){
		this.spaceStatusDao = spaceStatusDao;
		this.validation = validation;
	}
	
	public SpaceStatus openSpace(final String changedBy, final String message) {
		validation.openSpace(changedBy);
		return changeState(changedBy, message, true);
	}
	
	public SpaceStatus closeSpace(final String changedBy, final String message) {
		validation.closeSpace(changedBy);
		return changeState(changedBy, message, false);
	}

	private SpaceStatus changeState(final String changedBy, final String message, final boolean open) {
		final SpaceStatus status = new SpaceStatus();
		status.setOpenedBy(changedBy);
		if(open){
			status.setStatus(AppConstants.OPEN);
		}else{
			status.setStatus(AppConstants.CLOSED);
		}
		status.setTime(new Date().getTime());
		if(message != null){
			status.setMessage(new Text(message));
		}
		return this.spaceStatusDao.persist(status);
	}

	public SpaceStatus currentCopyStatus() {
		return new SpaceStatus(this.spaceStatusDao.findCurrentStatus());
	}
	
	public SpaceStatus currentStatus() {
		return this.spaceStatusDao.findCurrentStatus();
	}

	public SpaceStatus changeMessage(final SpaceStatus status, final String message) throws ValidationException {
		validation.changeMessage(status);
		if(message==null){
			status.setMessage(new Text(""));
		}else{
			status.setMessage(new Text(message));
		}
		return this.spaceStatusDao.persist(status);
	}

	public SpaceStatus findById(final Long id) throws ValidationException {
		validation.findById(id);
		return this.spaceStatusDao.findById(id);
	}
}