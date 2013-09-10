package de.hackerspacebremen.domain.api;

import java.io.IOException;

import de.hackerspacebremen.domain.val.ValidationException;

public interface APNSDataService {

	void register(String deviceId, String token) 
			throws ValidationException;

	void unregister(String deviceId) 
		throws ValidationException;

	void sendMessageToDevices(String message) 
			throws IOException, ValidationException;
}
