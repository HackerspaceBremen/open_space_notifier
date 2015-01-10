package de.hackerspacebremen.commands.resultobjects.v13.sensors;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Currency {

	EURO("EUR"),
	
	US_DOLLAR("USD"),
	
	POUNDS("GBP"),
	
	BITCOIN("BTC");

	private String value;
	
	private Currency(final String value){
		this.value = value;
	}
	
	@JsonValue
	public String getValue(){
		return this.value;
	}
}
