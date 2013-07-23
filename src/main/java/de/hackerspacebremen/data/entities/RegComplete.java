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

import de.liedtke.data.annotations.Entity;
import de.liedtke.data.annotations.FormatPart;
import de.liedtke.data.entity.BasicEntity;

@PersistenceCapable
@Entity(name="DoorKeyKeeper")
public final class RegComplete implements BasicEntity{

	
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	@FormatPart(key="RC1")
	private Key key;
	
	@Persistent
	@FormatPart(key="RC2")
	private Key keeperKey;

	@Persistent
	@Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
	@FormatPart(key="RC3")
	private boolean complete;
	
	
	@Override
	public Key getKey() {
		return key;
	}

	@Override
	public void setKey(Key key) {
		this.key = key;
	}

	public Key getKeeperKey() {
		return keeperKey;
	}

	public void setKeeperKey(Key keeperKey) {
		this.keeperKey = keeperKey;
	}

	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}
}
