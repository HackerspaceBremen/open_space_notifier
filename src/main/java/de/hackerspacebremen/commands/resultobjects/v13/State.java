package de.hackerspacebremen.commands.resultobjects.v13;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.hackerspacebremen.commands.resultobjects.v11.Icon;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public final class State {

	private boolean open;
	
	private Long lastchange;
	
	@JsonProperty("trigger_person")
	private String triggerPerson;
	
	private String message;
	
	private Icon icon;
}
