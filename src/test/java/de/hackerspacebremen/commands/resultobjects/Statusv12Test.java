package de.hackerspacebremen.commands.resultobjects;

import junit.framework.Assert;

import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.google.inject.Inject;

import de.hackerspacebremen.commands.resultobjects.v12.ContactV12;
import de.hackerspacebremen.commands.resultobjects.v12.IconV12;
import de.hackerspacebremen.commands.resultobjects.v12.StatusV12;
import de.hackerspacebremen.format.JacksonInstance;

@Guice
@Test
public final class Statusv12Test {

	@Inject
	private JacksonInstance jacksonInstance;
	
	public void formatTest() {
		final StatusV12 status = this.testData();
		
		final String test = this.jacksonInstance.write(status);
		Assert.assertNotNull(test);
	}

	private StatusV12 testData() {
		final StatusV12 status = new StatusV12();

		status.setAddress("Bornstraße 14/15, 28195 Bremen, Germany");

		final ContactV12 contact = new ContactV12();
		contact.setEmail("info@hackerspace-bremen.de");
		contact.setPhone("+49 421 14 62 92 15");
		contact.setTwitter("@hspacehb");
		status.setContact(contact);

		status.setError(null);
		status.setErrorCode(null);

		final IconV12 icon = new IconV12();
		icon.setOpen("http://example.com/images/status_auf_48px.png");
		icon.setClosed("http://example.com/images/status_zu_48px.png");
		status.setIcon(icon);

		status.setLat(53.08177947998047);
		status.setLogo("http://example.com/images/hackerspace_icon.png");
		status.setLon(8.805830955505371);

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
