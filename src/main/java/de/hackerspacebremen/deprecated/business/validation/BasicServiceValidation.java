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
package de.hackerspacebremen.deprecated.business.validation;

import com.google.appengine.api.datastore.Key;

import de.hackerspacebremen.deprecated.business.api.BasicService;
import de.hackerspacebremen.deprecated.data.entity.BasicEntity;
import de.hackerspacebremen.deprecated.validation.Validation;
import de.hackerspacebremen.deprecated.validation.ValidationException;

public class BasicServiceValidation extends Validation implements BasicService {

	protected BasicService basicService;

	@Override
	public <T extends BasicEntity> T findById(final Key key)
			throws ValidationException {
		this.validateNull(key, 91);
		return this.basicService.findById(key);
	}

	@Override
	public void close() {
		this.basicService.close();
	}

}
