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
package de.hackerspacebremen.domain.api;

import de.hackerspacebremen.data.entities.DoorKeyKeeper;
import de.hackerspacebremen.exceptions.AlreadyExistError;
import de.hackerspacebremen.exceptions.DoesntExistError;
import de.liedtke.business.api.BasicService;
import de.liedtke.validation.ValidationException;

public interface DoorKeyKeeperService extends BasicService{

	DoorKeyKeeper createNewKeeper(String name, String password, boolean admin,
			String fullName, String email)
					throws AlreadyExistError, ValidationException;
	
	DoorKeyKeeper findKeyKeeper(String name, String password)
			throws ValidationException;
	
	DoorKeyKeeper findKeyKeeper(String nick)
			throws ValidationException;
	
	boolean activateKeeper(String key, String nick)
			throws ValidationException;
	
	void authKeeper(String nickName, boolean admin, boolean active, String complete) 
			throws DoesntExistError,ValidationException;
	
	int amount()
			throws ValidationException;
}
