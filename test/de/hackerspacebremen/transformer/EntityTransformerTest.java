package de.hackerspacebremen.transformer;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import de.hackerspacebremen.common.PresentationMode;
import de.hackerspacebremen.controller.ResultObject;
import de.hackerspacebremen.data.entities.SpaceStatus;
import de.liedtke.data.entity.BasicEntity;

import flexjson.JSONSerializer;

public class EntityTransformerTest {

	final JSONSerializer allSerializer = new JSONSerializer().exclude("*.class")
			.transform(new EntityTransformer(PresentationMode.ALL), BasicEntity.class);
	
	final JSONSerializer viewSerializer = new JSONSerializer().exclude("*.class")
			.transform(new EntityTransformer(PresentationMode.VIEW), BasicEntity.class);
	
	final JSONSerializer bothSerializer = new JSONSerializer().exclude("*.class")
			.transform(new EntityTransformer(PresentationMode.VIEW, PresentationMode.ALL), BasicEntity.class);
	
	@Test
	public void testStatusAll() {
		final ResultObject resultObject = this.createSpaceStatus();
		final String test = allSerializer.serialize(resultObject);
		Assert.assertNotNull(test);
		final JSONObject json;
		try {
			json = new JSONObject(test);
			Assert.assertTrue(json.has("CODE"));
			Assert.assertEquals(json.getInt("CODE"),0);
			Assert.assertTrue(json.has("MESSAGE"));
			Assert.assertEquals(json.getString("MESSAGE"),"result message");
			Assert.assertTrue(json.has("SUCCESS"));
			Assert.assertEquals(json.getString("SUCCESS"),"result message");
			Assert.assertTrue(json.has("ERROR"));
			Assert.assertEquals(json.get("ERROR"), JSONObject.NULL);
			Assert.assertTrue(json.has("RESULT"));
			final JSONObject resultJson = json.getJSONObject("RESULT");
			Assert.assertTrue(resultJson.has("ST1"));
			// TODO check key
			Assert.assertFalse(resultJson.has("ST2"));
			Assert.assertFalse(resultJson.has("ST3"));
			Assert.assertTrue(resultJson.has("ST4"));
			// TODO check key
			Assert.assertFalse(resultJson.has("ST5"));
		} catch (JSONException e) {
			Assert.assertTrue(false,"JSONException occured: " + e.getMessage());
		}
	}
	
	@Test
	public void testStatusView() {
		final ResultObject resultObject = this.createSpaceStatus();
		final String test = viewSerializer.serialize(resultObject);
		Assert.assertNotNull(test);
		final JSONObject json;
		try {
			json = new JSONObject(test);
			Assert.assertTrue(json.has("CODE"));
			Assert.assertEquals(json.getInt("CODE"),0);
			Assert.assertTrue(json.has("MESSAGE"));
			Assert.assertEquals(json.getString("MESSAGE"),"result message");
			Assert.assertTrue(json.has("SUCCESS"));
			Assert.assertEquals(json.getString("SUCCESS"),"result message");
			Assert.assertTrue(json.has("ERROR"));
			Assert.assertEquals(json.get("ERROR"), JSONObject.NULL);
			Assert.assertTrue(json.has("RESULT"));
			final JSONObject resultJson = json.getJSONObject("RESULT");
			Assert.assertFalse(resultJson.has("ST1"));
			Assert.assertTrue(resultJson.has("ST2"));
			Assert.assertEquals(resultJson.getLong("ST2"), 123456789L);
			Assert.assertTrue(resultJson.has("ST3"));
			Assert.assertEquals(resultJson.getString("ST3"), "OPENED");
			Assert.assertFalse(resultJson.has("ST4"));
			Assert.assertTrue(resultJson.has("ST5"));
			// TODO check message
		} catch (JSONException e) {
			Assert.assertTrue(false,"JSONException occured: " + e.getMessage());
		}
	}
	
	@Test
	public void testStatusBoth() {
		final ResultObject resultObject = this.createSpaceStatus();
		final String test = bothSerializer.serialize(resultObject);
		Assert.assertNotNull(test);
		final JSONObject json;
		try {
			json = new JSONObject(test);
			Assert.assertTrue(json.has("CODE"));
			Assert.assertEquals(json.getInt("CODE"),0);
			Assert.assertTrue(json.has("MESSAGE"));
			Assert.assertEquals(json.getString("MESSAGE"),"result message");
			Assert.assertTrue(json.has("SUCCESS"));
			Assert.assertEquals(json.getString("SUCCESS"),"result message");
			Assert.assertTrue(json.has("ERROR"));
			Assert.assertEquals(json.get("ERROR"), JSONObject.NULL);
			Assert.assertTrue(json.has("RESULT"));
			final JSONObject resultJson = json.getJSONObject("RESULT");
			Assert.assertTrue(resultJson.has("ST1"));
			// TODO check key
			Assert.assertTrue(resultJson.has("ST2"));
			Assert.assertEquals(resultJson.getLong("ST2"), 123456789L);
			Assert.assertTrue(resultJson.has("ST3"));
			Assert.assertEquals(resultJson.getString("ST3"), "OPENED");
			Assert.assertTrue(resultJson.has("ST4"));
			// TODO check key
			Assert.assertTrue(resultJson.has("ST5"));
			// TODO check message
		} catch (JSONException e) {
			Assert.assertTrue(false,"JSONException occured: " + e.getMessage());
		}
	}
	
	private ResultObject createSpaceStatus(){
		final SpaceStatus status = new SpaceStatus();
		status.setKey(KeyFactory.createKey("SpaceStatus", 123));
		status.setMessage(new Text("test message"));
		status.setStatus("OPENED");
		status.setTime(123456789L);
		status.setOpenedBy(KeyFactory.createKey("DoorKeyKeeper", 456));
		return new ResultObject("result message", status);
	}
	
	// maximum eventual consistency
    private final LocalServiceTestHelper helper =
        new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig()
            .setDefaultHighRepJobPolicyUnappliedJobPercentage(100));

    @BeforeClass
    public void setUp() {
        helper.setUp();
    }

    @AfterClass
    public void tearDown() {
        helper.tearDown();
    }
}
