package de.hackerspacebremen.domain.val;

import com.google.inject.Inject;

import de.hackerspacebremen.domain.api.PropertyService;
import de.hackerspacebremen.valueobjects.properties.CertificateProperties;
import de.hackerspacebremen.valueobjects.properties.EmailProperties;
import de.hackerspacebremen.valueobjects.properties.GeneralProperties;
import de.hackerspacebremen.valueobjects.properties.PushProperties;

public class PropertyServiceValidation extends Validation implements
		PropertyService {

	@Inject
	private PropertyService propertyService;

	@Override
	public String findValueByKey(final String key) throws ValidationException {
		this.validateIfEmpty(key, 23);
		return propertyService.findValueByKey(key);
	}

	@Override
	public PushProperties fetchPushProperties() {
		return propertyService.fetchPushProperties();
	}

	@Override
	public PushProperties savePushProperties(final boolean gcmEnabled,
			final boolean apnsEnabled, final boolean mpnsEnabled,
			final String gcmKey, final String apnsPassword)
			throws ValidationException {
		if (gcmEnabled) {
			this.validateIfEmpty(gcmKey, 23);
		}
		if (apnsEnabled) {
			final String apnsCertificate = propertyService
					.findValueByKey("project.apns.file.key");
			this.validateIfEmpty(apnsCertificate, 25);
			this.validateIfEmpty(apnsPassword, 24);
		}
		return propertyService.savePushProperties(gcmEnabled, apnsEnabled,
				mpnsEnabled, gcmKey, apnsPassword);
	}

	@Override
	public CertificateProperties saveAPNSCertificate(
			final String apnsFileKeyString) throws ValidationException {
		this.validateIfEmpty(apnsFileKeyString, 24);
		return this.propertyService.saveAPNSCertificate(apnsFileKeyString);
	}

	@Override
	public CertificateProperties fetchCertificateProperties() {
		return propertyService.fetchCertificateProperties();
	}

	@Override
	public EmailProperties fetchEmailProperties() {
		return propertyService.fetchEmailProperties();
	}

	@Override
	public EmailProperties saveEmailProperties(final boolean mailEnabled,
			final String senderName, final String receiverName,
			final String subjectTag, final String subjectOpened,
			final String subjectClosed, final String message,
			final String content, final String opened, final String closed,
			final String negatedOpened, final String negatedClosed)
			throws ValidationException {
		// TODO validation
		return propertyService.saveEmailProperties(mailEnabled, senderName,
				receiverName, subjectTag, subjectOpened, subjectClosed,
				message, content, opened, closed, negatedOpened, negatedClosed);
	}

	@Override
	public GeneralProperties fetchGeneralProperties() {
		return propertyService.fetchGeneralProperties();
	}

	@Override
	public GeneralProperties saveGeneralProperties(String spaceName,
			String url, String locationAddress, double locationLatitude,
			double locationLongitude, String twitter, String facebookUrl,
			String googlePlusUrl, String identicaUrl, String foursquareUrl,
			String email, String mailinglist, String issueMail, String phone,
			String sip, String irc, String jabber) {
		// TODO validation
		return propertyService.saveGeneralProperties(spaceName, url,
				locationAddress, locationLatitude, locationLongitude, twitter,
				facebookUrl, googlePlusUrl, identicaUrl, foursquareUrl, email,
				mailinglist, issueMail, phone, sip, irc, jabber);
	}
}