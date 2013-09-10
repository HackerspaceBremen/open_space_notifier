package de.hackerspacebremen.domain.demo;

import com.google.inject.Inject;

import de.hackerspacebremen.common.AppConstants;
import de.hackerspacebremen.domain.api.AuthenticationService;
import de.hackerspacebremen.domain.val.ValidationException;

public class AuthenticateServiceTester implements AuthenticationService{

	@Inject
	private AuthenticationService authService;
	
	@Override
	public boolean authenticate(final String name, final String password)
			throws ValidationException {
		final boolean result;
		if(!AppConstants.PROD && name.equals("helge")){
			result = true;
		}else{
			result = authService.authenticate(name, password);
		}
		return result;
	}
}