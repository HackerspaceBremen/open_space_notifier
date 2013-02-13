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
package de.hackerspacebremen.data.cache;

import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

import net.sf.jsr107cache.Cache;
import de.hackerspacebremen.data.api.DoorKeyKeeperDAO;
import de.hackerspacebremen.data.entities.DoorKeyKeeper;
import de.liedtke.common.Constants;
import de.liedtke.data.cache.BasicDAOMem;
import de.liedtke.data.entity.BasicEntity;
import de.liedtke.format.FormatException;

public class DoorKeyKeeperMem extends BasicDAOMem implements DoorKeyKeeperDAO{

	/**
     * static attribute used for logging.
     */
    private static final Logger logger = Logger.getLogger(DoorKeyKeeperMem.class.getName());
	
	public DoorKeyKeeperMem(final DoorKeyKeeperDAO dao){
		this.cacheExtends.clear();
		this.cacheExtends.add("NAME");
		this.basicDAO = dao;
	}
	
	@Override
	public DoorKeyKeeper findByName(final String name) {
		final Cache cache = this.getOtherCache(this.cacheExtends.get(0));
		final byte[] value = (byte[]) cache.get(name);
		DoorKeyKeeper result = null;
		if(value != null && value.length>0){
			try {
				result = (DoorKeyKeeper) formatter.reformat(new String(value, Constants.UTF8), this.getEntityClass());
			} catch (UnsupportedEncodingException e) {
				logger.warning(Constants.UNSUPPORTED_EXCEPTION_OCCURED + e.getMessage());
			} catch(FormatException e){
				logger.warning(Constants.FORMAT_EXCEPTION_OCCURED + e.getMessage());
			}
		}else{
			result = ((DoorKeyKeeperDAO)this.basicDAO).findByName(name);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends BasicEntity> Class<T> getEntityClass() {
		return (Class<T>) DoorKeyKeeper.class;
	}

	@Override
	protected Object getKeyForCache(final BasicEntity entity, final String cacheNameExtend) {
		final Object result;
		if(cacheNameExtend.equals(this.cacheExtends.get(0))){
			result = ((DoorKeyKeeper)entity).getName();
		}else{
			result = null;
		}
		return result;
	}

}
