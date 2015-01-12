package de.hackerspacebremen.commands.resultobjects;

import org.testng.annotations.Test;

import com.google.appengine.api.datastore.Text;

import de.hackerspacebremen.data.entities.SpaceStatus;

@Test
public final class StatusTest {

	public void withMessage() {
		final Status test = new Status(testData());
		
		// TODO
	}

	public void withoutMessage() {
		final SpaceStatus spaceStatus = this.testData();
		spaceStatus.setMessage(null);
		
		final Status test = new Status(testData());
		
		// TODO
		
		spaceStatus.setMessage(new Text(""));
		final Status testEmpty = new Status(testData());
		
		// TODO
	}

	public void formattedTime() {
		// TODO
	}

	private SpaceStatus testData() {
		final SpaceStatus status = new SpaceStatus();
		status.setMessage(new Text("the space is now open"));
		status.setOpenedBy("testmethod");
		status.setStatus("OPEN");
		// status.setTime(time); TODO
		
		return status;
	}
}
