package de.hackerspacebremen.commands.resultobjects.v13.sensors;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ElevationUnit {

	METER("m");
	
	private String value;
	
	private ElevationUnit(final String value){
		this.value = value;
	}
	
	@JsonValue
	public String getValue(){
		return this.value;
	}
}
