package de.hackerspacebremen.domain.api;

import de.hackerspacebremen.domain.val.ValidationException;
import de.hackerspacebremen.valueobjects.properties.CertificateProperties;
import de.hackerspacebremen.valueobjects.properties.EmailProperties;
import de.hackerspacebremen.valueobjects.properties.GeneralProperties;
import de.hackerspacebremen.valueobjects.properties.PushProperties;

public interface PropertyService {
	
	<P> P fetchProperties(Class<P> propertyClass);

	PushProperties savePushProperties(final boolean gcmEnabled,
			final boolean apnsEnabled, final boolean mpnsEnabled,
			final String gcmKey, final String apnsPassword)
			throws ValidationException;

	EmailProperties saveEmailProperties(final boolean mailEnabled,
			final String senderName, final String receiverName,
			final String subjectTag, final String subjectOpened,
			final String subjectClosed, final String message,
			final String content, final String opened, final String closed,
			final String negatedOpened, final String negatedClosed)
			throws ValidationException;

	CertificateProperties saveAPNSCertificate(final String apnsFileKeyString)
			throws ValidationException;
	
	GeneralProperties saveGeneralProperties(final String spaceName, 
		String url, String locationAddress, double locationLatitude,
		double locationLongitude,String twitter, String facebookUrl,
		String googlePlusUrl, String identicaUrl, String foursquareUrl,
		String email, String mailinglist, String issueMail, String phone,
		String sip, String irc, String jabber);

	String findValueByKey(String key) throws ValidationException;
}
