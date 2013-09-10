package de.hackerspacebremen.modules;

import com.google.inject.AbstractModule;

import de.hackerspacebremen.domain.api.AuthenticationService;
import de.hackerspacebremen.domain.mocks.AuthenticationServiceMock;
import de.hackerspacebremen.domain.val.AuthServiceValidation;
import de.hackerspacebremen.modules.binding.annotations.Demo;
import de.hackerspacebremen.modules.binding.annotations.ITest;

public class ValidationModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(AuthenticationService.class).annotatedWith(Demo.class).to(AuthenticationServiceMock.class);
		bind(AuthenticationService.class).annotatedWith(ITest.class).to(AuthServiceValidation.class);
	}

}
