package de.hackerspacebremen.domain.mocks;

import de.hackerspacebremen.domain.api.PropertyService;
import de.hackerspacebremen.domain.val.ValidationException;
import de.hackerspacebremen.valueobjects.properties.CertificateProperties;
import de.hackerspacebremen.valueobjects.properties.EmailProperties;
import de.hackerspacebremen.valueobjects.properties.GeneralProperties;
import de.hackerspacebremen.valueobjects.properties.PushProperties;

public final class PropertyServiceMock implements PropertyService {

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
			final boolean apnsEnabled, final boolean mpnsEnabled,
			final String gcmKey, final String apnsPassword)
			throws ValidationException {
		return new PushProperties();
	}

	@Override
	public CertificateProperties saveAPNSCertificate(
			final String apnsFileKeyString) throws ValidationException {
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

	@Override
	public EmailProperties saveEmailProperties(final boolean mailEnabled,
			final String senderName, final String receiverName,
			final String subjectTag, final String subjectOpened,
			final String subjectClosed, final String message,
			final String content, final String opened, final String closed,
			final String negatedOpened, final String negatedClosed)
			throws ValidationException {
		return new EmailProperties();
	}

	@Override
	public GeneralProperties fetchGeneralProperties() {
		return new GeneralProperties();
	}

	@Override
	public GeneralProperties saveGeneralProperties(String spaceName,
			String url, String locationAddress, double locationLatitude,
			double locationLongitude, String twitter, String facebookUrl,
			String googlePlusUrl, String identicaUrl, String foursquareUrl,
			String email, String mailinglist, String issueMail, String phone,
			String sip, String irc, String jabber) {
		return new GeneralProperties();
	}

}
