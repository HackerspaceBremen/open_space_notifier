package de.hackerspacebremen.commands.resultobjects;

import junit.framework.Assert;

import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.google.inject.Inject;

import de.hackerspacebremen.commands.resultobjects.v11.Icon;
import de.hackerspacebremen.commands.resultobjects.v12.StatusV12;
import de.hackerspacebremen.commands.resultobjects.v9.Contact;
import de.hackerspacebremen.format.JacksonInstance;

@Guice
@Test
public final class StatusV12Test {

	@Inject
	private JacksonInstance jacksonInstance;
	private static final String JSON_RESULT = "{\"api\":\"0.12\",\"space\":\"Hackerspace Bremen e.V.\",\"logo\":\"http://example.com/images/hackerspace_icon.png\",\"url\":\"http://www.hackerspace-bremen.de\",\"address\":\"Bornstraße 14/15, 28195 Bremen, Germany\",\"lat\":53.08177947998047,\"lon\":8.805830955505371,\"contact\":{\"phone\":\"+49 421 14 62 92 15\",\"twitter\":\"@hspacehb\",\"email\":\"info@hackerspace-bremen.de\"},\"icon\":{\"open\":\"http://example.com/images/status_auf_48px.png\",\"closed\":\"http://example.com/images/status_zu_48px.png\"},\"lastchange\":1420661704,\"open\":false,\"status\":\"po\",\"SUCCESS\":\"Status found\",\"RESULT\":{\"ST2\":\"1420661704902\",\"ST3\":\"CLOSED\",\"ST5\":\"po\"}}";
	
	public void formatTest() {
		final StatusV12 status = this.testData();
		
		final String test = this.jacksonInstance.write(status);
		Assert.assertNotNull(test);
		Assert.assertEquals(JSON_RESULT, test);
	}

	private StatusV12 testData() {
		final StatusV12 status = new StatusV12();

		status.setAddress("Bornstraße 14/15, 28195 Bremen, Germany");

		final Contact contact = new Contact();
		contact.setEmail("info@hackerspace-bremen.de");
		contact.setPhone("+49 421 14 62 92 15");
		contact.setTwitter("@hspacehb");
		status.setContact(contact);

		status.setError(null);
		status.setErrorCode(null);

		final Icon icon = new Icon();
		icon.setOpen("http://example.com/images/status_auf_48px.png");
		icon.setClosed("http://example.com/images/status_zu_48px.png");
		status.setIcon(icon);

		status.setLat(53.08177947998047);
		status.setLogo("http://example.com/images/hackerspace_icon.png");
		status.setLon(8.805830955505371);

		status.setLastchange(1420661704902L / 1000L);
		
		final Status statusInfo = new Status();
		statusInfo.setMessage("po");
		statusInfo.setStatus("CLOSED");
		statusInfo.setTime(String.valueOf(1420661704902L));
		status.setResult(statusInfo);

		status.setSpace("Hackerspace Bremen e.V.");
		status.setSuccess("Status found");
		status.setUrl("http://www.hackerspace-bremen.de");

		return status;
	}
}
