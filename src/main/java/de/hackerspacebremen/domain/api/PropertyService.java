package de.hackerspacebremen.domain.api;

import de.hackerspacebremen.domain.val.ValidationException;
import de.hackerspacebremen.valueobjects.CertificateProperties;
import de.hackerspacebremen.valueobjects.PushProperties;

public interface PropertyService {

	PushProperties fetchPushProperties();
	
	CertificateProperties fetchCertificateProperties();
	
	PushProperties savePushProperties(final boolean gcmEnabled, 
			final boolean apnsEnabled, final boolean mpnsEnabled,
			final String gcmKey, final String apnsPassword) 
					throws ValidationException;
	
	CertificateProperties saveAPNSCertificate(
			final String apnsFileKeyString) throws ValidationException;
	
	String findValueByKey(String key) throws ValidationException;
}
