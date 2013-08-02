package de.hackerspacebremen.modules;

import com.google.inject.AbstractModule;

import de.hackerspacebremen.domain.GCMAuthServiceImpl;
import de.hackerspacebremen.domain.GCMDataServiceImpl;
import de.hackerspacebremen.domain.LDAPServiceImpl;
import de.hackerspacebremen.domain.SpaceStatusServiceImpl;
import de.hackerspacebremen.domain.api.GCMAuthService;
import de.hackerspacebremen.domain.api.GCMDataService;
import de.hackerspacebremen.domain.api.LDAPService;
import de.hackerspacebremen.domain.api.SpaceStatusService;
import de.hackerspacebremen.domain.demo.LDAPServiceDemo;
import de.hackerspacebremen.domain.val.GCMAuthServiceValidation;
import de.hackerspacebremen.domain.val.GCMDataServiceValidation;
import de.hackerspacebremen.domain.val.LDAPServiceValidation;
import de.hackerspacebremen.domain.val.SpaceStatusServiceValidation;
import de.hackerspacebremen.modules.binding.annotations.Demo;
import de.hackerspacebremen.modules.binding.annotations.Proxy;

public class DomainModule extends AbstractModule{

	@Override
	protected void configure() {
		bind(SpaceStatusService.class).to(SpaceStatusServiceImpl.class);
		bind(SpaceStatusService.class).annotatedWith(Proxy.class).to(SpaceStatusServiceValidation.class);
		
		bind(LDAPService.class).to(LDAPServiceImpl.class);
		bind(LDAPService.class).annotatedWith(Proxy.class).to(LDAPServiceValidation.class);
		// TODO used for events, i.e. maker faire, works only on the test server
		bind(LDAPService.class).annotatedWith(Demo.class).to(LDAPServiceDemo.class);
		
		bind(GCMDataService.class).to(GCMDataServiceImpl.class);
		bind(GCMDataService.class).annotatedWith(Proxy.class).to(GCMDataServiceValidation.class);
		
		bind(GCMAuthService.class).to(GCMAuthServiceImpl.class);
		bind(GCMAuthService.class).annotatedWith(Proxy.class).to(GCMAuthServiceValidation.class);
	}

}
