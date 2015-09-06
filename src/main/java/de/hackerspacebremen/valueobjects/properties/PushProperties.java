package de.hackerspacebremen.valueobjects.properties;

import static de.hackerspacebremen.common.PropertyConstants.APNS_ENABLED;
import static de.hackerspacebremen.common.PropertyConstants.APNS_PASSWORD;
import static de.hackerspacebremen.common.PropertyConstants.GCM_ENABLED;
import static de.hackerspacebremen.common.PropertyConstants.GCM_KEY;
import static de.hackerspacebremen.common.PropertyConstants.MPNS_ENABLED;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class PushProperties {

	@DataProperty(key=GCM_ENABLED, defaultValue="false")
	private boolean gcmEnabled;
	
	@DataProperty(key=GCM_KEY)
	private String gcmApiKey;
	
	@DataProperty(key=APNS_ENABLED, defaultValue="false")
	private boolean apnsEnabled;
	
	@DataProperty(key=MPNS_ENABLED, defaultValue="false")
	private boolean mpnsEnabled;
	
	@DataProperty(key=APNS_PASSWORD)
	private String apnsPassword;
}
