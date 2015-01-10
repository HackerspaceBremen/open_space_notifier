package de.hackerspacebremen.commands.resultobjects.v12;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@JsonInclude(Include.NON_NULL)
@EqualsAndHashCode(callSuper=false)
public final class Feed {

	private String name;
	
	private String type;
	
	private String url;
}
