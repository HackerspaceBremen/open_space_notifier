package de.hackerspacebremen.commands.resultobjects.v12;

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
import de.hackerspacebremen.commands.resultobjects.v13.StatusV13;
import de.hackerspacebremen.commands.resultobjects.v8.Event;
import de.hackerspacebremen.commands.resultobjects.v9.Contact;
import de.hackerspacebremen.common.SpaceAPIVersion;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public final class StatusV12 extends BasicResultObject implements IStatus{

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
	
	public StatusV12(final StatusV13 statusV13, final Status status){
		// TODO
	}
	
	public String getStatus(){
		return this.result.getMessage();
	}
	
	public boolean isOpen(){
		return this.result.getStatus().equals("OPEN");
	}
	
	public Long getLastchange(){
		return Long.valueOf(Long.valueOf(this.result.getTime()) / 1000L);
	}
}
