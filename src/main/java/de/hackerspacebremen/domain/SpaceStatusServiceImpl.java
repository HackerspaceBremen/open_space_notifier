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

import org.springframework.stereotype.Service;

import com.google.appengine.api.datastore.Text;
import com.google.inject.Inject;

import de.hackerspacebremen.common.AppConstants;
import de.hackerspacebremen.data.api.SpaceStatusDAO;
import de.hackerspacebremen.data.entities.SpaceStatus;
import de.hackerspacebremen.domain.api.SpaceStatusService;
import de.hackerspacebremen.domain.val.ValidationException;

@Service
public class SpaceStatusServiceImpl implements SpaceStatusService{
	
	private SpaceStatusDAO spaceStatusDAO;
	
	@Inject
	public SpaceStatusServiceImpl(final SpaceStatusDAO spaceStatusDAO){
		this.spaceStatusDAO = spaceStatusDAO;
	}
	
	@Override
	public SpaceStatus openSpace(final String changedBy, final String message) {
		return changeState(changedBy, message, true);
	}
	
	@Override
	public SpaceStatus closeSpace(final String changedBy, final String message) {
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
		return this.spaceStatusDAO.persist(status);
	}

	@Override
	public SpaceStatus currentCopyStatus() {
		return new SpaceStatus(this.spaceStatusDAO.findCurrentStatus());
	}
	
	@Override
	public SpaceStatus currentStatus() {
		return this.spaceStatusDAO.findCurrentStatus();
	}

	@Override
	public SpaceStatus changeMessage(final SpaceStatus status, final String message) throws ValidationException {
		if(message==null){
			status.setMessage(new Text(""));
		}else{
			status.setMessage(new Text(message));
		}
		return this.spaceStatusDAO.persist(status);
	}

	@Override
	public SpaceStatus findById(final Long id) throws ValidationException {
		return this.spaceStatusDAO.findById(id);
	}
}