package de.hackerspacebremen.commands.resultobject.v8;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hackerspacebremen.commands.resultobject.BasicResultObject;
import de.hackerspacebremen.commands.resultobject.IStatus;
import de.hackerspacebremen.commands.resultobject.v13.EventV13;
import de.hackerspacebremen.commands.resultobject.v13.StatusV13;
import de.hackerspacebremen.common.SpaceAPIVersion;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
		this.address = status.getLocation().getAddress();
		this.cam = status.getCam();
		if (status.getEvents() != null) {
			this.events = new ArrayList<>(status.getEvents().size());
			for (final EventV13 eventV13 : status.getEvents()) {
				this.events.add(new Event(eventV13));
			}
		}
		this.lastchange = status.getState().getLastchange();
		this.lat = status.getLocation().getLat();
		this.logo = status.getLogo();
		this.lon = status.getLocation().getLon();
		this.open = status.getState().isOpen();
		this.space = status.getSpace();
		this.status = status.getState().getMessage();
		if(status.getStream() != null){
			this.stream = new HashMap<>();
			if(status.getStream().getM4() != null){
				this.stream.put("m4", status.getStream().getM4());
			}
			if(status.getStream().getMjpeg() != null){
				this.stream.put("mjpeg", status.getStream().getMjpeg());
			}
			if(status.getStream().getUstream() != null){
				this.stream.put("ustream", status.getStream().getUstream());
			}
		}
		this.url = status.getUrl();
	}
}
