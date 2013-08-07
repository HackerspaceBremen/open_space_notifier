package de.hackerspacebremen.domain.val;

import com.google.inject.Inject;

import de.hackerspacebremen.domain.api.LDAPService;
import de.hackerspacebremen.modules.binding.annotations.Demo;

public class LDAPServiceValidation extends Validation implements LDAPService{

	@Inject
	@Demo
	private LDAPService ldapService;
	
	@Override
	public boolean authenticate(final String name, final String password) throws ValidationException{
		this.validateIfEmpty(name, 11);
		this.validateIfEmpty(password, 12);
		return this.ldapService.authenticate(name, password);
	}

}