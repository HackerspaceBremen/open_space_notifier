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
package de.hackerspacebremen.format;

import org.apache.commons.lang.StringEscapeUtils;

import com.google.appengine.api.datastore.Text;

import de.hackerspacebremen.common.AppConstants;
import de.hackerspacebremen.data.entities.SpaceStatus;

/**
 * @author Steve
 *
 */
public final class MessageFormat {
	
	public static void fitMessageSize(final SpaceStatus status){
		if(status.getMessage() != null && status.getMessage().getValue().length()>AppConstants.MESSAGE_MAX_SIZE){
			status.setMessage(new Text(status.getMessage().getValue().substring(0, AppConstants.MESSAGE_MAX_SIZE) + " [..]"));
		}
	}
	
	public static void fitSmallMessageSize(final SpaceStatus status){
		if(status.getMessage() != null && status.getMessage().getValue().length()>AppConstants.MESSAGE_SMALL_MAX_SIZE){
			status.setMessage(new Text(status.getMessage().getValue().substring(0, AppConstants.MESSAGE_SMALL_MAX_SIZE) + " [..]"));
		}
	}
	
	public static String fitMessageSize(final String message){
		String result = message;
		if(message!=null && message.length()>AppConstants.MESSAGE_MAX_SIZE){
			result = message.substring(0, AppConstants.MESSAGE_MAX_SIZE) + " [..]";
		}
		return result;
	}
	
	public static void htmlEncode(final SpaceStatus status){
		if(status.getMessage() != null && status.getMessage().getValue()!=null){
			status.setMessage(new Text(StringEscapeUtils.escapeHtml(status.getMessage().getValue())));
		}
	}
}
