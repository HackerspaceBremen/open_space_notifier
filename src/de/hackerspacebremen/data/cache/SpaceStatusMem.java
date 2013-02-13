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
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheException;
import net.sf.jsr107cache.CacheManager;

import com.google.appengine.api.datastore.Key;

import de.hackerspacebremen.data.api.SpaceStatusDAO;
import de.hackerspacebremen.data.entities.SpaceStatus;
import de.liedtke.common.Constants;
import de.liedtke.data.cache.BasicDAOMem;
import de.liedtke.data.entity.BasicEntity;
import de.liedtke.format.FormatException;

@Service
public class SpaceStatusMem extends BasicDAOMem implements SpaceStatusDAO{

	/**
     * static attribute used for logging.
     */
    private static final Logger logger = Logger.getLogger(SpaceStatusMem.class.getName());
	
    @Resource(name="spaceStatusDB")
    private SpaceStatusDAO spaceStatusDAO;
    
    // needed for di
    public SpaceStatusMem(){
    	this.basicDAO = spaceStatusDAO;
    }
    
	public SpaceStatusMem(final SpaceStatusDAO dao){
		this.basicDAO = dao;
		spaceStatusDAO = (SpaceStatusDAO) basicDAO;
	}
	
	@Override
	public SpaceStatus findCurrentStatus() {
		final Cache cache = this.getCache();
		final byte[] value = (byte[])cache.get("current");
		SpaceStatus status = null;
		try{
			if(value==null){
//				status = ((SpaceStatusDAO)this.basicDAO).findCurrentStatus();
				status = spaceStatusDAO.findCurrentStatus();
				if(status!=null)
					cache.put("current", formatter.format(status).getBytes(Constants.UTF8));
			}else{
				status = (SpaceStatus)formatter.reformat(new String(value, Constants.UTF8), this.getEntityClass());
			}
		} catch (UnsupportedEncodingException e) {
			logger.warning(Constants.UNSUPPORTED_EXCEPTION_OCCURED + e.getMessage());
		} catch(FormatException e){
			logger.warning(Constants.FORMAT_EXCEPTION_OCCURED + e.getMessage());
		}
		return status;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends BasicEntity> Class<T> getEntityClass() {
		return (Class<T>) SpaceStatus.class;
	}

	@Override
	protected Object getKeyForCache(final BasicEntity entity, final String arg1) {
		return null;
	}

	@Override
	public <T extends BasicEntity> T findByKey(final Key key) {
//		return ((SpaceStatusDAO)this.basicDAO).findByKey(key);
		return spaceStatusDAO.findByKey(key);
	}
	
	@Override
	public <T extends BasicEntity> List<T> findAll() {
//		return ((SpaceStatusDAO)this.basicDAO).findAll();
		return spaceStatusDAO.findAll();
	}
	
	public <T extends BasicEntity> T persist(final T entity) {
//		final T persistedEntity = this.basicDAO.persist(entity);
		final T persistedEntity = spaceStatusDAO.persist(entity);
		final Cache cache = this.getCache();
		try{
			cache.put("current", formatter.format(persistedEntity).getBytes(Constants.UTF8));
		} catch(UnsupportedEncodingException e){
			logger.warning(Constants.UNSUPPORTED_EXCEPTION_OCCURED + e.getMessage());
		} catch(FormatException e){
			logger.warning(Constants.FORMAT_EXCEPTION_OCCURED + e.getMessage());
		}
		return persistedEntity;
	};
	
	public <T extends BasicEntity> void delete(final T entity) {
		final Cache cache = this.getCache();
		final byte[] value = (byte[])cache.get("current");
		try{
			final SpaceStatus status = (SpaceStatus)formatter.reformat(new String(value, Constants.UTF8), this.getEntityClass());
			final boolean isCurrent =status.getKey().equals(entity.getKey()); 
//			((SpaceStatusDAO)this.basicDAO).delete(entity);
			spaceStatusDAO.delete(entity);
			if(isCurrent){
				cache.put("current", formatter.format(((SpaceStatusDAO)this.basicDAO).findCurrentStatus()).getBytes(Constants.UTF8));
			}
		
		} catch (UnsupportedEncodingException e) {
			logger.warning(Constants.UNSUPPORTED_EXCEPTION_OCCURED + e.getMessage());
		} catch(FormatException e){
			logger.warning(Constants.FORMAT_EXCEPTION_OCCURED + e.getMessage());
		}
	};
	
	private Cache getCache(){
		final CacheManager manager = CacheManager.getInstance();
		Cache cache = manager.getCache(this.getEntityClass().getSimpleName());
		if(cache == null){
			try {
				cache = manager.getCacheFactory().createCache(Collections.emptyMap());
				manager.registerCache(this.getEntityClass().getSimpleName(), cache);
			} catch (CacheException e) {
				logger.warning("CacheException occured: " + e.getMessage());
			}
		}
		return cache;
	}
}
