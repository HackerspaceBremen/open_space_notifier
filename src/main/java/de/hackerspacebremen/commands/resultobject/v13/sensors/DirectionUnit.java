package de.hackerspacebremen.commands.resultobject.v13.sensors;

import com.fasterxml.jackson.annotation.JsonValue;

public enum DirectionUnit {

	DEGREE("°");
	
	private String value;
	
	private DirectionUnit(final String value){
		this.value = value;
	}
	
	@JsonValue
	public String getValue(){
		return this.value;
	}
}
