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
package de.hackerspacebremen.transformer;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import de.hackerspacebremen.format.LanguageFormat;
import flexjson.transformer.AbstractTransformer;

/**
 * @author Steve Liedtke
 *
 */
public class SpeakingDateTransformer extends AbstractTransformer{
	
	private static final DateTimeZone ZONE = DateTimeZone.forID("Europe/Berlin");

	private static final DateTimeFormatter FMT_CLOCK = DateTimeFormat.forPattern("HH:mm");
	
	private static final DateTimeFormatter FMT_DATE = DateTimeFormat.forPattern("dd.MM");
	
	private static final DateTimeFormatter FMT_DATE_YEAR = DateTimeFormat.forPattern("dd.MM.yyyy");
	
	private String[] FORMAT_ADS = {"heute", "gestern", "um", "Uhr"};
	
	private String[] WEEKDAYS = {"Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag", "Sonntag"};
	
	private final LanguageFormat langFormat;
	
	private final boolean opened;
	
	public SpeakingDateTransformer(final LanguageFormat format, final boolean opened){
		langFormat = format;
		this.opened = opened;
		
		if(langFormat!=null){
			switch(langFormat){
			case GERMAN:
				FORMAT_ADS = new String[] {"heute", "gestern", "um", "Uhr"};
				WEEKDAYS = new String[]{"Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag", "Sonntag"};
				break;
			case ENGLISH:
				FORMAT_ADS = new String[]{"Today", "Yesterday", "at", "o'clock"};
				WEEKDAYS = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
				break;
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see flexjson.transformer.Transformer#transform(java.lang.Object)
	 */
	@Override
	public void transform(final Object longObject) {
		final Long value = (Long) longObject;
		if(langFormat==null){
			getContext().write(value.toString());
		}else{
			final Date date = new Date(value.longValue());
			getContext().writeQuoted(this.createTimeFormat(date));
		}
	}

	public String createTimeFormat(final Date date){
		switch(langFormat){
		case GERMAN:
			if(opened){
				return "Der Space  ist seit " + this.format(date) + " ge\u00f6ffnet";
			}else{
				return "Der Space  ist seit " + this.format(date) + " geschlossen";
			}
		default:
			if(opened){
				return "The space is open since " + this.format(date);
			}else{
				return "The space is closed since " + this.format(date);
			}
		}
	}
	
	public String format(final Date date){
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
	
	private boolean isThisYear(final DateTime dateTime){
		final DateTime now = new DateTime(ZONE);
		return now.getYear() == dateTime.getYear();
	}
	
	private boolean isMax6DaysAgo(final DateTime dateTime){
		final DateTime now = new DateTime(ZONE);
		return now.getDayOfYear()-dateTime.getDayOfYear()<7;
	}
	
	private boolean isToday(final DateTime dateTime){
		final DateTime now = new DateTime(ZONE);
		return now.getDayOfYear() == dateTime.getDayOfYear();
	}
	
	private boolean isYesterday(final DateTime dateTime){
		final DateTime now = new DateTime(ZONE);
		return now.getDayOfYear() == dateTime.getDayOfYear()+1;
	}
}
