package de.hackerspacebremen.commands.resultobjects.v13.radioshow;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@JsonInclude(Include.NON_NULL)
@EqualsAndHashCode(callSuper=false)
public final class RadioShow {

	private String name;
	
	private String url;
	
	private StreamEncoder type;
	
	private String start;
	
	private String end;
}
