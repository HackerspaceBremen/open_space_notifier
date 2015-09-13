package de.hackerspacebremen.commands.resultobject.v13;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class Location {

	private String address;
	
	private double lat;
	
	private double lon;
}
