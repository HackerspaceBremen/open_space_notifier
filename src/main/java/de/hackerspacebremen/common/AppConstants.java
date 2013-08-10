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
package de.hackerspacebremen.common;


public final class AppConstants {

	private AppConstants(){
		// nothing to implement
	}
	
	public static final String OPEN = "OPEN";
	
	public static final String CLOSED = "CLOSED";
	
	public static final String LEVEL_VIEW = "VIEW";
	
	public static final int MESSAGE_MAX_SIZE = 500;
	
	public static final boolean PROD = !System.getProperty("com.google.appengine.application.id").contains("test");
}
