package de.hackerspacebremen.commands.resultobject.v13.sensors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public final class RadiationSensor extends SensorInformation{
	
	private Double value;
	
	private RadiationUnit unit;
	
	@JsonProperty("dead_time")
	private Double deadTime;
	
	@JsonProperty("conversion_factory")
	private Double conversionFactor;
	
	private String location;
	
	private String name;
	
	private String description;
}
