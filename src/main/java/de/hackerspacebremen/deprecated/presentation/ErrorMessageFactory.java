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
package de.hackerspacebremen.deprecated.presentation;

class ErrorMessageFactory {

	private static ErrorMessages errorMessages = null;

	public static <T extends ErrorMessages> ErrorMessages getSingleton(
			final Class<T> errClass) throws InitializationException {
		if (errorMessages == null) {
			try {
				errorMessages = errClass.newInstance();
			} catch (InstantiationException e) {
				throw new InitializationException();
			} catch (IllegalAccessException e) {
				throw new InitializationException();
			}
		}
		return errorMessages;
	}
}
