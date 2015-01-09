package de.hackerspacebremen.domain.val;

import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.google.inject.Inject;

import de.hackerspacebremen.domain.api.GCMDataService;
import de.hackerspacebremen.modules.ValidationModule;
import de.hackerspacebremen.modules.binding.annotations.ITest;

@Guice(modules=ValidationModule.class)
@Test
public final class GCMDataValidationSendTest {

	@Inject
	@ITest
	private GCMDataService gcmDataService;
	
	public void testSend() throws ValidationException{
		gcmDataService.sendMessageToDevices("correct message");
	}
	
	@Test(expectedExceptions=ValidationException.class, expectedExceptionsMessageRegExp="see error code 10")
	public void testSendNull() throws ValidationException{
		gcmDataService.sendMessageToDevices(null);
	}
	
	@Test(expectedExceptions=ValidationException.class, expectedExceptionsMessageRegExp="see error code 10")
	public void testSendEmpty() throws ValidationException{
		gcmDataService.sendMessageToDevices("");
	}
}
