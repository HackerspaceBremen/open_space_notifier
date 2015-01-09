package de.hackerspacebremen.domain.api;

import de.hackerspacebremen.domain.val.ValidationException;
import de.hackerspacebremen.valueobjects.CertificateProperties;
import de.hackerspacebremen.valueobjects.EmailProperties;
import de.hackerspacebremen.valueobjects.PushProperties;

public interface PropertyService {

	PushProperties fetchPushProperties();

	CertificateProperties fetchCertificateProperties();

	EmailProperties fetchEmailProperties();

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

	String findValueByKey(String key) throws ValidationException;
}
