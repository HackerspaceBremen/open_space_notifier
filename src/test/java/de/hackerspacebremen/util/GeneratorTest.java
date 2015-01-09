package de.hackerspacebremen.util;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class GeneratorTest {

	public void testRandomString(){
		final String result = Generator.randomString(10);
		Assert.assertEquals(result.length(), 10);
	}
	
	public void testRandomStringNegativeLength(){
		final String result = Generator.randomString(-4);
		Assert.assertEquals(result.length(), 0);
	}
	
	public void testRandomStringZeroLength(){
		final String result = Generator.randomString(0);
		Assert.assertEquals(result.length(), 0);
	}
}
