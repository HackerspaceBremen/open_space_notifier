package de.hackerspacebremen.domain.val;

import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.google.inject.Inject;

import de.hackerspacebremen.domain.api.PropertyService;
import de.hackerspacebremen.modules.ValidationModule;
import de.hackerspacebremen.modules.binding.annotations.ITest;

@Guice(modules=ValidationModule.class)
@Test
public final class PropertyValidationSave2Test {

	@Inject
	@ITest
	private PropertyService propertyService;

	public void testSaveAPNSCertificate() throws ValidationException{
		this.propertyService.saveAPNSCertificate("test");
	}
	
	@Test(expectedExceptions=ValidationException.class, expectedExceptionsMessageRegExp="see error code 24")
	public void testSaveAPNSCertificateNull() throws ValidationException{
		this.propertyService.saveAPNSCertificate(null);
	}
	
	@Test(expectedExceptions=ValidationException.class, expectedExceptionsMessageRegExp="see error code 24")
	public void testSaveAPNSCertificateEmpty() throws ValidationException{
		this.propertyService.saveAPNSCertificate("");
	}
}
