package de.hackerspacebremen.transformer;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import de.hackerspacebremen.data.entities.SpaceStatus;
import flexjson.JSONSerializer;

public class AppEngineKeyTransformerTest {
  
	final JSONSerializer serializer = new JSONSerializer().exclude("*.class")
			.transform(new AppEngineKeyTransformer(), Key.class);
	
	final JSONSerializer wrongSerializer = new JSONSerializer().exclude("*.class")
			.transform(new AppEngineKeyTransformer(), "message");
	
	@Test
	public void test() {
		final SpaceStatus status = this.createSpaceStatus();
		final String test = serializer.serialize(status);
		Assert.assertNotNull(test);
		try{
			final JSONObject json = new JSONObject(test);
			Assert.assertTrue(json.has("key"));
			Assert.assertEquals(json.getString("key"), KeyFactory.keyToString(status.getKey()));
			Assert.assertTrue(json.has("openedBy"));
			Assert.assertEquals(json.getString("openedBy"), KeyFactory.keyToString(status.getOpenedBy()));
		}catch(JSONException e){
			Assert.assertTrue(false,"JSONException occured: " + e.getMessage());
		}
	}
	
	@Test(expectedExceptions=flexjson.JSONException.class)
	public void testWrongUsage() {
		final SpaceStatus status = this.createSpaceStatus();
		wrongSerializer.serialize(status);
	}
  
	private SpaceStatus createSpaceStatus(){
		final SpaceStatus status = new SpaceStatus();
		status.setKey(KeyFactory.createKey("SpaceStatus", 123));
		status.setMessage(new Text("test message"));
		status.setStatus("OPENED");
		status.setTime(123456789L);
		status.setOpenedBy(KeyFactory.createKey("DoorKeyKeeper", 456));
		return status;
	}
  
  //maximum eventual consistency
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
