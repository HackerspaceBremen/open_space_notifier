package de.hackerspacebremen.commands.resultobjects.v8;

import java.util.List;
import java.util.Map;

import de.hackerspacebremen.commands.resultobjects.BasicResultObject;
import de.hackerspacebremen.commands.resultobjects.IStatus;
import de.hackerspacebremen.commands.resultobjects.v13.StatusV13;
import de.hackerspacebremen.common.SpaceAPIVersion;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public final class StatusV8 extends BasicResultObject implements IStatus{

	private final SpaceAPIVersion api = SpaceAPIVersion.API_0_08;
	
	private String space;
	
	private String logo;
	
	private String url;
	
	private String address;
	
	private String phone;
	
	private Double lon;
	
	private Double lat;
	
	private List<String> cam;
	
	private Map<String, String> stream;
	
	private boolean open;
	
	private String status;
	
	private Long lastchange;
	
	private List<Event> events;
	
	public StatusV8(final StatusV13 status) {
		// TODO
	}
}
