package de.hackerspacebremen.domain.val;

import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.google.inject.Inject;

import de.hackerspacebremen.domain.api.APNSDataService;
import de.hackerspacebremen.modules.ValidationModule;
import de.hackerspacebremen.modules.binding.annotations.ITest;

@Guice(modules=ValidationModule.class)
@Test
public final class APNSDataValidationUnregisterTest {

	
	@Inject
	@ITest
	private APNSDataService apnsDataValidation;
	
	public void testUnunregister() throws ValidationException{
		this.apnsDataValidation.unregister("test");
	}
	
	@Test(expectedExceptions=ValidationException.class, expectedExceptionsMessageRegExp="see error code 28")
	public void testunregisterNull1() throws ValidationException{
		this.apnsDataValidation.unregister(null);
	}
	
	@Test(expectedExceptions=ValidationException.class, expectedExceptionsMessageRegExp="see error code 28")
	public void testunregisterNull2() throws ValidationException{
		this.apnsDataValidation.unregister("");
	}
}
