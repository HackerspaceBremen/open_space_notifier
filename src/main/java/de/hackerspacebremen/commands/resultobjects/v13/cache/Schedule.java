package de.hackerspacebremen.commands.resultobjects.v13.cache;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Schedule {

	TWO_MINUTES("m.02"),
	
	FIVE_MINUTES("m.05"),
	
	TEN_MINUTES("m.10"),
	
	QUARTER_HOUR("m.15"),
	
	HALF_HOUR("m.30"),
	
	ONE_HOUR("h.01"),
	
	TWO_HOURS("h.02"),
	
	FOUR_HOURS("h.04"),
	
	EIGHT_HOURS("h.08"),
	
	HALF_DAY("h.12"),
	
	ONE_DAY("d.01");
	
	private String value;
	
	private Schedule(final String value){
		this.value = value;
	}
	
	@JsonValue
	public String getValue(){
		return this.value;
	}
}
