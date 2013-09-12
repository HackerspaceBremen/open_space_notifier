package de.hackerspacebremen.domain.val;

import java.io.IOException;

import com.google.inject.Inject;

import de.hackerspacebremen.domain.api.APNSDataService;

public class APNSDataValidation extends Validation implements APNSDataService {

	@Inject
	private APNSDataService apnsDataService;
	
	@Override
	public void register(final String deviceId, final String token)
			throws ValidationException {
		apnsDataService.register(deviceId, token);
	}

	@Override
	public void unregister(final String deviceId) throws ValidationException {
		apnsDataService.unregister(deviceId);
	}

	@Override
	public void sendMessageToDevices(final String message) throws IOException,
			ValidationException {
		apnsDataService.sendMessageToDevices(message);
	}
}