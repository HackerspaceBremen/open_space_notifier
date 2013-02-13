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

import java.util.logging.Logger;

import com.google.appengine.api.datastore.KeyFactory;

import de.hackerspacebremen.data.RegCompleteDB;
import de.hackerspacebremen.data.api.DoorKeyKeeperDAO;
import de.hackerspacebremen.data.api.RegCompleteDAO;
import de.hackerspacebremen.data.cache.RegCompleteMem;
import de.hackerspacebremen.data.entities.DoorKeyKeeper;
import de.hackerspacebremen.data.entities.RegComplete;
import de.hackerspacebremen.domain.api.DoorKeyKeeperService;
import de.hackerspacebremen.email.ActivationEmail;
import de.hackerspacebremen.email.RegistryEmail;
import de.hackerspacebremen.exceptions.AlreadyExistError;
import de.hackerspacebremen.exceptions.DoesntExistError;
import de.liedtke.business.BasicServiceImpl;
import de.liedtke.util.Encryption;
import de.liedtke.validation.ValidationException;

public class DoorKeyKeeperServiceImpl extends BasicServiceImpl implements
		DoorKeyKeeperService {

	/**
	 * static attribute used for logging.
	 */
	private static final Logger logger = Logger
			.getLogger(DoorKeyKeeperServiceImpl.class.getName());

	public DoorKeyKeeperServiceImpl(final DoorKeyKeeperDAO dao) {
		this.basicDAO = dao;
	}

	@Override
	public DoorKeyKeeper createNewKeeper(final String name,
			final String password, final boolean admin, final String fullName,
			final String email) throws AlreadyExistError {
		if (((DoorKeyKeeperDAO) this.basicDAO).findByName(name) != null) {
			throw new AlreadyExistError();
		}
		DoorKeyKeeper keeper = new DoorKeyKeeper();
		keeper.setName(name);
		keeper.setAdmin(admin);
		keeper.setFullName(fullName);
		keeper.setEmail(email);
		if (admin)
			keeper.setActive(true);
		else
			keeper.setActive(false);
		keeper = ((DoorKeyKeeperDAO) this.basicDAO).persist(keeper);
		keeper.setPass(Encryption.encryptBlowfish(password, KeyFactory
				.keyToString(keeper.getKey()).substring(0, 30)));
		final DoorKeyKeeper result = ((DoorKeyKeeperDAO) this.basicDAO)
				.persist(keeper);
		final RegCompleteDAO dao = new RegCompleteMem(new RegCompleteDB());
		final RegComplete complete = new RegComplete();
		complete.setKeeperKey(result.getKey());
		complete.setComplete(false);
		dao.persist(complete);
		dao.close();
		new RegistryEmail(result).send();
		return result;
	}

	@Override
	public DoorKeyKeeper findKeyKeeper(final String name, final String password) {
		DoorKeyKeeper keeper = ((DoorKeyKeeperDAO) this.basicDAO)
				.findByName(name);
		if (keeper == null || !keeper.isActive()) {
			logger.info("Keeper not found or is not active");
			keeper = null;
		} else {
			if (!keeper.getPass().equals(
					Encryption.encryptBlowfish(password, KeyFactory
							.keyToString(keeper.getKey()).substring(0, 30)))) {
				// This try catch is needed to check the first encrypted
				// passwords of the prod system
				try {
					if (!keeper.getPass().equals(
							Encryption.encryptBlowfish(password,
									KeyFactory.keyToString(keeper.getKey())))) {
						keeper = null;
						logger.info("Wrong Password");
					}
				} catch (NullPointerException ne) {
					keeper = null;
					logger.info("Wrong Password through NullPointerException");
				}
			}
		}
		return keeper;
	}

	@Override
	public int amount() {
		return ((DoorKeyKeeperDAO) this.basicDAO).findAll().size();
	}

	@Override
	public void authKeeper(String nickName, boolean admin, boolean active,
			String complete) throws DoesntExistError {
		final DoorKeyKeeper keeper = ((DoorKeyKeeperDAO) this.basicDAO)
				.findByName(nickName);
		if (keeper == null) {
			throw new DoesntExistError();
		} else {
			final boolean changedAdmin = admin != keeper.isAdmin();
			final boolean changedActive = active != keeper.isActive();
			keeper.setAdmin(admin);
			keeper.setActive(active);
			if (complete != null && complete.equals("true")) {
				final RegCompleteDAO dao = new RegCompleteMem(
						new RegCompleteDB());
				final RegComplete regComplete = dao.findByKeeper(keeper
						.getKey());
				if (!regComplete.isComplete()) {
					regComplete.setComplete(true);
					dao.persist(regComplete);
					dao.close();
				}
			}
			if (changedActive || changedAdmin) {
				new ActivationEmail(((DoorKeyKeeperDAO) this.basicDAO)
						.persist(keeper)).send();
			}
		}
	}

	@Override
	public boolean activateKeeper(final String key, final String nick) {
		final DoorKeyKeeper keeper = ((DoorKeyKeeperDAO) this.basicDAO)
				.findByName(nick);
		final boolean failed;
		if (keeper == null) {
			failed = true;
		} else {
			final String registrationKey = KeyFactory.keyToString(keeper
					.getKey());
			if (registrationKey.equals(key)) {
				final RegCompleteDAO dao = new RegCompleteMem(
						new RegCompleteDB());
				final RegComplete regComplete = dao.findByKeeper(keeper
						.getKey());
				if (regComplete == null || regComplete.isComplete()) {
					failed = true;
				} else {
					regComplete.setComplete(true);
					dao.persist(regComplete);
					failed = false;
				}
				dao.close();
			} else {
				failed = true;
			}
		}
		return failed;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hackerspacebremen.domain.api.DoorKeyKeeperService#findKeyKeeper(java
	 * .lang.String)
	 */
	@Override
	public DoorKeyKeeper findKeyKeeper(final String nick)
			throws ValidationException {
		DoorKeyKeeper keeper = ((DoorKeyKeeperDAO) this.basicDAO)
				.findByName(nick);
		if (keeper == null || !keeper.isActive()) {
			logger.info("Keeper not found or is not active");
			keeper = null;
		}
		return keeper;
	}

}