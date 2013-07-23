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
package de.hackerspacebremen.commands.helper;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import de.hackerspacebremen.common.AppConstants;
import de.hackerspacebremen.data.entities.SpaceStatus;
import de.hackerspacebremen.format.SpeakingDateFormat;

@Deprecated
public class StatusTimeFormat {

	private final String resultWithFormat;
	private final SpaceStatus status;
	
	public StatusTimeFormat(final String resultWithFormat, final SpaceStatus status){
		this.resultWithFormat = resultWithFormat;
		this.status = status;
	}
	
	public String createOtherFormat(final String parameter) throws JSONException {
		final String result;
		if(parameter == null){
			result = resultWithFormat;
		}else {
			final String time = this.createTimeFormat(parameter);
			if(time == null){
				result = resultWithFormat;
			}else{
				final JSONObject object = new JSONObject(resultWithFormat);
				object.put("ST2", time);
				result = object.toString();
			}
		}

		return result;
	}
	
	public String createTimeFormat(final String parameter){
		final String result;
		SpeakingDateFormat.setFormat(parameter); 
		final Date date = new Date(status.getTime());
		if(parameter.equals("de")){
			if(status.getStatus().equals(AppConstants.OPEN)){
				result = "Der Space  ist seit " + SpeakingDateFormat.format(date) + " ge\u00f6ffnet";
			}else{
				result = "Der Space  ist seit " + SpeakingDateFormat.format(date) + " geschlossen";
			}
		}else if(parameter.equals("en")){
			if(status.getStatus().equals(AppConstants.OPEN)){
				result = "The space is open since " + SpeakingDateFormat.format(date);
			}else{
				result = "The space is closed since " + SpeakingDateFormat.format(date);
			}
		}else{
			result = "" + status.getTime();
		}
		return result;
	}
}
