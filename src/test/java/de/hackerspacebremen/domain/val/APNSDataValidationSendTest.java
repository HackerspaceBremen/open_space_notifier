package de.hackerspacebremen.domain.val;

import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.google.inject.Inject;

import de.hackerspacebremen.domain.api.APNSDataService;
import de.hackerspacebremen.modules.ValidationModule;
import de.hackerspacebremen.modules.binding.annotations.ITest;

@Guice(modules=ValidationModule.class)
@Test
public final class APNSDataValidationSendTest {

	
	@Inject
	@ITest
	private APNSDataService apnsDataValidation;
	
	public void testSend() throws ValidationException{
		apnsDataValidation.sendMessageToDevices("Space ist schon lange zu!", "Weil ist so ...");
	}
	
	public void testSendMessageNull() throws ValidationException{
		// totally fine
		apnsDataValidation.sendMessageToDevices("Space ist schon lange zu!", null);
	}
	
	public void testSendMessageEmpty() throws ValidationException{
		// that's correct too
		apnsDataValidation.sendMessageToDevices("Space ist schon lange zu!", "");
	}
	
	@Test(expectedExceptions=ValidationException.class, expectedExceptionsMessageRegExp="see error code 29")
	public void testSendStatusNull() throws ValidationException{
		// totally fine
		apnsDataValidation.sendMessageToDevices(null, "that's not allowed");
	}
	
	@Test(expectedExceptions=ValidationException.class, expectedExceptionsMessageRegExp="see error code 29")
	public void testSendStatusEmpty() throws ValidationException{
		// totally fine
		apnsDataValidation.sendMessageToDevices("", "not allowed aswell");
	}
}
