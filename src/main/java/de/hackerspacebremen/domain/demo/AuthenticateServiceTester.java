package de.hackerspacebremen.domain.demo;

import de.hackerspacebremen.common.AppConstants;
import de.hackerspacebremen.domain.validation.ValidationException;

public class AuthenticateServiceTester {

	
	// TODO include this in implementation
	public boolean authenticate(final String name, final String password)
			throws ValidationException {
		final boolean result;
		if(!AppConstants.PROD && name.equals("helge")){
			result = true;
		}else{
			result = false; // temp
//			result = authService.authenticate(name, password);
		}
		return result;
	}
}