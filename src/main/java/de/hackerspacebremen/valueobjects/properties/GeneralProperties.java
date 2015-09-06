package de.hackerspacebremen.valueobjects.properties;

import static de.hackerspacebremen.common.PropertyConstants.GENERAL_CONTACT_EMAIL;
import static de.hackerspacebremen.common.PropertyConstants.GENERAL_CONTACT_IRC;
import static de.hackerspacebremen.common.PropertyConstants.GENERAL_CONTACT_ISSUE_MAIL;
import static de.hackerspacebremen.common.PropertyConstants.GENERAL_CONTACT_JABBER;
import static de.hackerspacebremen.common.PropertyConstants.GENERAL_CONTACT_MAILINGLIST;
import static de.hackerspacebremen.common.PropertyConstants.GENERAL_CONTACT_PHONE;
import static de.hackerspacebremen.common.PropertyConstants.GENERAL_CONTACT_SIP;
import static de.hackerspacebremen.common.PropertyConstants.GENERAL_LOCATION_ADDRESS;
import static de.hackerspacebremen.common.PropertyConstants.GENERAL_LOCATION_LATITUDE;
import static de.hackerspacebremen.common.PropertyConstants.GENERAL_LOCATION_LONGITUDE;
import static de.hackerspacebremen.common.PropertyConstants.GENERAL_SOCIAL_FACEBOOK;
import static de.hackerspacebremen.common.PropertyConstants.GENERAL_SOCIAL_FOURSQUARE;
import static de.hackerspacebremen.common.PropertyConstants.GENERAL_SOCIAL_GPLUS;
import static de.hackerspacebremen.common.PropertyConstants.GENERAL_SOCIAL_IDENTICA;
import static de.hackerspacebremen.common.PropertyConstants.GENERAL_SOCIAL_TWITTER;
import static de.hackerspacebremen.common.PropertyConstants.GENERAL_SPACE_NAME;
import static de.hackerspacebremen.common.PropertyConstants.GENERAL_URL;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class GeneralProperties {

	@DataProperty(key=GENERAL_SPACE_NAME)
	private String spaceName;
	
	@DataProperty(key=GENERAL_URL)
	private String url;
	
	// location
	
	@DataProperty(key=GENERAL_LOCATION_ADDRESS)
	private String locationAddress;
	
	@DataProperty(key=GENERAL_LOCATION_LATITUDE, defaultValue="0.0")
	private double locationLatitude;
	
	@DataProperty(key=GENERAL_LOCATION_LONGITUDE, defaultValue="0.0")
	private double locationLongitude;
	
	// social networks
	
	@DataProperty(key=GENERAL_SOCIAL_TWITTER)
	private String twitter;
	
	@DataProperty(key=GENERAL_SOCIAL_FACEBOOK)
	private String facebookUrl;
	
	@DataProperty(key=GENERAL_SOCIAL_GPLUS)
	private String googlePlusUrl;
	
	@DataProperty(key=GENERAL_SOCIAL_IDENTICA)
	private String identicaUrl;
	
	@DataProperty(key=GENERAL_SOCIAL_FOURSQUARE)
	private String foursquareUrl;
	
	// contact
	
	@DataProperty(key=GENERAL_CONTACT_EMAIL)
	private String email;
	
	@DataProperty(key=GENERAL_CONTACT_MAILINGLIST)
	private String mailinglist;
	
	@DataProperty(key=GENERAL_CONTACT_ISSUE_MAIL)
	private String issueMail;
	
	@DataProperty(key=GENERAL_CONTACT_PHONE)
	private String phone;
	
	@DataProperty(key=GENERAL_CONTACT_SIP)
	private String sip;
	
	@DataProperty(key=GENERAL_CONTACT_IRC)
	private String irc;
	
	@DataProperty(key=GENERAL_CONTACT_JABBER)
	private String jabber;
}
