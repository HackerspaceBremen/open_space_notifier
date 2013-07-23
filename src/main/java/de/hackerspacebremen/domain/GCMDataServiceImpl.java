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

import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.Result;

import de.hackerspacebremen.data.api.GCMDataDAO;
import de.hackerspacebremen.data.entities.GCMData;
import de.hackerspacebremen.domain.api.GCMDataService;
import de.hackerspacebremen.gcm.GCMMessageSender;
import de.liedtke.business.BasicServiceImpl;
import de.liedtke.validation.ValidationException;

public class GCMDataServiceImpl extends BasicServiceImpl implements GCMDataService{

//	/**
//	 * static attribute used for logging.
//	*/
//	private static final Logger logger = Logger.getLogger(GCMDataServiceImpl.class.getName());
	
	public GCMDataServiceImpl(final GCMDataDAO dao){
		this.basicDAO = dao;
	}
	
	@Override
	public void register(final String deviceId, final String registrationId) {
		GCMData gcmData = ((GCMDataDAO)this.basicDAO).findByDeviceId(deviceId);
		if(gcmData == null){
			gcmData = new GCMData();
			gcmData.setDeviceId(deviceId);
			gcmData.setRegistrationId(registrationId);
			((GCMDataDAO)this.basicDAO).persist(gcmData);
		}else if(!gcmData.getRegistrationId().equals(registrationId)){
			gcmData.setDeviceId(deviceId);
			gcmData.setRegistrationId(registrationId);
			((GCMDataDAO)this.basicDAO).persist(gcmData);
		}
	}
	
	@Override
	public void unregister(final String deviceId)
			throws ValidationException {
		GCMData gcmData = ((GCMDataDAO)this.basicDAO).findByDeviceId(deviceId);
		((GCMDataDAO)this.basicDAO).delete(gcmData);
	}

	@Override
	public void sendMessageToDevices(final String authToken, final String message) throws IOException{
		final List<GCMData> devices = ((GCMDataDAO)this.basicDAO).findAll();
		for(final GCMData gcmData : devices){
			final GCMMessageSender sender = new GCMMessageSender(message);
			final Result result = sender.sendMessage(gcmData.getRegistrationId());
			if (result.getMessageId() != null) {
				String canonicalRegId = result.getCanonicalRegistrationId();
				if (canonicalRegId != null) {
					// same device has more than on registration ID: update database
					gcmData.setRegistrationId(canonicalRegId);
					((GCMDataDAO)this.basicDAO).persist(gcmData);
				}
			} else {
				String error = result.getErrorCodeName();
				if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
					// application has been removed from device - unregister database
					((GCMDataDAO)this.basicDAO).delete(gcmData);
				}
			}
		}
//		for(final Result result : multiResult.getResults()){
//			if (result.getMessageId() != null) {
//				String canonicalRegId = result.getCanonicalRegistrationId();
//				if (canonicalRegId != null) {
//					// same device has more than on registration ID: update database
//					logger.warning("canonicalRegId: " + canonicalRegId);
//				}
//			} else {
//				String error = result.getErrorCodeName();
//				if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
//					// application has been removed from device - unregister database
//					logger.warning("canonicalRegId: " + result.getCanonicalRegistrationId());
//				}
//			}
//		}
	}
	
//	private List<String> deviceList(final List<GCMData> devices){
//		final List<String> result = new ArrayList<String>(devices.size());
//		for(final GCMData device : devices){
//			result.add(device.getRegistrationId());
//		}
//		return result;
//	}
}