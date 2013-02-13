package de.hackerspacebremen.transformer;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.appengine.api.datastore.Text;

import de.hackerspacebremen.data.entities.SpaceStatus;
import flexjson.JSONSerializer;

public class AppEngineTextTransformerTest {

	final JSONSerializer serializer = new JSONSerializer().exclude("*.class")
			.transform(new AppEngineTextTransformer(), Text.class);

	final JSONSerializer wrongSerializer = new JSONSerializer().exclude(
			"*.class").transform(new AppEngineTextTransformer(), "time");

	@Test
	public void test() {
		final SpaceStatus status = this.createSpaceStatus();
		final String test = serializer.serialize(status);
		Assert.assertNotNull(test);
		try{
			final JSONObject json = new JSONObject(test);
			Assert.assertTrue(json.has("message"));
			Assert.assertEquals(json.getString("message"), status.getMessage().getValue());
		}catch(JSONException e){
			Assert.assertTrue(false,"JSONException occured: " + e.getMessage());
		}
	}
	
	@Test(expectedExceptions=flexjson.JSONException.class)
	public void testWrongSerializer() {
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
