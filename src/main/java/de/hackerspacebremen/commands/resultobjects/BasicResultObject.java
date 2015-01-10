package de.hackerspacebremen.commands.resultobjects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

@Data
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
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
	
	public BasicResultObject(final String success){
		this.success = success;
	}
}
