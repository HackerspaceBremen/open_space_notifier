package de.hackerspacebremen.commands.resultobject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class BasicResultObject {

	@JsonProperty("SUCCESS")
	private String success;
	
	@Getter
	@Setter
	@JsonProperty("ERROR")
	private String error;
	
	@Getter
	@Setter
	@JsonProperty("CODE")
	private Integer errorCode;
	
	@JsonProperty("WARN")
	private String warn;
	
	@JsonProperty("STACK_TRACE")
	private String stackTrace;
	
	public BasicResultObject(final String success){
		this.success = success;
	}
	
	public BasicResultObject(final int errorCode, final String error){
		this.errorCode = Integer.valueOf(errorCode);
		this.error = error;
	}
}

