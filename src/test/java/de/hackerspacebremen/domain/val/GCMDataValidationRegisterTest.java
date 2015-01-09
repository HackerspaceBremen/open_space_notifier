package de.hackerspacebremen.domain.val;

import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.google.inject.Inject;

import de.hackerspacebremen.domain.api.GCMDataService;
import de.hackerspacebremen.modules.ValidationModule;
import de.hackerspacebremen.modules.binding.annotations.ITest;

@Guice(modules=ValidationModule.class)
@Test
public final class GCMDataValidationRegisterTest {

	@Inject
	@ITest
	private GCMDataService gcmDataService;
	
	public void testRegister() throws ValidationException{
		gcmDataService.register("all", "correct");
	}
	
	@Test(expectedExceptions=ValidationException.class, expectedExceptionsMessageRegExp="see error code 7")
	public void testRegisterNull1() throws ValidationException{
		gcmDataService.register(null, "not correct");
	}
	
	@Test(expectedExceptions=ValidationException.class, expectedExceptionsMessageRegExp="see error code 7")
	public void testRegisterEmpty1() throws ValidationException{
		gcmDataService.register("", "also false usage");
	}
	
	@Test(expectedExceptions=ValidationException.class, expectedExceptionsMessageRegExp="see error code 8")
	public void testRegisterNull2() throws ValidationException{
		gcmDataService.register("also wrong", null);
	}
	
	@Test(expectedExceptions=ValidationException.class, expectedExceptionsMessageRegExp="see error code 8")
	public void testRegisterEmpty2() throws ValidationException{
		gcmDataService.register("and this too", "");
	}
}
