package de.hackerspacebremen.commands.resultobjects.v12;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonProperty;

import de.hackerspacebremen.commands.resultobjects.BasicResultObject;
import de.hackerspacebremen.commands.resultobjects.IStatus;
import de.hackerspacebremen.commands.resultobjects.Status;
import de.hackerspacebremen.commands.resultobjects.v11.Icon;
import de.hackerspacebremen.commands.resultobjects.v13.EventV13;
import de.hackerspacebremen.commands.resultobjects.v13.StatusV13;
import de.hackerspacebremen.commands.resultobjects.v8.Event;
import de.hackerspacebremen.commands.resultobjects.v9.Contact;
import de.hackerspacebremen.common.SpaceAPIVersion;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public final class StatusV12 extends BasicResultObject implements IStatus {

	private final SpaceAPIVersion api = SpaceAPIVersion.API_0_12;

	private String space;

	private String logo;

	private String url;

	private String address;

	private Double lat;

	private Double lon;

	private List<String> cam;

	private Map<String, String> stream;

	private List<Event> events;

	private Contact contact;

	private Icon icon;

	private List<Sensor> sensors;

	private List<Feed> feeds;

	@JsonProperty("RESULT")
	private Status result;
	
	private Long lastchange;

	public StatusV12(final StatusV13 statusV13, final Status status) {
		this.address = statusV13.getLocation().getAddress();
		this.cam = statusV13.getCam();
		this.contact = new Contact(statusV13.getContact());
		if (statusV13.getEvents() != null) {
			this.events = new ArrayList<>(statusV13.getEvents().size());
			for (final EventV13 eventV13 : statusV13.getEvents()) {
				this.events.add(new Event(eventV13));
			}
		}
		// TODO feeds
		this.icon = statusV13.getState().getIcon();
		this.lat = statusV13.getLocation().getLat();
		this.lon = statusV13.getLocation().getLon();
		this.logo = statusV13.getLogo();
		this.result = status;
		// TODO sensors
		this.space = statusV13.getSpace();
		// TODO stream
		this.url = statusV13.getUrl();
		this.lastchange = statusV13.getState().getLastchange();
	}

	public String getStatus() {
		return this.result.getMessage();
	}

	public boolean isOpen() {
		return this.result.getStatus().equals("OPEN");
	}
}
