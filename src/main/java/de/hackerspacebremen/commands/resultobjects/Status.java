package de.hackerspacebremen.commands.resultobjects;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

public final class Status {

	/**
	 * ST2.
	 */
	@Getter
	@Setter
	@JsonProperty("ST2")
	private String time;
	
	/**
	 * ST3.
	 */
	@Getter
	@Setter
	@JsonProperty("ST3")
	private String status;
	
	/**
	 * ST5.
	 */
	@Getter
	@Setter
	@JsonProperty("ST5")
	private String message;
}
