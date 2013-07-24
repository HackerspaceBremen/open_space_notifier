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
package de.hackerspacebremen.deprecated.format;

import de.hackerspacebremen.deprecated.data.entity.BasicEntity;

// TODO implement XMLFormatter
public class XMLFormatter implements Formatter {

	@Override
	public <T extends BasicEntity> String format(final T entity,
			final String... levels) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends BasicEntity> T reformat(String object,
			final Class<T> entityClass) {
		// TODO Auto-generated method stub
		return null;
	}

}
