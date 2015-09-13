package de.hackerspacebremen.commands.resultobject.v13.sensors;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SpeedUnit {

	METER_PER_SECONDS("m/s"),
	
	KILOMETER_PER_HOUR("km/h");
	
	//TODO more: kn
	
	private final String value;
	
	private SpeedUnit(final String value){
		this.value = value;
	}
	
	@JsonValue
	public String getValue(){
		return this.value;
	}
}
