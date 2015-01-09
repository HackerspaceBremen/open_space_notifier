package de.hackerspacebremen.modules;

import com.google.inject.AbstractModule;

import de.hackerspacebremen.domain.api.APNSDataService;
import de.hackerspacebremen.domain.api.AuthenticationService;
import de.hackerspacebremen.domain.api.GCMDataService;
import de.hackerspacebremen.domain.api.PropertyService;
import de.hackerspacebremen.domain.mocks.APNSDataServiceMock;
import de.hackerspacebremen.domain.mocks.AuthenticationServiceMock;
import de.hackerspacebremen.domain.mocks.GCMDataServiceMock;
import de.hackerspacebremen.domain.mocks.PropertyServiceMock;
import de.hackerspacebremen.domain.val.APNSDataValidation;
import de.hackerspacebremen.domain.val.AuthServiceValidation;
import de.hackerspacebremen.domain.val.GCMDataServiceValidation;
import de.hackerspacebremen.domain.val.PropertyServiceValidation;
import de.hackerspacebremen.modules.binding.annotations.Demo;
import de.hackerspacebremen.modules.binding.annotations.ITest;

public class ValidationModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(AuthenticationService.class).annotatedWith(Demo.class).to(AuthenticationServiceMock.class);
		bind(AuthenticationService.class).annotatedWith(ITest.class).to(AuthServiceValidation.class);
		
		bind(APNSDataService.class).to(APNSDataServiceMock.class);
		bind(APNSDataService.class).annotatedWith(ITest.class).to(APNSDataValidation.class);
		
		bind(GCMDataService.class).to(GCMDataServiceMock.class);
		bind(GCMDataService.class).annotatedWith(ITest.class).to(GCMDataServiceValidation.class);
		
		bind(PropertyService.class).to(PropertyServiceMock.class);
		bind(PropertyService.class).annotatedWith(ITest.class).to(PropertyServiceValidation.class);
	}

}
