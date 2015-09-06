package de.hackerspacebremen.domain.val;

import org.springframework.stereotype.Component;

@Component
public class ApnsDataValidation extends Validation {

	public void register(final String deviceId, final String token)
			throws ValidationException {
		this.validateIfEmpty(deviceId, 26);
		this.validateIfEmpty(token, 27);
	}

	public void unregister(final String deviceId) throws ValidationException {
		this.validateIfEmpty(deviceId, 28);
	}

	public void sendMessageToDevices(final String statusShort, final String message) throws 
			ValidationException {
		this.validateIfEmpty(statusShort, 29);
	}
}