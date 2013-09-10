package de.hackerspacebremen.domain.mocks;

import de.hackerspacebremen.domain.api.AuthenticationService;
import de.hackerspacebremen.domain.val.ValidationException;

public class AuthenticationServiceMock implements AuthenticationService{

	@Override
	public boolean authenticate(final String name, final String password)
			throws ValidationException {
		return true;
	}

}
