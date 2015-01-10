package de.hackerspacebremen.commands.resultobjects.v9;

import java.util.List;
import java.util.Map;

import de.hackerspacebremen.commands.resultobjects.BasicResultObject;
import de.hackerspacebremen.commands.resultobjects.IStatus;
import de.hackerspacebremen.commands.resultobjects.v13.StatusV13;
import de.hackerspacebremen.commands.resultobjects.v8.Event;
import de.hackerspacebremen.common.SpaceAPIVersion;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public final class StatusV9 extends BasicResultObject implements IStatus{
	
	private final SpaceAPIVersion api = SpaceAPIVersion.API_0_09;

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
	
	public StatusV9(final StatusV13 status) {
		// TODO
	}
}
