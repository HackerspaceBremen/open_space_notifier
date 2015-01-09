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
import de.hackerspacebremen.valueobjects.EmailProperties;

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
		final String message;
		if(spaceStatus.getMessage()==null){
			message = "";
		}else{
			message = de.hackerspacebremen.format.MessageFormat.fitMessageSize(spaceStatus.getMessage().getValue());
		}
		final boolean opened = spaceStatus.getStatus().equals(AppConstants.OPEN);
		final EmailProperties emailProperties = propertyService.fetchEmailProperties();
		try {
			final String senderAddress = propertyService.findValueByKey(EMAIL_ADDRESSED_ADDRESS);
			final Session session = Session.getDefaultInstance(new Properties(), null);
			final MimeMessage msg = new MimeMessage(session);
			final String url = "https://"+System.getProperty("com.google.appengine.application.id")+".appspot.com/";
			msg.setFrom(new InternetAddress(propertyService.findValueByKey(PROJECT_ADMIN_EMAIL), emailProperties.getSenderName()));
	        msg.addRecipient(Message.RecipientType.TO,
	                         new InternetAddress(senderAddress, emailProperties.getReceiverName()));
	        final String subject, status, negatedStatus;
	        if(opened){
	        	subject = emailProperties.getSubjectOpened();
	        	status = emailProperties.getOpened();
	        	negatedStatus = emailProperties.getNegatedOpened();
	        }else{
	        	subject = emailProperties.getSubjectClosed();
	        	status = emailProperties.getClosed();
	        	negatedStatus = emailProperties.getNegatedClosed();
	        }
	        msg.setSubject(emailProperties.getSubjectTag() + " " + subject, Constants.UTF8);
	        msg.setHeader("Content-Type", "text/plain; charset=utf-8");
	        msg.setText(emailProperties.getContent(status, message, url, negatedStatus));
	        
	        Transport.send(msg);
		} catch (MessagingException | UnsupportedEncodingException | ValidationException e) {
			logger.warning(e.getClass().getSimpleName() +": " + e.getMessage());
		} 
	}
}