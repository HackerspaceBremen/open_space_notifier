package de.hackerspacebremen.commands.resultobjects.v13.sensors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@JsonInclude(Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
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
