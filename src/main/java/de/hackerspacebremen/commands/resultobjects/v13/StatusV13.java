package de.hackerspacebremen.commands.resultobjects.v13;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import de.hackerspacebremen.commands.resultobjects.AbstractResult;
import de.hackerspacebremen.commands.resultobjects.IStatus;
import de.hackerspacebremen.common.SpaceAPIVersion;

@Data
@EqualsAndHashCode(callSuper=false)
public final class StatusV13 extends AbstractResult implements IStatus {

	private final SpaceAPIVersion api = SpaceAPIVersion.API_0_13;
	
	private String space;
	
	private String logo;
	
	private String url;
	
	private LocationV13 location;
	
	private SpaceFEDV13 spacefed;
	
	private List<String> cam;
	
	private StreamV13 stream;
	
	private StateV13 state;
	
	private List<EventV13> events;
	
	private ContactV13 contact;
	
	@JsonProperty("issue_report_channels")
	private List<IssueReport> issueReportChannels;
	
	// TODO fill SensorsV13
	private SensorsV13 sensors;
	
	// TODO add feeds, cache, projects and radio_show
}
