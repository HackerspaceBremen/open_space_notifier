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
package de.hackerspacebremen.email;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.google.inject.Inject;

import de.hackerspacebremen.common.AppConstants;
import de.hackerspacebremen.data.entities.SpaceStatus;
import de.hackerspacebremen.domain.api.PropertyService;
import de.hackerspacebremen.domain.val.ValidationException;
import de.hackerspacebremen.modules.binding.annotations.Proxy;
import de.hackerspacebremen.util.Constants;
import de.hackerspacebremen.util.PropertyHelper;

/**
 * @author Steve
 *
 */
public class StatusEmail{
	
	private static final String PROJECT_ADMIN_EMAIL = "project.admin.email";

	private static final String EMAIL_ADDRESSED_ADDRESS = "project.email.addressed.address";

	/**
     * static attribute used for logging.
     */
    private static final Logger logger = Logger.getLogger(StatusEmail.class.getName());
    
    @Inject
    @Proxy
    private PropertyService propertyService;

	public void send(final SpaceStatus spaceStatus) {
		final String message = de.hackerspacebremen.format.MessageFormat.fitMessageSize(spaceStatus.getMessage().toString());
		final boolean opened = spaceStatus.getStatus().equals(AppConstants.OPEN);
		try {
			final String senderAddress = propertyService.findValueByKey(EMAIL_ADDRESSED_ADDRESS);
			final Session session = Session.getDefaultInstance(new Properties(), null);
			final MimeMessage msg = new MimeMessage(session);
			final String url = "https://"+System.getProperty("com.google.appengine.application.id")+".appspot.com/";
			msg.setFrom(new InternetAddress(propertyService.findValueByKey(PROJECT_ADMIN_EMAIL), "Hackerspace Bremen"));
	        msg.addRecipient(Message.RecipientType.TO,
	                         new InternetAddress(senderAddress, "Hackerspace Bremen Mailingliste"));
	        if(opened){
	        	msg.setSubject(PropertyHelper.getEmailPropertyValue("email.statuschanged.subject.opened"), Constants.UTF8);
	        }else{
	        	msg.setSubject(PropertyHelper.getEmailPropertyValue("email.statuschanged.subject.closed"), Constants.UTF8);
	        }
	        final String status;
	        final String negatedStatus;
	        if(opened){
	        	status = PropertyHelper.getEmailPropertyValue("email.statuschanged.opened");
	        	negatedStatus = PropertyHelper.getEmailPropertyValue("email.statuschanged.negated.opened");
	        }else{
	        	status = PropertyHelper.getEmailPropertyValue("email.statuschanged.closed");
	        	negatedStatus = PropertyHelper.getEmailPropertyValue("email.statuschanged.negated.closed");
	        }
	        final String messageVariable;
	        final String messageVariable2;
	        if(message == null || message.isEmpty()){
	        	messageVariable = "";
	        	messageVariable2 = "";
	        }else{
	        	messageVariable = PropertyHelper.getEmailPropertyValue("email.statuschanged.message");
	        	messageVariable2 = message + "'";
	        }
	        
	        msg.setHeader("Content-Type", "text/plain; charset=utf-8");
	        final MessageFormat form = new MessageFormat(PropertyHelper.getEmailPropertyValue("email.statuschanged.content"));
	        msg.setText(form.format(new String[]{status, messageVariable, messageVariable2, url, negatedStatus}));
	        Transport.send(msg);
		} catch (MessagingException e) {
			logger.warning("MessagingException: " + e.getMessage());
		} catch (UnsupportedEncodingException e) {
			logger.warning("UnsupportedEncodingException: " + e.getMessage());
		} catch(ValidationException e){
			logger.warning("ValidationException: " + e.getMessage());
		}
	}

}
