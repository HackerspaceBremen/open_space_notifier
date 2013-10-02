package de.hackerspacebremen.domain.val;

import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.google.inject.Inject;

import de.hackerspacebremen.domain.api.PropertyService;
import de.hackerspacebremen.modules.ValidationModule;
import de.hackerspacebremen.modules.binding.annotations.ITest;

@Guice(modules=ValidationModule.class)
@Test
public final class PropertyValidationSaveTest {

	@Inject
	@ITest
	private PropertyService propertyService;

	public void testSavePushProperties() throws ValidationException{
		this.propertyService.savePushProperties(true, true, true, "test", "test2");
	}
	
	@Test(expectedExceptions=ValidationException.class, expectedExceptionsMessageRegExp="see error code 23")
	public void testSavePushPropertiesGCMNull() throws ValidationException{
		this.propertyService.savePushProperties(true, true, true, null, "test2");
	}
	
	@Test(expectedExceptions=ValidationException.class, expectedExceptionsMessageRegExp="see error code 23")
	public void testSavePushPropertiesGCMEmpty() throws ValidationException{
		this.propertyService.savePushProperties(true, true, true, "", "test2");
	}
	
	public void testSavePushPropertiesGCMNull2() throws ValidationException{
		this.propertyService.savePushProperties(false, true, true, null, "test2");
	}
	
	public void testSavePushPropertiesGCMEmpty2() throws ValidationException{
		this.propertyService.savePushProperties(false, true, true, "", "test2");
	}
	
	@Test(expectedExceptions=ValidationException.class, expectedExceptionsMessageRegExp="see error code 24")
	public void testSavePushPropertiesAPNSNull() throws ValidationException{
		this.propertyService.savePushProperties(true, true, true, "test", null);
	}
	
	public void testSavePushPropertiesAPNSNull2() throws ValidationException{
		this.propertyService.savePushProperties(true, false, true, "test", null);
	}
	
	@Test(expectedExceptions=ValidationException.class, expectedExceptionsMessageRegExp="see error code 24")
	public void testSavePushPropertiesAPNSEmpty() throws ValidationException{
		this.propertyService.savePushProperties(true, true, true, "test", "");
	}
	
	public void testSavePushPropertiesAPNSEmpty2() throws ValidationException{
		this.propertyService.savePushProperties(true, false, true, "test", "");
	}
}
