package de.hackerspacebremen.commands.resultobjects.v11;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@JsonInclude(Include.NON_NULL)
@EqualsAndHashCode(callSuper=false)
public class Icon {

	private String open;
	
	private String closed;
}
