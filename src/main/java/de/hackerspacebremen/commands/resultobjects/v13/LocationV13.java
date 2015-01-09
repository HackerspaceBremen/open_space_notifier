package de.hackerspacebremen.commands.resultobjects.v13;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class LocationV13 {

	private String address;
	
	private double lat;
	
	private double lon;
}
