package de.hackerspacebremen.domain.mocks;

import de.hackerspacebremen.domain.api.GCMDataService;
import de.hackerspacebremen.domain.val.ValidationException;

public class GCMDataServiceMock implements GCMDataService{

	@Override
	public void register(final String deviceId, final String registrationId)
			throws ValidationException {
		// do nothing
	}

	@Override
	public void unregister(final String deviceId) throws ValidationException {
		// do nothing
	}

	@Override
	public void sendMessageToDevices(final String message) throws 
			ValidationException {
		// do nothing
	}

}
