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

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.appengine.api.datastore.Text;

import de.hackerspacebremen.common.AppConstants;
import de.hackerspacebremen.data.api.SpaceStatusDAO;
import de.hackerspacebremen.data.entities.DoorKeyKeeper;
import de.hackerspacebremen.data.entities.SpaceStatus;
import de.hackerspacebremen.domain.api.SpaceStatusService;
import de.hackerspacebremen.email.StatusEmail;
import de.liedtke.business.BasicServiceImpl;
import de.liedtke.validation.ValidationException;

@Service
public class SpaceStatusServiceImpl extends BasicServiceImpl 
	implements SpaceStatusService{
	
	@Resource(name="spaceStatusMem")
	public SpaceStatusDAO spaceStatusDAO;
	
	// needed for di
	public SpaceStatusServiceImpl(){
		this.basicDAO = this.spaceStatusDAO;
	}
	
	public SpaceStatusServiceImpl(final SpaceStatusDAO dao){
		this.basicDAO = dao;
		this.spaceStatusDAO = (SpaceStatusDAO) basicDAO;
	}
	
	@Override
	public SpaceStatus openSpace(final DoorKeyKeeper keeper, final String message) {
		final SpaceStatus status = new SpaceStatus();
		status.setOpenedBy(keeper.getKey());
		status.setStatus(AppConstants.OPEN);
		status.setTime(new Date().getTime());
		if(message != null){
			status.setMessage(new Text(message));
		}
//		final SpaceStatus result = ((SpaceStatusDAO)this.basicDAO).persist(status);
		final SpaceStatus result = spaceStatusDAO.persist(status);
		new StatusEmail(message, true).send();
		return result;
	}

	@Override
	public SpaceStatus closeSpace(final DoorKeyKeeper keeper, final String message) {
		final SpaceStatus status = new SpaceStatus();
		status.setOpenedBy(keeper.getKey());
		status.setStatus(AppConstants.CLOSED);
		status.setTime(new Date().getTime());
		if(message != null){
			status.setMessage(new Text(message));
		}
//		final SpaceStatus result = ((SpaceStatusDAO)this.basicDAO).persist(status);
		final SpaceStatus result = spaceStatusDAO.persist(status);
		new StatusEmail(message, false).send();
		return result;
	}

	@Override
	public SpaceStatus currentStatus() {
//		return ((SpaceStatusDAO)this.basicDAO).findCurrentStatus();
		return spaceStatusDAO.findCurrentStatus();
	}

	@Override
	public SpaceStatus changeMessage(final SpaceStatus status, final String message) throws ValidationException {
		if(message==null){
			status.setMessage(new Text(""));
		}else{
			status.setMessage(new Text(message));
		}
//		return ((SpaceStatusDAO)this.basicDAO).persist(status);
		return spaceStatusDAO.persist(status);
	}
}