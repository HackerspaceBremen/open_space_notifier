package de.hackerspacebremen.domain.val;

import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.google.inject.Inject;

import de.hackerspacebremen.domain.api.PropertyService;
import de.hackerspacebremen.modules.ValidationAlternativeModule;
import de.hackerspacebremen.modules.binding.annotations.ITest;

@Guice(modules=ValidationAlternativeModule.class)
@Test
public final class PropertyValidationSave3Test {

	@Inject
	@ITest
	private PropertyService propertyService;

	// for this test the certificate can't be found with the method findValueByKey
	@Test(expectedExceptions=ValidationException.class, expectedExceptionsMessageRegExp="see error code 25")
	public void testSavePushProperties() throws ValidationException{
		this.propertyService.savePushProperties(true, true, true, "test", "test2");
	}
}
