package de.hackerspacebremen.commands.resultobjects.v13.sensors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@JsonInclude(Include.NON_NULL)
@EqualsAndHashCode(callSuper=false)
public final class Properties {
	
	private SpeedProperty speed;
	
	private SpeedProperty gust;
	
	private DirectionProperty direction;
	
	private ElevationProperty elevation;
}
