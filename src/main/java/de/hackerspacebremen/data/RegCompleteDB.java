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

import com.google.appengine.api.datastore.Key;

import de.hackerspacebremen.data.api.RegCompleteDAO;
import de.hackerspacebremen.data.entities.RegComplete;
import de.hackerspacebremen.deprecated.data.BasicDAODB;
import de.hackerspacebremen.deprecated.data.entity.BasicEntity;

public class RegCompleteDB extends BasicDAODB implements RegCompleteDAO{

	@SuppressWarnings("unchecked")
	@Override
	public <T extends BasicEntity> Class<T> getEntityClass() {
		return (Class<T>) RegComplete.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public RegComplete findByKeeper(final Key keeperKey) {
		final Query query = pManager.newQuery(RegComplete.class, "this.keeperKey == keeperKey");
        query.declareParameters("com.google.appengine.api.datastore.Key keeperKey");
        List<RegComplete> lectures = (List<RegComplete>) query.execute(keeperKey);
        final RegComplete regComplete;
        if(lectures == null || lectures.isEmpty()){
            regComplete = null;
        }else{
        	regComplete = lectures.get(0);
        }
        return regComplete;
	}

}
