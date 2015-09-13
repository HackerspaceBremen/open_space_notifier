package de.hackerspacebremen.commands.resultobject.v13.sensors;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ConnectionType {

	WIFI("wifi"),
	
	CABLE("cable"),
	
	SPACENET("spacenet");
	
	private String value;
	
	private ConnectionType(final String value){
		this.value = value;
	}
	
	@JsonValue
	public String getValue(){
		return this.value;
	}
}
