package de.hackerspacebremen.commands.resultobject.v9;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import de.hackerspacebremen.commands.resultobject.v13.contact.ContactV13;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
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
	
	public Contact(final ContactV13 contact){
		this.phone = contact.getPhone();
		this.sip = contact.getSip();
		this.keymaster = contact.getKeymaster();
		this.irc = contact.getIrc();
		this.twitter = contact.getTwitter();
		this.email = contact.getEmail();
		this.mailinglist = contact.getMailinglist();
		this.jabber = contact.getJabber();
	}
}
