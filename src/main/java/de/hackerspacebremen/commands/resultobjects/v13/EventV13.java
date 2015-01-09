package de.hackerspacebremen.commands.resultobjects.v13;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public final class EventV13 {

	private String name;
	
	private String type;
	
	private long timestamp;
	
	private String extra;
}
