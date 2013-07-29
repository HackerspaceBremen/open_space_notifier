package de.hackerspacebremen.domain.api;

import de.hackerspacebremen.domain.val.ValidationException;


public interface LDAPService {

	boolean authenticate(final String name, final String password) throws ValidationException;
	
	// TODO get email and full name over login name
}
