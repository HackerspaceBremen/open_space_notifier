package de.hackerspacebremen.domain.api;

import de.hackerspacebremen.domain.val.ValidationException;

public interface APNSDataService {

	void register(String deviceId, String token) 
			throws ValidationException;

	void unregister(String deviceId) 
		throws ValidationException;

	void sendMessageToDevices(String statusShort, String message) 
			throws ValidationException;
}
