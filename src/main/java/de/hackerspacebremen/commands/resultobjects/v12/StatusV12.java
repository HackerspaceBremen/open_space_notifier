package de.hackerspacebremen.commands.resultobjects.v12;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonProperty;

import de.hackerspacebremen.commands.resultobjects.AbstractResult;
import de.hackerspacebremen.commands.resultobjects.IStatus;
import de.hackerspacebremen.commands.resultobjects.Status;
import de.hackerspacebremen.common.SpaceAPIVersion;

@Data
@EqualsAndHashCode(callSuper=false)
public final class StatusV12 extends AbstractResult implements IStatus{

	private final SpaceAPIVersion api = SpaceAPIVersion.API_0_12;
	
	private String space;
	
	private String url;
	
	private IconV12 icon;
	
	private String address;
	
	private ContactV12 contact;
	
	private String logo;
	
	private double lat;
	
	private double lon;
	
	@JsonProperty("RESULT")
	private Status result;
	
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
