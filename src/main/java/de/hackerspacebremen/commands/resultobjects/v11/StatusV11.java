package de.hackerspacebremen.commands.resultobjects.v11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;
import de.hackerspacebremen.commands.resultobjects.BasicResultObject;
import de.hackerspacebremen.commands.resultobjects.IStatus;
import de.hackerspacebremen.commands.resultobjects.v13.EventV13;
import de.hackerspacebremen.commands.resultobjects.v13.StatusV13;
import de.hackerspacebremen.commands.resultobjects.v8.Event;
import de.hackerspacebremen.commands.resultobjects.v9.Contact;
import de.hackerspacebremen.common.SpaceAPIVersion;

@Data
@EqualsAndHashCode(callSuper = false)
public final class StatusV11 extends BasicResultObject implements IStatus {

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
		this.address = status.getLocation().getAddress();
		this.cam = status.getCam();
		this.contact = new Contact(status.getContact());
		if (status.getEvents() != null) {
			this.events = new ArrayList<>(status.getEvents().size());
			for (final EventV13 eventV13 : status.getEvents()) {
				this.events.add(new Event(eventV13));
			}
		}
		this.icon = status.getState().getIcon();
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
