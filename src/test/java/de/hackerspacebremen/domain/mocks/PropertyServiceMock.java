package de.hackerspacebremen.domain.mocks;

import de.hackerspacebremen.domain.api.PropertyService;
import de.hackerspacebremen.domain.val.ValidationException;
import de.hackerspacebremen.valueobjects.CertificateProperties;
import de.hackerspacebremen.valueobjects.EmailProperties;
import de.hackerspacebremen.valueobjects.PushProperties;

public final class PropertyServiceMock implements PropertyService{

	@Override
	public PushProperties fetchPushProperties() {
		return new PushProperties();
	}

	@Override
	public CertificateProperties fetchCertificateProperties() {
		return new CertificateProperties();
	}

	@Override
	public PushProperties savePushProperties(final boolean gcmEnabled,
			final boolean apnsEnabled, final boolean mpnsEnabled, final String gcmKey,
			final String apnsPassword) throws ValidationException {
		return new PushProperties();
	}

	@Override
	public CertificateProperties saveAPNSCertificate(final String apnsFileKeyString)
			throws ValidationException {
		return new CertificateProperties();
	}

	@Override
	public String findValueByKey(final String key) throws ValidationException {
		return "dummy";
	}

	@Override
	public EmailProperties fetchEmailProperties() {
		return new EmailProperties();
	}

}
