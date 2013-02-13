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
package de.hackerspacebremen;

import de.hackerspacebremen.data.GCMAuthDB;
import de.hackerspacebremen.data.GCMDataDB;
import de.hackerspacebremen.data.DoorKeyKeeperDB;
import de.hackerspacebremen.data.SpaceStatusDB;
import de.hackerspacebremen.data.cache.GCMAuthMem;
import de.hackerspacebremen.data.cache.GCMDataMem;
import de.hackerspacebremen.data.cache.DoorKeyKeeperMem;
import de.hackerspacebremen.data.cache.SpaceStatusMem;
import de.hackerspacebremen.data.cache.TanMem;
import de.hackerspacebremen.domain.GCMAuthServiceImpl;
import de.hackerspacebremen.domain.GCMDataServiceImpl;
import de.hackerspacebremen.domain.DoorKeyKeeperServiceImpl;
import de.hackerspacebremen.domain.SpaceStatusServiceImpl;
import de.hackerspacebremen.domain.TanServiceImpl;
import de.hackerspacebremen.domain.api.GCMAuthService;
import de.hackerspacebremen.domain.api.GCMDataService;
import de.hackerspacebremen.domain.api.DoorKeyKeeperService;
import de.hackerspacebremen.domain.api.SpaceStatusService;
import de.hackerspacebremen.domain.api.TanService;
import de.hackerspacebremen.domain.val.GCMAuthServiceValidation;
import de.hackerspacebremen.domain.val.GCMDataServiceValidation;
import de.hackerspacebremen.domain.val.DoorKeyKeeperServiceValidation;
import de.hackerspacebremen.domain.val.SpaceStatusServiceValidation;
import de.hackerspacebremen.domain.val.TanServiceValidation;

public final class Factory {

	private Factory(){
		// nothing to implement
	}
	
	public static SpaceStatusService createSpaceStatusService(){
		return new SpaceStatusServiceValidation(
				new SpaceStatusServiceImpl(
						new SpaceStatusMem(
								new SpaceStatusDB())));
	}
	
	public static DoorKeyKeeperService createDoorKeyKeeperService(){
		return new DoorKeyKeeperServiceValidation(
				new DoorKeyKeeperServiceImpl(
					new DoorKeyKeeperMem(
							new DoorKeyKeeperDB())));
	}
	
	public static GCMDataService createGCMDataService(){
		return new GCMDataServiceValidation(
				new GCMDataServiceImpl(
					new GCMDataMem(
						new GCMDataDB())));
	}
	
	public static GCMAuthService createGCMAuthService(){
		return new GCMAuthServiceValidation(
				new GCMAuthServiceImpl(
					new GCMAuthMem(
						new GCMAuthDB())));
	}
	
	public static TanService createTanService(){
		return new TanServiceValidation(new TanServiceImpl(new TanMem()));
	}
}
