package de.hackerspacebremen.commands.resultobject.v13.sensors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public final class Properties {
	
	private SpeedProperty speed;
	
	private SpeedProperty gust;
	
	private DirectionProperty direction;
	
	private ElevationProperty elevation;
}
