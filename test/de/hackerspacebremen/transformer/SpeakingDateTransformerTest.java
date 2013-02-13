package de.hackerspacebremen.transformer;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.appengine.api.datastore.Text;

import de.hackerspacebremen.data.entities.SpaceStatus;
import de.hackerspacebremen.format.LanguageFormat;
import flexjson.JSONSerializer;

public class SpeakingDateTransformerTest {
	
	final JSONSerializer withoutFormatSerializer = new JSONSerializer().exclude("*.class")
			.transform(new SpeakingDateTransformer(null, true), "time");
	
	final JSONSerializer germanOpenedSerializer = new JSONSerializer().exclude("*.class")
			.transform(new SpeakingDateTransformer(LanguageFormat.GERMAN, true), "time");
	
	final JSONSerializer germanClosedSerializer = new JSONSerializer().exclude("*.class")
			.transform(new SpeakingDateTransformer(LanguageFormat.GERMAN, false), "time");
	
	final JSONSerializer englishOpenedSerializer = new JSONSerializer().exclude("*.class")
			.transform(new SpeakingDateTransformer(LanguageFormat.ENGLISH, true), "time");
	
	final JSONSerializer englishClosedSerializer = new JSONSerializer().exclude("*.class")
			.transform(new SpeakingDateTransformer(LanguageFormat.ENGLISH, false), "time");

	final JSONSerializer wrongSerializer = new JSONSerializer().exclude(
			"*.class").transform(new SpeakingDateTransformer(null, true), "message");
	
	@Test
	public void testWithoutFormat() {
		final SpaceStatus status = this.createSpaceStatus();
		final String test = withoutFormatSerializer.serialize(status);
		Assert.assertNotNull(test);
		try{
			final JSONObject json = new JSONObject(test);
			Assert.assertTrue(json.has("time"));
			Assert.assertEquals(json.getLong("time"), 123456789L);
		}catch(JSONException e){
			Assert.assertTrue(false,"JSONException occured: " + e.getMessage());
		}
	}
	
	@Test
	public void testGerman() {
		final SpaceStatus status = this.createSpaceStatus();
		final String testOpened = germanOpenedSerializer.serialize(status);
		Assert.assertNotNull(testOpened);
		try{
			final JSONObject json = new JSONObject(testOpened);
			Assert.assertTrue(json.has("time"));
			Assert.assertEquals(json.getString("time"), "Der Space  ist seit 02.01.1970 um 11:17 Uhr ge\u00f6ffnet");
		}catch(JSONException e){
			Assert.assertTrue(false,"JSONException occured: " + e.getMessage());
		}
		
		final String testClosed = germanClosedSerializer.serialize(status);
		Assert.assertNotNull(testClosed);
		try{
			final JSONObject json = new JSONObject(testClosed);
			Assert.assertTrue(json.has("time"));
			Assert.assertEquals(json.getString("time"), "Der Space  ist seit 02.01.1970 um 11:17 Uhr geschlossen");
		}catch(JSONException e){
			Assert.assertTrue(false,"JSONException occured: " + e.getMessage());
		}
	}
	
	@Test
	public void testEnglish() {
		final SpaceStatus status = this.createSpaceStatus();
		final String testOpened = englishOpenedSerializer.serialize(status);
		Assert.assertNotNull(testOpened);
		try{
			final JSONObject json = new JSONObject(testOpened);
			Assert.assertTrue(json.has("time"));
			Assert.assertEquals(json.getString("time"), "The space is open since 02.01.1970 at 11:17 o'clock");
		}catch(JSONException e){
			Assert.assertTrue(false,"JSONException occured: " + e.getMessage());
		}
		
		final String testClosed = englishClosedSerializer.serialize(status);
		Assert.assertNotNull(testClosed);
		try{
			final JSONObject json = new JSONObject(testClosed);
			Assert.assertTrue(json.has("time"));
			Assert.assertEquals(json.getString("time"), "The space is closed since 02.01.1970 at 11:17 o'clock");
		}catch(JSONException e){
			Assert.assertTrue(false,"JSONException occured: " + e.getMessage());
		}
	}
	
	@Test(expectedExceptions=flexjson.JSONException.class)
	public void wrongUsageTest() {
		final SpaceStatus status = this.createSpaceStatus();
		wrongSerializer.serialize(status);
	}
	
	private SpaceStatus createSpaceStatus() {
		final SpaceStatus status = new SpaceStatus();
		status.setMessage(new Text("test message"));
		status.setStatus("OPENED");
		status.setTime(123456789L);
		return status;
	}
}
