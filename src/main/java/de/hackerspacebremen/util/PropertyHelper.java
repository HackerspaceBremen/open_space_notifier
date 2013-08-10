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
package de.hackerspacebremen.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * @author Steve
 *
 */
public final class PropertyHelper {

	/**
     * static attribute used for logging.
     */
    private static final Logger logger = Logger.getLogger(PropertyHelper.class.getName());
    
    public static String getEmailPropertyValue(final String key){
		Properties properties = new Properties();
		String result = null;
		final URL resource = PropertyHelper.class.getClassLoader().getResource("email_text.properties");
		try {
			properties.load(new InputStreamReader(resource.openStream(), Constants.UTF8));
		    result = properties.getProperty(key);
		} catch (IOException e) {
			logger.warning("property with key '" + key + "' couldn't be found");
		}
		
		return result;
	}
}
