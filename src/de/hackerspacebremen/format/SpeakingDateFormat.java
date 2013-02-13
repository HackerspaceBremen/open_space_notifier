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

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

@Deprecated
public final class SpeakingDateFormat {

	private static final DateTimeZone ZONE = DateTimeZone.forID("Europe/Berlin");
	
	private static final DateTimeFormatter FMT_CLOCK = DateTimeFormat.forPattern("HH:mm");
	
	private static final DateTimeFormatter FMT_DATE = DateTimeFormat.forPattern("dd.MM");
	
	private static final DateTimeFormatter FMT_DATE_YEAR = DateTimeFormat.forPattern("dd.MM.yyyy");
	
	private static String[] FORMAT_ADS = {"Heute", "Gestern", "um", "Uhr"};
	
	private static String[] WEEKDAYS = {"Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag", "Sonntag"};
	
	private SpeakingDateFormat(){
		// nothing to implement
	}
	
	public static void setFormat(final String format){
		if(format!=null && format.equals("de")){
			final String[] deFormat = {"Heute", "Gestern", "um", "Uhr"};
			final String[] deWeekdays = {"Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag", "Sonntag"};
			FORMAT_ADS = deFormat;
			WEEKDAYS = deWeekdays;
		}else {
			final String[] enFormat ={"Today", "Yesterday", "at", "o'clock"}; 
			final String[] enWeekdays = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
			FORMAT_ADS = enFormat;
			WEEKDAYS = enWeekdays;
		}
	}
	
	public static String format(final Date date){
		final DateTime dateTime = new DateTime(date, ZONE);
		final String result;
		if(isToday(dateTime)){
			 result = FORMAT_ADS[0] + ", " + FMT_CLOCK.print(dateTime) + " " + FORMAT_ADS[3];
		}else if(isYesterday(dateTime)){
			result = FORMAT_ADS[1] + ", " + FMT_CLOCK.print(dateTime) + " " + FORMAT_ADS[3];
		}else if(isMax6DaysAgo(dateTime)){
			result = WEEKDAYS[dateTime.getDayOfWeek()-1] + ", " + FMT_CLOCK.print(dateTime) + " " + FORMAT_ADS[3];
		}else if(isThisYear(dateTime)){
			result = FMT_DATE.print(dateTime) + " " + FORMAT_ADS[2] + " " + FMT_CLOCK.print(dateTime) + " " + FORMAT_ADS[3];
		}else{
			result = FMT_DATE_YEAR.print(dateTime) + " " + FORMAT_ADS[2] + " " + FMT_CLOCK.print(dateTime) + " " + FORMAT_ADS[3];
		}
		
		return result;
	}
	
	private static boolean isThisYear(final DateTime dateTime){
		final DateTime now = new DateTime(ZONE);
		return now.getYear() == dateTime.getYear();
	}
	
	private static boolean isMax6DaysAgo(final DateTime dateTime){
		final DateTime now = new DateTime(ZONE);
		return now.getDayOfYear()-dateTime.getDayOfYear()<7;
	}
	
	private static boolean isToday(final DateTime dateTime){
		final DateTime now = new DateTime(ZONE);
		return now.getDayOfYear() == dateTime.getDayOfYear();
	}
	
	private static boolean isYesterday(final DateTime dateTime){
		final DateTime now = new DateTime(ZONE);
		return now.getDayOfYear() == dateTime.getDayOfYear()+1;
	}
}
