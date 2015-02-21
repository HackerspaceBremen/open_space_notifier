package de.hackerspacebremen.domain.val;

import junit.framework.Assert;

import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.google.inject.Inject;

import de.hackerspacebremen.domain.api.PropertyService;
import de.hackerspacebremen.modules.ValidationModule;
import de.hackerspacebremen.modules.binding.annotations.ITest;
import de.hackerspacebremen.valueobjects.properties.CertificateProperties;
import de.hackerspacebremen.valueobjects.properties.PushProperties;

@Guice(modules = ValidationModule.class)
@Test
public final class PropertyValidationFetchAndFindTest {

	@Inject
	@ITest
	private PropertyService propertyService;

	public void testFetchPush() {
		final PushProperties test = propertyService
				.fetchProperties(PushProperties.class);
		Assert.assertNotNull(test);
	}

	public void testFetchCertificate() {
		final CertificateProperties test = propertyService
				.fetchProperties(CertificateProperties.class);
		Assert.assertNotNull(test);
	}

	public void testFindValueByKey() throws ValidationException {
		final String test = propertyService.findValueByKey("test");
		Assert.assertNotNull(test);
		Assert.assertEquals("dummy", test);
	}

	@Test(expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = "see error code 23")
	public void testFindValueByKeyNull() throws ValidationException {
		propertyService.findValueByKey(null);
	}

	@Test(expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = "see error code 23")
	public void testFindValueByKeyEmpty() throws ValidationException {
		propertyService.findValueByKey("");
	}
}
