package de.hackerspacebremen.commands.resultobjects.v13.contact;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonProperty;

import de.hackerspacebremen.commands.resultobjects.v9.Contact;

@Data
@EqualsAndHashCode(callSuper=false)
public final class ContactV13 extends Contact{

	private String facebook;
	
	private Google google;
	
	private String identica;
	
	private String foursquare;
	
	@JsonProperty("issue_mail")
	private String issueMail;
}
