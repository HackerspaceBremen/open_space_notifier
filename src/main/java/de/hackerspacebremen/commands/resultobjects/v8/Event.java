package de.hackerspacebremen.commands.resultobjects.v8;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@JsonInclude(Include.NON_NULL)
@EqualsAndHashCode(callSuper=false)
public class Event {

	private String name;
	
	private String type;
	
	@JsonProperty("t")
	private long timestamp;
	
	private String extra;
}
