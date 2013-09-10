package de.hackerspacebremen.domain.val;

import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.google.inject.Inject;

import de.hackerspacebremen.domain.api.AuthenticationService;
import de.hackerspacebremen.modules.ValidationModule;
import de.hackerspacebremen.modules.binding.annotations.ITest;

@Guice(modules = ValidationModule.class)
@Test
public class AuthServiceValidationTest {

	@Inject
	@ITest
	private AuthenticationService authenticationService;
	
	public void testAuthenticate() throws ValidationException{
		authenticationService.authenticate("testitest", "blubb");
	}
	
	@Test(expectedExceptions=ValidationException.class, expectedExceptionsMessageRegExp="see error code 11")
	public void testAuthenticateNullName() throws ValidationException{
		authenticationService.authenticate(null, "blubb");
	}
	
	@Test(expectedExceptions=ValidationException.class, expectedExceptionsMessageRegExp="see error code 11")
	public void testAuthenticateEmptyName() throws ValidationException{
		authenticationService.authenticate("", "blubb");
	}
	
	@Test(expectedExceptions=ValidationException.class, expectedExceptionsMessageRegExp="see error code 12")
	public void testAuthenticateNullPassword() throws ValidationException{
		authenticationService.authenticate("testitest", null);
	}
	
	@Test(expectedExceptions=ValidationException.class, expectedExceptionsMessageRegExp="see error code 12")
	public void testAuthenticateEmptyPassword() throws ValidationException{
		authenticationService.authenticate("testitest", "");
	}
}
