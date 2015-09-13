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
package de.hackerspacebremen.gcm;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

import de.hackerspacebremen.domain.PropertyService;
import de.hackerspacebremen.domain.validation.ValidationException;

@Component
public final class GcmMessageSender {
	
	private PropertyService propertyService;
	
	@Autowired
	public GcmMessageSender(PropertyService propertyService) {
		this.propertyService = propertyService;
	}
	
	public MulticastResult sendMessage(final String message, final List<String> devices) throws IOException, ValidationException{
		Sender sender = new Sender(propertyService.findValueByKey("project.key"));
		Message gcmMessage = new Message.Builder().addData("payload", message).collapseKey("0").build();
		return sender.send(gcmMessage, devices, 5);
	}
	
	public Result sendMessage(final String message, final String device) throws IOException, ValidationException{
		Sender sender = new Sender(propertyService.findValueByKey("project.key"));
		Message gcmMessage = new Message.Builder().addData("payload", message).collapseKey("0").build();
		return sender.send(gcmMessage, device, 5);
	}
	
}
