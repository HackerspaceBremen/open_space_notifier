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
package de.hackerspacebremen;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import de.hackerspacebremen.util.ErrorMessages;


@Service
public class MyErrorMessages implements ErrorMessages{

	private final Map<Integer,String> errorMessages = new HashMap<Integer, String>();
	
	public MyErrorMessages(){
		errorMessages.put(Integer.valueOf(1), "Either no Keykeeper with the given name exists " +
				"or the keeper has no authentication to open or close the space");
		errorMessages.put(Integer.valueOf(2), "A Keykeeper with this name already exists");
		errorMessages.put(Integer.valueOf(3), "The space was already opened!");
		errorMessages.put(Integer.valueOf(4), "The space was already closed!");
		errorMessages.put(Integer.valueOf(5), "You have no admin priviliges");
		errorMessages.put(Integer.valueOf(6), "No keeper found under the given nick");
		errorMessages.put(Integer.valueOf(7), "The param deviceid is empty");
		errorMessages.put(Integer.valueOf(8), "The param registrationid is empty");
		errorMessages.put(Integer.valueOf(9), "The authToken is empty");
		errorMessages.put(Integer.valueOf(10), "The message is empty");
		errorMessages.put(Integer.valueOf(11), "The param name is empty");
		errorMessages.put(Integer.valueOf(12), "The param pass is empty");
		errorMessages.put(Integer.valueOf(13), "The param fullName is empty");
		errorMessages.put(Integer.valueOf(14), "The param email is empty");
		errorMessages.put(Integer.valueOf(15), "The registration key is empty");
		errorMessages.put(Integer.valueOf(16), "The keeper was not given");
		errorMessages.put(Integer.valueOf(17), "There is no status yet");
		errorMessages.put(Integer.valueOf(18), "Either no Keykeeper with the given name exists " +
				"or the keeper has no authentication to change the message");
		errorMessages.put(Integer.valueOf(19), "The status changed while you entered the message");
		errorMessages.put(Integer.valueOf(20), "The given status is null");
		errorMessages.put(Integer.valueOf(21), "The param hash is empty");
		errorMessages.put(Integer.valueOf(22), "The given id doesn't exist in the database!");
		errorMessages.put(Integer.valueOf(23), "The given key should at least have one character!");
		errorMessages.put(Integer.valueOf(24), "To enable APNS a valid password is needed");
		errorMessages.put(Integer.valueOf(25), "To enable APNS you need to add a apns certificate(.p12)");
		errorMessages.put(Integer.valueOf(26), "The deviceId can't be accepted when null or empty");
		errorMessages.put(Integer.valueOf(27), "The token can't be accepted when null or empty");
		errorMessages.put(Integer.valueOf(28), "To unregister a not empty deviceId is needed");
		errorMessages.put(Integer.valueOf(29), "The shortStatus can't be null or empty"); 
		errorMessages.put(Integer.valueOf(55), "The attempt to authenticate was to high. " +
				"You are blocked for a maximum of 15 minutes.");
		errorMessages.put(Integer.valueOf(66), "An validation exception occured! Please contact the admin");
		errorMessages.put(Integer.valueOf(77), "An format exception occured! Please contact the admin");
		errorMessages.put(Integer.valueOf(88), "An json exception occured!  Please contact the admin");
		errorMessages.put(Integer.valueOf(99), "Keykeeper who opened the space doesn't exist anymore");
	}
	
	@Override
	public String getMessage(final int code) {
		String result = errorMessages.get(Integer.valueOf(code));
		if(result == null){
			result = "Errormessage couldn't be found! Please ask the admin";
		}
		return result;
	}
}