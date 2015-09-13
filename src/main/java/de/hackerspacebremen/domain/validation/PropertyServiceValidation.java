package de.hackerspacebremen.domain.validation;

import org.springframework.stereotype.Component;

@Component
public class PropertyServiceValidation extends Validation {

	public void findValueByKey(final String key) throws ValidationException {
		this.validateIfEmpty(key, 23);
	}

	public void validateGcm(final boolean gcmEnabled, final String gcmKey) {
		if (gcmEnabled) {
			this.validateIfEmpty(gcmKey, 23);
		}
	}

	public void validateApns(final boolean apnsEnabled, final String apnsPassword, final String apnsCertificate) {
		if (apnsEnabled) {
			this.validateIfEmpty(apnsCertificate, 25);
			this.validateIfEmpty(apnsPassword, 24);
		}
	}

	public void saveAPNSCertificate(final String apnsFileKeyString) {
		this.validateIfEmpty(apnsFileKeyString, 24);
	}
}