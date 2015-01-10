package de.hackerspacebremen.commands.resultobjects.v13;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import de.hackerspacebremen.commands.resultobjects.v11.Icon;

@Data
@JsonInclude(Include.NON_NULL)
@EqualsAndHashCode(callSuper=false)
public final class State {

	private boolean open;
	
	private Long lastchange;
	
	@JsonProperty("trigger_person")
	private String triggerPerson;
	
	private String message;
	
	private Icon icon;
}
