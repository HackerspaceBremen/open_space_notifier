package de.hackerspacebremen.commands.resultobject.v13.sensors;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SupplyUnit {

	BOTTLES("btl"),
	
	CRATES("crt");
	
	private String value;
	
	private SupplyUnit(final String value){
		this.value = value;
	}
	
	@JsonValue
	public String getValue(){
		return this.value;
	}
}
