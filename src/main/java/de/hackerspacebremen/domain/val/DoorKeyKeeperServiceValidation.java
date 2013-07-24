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
package de.hackerspacebremen.domain.val;

import de.hackerspacebremen.data.entities.DoorKeyKeeper;
import de.hackerspacebremen.deprecated.business.validation.BasicServiceValidation;
import de.hackerspacebremen.deprecated.validation.ValidationException;
import de.hackerspacebremen.domain.api.DoorKeyKeeperService;
import de.hackerspacebremen.exceptions.AlreadyExistError;
import de.hackerspacebremen.exceptions.DoesntExistError;

public class DoorKeyKeeperServiceValidation extends BasicServiceValidation 
											implements DoorKeyKeeperService{

	public DoorKeyKeeperServiceValidation(final DoorKeyKeeperService service){
		this.basicService = service;
	}
	
	@Override
	public DoorKeyKeeper createNewKeeper(final String name, final String password,
			final boolean admin, final String fullName, final String email)
			throws AlreadyExistError, ValidationException {
		this.validateIfEmpty(name, 11);
		this.validateIfEmpty(password, 12);
		this.validateIfEmpty(fullName, 13);
		this.validateIfEmpty(email, 14);
		// TODO do more validations
		return ((DoorKeyKeeperService)this.basicService).createNewKeeper(name, password, admin, fullName, email);
	}

	@Override
	public DoorKeyKeeper findKeyKeeper(final String name, final String password) 
		throws ValidationException{
		this.validateIfEmpty(name, 11);
		this.validateIfEmpty(password, 12);
		return ((DoorKeyKeeperService)this.basicService).findKeyKeeper(name, password);
	}

	@Override
	public boolean activateKeeper(final String key, final String nick) 
			throws ValidationException {
		this.validateIfEmpty(key, 15);
		this.validateIfEmpty(nick, 11);
		return ((DoorKeyKeeperService)this.basicService).activateKeeper(key, nick);
	}

	@Override
	public void authKeeper(final String nickName, final boolean admin, final boolean active,
			final String complete) throws DoesntExistError, ValidationException {
		this.validateIfEmpty(nickName, 11);
		((DoorKeyKeeperService)this.basicService).authKeeper(nickName, admin, active, complete);
	}

	@Override
	public int amount() throws ValidationException{
		return ((DoorKeyKeeperService)this.basicService).amount();
	}

	/* (non-Javadoc)
	 * @see de.hackerspacebremen.domain.api.DoorKeyKeeperService#findKeyKeeper(java.lang.String)
	 */
	@Override
	public DoorKeyKeeper findKeyKeeper(final String nick) throws ValidationException {
		this.validateIfEmpty(nick, 11);
		return ((DoorKeyKeeperService)this.basicService).findKeyKeeper(nick);
	}

}
