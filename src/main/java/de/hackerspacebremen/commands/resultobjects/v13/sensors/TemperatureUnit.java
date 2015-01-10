package de.hackerspacebremen.commands.resultobjects.v13.sensors;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TemperatureUnit {
 
	CELSIUS("°C"),
	
	FAHRENHEIT("°F"),
	
	KELVIN("K"),
	
	RANKINE("°R"),
	
	DELISLE("°De"),
	
	NEWTON("°N"),
	
	RÉAUMUR("°Ré"),
	
	ROMER("°Rø"),
	
	UNKNOWN("?");
	
	private String value;
	
	private TemperatureUnit(final String value){
		this.value = value;
	}
	
	@JsonValue
	public String getValue(){
		return this.value;
	}
}
