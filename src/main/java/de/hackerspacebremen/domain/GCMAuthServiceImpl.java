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

import java.util.List;
import java.util.logging.Logger;

import com.google.inject.Inject;

import de.hackerspacebremen.data.api.GCMAuthDAO;
import de.hackerspacebremen.data.entities.GCMAuth;
import de.hackerspacebremen.domain.api.GCMAuthService;
import de.hackerspacebremen.util.Generator;

public class GCMAuthServiceImpl implements GCMAuthService{

	/**
	 * static attribute used for logging.
	*/
	private static final Logger logger = Logger.getLogger(GCMAuthServiceImpl.class.getName());
	
	private GCMAuthDAO gcmAuthDAO;
	
	@Inject
	public GCMAuthServiceImpl(final GCMAuthDAO dao){
		this.gcmAuthDAO = dao;
	}
	
	@Override
	public GCMAuth getAuthToken() {
		final List<GCMAuth> list = gcmAuthDAO.findAll();
		final GCMAuth result;
		if(list.isEmpty()){
			result = getNewToken(gcmAuthDAO);
		}else if(list.size()>1){
			for(final GCMAuth auth : list){
				gcmAuthDAO.delete(auth);
			}
			result = getNewToken(gcmAuthDAO);
		}else{
			result = list.get(0);
		}
		return result;
	}

	private GCMAuth getNewToken(final GCMAuthDAO dao){
		GCMAuth auth = new GCMAuth();
		try {
		    auth.setToken(Generator.randomString(20));
		    auth = dao.persist(auth);
		} catch(NullPointerException e){
			logger.warning("A NullPointerException occured and the authToken wasn't created!");
			auth = null;
		}
		return auth;
	}
}
