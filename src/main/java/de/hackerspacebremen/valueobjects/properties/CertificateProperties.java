package de.hackerspacebremen.valueobjects.properties;

import static de.hackerspacebremen.common.PropertyConstants.APNS_FILE_KEY_STRING;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class CertificateProperties {
	
	@DataProperty(key=APNS_FILE_KEY_STRING)
	private String apnsCertificate;
	
}
