package de.hackerspacebremen.domain.val;

import org.springframework.stereotype.Component;

@Component
public class AuthServiceValidation extends Validation {

	public void authenticate(final String name, final String password) {
		this.validateIfEmpty(name, 11);
		this.validateIfEmpty(password, 12);
	}

}
