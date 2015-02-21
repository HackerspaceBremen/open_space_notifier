package de.hackerspacebremen.valueobjects.properties;

import static de.hackerspacebremen.common.PropertyConstants.*;
import lombok.Data;

@Data
public final class CertificateProperties {
	
	@DataProperty(key=APNS_FILE_KEY_STRING)
	private String apnsCertificate;
	
}
