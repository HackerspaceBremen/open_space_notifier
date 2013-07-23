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
package de.hackerspacebremen.util;

public class Constants {

	private Constants() {
		// nothign to implement.
	}

	/**
	 * constant for 'JSONException occured: '.
	 */
	public static final String JSON_EXCEPTION_OCCURED = "JSONException occured: ";

	/**
	 * constant for 'UnsupportedEncodingException occured: '.
	 */
	public static final String UNSUPPORTED_EXCEPTION_OCCURED = "UnsupportedEncodingException occured: ";

	/**
	 * constant for 'FormatException occured: '.
	 */
	public static final String FORMAT_EXCEPTION_OCCURED = "FormatException occured: ";

	/**
	 * constant for 'UTF-8'.
	 */
	public static final String UTF8 = "UTF-8";

	/**
	 * constant for 'ALL'.
	 */
	public static final String ALL = "ALL";

	/**
	 * constant for CACHE_VALUE_SIZE.
	 */
	public static final int CACHE_VALUE_SIZE = 900000;
}
