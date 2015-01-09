package de.hackerspacebremen.commands.resultobjects.v13;

import com.fasterxml.jackson.annotation.JsonValue;

public enum IssueReport {

	EMAIL("email"), 
	ISSUE_MAIL("issue_mail"), 
	TWITTER("twitter"), 
	MAILING_LIST("ml");
	
	private String value;
	
	private IssueReport(final String value){
		this.value = value;
	}
	
	@JsonValue
	public String getValue(){
		return this.value;
	}
}
