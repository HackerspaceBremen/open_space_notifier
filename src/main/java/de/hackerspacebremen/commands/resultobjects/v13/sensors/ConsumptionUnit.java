package de.hackerspacebremen.commands.resultobjects.v13.sensors;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ConsumptionUnit {

	WATT("W");
	
	// TODO more mW, VA
	
	private String value;
	
	private ConsumptionUnit(final String value){
		this.value = value;
	}
	
	@JsonValue
	public String getValue(){
		return this.value;
	}
}
