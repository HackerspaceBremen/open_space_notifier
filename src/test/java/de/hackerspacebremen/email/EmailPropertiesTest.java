package de.hackerspacebremen.email;

import junit.framework.Assert;

import org.testng.annotations.Test;

import de.hackerspacebremen.valueobjects.properties.EmailProperties;

@Test
public final class EmailPropertiesTest {

	public void testGetContent(){
		final EmailProperties properties = new EmailProperties();
		
		properties.setMessage("test");
		properties.setContent("status test $STATUS$ and $MESSAGE$ or $URL$ but not $NEG_STATUS$");
		
		final String test = properties.getContent("status", "message", "url", "neg status");
		Assert.assertNotNull(test);
		Assert.assertEquals("status test status and \n\ntest\n'message' or url but not neg status", test);
	}
	
	public void testNullMessage(){
		final EmailProperties properties = new EmailProperties();
		
		properties.setMessage("test");
		properties.setContent("status test $STATUS$ and $MESSAGE$ or $URL$ but not $NEG_STATUS$");
		
		final String test = properties.getContent("status", null, "url", "neg status");
		Assert.assertNotNull(test);
		Assert.assertEquals("status test status and \n\n or url but not neg status", test);
	}
	
	public void testEmptyMessage(){
		final EmailProperties properties = new EmailProperties();
		
		properties.setMessage("test");
		properties.setContent("status test $STATUS$ and $MESSAGE$ or $URL$ but not $NEG_STATUS$");
		
		final String test = properties.getContent("status", "", "url", "neg status");
		Assert.assertNotNull(test);
		Assert.assertEquals("status test status and \n\n or url but not neg status", test);
	}
}
