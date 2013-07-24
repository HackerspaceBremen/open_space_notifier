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
package de.hackerspacebremen.data;

import java.util.List;
import java.util.logging.Logger;

import javax.jdo.Query;

import de.hackerspacebremen.data.api.GCMDataDAO;
import de.hackerspacebremen.data.entities.GCMData;
import de.hackerspacebremen.deprecated.data.BasicDAODB;
import de.hackerspacebremen.deprecated.data.entity.BasicEntity;

public class GCMDataDB extends BasicDAODB implements GCMDataDAO{

	/**
     * static attribute used for logging.
     */
    private static final Logger logger = Logger.getLogger(GCMDataDB.class.getName());
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends BasicEntity> Class<T> getEntityClass() {
		return (Class<T>) GCMData.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public GCMData findByDeviceId(final String deviceId) {
		final List<GCMData> data;
		final Query query = pManager.newQuery(GCMData.class, "this.deviceId == deviceId");
		query.declareParameters("String deviceId");
		data = (List<GCMData>) query.execute(deviceId);
 		final GCMData result;
		if(data == null || data.isEmpty()){
 			result = null;
 		}else if(data.size()>1){
 			result = null;
 			logger.warning("More than one result shouldn't be possible");
 		}else{
 			result = data.get(0);
 		}
		return result;
	}

}
