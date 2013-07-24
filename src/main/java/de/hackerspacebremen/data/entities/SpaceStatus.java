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
import com.google.appengine.api.datastore.Text;

import de.hackerspacebremen.common.AppConstants;
import de.hackerspacebremen.deprecated.data.annotations.Entity;
import de.hackerspacebremen.deprecated.data.annotations.FormatPart;
import de.hackerspacebremen.deprecated.data.entity.BasicEntity;
import flexjson.JSON;

@PersistenceCapable
@Entity(name="SpaceStatus")
public final class SpaceStatus implements BasicEntity{

	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	@FormatPart(key="ST1")
	private Key key;
	
	@Persistent
	@FormatPart(key="ST2", level=AppConstants.LEVEL_VIEW)
	private long time;
	
	@Persistent
	@Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
	@FormatPart(key="ST3", level=AppConstants.LEVEL_VIEW)
	private String status;
	
	/**
	 * opened by {@link DoorKeyKeeper}.
	 */
	@Persistent
	@Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
	@FormatPart(key="ST4")
	private Key openedBy;

	@Persistent
	@Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
	@FormatPart(key="ST5", level=AppConstants.LEVEL_VIEW)
	private Text message;
	
	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	@JSON
	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	@JSON
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@JSON
	public Key getOpenedBy() {
		return openedBy;
	}

	public void setOpenedBy(Key openedBy) {
		this.openedBy = openedBy;
	}

	@JSON
	public Text getMessage() {
		return message;
	}

	public void setMessage(Text message) {
		this.message = message;
	}

}
