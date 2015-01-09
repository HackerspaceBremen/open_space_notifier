package de.hackerspacebremen.commands.resultobjects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Setter;
import lombok.Getter;

@JsonInclude(Include.NON_NULL)
public class AbstractResult {

	/**
	 * SUCCESS.
	 */
	@Getter
	@Setter
	@JsonProperty("SUCCESS")
	private String success;
	
	/**
	 * ERROR.
	 */
	@Getter
	@Setter
	@JsonProperty("ERROR")
	private String error;
	
	/**
	 * CODE.
	 */
	@Getter
	@Setter
	@JsonProperty("CODE")
	private Integer errorCode;
}
