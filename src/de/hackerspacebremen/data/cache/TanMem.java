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

import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheException;
import net.sf.jsr107cache.CacheManager;

import com.google.appengine.api.datastore.Key;

import de.hackerspacebremen.data.api.TanDAO;
import de.hackerspacebremen.data.entities.Tan;
import de.liedtke.common.Constants;
import de.liedtke.data.entity.BasicEntity;
import de.liedtke.format.FormatException;
import de.liedtke.format.FormatFactory;
import de.liedtke.format.Formatter;

/**
 * @author Steve Liedtke
 */
public final class TanMem implements TanDAO{

	/**
     * static attribute used for logging.
     */
    private static final Logger logger = Logger.getLogger(TanMem.class.getName());
    
    protected static final Formatter formatter = FormatFactory.getFormatter("json");
	
	/* (non-Javadoc)
	 * @see de.hackerspacebremen.data.api.TanDAO#findByTanString(java.lang.String)
	 */
	@Override
	public Tan findCurrent() {
		final Cache cache = this.getCache();
		final byte[] value = (byte[])cache.get("active_tan");
		Tan result = null;
		try {
			result = (Tan)formatter.reformat(new String(value, Constants.UTF8), Tan.class);
		} catch (UnsupportedEncodingException e) {
			logger.warning(Constants.UNSUPPORTED_EXCEPTION_OCCURED + e.getMessage());
		} catch(FormatException e){
			logger.warning(Constants.FORMAT_EXCEPTION_OCCURED + e.getMessage());
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see de.liedtke.data.api.BasicDAO#close()
	 */
	@Override
	public void close() {
		// nothing to implement here		
	}

	/* (non-Javadoc)
	 * @see de.liedtke.data.api.BasicDAO#delete(de.liedtke.data.entity.BasicEntity)
	 */
	@Override
	public <T extends BasicEntity> void delete(T arg0) {
		// nothing to implement here
	}

	/* (non-Javadoc)
	 * @see de.liedtke.data.api.BasicDAO#findAll()
	 */
	@Override
	public <T extends BasicEntity> List<T> findAll() {
		// nothing to implement here
		return null;
	}

	/* (non-Javadoc)
	 * @see de.liedtke.data.api.BasicDAO#findByKey(com.google.appengine.api.datastore.Key)
	 */
	@Override
	public <T extends BasicEntity> T findByKey(Key arg0) {
		// nothing to implement here
		return null;
	}

	/* (non-Javadoc)
	 * @see de.liedtke.data.api.BasicDAO#persist(de.liedtke.data.entity.BasicEntity)
	 */
	@Override
	public <T extends BasicEntity> T persist(final T entity) {
		final Cache cache = this.getCache();
		try{
			cache.put("active_tan", formatter.format(entity).getBytes(Constants.UTF8));
		} catch(UnsupportedEncodingException e){
			logger.warning(Constants.UNSUPPORTED_EXCEPTION_OCCURED + e.getMessage());
		} catch(FormatException e){
			logger.warning(Constants.FORMAT_EXCEPTION_OCCURED + e.getMessage());
		}
		return entity;
	}
	
	/* (non-Javadoc)
	 * @see de.liedtke.data.api.BasicDAO#persist(de.liedtke.data.entity.BasicEntity)
	 */
	@Override
	public <T extends BasicEntity> T persist2(final T entity) {
		final Cache cache = this.getCache();
		try{
			cache.put("active_tan", formatter.format(entity).getBytes(Constants.UTF8));
		} catch(UnsupportedEncodingException e){
			logger.warning(Constants.UNSUPPORTED_EXCEPTION_OCCURED + e.getMessage());
		} catch(FormatException e){
			logger.warning(Constants.FORMAT_EXCEPTION_OCCURED + e.getMessage());
		}
		return entity;
	}
	
	private Cache getCache(){
		final CacheManager manager = CacheManager.getInstance();
		Cache cache = manager.getCache(this.getClass().getSimpleName() + "_singleton");
		if(cache == null){
			try {
				cache = manager.getCacheFactory().createCache(Collections.emptyMap());
				manager.registerCache(this.getClass().getSimpleName() + "_singleton", cache);
			} catch (CacheException e) {
				logger.warning("CacheException occured: " + e.getMessage());
			}
		}
		return cache;
	}
}