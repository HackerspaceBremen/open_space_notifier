package de.hackerspacebremen.domain.mocks;

import de.hackerspacebremen.domain.api.APNSDataService;
import de.hackerspacebremen.domain.val.ValidationException;

public final class APNSDataServiceMock implements APNSDataService{

	@Override
	public void register(final String deviceId, final String token)
			throws ValidationException {
		// do nothing
	}

	@Override
	public void unregister(final String deviceId) throws ValidationException {
		// do nothing
	}

	@Override
	public void sendMessageToDevices(final String statusShort, final String message)
			throws ValidationException {
		// do nothing
	}

}
