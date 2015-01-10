package de.hackerspacebremen.commands.resultobjects.v13.sensors;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@JsonInclude(Include.NON_NULL)
@EqualsAndHashCode(callSuper=false)
public final class PresentPeople extends SensorInformation{

	private Integer value;
	
	private List<String> names;
}
