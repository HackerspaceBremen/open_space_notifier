package de.hackerspacebremen.commands.resultobjects.v12;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ContactV12 {

	private String phone;
	
	private String sip;
	
	private List<String> keymaster;
	
	private String irc;
	
	private String twitter;
	
	private String email;
	
	private String ml;
	
	private String jabber;
}
