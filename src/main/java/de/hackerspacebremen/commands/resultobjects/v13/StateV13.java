package de.hackerspacebremen.commands.resultobjects.v13;

import com.fasterxml.jackson.annotation.JsonProperty;

import de.hackerspacebremen.commands.resultobjects.v12.IconV12;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public final class StateV13 {

	private boolean open;
	
	private long lastchange;
	
	@JsonProperty("trigger_person")
	private String triggerPerson;
	
	private String message;
	
	private IconV12 icon;
}
