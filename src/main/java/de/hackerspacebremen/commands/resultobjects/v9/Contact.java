package de.hackerspacebremen.commands.resultobjects.v9;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@JsonInclude(Include.NON_NULL)
@EqualsAndHashCode(callSuper=false)
public class Contact {

	private String phone;
	
	private String sip;
	
	private List<String> keymaster;
	
	private String irc;
	
	private String twitter;
	
	private String email;
	
	@JsonProperty("ml")
	private String mailinglist;
	
	private String jabber;
}
