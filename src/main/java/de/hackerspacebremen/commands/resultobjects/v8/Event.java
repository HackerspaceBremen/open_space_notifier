package de.hackerspacebremen.commands.resultobjects.v8;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.hackerspacebremen.commands.resultobjects.v13.EventV13;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
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
