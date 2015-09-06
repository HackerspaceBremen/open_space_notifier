package de.hackerspacebremen.modules;

import com.google.inject.AbstractModule;

public class DomainModule extends AbstractModule{

	@Override
	protected void configure() {
		
		// used for events, i.e. maker faire, works only on the test server
		//bind(AuthenticationService.class).annotatedWith(Demo.class).to(AuthenticateServiceDemo.class);
		// used for test accounts
//		bind(AuthenticationService.class).annotatedWith(Demo.class).to(AuthenticateServiceTester.class);
		
	}

}
