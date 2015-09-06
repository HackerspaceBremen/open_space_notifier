package de.hackerspacebremen.commands.resultobjects.v13;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import de.hackerspacebremen.commands.resultobjects.BasicResultObject;
import de.hackerspacebremen.commands.resultobjects.IStatus;
import de.hackerspacebremen.commands.resultobjects.v13.cache.Cache;
import de.hackerspacebremen.commands.resultobjects.v13.contact.ContactV13;
import de.hackerspacebremen.commands.resultobjects.v13.feeds.Feeds;
import de.hackerspacebremen.commands.resultobjects.v13.radioshow.RadioShow;
import de.hackerspacebremen.commands.resultobjects.v13.sensors.Sensors;
import de.hackerspacebremen.common.SpaceAPIVersion;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class StatusV13 extends BasicResultObject implements IStatus {

	private final SpaceAPIVersion api = SpaceAPIVersion.API_0_13;
	
	private String space;
	
	private String logo;
	
	private String url;
	
	private Location location;
	
	private SpaceFED spacefed;
	
	private List<String> cam;
	
	private Stream stream;
	
	private State state;
	
	private List<EventV13> events;
	
	private ContactV13 contact;
	
	@JsonProperty("issue_report_channels")
	private List<IssueReport> issueReportChannels;
	
	private Sensors sensors;
	
	private Feeds feeds;
	
	private Cache cache;
	
	private List<String> projects;
	
	@JsonProperty("radio_show")
	private List<RadioShow> radioShow;
}
