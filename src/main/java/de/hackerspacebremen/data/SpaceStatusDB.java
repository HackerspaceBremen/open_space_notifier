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

import javax.jdo.Query;

import org.springframework.stereotype.Service;

import de.hackerspacebremen.data.api.SpaceStatusDAO;
import de.hackerspacebremen.data.entities.SpaceStatus;
import de.hackerspacebremen.deprecated.data.BasicDAODB;
import de.hackerspacebremen.deprecated.data.entity.BasicEntity;

@Service
public final class SpaceStatusDB extends BasicDAODB implements SpaceStatusDAO{
	
	@SuppressWarnings("unchecked")
	@Override
	public SpaceStatus findCurrentStatus() {
		final List<SpaceStatus> statusList;
		final Query query = pManager.newQuery(SpaceStatus.class);
		query.setOrdering("time desc");
		statusList = (List<SpaceStatus>) query.execute();
 		final SpaceStatus result;
		if(statusList == null || statusList.isEmpty()){
 			result = null;
 		}else{
 			result = statusList.get(0);
 		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends BasicEntity> Class<T> getEntityClass() {
		return (Class<T>) SpaceStatus.class;
	}
}
