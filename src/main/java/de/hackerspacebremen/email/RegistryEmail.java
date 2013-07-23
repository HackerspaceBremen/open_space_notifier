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

import com.google.appengine.api.datastore.KeyFactory;

import de.hackerspacebremen.common.AppConstants;
import de.hackerspacebremen.data.entities.DoorKeyKeeper;
import de.hackerspacebremen.util.Constants;
import de.hackerspacebremen.util.PropertyHelper;

/**
 * @author Steve
 * 
 */
public class RegistryEmail implements SendEmail {

	/**
     * static attribute used for logging.
     */
    private static final Logger logger = Logger.getLogger(RegistryEmail.class.getName());
	
	private final DoorKeyKeeper keeper;

	public RegistryEmail(final DoorKeyKeeper keeper) {
		this.keeper = keeper;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.hackerspacebremen.email.SendEmail#send()
	 */
	@Override
	public void send() {
		// TODO need something more secure
		final String registrationKey = KeyFactory.keyToString(keeper.getKey());
		final String url = "https://"
				+ System.getProperty("com.google.appengine.application.id")
				+ ".appspot.com/activate?key=" + registrationKey + "&nick="
				+ keeper.getName();

		final Session session = Session.getDefaultInstance(new Properties(),
				null);
		final MimeMessage msg = new MimeMessage(session);
		try {
			msg.setFrom(new InternetAddress(AppConstants.ADMIN_EMAIL,
					"Hackerspace Bremen"));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
					keeper.getEmail(), keeper.getFullName()));
			msg.setSubject(
					PropertyHelper.getEmailPropertyValue("email.registry.subject"),
					Constants.UTF8);
			msg.setHeader("Content-Type", "text/plain; charset=utf-8");
			final MessageFormat form = new MessageFormat(
					PropertyHelper.getEmailPropertyValue("email.registry.content"));
			msg.setText(form.format(new String[] { keeper.getFullName(),
					keeper.getName(), keeper.getEmail(), url }));
			Transport.send(msg);
		} catch (MessagingException e) {
			logger.warning("MessagingException: " + e.getMessage());
		} catch (UnsupportedEncodingException e) {
			logger.warning("UnsupportedEncodingException: " + e.getMessage());
		}
	}

}