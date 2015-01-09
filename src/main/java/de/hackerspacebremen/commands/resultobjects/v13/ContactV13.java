package de.hackerspacebremen.commands.resultobjects.v13;

import com.fasterxml.jackson.annotation.JsonProperty;

import de.hackerspacebremen.commands.resultobjects.v12.ContactV12;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public final class ContactV13  extends ContactV12{

	private String facebook;
	
	private GoogleV13 google;
	
	private String identica;
	
	private String foursquare;
	
	@JsonProperty("issue_mail")
	private String issueMail;
}
