package de.hackerspacebremen.modules;

import com.google.inject.AbstractModule;

import de.hackerspacebremen.domain.api.PropertyService;
import de.hackerspacebremen.domain.mocks.PropertyServiceAlternativeMock;
import de.hackerspacebremen.domain.val.PropertyServiceValidation;
import de.hackerspacebremen.modules.binding.annotations.ITest;

public class ValidationAlternativeModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(PropertyService.class).to(PropertyServiceAlternativeMock.class);
		bind(PropertyService.class).annotatedWith(ITest.class).to(PropertyServiceValidation.class);
	}

}
