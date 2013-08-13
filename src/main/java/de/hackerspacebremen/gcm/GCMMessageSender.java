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

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.google.inject.Inject;

import de.hackerspacebremen.domain.api.PropertyService;
import de.hackerspacebremen.domain.val.ValidationException;
import de.hackerspacebremen.modules.binding.annotations.Proxy;

public final class GCMMessageSender {
	
	@Inject
	@Proxy
	private PropertyService propertyService;
	
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
	
//	@Deprecated
//	public static int sendMessage(final String auth_token, final String registrationId,
//			final String message) throws IOException{
//		StringBuilder postDataBuilder = new StringBuilder();
//		postDataBuilder.append("registration_id=")
//				.append(registrationId);
//		postDataBuilder.append("&collapse_key=0");
//		postDataBuilder.append("&data.payload=")
//				.append(URLEncoder.encode(message, "UTF-8"));
//
//		byte[] postData = postDataBuilder.toString().getBytes("UTF-8");
//
//		// Hit the dm URL.
//
//		URL url = new URL("https://android.clients.google.com/c2dm/send");
//		HttpURLConnection conn = (HttpURLConnection) url.openConnection(); 
//		conn.setDoOutput(true);
//		conn.setUseCaches(false);
//		conn.setRequestMethod("POST");
//		conn.setRequestProperty("Content-Type",
//				"application/x-www-form-urlencoded;charset=UTF-8");
//		conn.setRequestProperty("Content-Length",
//				Integer.toString(postData.length));
//		conn.setRequestProperty("Authorization", "GoogleLogin auth="
//				+ auth_token);
//
//		OutputStream out = conn.getOutputStream();
//		out.write(postData);
//		out.close();
//
//		int responseCode = conn.getResponseCode();
//		return responseCode;
//	}
}
