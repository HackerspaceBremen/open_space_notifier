package de.hackerspacebremen.commands.resultobjects.v8;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import de.hackerspacebremen.commands.resultobjects.v13.EventV13;
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
	
	public Event(final EventV13 eventV13) {
		this.name = eventV13.getName();
		this.type = eventV13.getType();
		this.timestamp = eventV13.getTimestamp();
		this.extra = eventV13.getExtra();
	}
}
