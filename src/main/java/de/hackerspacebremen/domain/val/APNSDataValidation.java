package de.hackerspacebremen.domain.val;

import com.google.inject.Inject;

import de.hackerspacebremen.domain.api.APNSDataService;

public class APNSDataValidation extends Validation implements APNSDataService {

	@Inject
	private APNSDataService apnsDataService;
	
	@Override
	public void register(final String deviceId, final String token)
			throws ValidationException {
		this.validateIfEmpty(deviceId, 26);
		this.validateIfEmpty(token, 27);
		apnsDataService.register(deviceId, token);
	}

	@Override
	public void unregister(final String deviceId) throws ValidationException {
		this.validateIfEmpty(deviceId, 28);
		apnsDataService.unregister(deviceId);
	}

	@Override
	public void sendMessageToDevices(final String statusShort, final String message) throws 
			ValidationException {
		this.validateIfEmpty(statusShort, 29);
		apnsDataService.sendMessageToDevices(statusShort, message);
	}
}