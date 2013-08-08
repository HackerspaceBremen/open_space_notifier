package de.hackerspacebremen.domain.api;

import de.hackerspacebremen.domain.val.ValidationException;


public interface AuthenticationService {

	boolean authenticate(final String name, final String password) throws ValidationException;
}
