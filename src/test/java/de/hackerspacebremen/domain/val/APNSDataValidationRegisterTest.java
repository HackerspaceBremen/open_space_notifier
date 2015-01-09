package de.hackerspacebremen.domain.val;

import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.google.inject.Inject;

import de.hackerspacebremen.domain.api.APNSDataService;
import de.hackerspacebremen.modules.ValidationModule;
import de.hackerspacebremen.modules.binding.annotations.ITest;

@Guice(modules=ValidationModule.class)
@Test
public final class APNSDataValidationRegisterTest {

	
	@Inject
	@ITest
	private APNSDataService apnsDataValidation;
	
	public void testRegister() throws ValidationException{
		this.apnsDataValidation.register("test", "itest");
	}
	
	@Test(expectedExceptions=ValidationException.class, expectedExceptionsMessageRegExp="see error code 26")
	public void testRegisterNull1() throws ValidationException{
		this.apnsDataValidation.register(null, "itest");
	}
	
	@Test(expectedExceptions=ValidationException.class, expectedExceptionsMessageRegExp="see error code 27")
	public void testRegisterNull2() throws ValidationException{
		this.apnsDataValidation.register("test", null);
	}
	
	@Test(expectedExceptions=ValidationException.class, expectedExceptionsMessageRegExp="see error code 26")
	public void testRegisterEmpty1() throws ValidationException{
		this.apnsDataValidation.register("", "itest");
	}
	
	@Test(expectedExceptions=ValidationException.class, expectedExceptionsMessageRegExp="see error code 27")
	public void testRegisterEmpty2() throws ValidationException{
		this.apnsDataValidation.register("test", "");
	}
}
