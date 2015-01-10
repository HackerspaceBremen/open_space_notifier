package de.hackerspacebremen.commands.resultobjects.v11;

import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;
import de.hackerspacebremen.commands.resultobjects.BasicResultObject;
import de.hackerspacebremen.commands.resultobjects.IStatus;
import de.hackerspacebremen.commands.resultobjects.v13.StatusV13;
import de.hackerspacebremen.commands.resultobjects.v8.Event;
import de.hackerspacebremen.commands.resultobjects.v9.Contact;
import de.hackerspacebremen.common.SpaceAPIVersion;


@Data
@EqualsAndHashCode(callSuper=false)
public final class StatusV11 extends BasicResultObject implements IStatus{

	private final SpaceAPIVersion api = SpaceAPIVersion.API_0_11;
	
	private String space;
	
	private String logo;
	
	private String url;
	
	private String address;
	
	private Double lat;
	
	private Double lon;
	
	private List<String> cam;
	
	private Map<String, String> stream;
	
	private boolean open;
	
	private String status;
	
	private Long lastchange;
	
	private List<Event> events;
	
	private Contact contact;
	
	private Icon icon;

	public StatusV11(final StatusV13 status) {
		// TODO
	}
}
