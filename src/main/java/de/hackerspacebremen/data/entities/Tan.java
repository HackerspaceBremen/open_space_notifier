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

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.google.appengine.api.datastore.Key;

import de.hackerspacebremen.common.AppConstants;
import de.liedtke.data.annotations.Entity;
import de.liedtke.data.annotations.FormatPart;
import de.liedtke.data.entity.BasicEntity;

/**
 * This entity is currently not saved in the database.
 * 
 * @author Steve Liedtke
 */
@PersistenceCapable
@Entity(name="Tan")
public final class Tan implements BasicEntity{

//	@PrimaryKey
//	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
//	@FormatPart(key="T1")
//	private Key key;
	
	@Persistent
	@FormatPart(key="T2", level=AppConstants.LEVEL_VIEW)
	private String tanString;
	
//	@Persistent
//	@Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
//	@FormatPart(key="T3")
//	private Boolean active;
	
	public Key getKey() {
		return null;
	}

	public void setKey(Key key) {
		// do nothing
	}

	public String getTanString() {
		return tanString;
	}

	public void setTanString(String tanString) {
		this.tanString = tanString;
	}

//	public Boolean getActive() {
//		return active;
//	}
//
//	public void setActive(Boolean active) {
//		this.active = active;
//	}
}