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
package de.hackerspacebremen.domain;

import java.io.IOException;
import java.util.List;

import javax.inject.Provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.Result;


import de.hackerspacebremen.data.entities.GcmData;
import de.hackerspacebremen.data.objectify.GcmDataDao;
import de.hackerspacebremen.domain.val.GcmDataServiceValidation;
import de.hackerspacebremen.domain.val.ValidationException;
import de.hackerspacebremen.gcm.GcmMessageSender;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GcmDataService {

	private GcmDataDao gcmDataDAO;

	private Provider<GcmMessageSender> gcmMessageSenderProvider;

	private GcmDataServiceValidation validation;

	@Autowired
	public GcmDataService(GcmDataDao gcmDataDAO, Provider<GcmMessageSender> gcmMessageSenderProvider,
			GcmDataServiceValidation validation) {
		this.gcmDataDAO = gcmDataDAO;
		this.gcmMessageSenderProvider = gcmMessageSenderProvider;
		this.validation = validation;
	}

	public void register(final String deviceId, final String registrationId) {
		validation.register(deviceId, registrationId);
		GcmData gcmData = gcmDataDAO.findByDeviceId(deviceId);
		if (gcmData == null) {
			gcmData = new GcmData();
			gcmData.setDeviceId(deviceId);
			gcmData.setRegistrationId(registrationId);
			gcmDataDAO.persist(gcmData);
		} else if (!gcmData.getRegistrationId().equals(registrationId)) {
			gcmData.setDeviceId(deviceId);
			gcmData.setRegistrationId(registrationId);
			gcmDataDAO.persist(gcmData);
		}
	}

	public void unregister(final String deviceId) throws ValidationException {
		validation.unregister(deviceId);
		GcmData gcmData = gcmDataDAO.findByDeviceId(deviceId);
		gcmDataDAO.delete(gcmData);
	}

	public void sendMessageToDevices(final String message) throws ValidationException {
		validation.sendMessageToDevices(message);
		final List<GcmData> devices = gcmDataDAO.findAll();
		for (final GcmData gcmData : devices) {
			final GcmMessageSender sender = gcmMessageSenderProvider.get();
			Result result;
			try {
				result = sender.sendMessage(message, gcmData.getRegistrationId());

				if (result.getMessageId() != null) {
					String canonicalRegId = result.getCanonicalRegistrationId();
					if (canonicalRegId != null) {
						// same device has more than on registration ID: update
						// database
						gcmData.setRegistrationId(canonicalRegId);
						gcmDataDAO.persist(gcmData);
					}
				} else {
					String error = result.getErrorCodeName();
					if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
						// application has been removed from device - unregister
						// database
						gcmDataDAO.delete(gcmData);
					}
				}
			} catch (IOException e) {
				log.error("Unexpected IOException occured: ", e);
			}
		}
	}
}
