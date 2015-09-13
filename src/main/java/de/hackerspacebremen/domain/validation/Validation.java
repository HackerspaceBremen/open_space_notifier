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
package de.hackerspacebremen.domain.validation;

public class Validation {

	public void validateNull(final Object value, final int errorCode)
			throws ValidationException {
		if (value == null)
			throw new ValidationException(errorCode);
	}

	public void validateIfEmpty(final String value, final int errorCode)
			throws ValidationException {
		if (value == null || value.isEmpty())
			throw new ValidationException(errorCode);
	}

	public void close() {
		// do nothing
	}
}
