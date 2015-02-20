package de.hackerspacebremen.valueobjects.properties;

import lombok.Getter;
import lombok.Setter;

public final class PushProperties {

	@Getter
	@Setter
	private boolean gcmEnabled;
	
	@Getter
	@Setter
	private String gcmApiKey;
	
	@Getter
	@Setter
	private boolean apnsEnabled;
	
	@Getter
	@Setter
	private boolean mpnsEnabled;
	
	@Getter
	@Setter
	private String apnsPassword;
	
	public PushProperties(){
		gcmEnabled = false;
		gcmApiKey = "";
		apnsEnabled = false;
		mpnsEnabled = false;
		apnsPassword = "";
	}
}
