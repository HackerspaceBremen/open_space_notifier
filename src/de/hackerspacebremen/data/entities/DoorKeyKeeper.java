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
package de.hackerspacebremen.data.entities;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

import de.hackerspacebremen.common.AppConstants;
import de.liedtke.data.annotations.Entity;
import de.liedtke.data.annotations.FormatPart;
import de.liedtke.data.entity.BasicEntity;

@PersistenceCapable
@Entity(name="DoorKeyKeeper")
public final class DoorKeyKeeper implements BasicEntity{

	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	@FormatPart(key="DKK1")
	private Key key;
	
	@Persistent
	@FormatPart(key="DKK2", level=AppConstants.LEVEL_VIEW)
	private String name;
	
	@Persistent
	@Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
	@FormatPart(key="DKK3")
	private String pass;
	
	@Persistent
	@Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
	@FormatPart(key="DKK4")
	private boolean active;
	
	@Persistent
	@Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
	@FormatPart(key="DKK5")
	private boolean admin;
	
	@Persistent
	@Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
	@FormatPart(key="DKK6")
	private String fullName;
	
	@Persistent
	@FormatPart(key="DKK7")
	private String email;
	
	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
