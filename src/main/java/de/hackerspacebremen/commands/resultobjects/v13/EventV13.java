package de.hackerspacebremen.commands.resultobjects.v13;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@JsonInclude(Include.NON_NULL)
@EqualsAndHashCode(callSuper=false)
public final class EventV13 {

	private String name;
	
	private String type;
	
	private long timestamp;
	
	private String extra;
}
