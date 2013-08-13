package de.hackerspacebremen.domain.val;

import com.google.inject.Inject;

import de.hackerspacebremen.domain.api.AuthenticationService;
import de.hackerspacebremen.modules.binding.annotations.Demo;

public class AuthServiceValidation extends Validation implements AuthenticationService{

	@Inject
	@Demo
	private AuthenticationService authService;
	
	@Override
	public boolean authenticate(final String name, final String password) throws ValidationException{
		this.validateIfEmpty(name, 11);
		this.validateIfEmpty(password, 12);
		return this.authService.authenticate(name, password);
	}

}
