package de.hackerspacebremen.domain.val;

import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.google.inject.Inject;

import de.hackerspacebremen.domain.api.GCMDataService;
import de.hackerspacebremen.modules.ValidationModule;
import de.hackerspacebremen.modules.binding.annotations.ITest;

@Guice(modules=ValidationModule.class)
@Test
public final class GCMDataValidationUnregisterTest {

	@Inject
	@ITest
	private GCMDataService gcmDataService;
	
	public void testUnregister() throws ValidationException{
		gcmDataService.unregister("correct usage");
	}
	
	@Test(expectedExceptions=ValidationException.class, expectedExceptionsMessageRegExp="see error code 7")
	public void testUnregisterNull() throws ValidationException{
		gcmDataService.unregister(null);
	}
	
	@Test(expectedExceptions=ValidationException.class, expectedExceptionsMessageRegExp="see error code 7")
	public void testUnregisterEmpty() throws ValidationException{
		gcmDataService.unregister("");
	}
}
