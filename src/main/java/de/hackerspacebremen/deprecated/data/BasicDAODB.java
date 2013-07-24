/*
 * ljal - Java App Engine library
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
package de.hackerspacebremen.deprecated.data;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.JDOHelper;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.ObjectState;
import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;

import com.google.appengine.api.datastore.Key;

import de.hackerspacebremen.deprecated.data.api.BasicDAO;
import de.hackerspacebremen.deprecated.data.entity.BasicEntity;
import de.hackerspacebremen.deprecated.util.PMF;

public abstract class BasicDAODB implements BasicDAO {

	protected PersistenceManager pManager = de.hackerspacebremen.deprecated.util.PMF.get()
			.getPersistenceManager();

	/**
	 * static attribute used for logging.
	 */
	private static final Logger logger = Logger.getLogger(BasicDAODB.class
			.getName());

	/**
	 * Abstract method so every child tells the class it representates.
	 * 
	 * @return {@link Class}
	 */
	public abstract <T extends BasicEntity> Class<T> getEntityClass();

	@Override
	public <T extends BasicEntity> void delete(T entity) {
		Transaction tx = this.pManager.currentTransaction();
		tx.begin();
		final ObjectState state = JDOHelper.getObjectState(entity);
		logger.info("entity state is " + state.toString());
		if (state.equals(ObjectState.TRANSIENT)
				|| state.equals(ObjectState.HOLLOW_PERSISTENT_NONTRANSACTIONAL)) {
			this.pManager.deletePersistent(this.pManager.getObjectById(
					entity.getClass(), entity.getKey()));
		} else {
			this.pManager.deletePersistent(entity);
		}
		tx.commit();
		this.pManager.flush();
	}

	@Override
	public <T extends BasicEntity> T persist(T entity) {
		final PersistenceManager pManager = PMF.get().getPersistenceManager();
		T result = null;
		try {
			result = pManager.makePersistent(entity);
			pManager.flush();
		} finally {
			pManager.close();
		}
		return result;
	}
	
	@Override
	public <T extends BasicEntity> T persist2(T entity) {
		T result = null;
		result = pManager.makePersistent(entity);
		pManager.flush();
		
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends BasicEntity> T findByKey(final Key key) {
		T result;
		try {
			result = (T) this.pManager
					.getObjectById(this.getEntityClass(), key);
		} catch (JDOObjectNotFoundException e) {
			result = null;
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends BasicEntity> List<T> findAll() {
		List<T> result = (List<T>) this.pManager
				.newQuery(this.getEntityClass()).execute();
		if (result == null) {
			result = new ArrayList<T>();
		}
		return result;
	}

	@Override
	public void close() {
		this.pManager.close();
	}
}
