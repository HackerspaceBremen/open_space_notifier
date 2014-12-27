package de.hackerspacebremen.domain;

import static de.hackerspacebremen.common.PropertyConstants.*;
import static de.hackerspacebremen.common.EmailDefaults.*;

import com.google.inject.Inject;
import com.google.inject.Provider;

import de.hackerspacebremen.data.api.PropertyDAO;
import de.hackerspacebremen.data.entities.Property;
import de.hackerspacebremen.domain.api.PropertyService;
import de.hackerspacebremen.domain.val.ValidationException;
import de.hackerspacebremen.exceptions.NotCompletelyConfigured;
import de.hackerspacebremen.valueobjects.CertificateProperties;
import de.hackerspacebremen.valueobjects.EmailProperties;
import de.hackerspacebremen.valueobjects.PushProperties;

public class PropertyServiceImpl implements PropertyService {

	@Inject
	private PropertyDAO propertyDAO;

	@Inject
	private Provider<PushProperties> pushProperties;

	@Inject
	private Provider<EmailProperties> emailProperties;

	@Inject
	private Provider<CertificateProperties> certificateProperties;

	@Override
	public String findValueByKey(final String key) throws ValidationException {
		Property property = propertyDAO.findByKey(key);
		if (property == null) {
			property = new Property();
			property.setKey(key);
			property.setValue("");
			propertyDAO.persist(property);
			throw new NotCompletelyConfigured();
		}
		return property.getValue();
	}

	@Override
	public PushProperties fetchPushProperties() {
		final PushProperties properties = pushProperties.get();
		properties.setGcmEnabled(Boolean.valueOf(findProperty(GCM_ENABLED,
				"false").getValue()));
		properties.setGcmApiKey(findProperty(GCM_KEY, "").getValue());
		properties.setApnsEnabled(Boolean.valueOf(findProperty(APNS_ENABLED,
				"false").getValue()));
		properties.setApnsPassword(findProperty(APNS_PASSWORD, "").getValue());
		properties.setMpnsEnabled(Boolean.valueOf(findProperty(MPNS_ENABLED,
				"false").getValue()));
		properties.setMailEnabled(Boolean.valueOf(findProperty(MAIL_ENABLED,
				"false").getValue()));
		return properties;
	}

	@Override
	public EmailProperties fetchEmailProperties() {
		final EmailProperties properties = emailProperties.get();
		properties.setSenderName(findProperty(EMAIL_SENDER_NAME,
				EMAIL_DEFAULT_SENDER_NAME).getValue());
		properties.setReceiverName(findProperty(EMAIL_RECEIVER_NAME,
				EMAIL_DEFAULT_RECEIVER_NAME).getValue());
		properties.setSubjectTag(findProperty(EMAIL_SUBJECT_TAG,
				EMAIL_DEFAULT_SUBJECT_TAG).getValue());
		properties.setSubjectOpened(findProperty(EMAIL_SUBJECT_OPENED,
				EMAIL_DEFAULT_SUBJECT_OPENED).getValue());
		properties.setSubjectClosed(findProperty(EMAIL_SUBJECT_CLOSED,
				EMAIL_DEFAULT_SUBJECT_CLOSED).getValue());
		// properties.setContentPart1(findProperty(EMAIL_CONTENT_PART1,
		// EMAIL_DEFAULT_CONTENT_PART1).getValue());
		// properties.setContentPart2(findProperty(EMAIL_CONTENT_PART2,
		// EMAIL_DEFAULT_CONTENT_PART2).getValue());
		// properties.setContentPart3(findProperty(EMAIL_CONTENT_PART3,
		// EMAIL_DEFAULT_CONTENT_PART3).getValue());
		// properties.setContentPart4(findProperty(EMAIL_CONTENT_PART4,
		// EMAIL_DEFAULT_CONTENT_PART4).getValue());
		properties
				.setContent(findProperty(EMAIL_CONTENT, EMAIL_DEFAULT_CONTENT)
						.getValue());
		properties.setOpened(findProperty(EMAIL_OPENED, EMAIL_DEFAULT_OPENED)
				.getValue());
		properties.setClosed(findProperty(EMAIL_CLOSED, EMAIL_DEFAULT_CLOSED)
				.getValue());
		properties
				.setMessage(findProperty(EMAIL_MESSAGE, EMAIL_DEFAULT_MESSAGE)
						.getValue());
		properties.setNegatedOpened(findProperty(EMAIL_NEGATED_OPENED,
				EMAIL_DEFAULT_NEGATED_OPENED).getValue());
		properties.setNegatedClosed(findProperty(EMAIL_NEGATED_CLOSED,
				EMAIL_DEFAULT_NEGATED_CLOSED).getValue());
		return properties;
	}

	private Property findProperty(final String key, final String defaultValue) {
		Property property = propertyDAO.findByKey(key);
		if (property == null) {
			property = new Property();
			property.setKey(key);
			property.setValue(defaultValue);
			property = propertyDAO.persist(property);
		}
		return property;
	}

	@Override
	public PushProperties savePushProperties(boolean gcmEnabled,
			boolean apnsEnabled, boolean mpnsEnabled, String gcmKey,
			final String apnsPassword) {

		this.saveProperty(GCM_ENABLED, String.valueOf(gcmEnabled));
		this.saveProperty(APNS_ENABLED, String.valueOf(apnsEnabled));
		this.saveProperty(MPNS_ENABLED, String.valueOf(mpnsEnabled));
		this.saveProperty(GCM_KEY, gcmKey);
		this.saveProperty(APNS_PASSWORD, apnsPassword);

		return this.fetchPushProperties();
	}

	private void saveProperty(final String key, final String value) {
		final Property property = propertyDAO.findByKey(key);
		property.setValue(value);
		propertyDAO.persist(property);
	}

	@Override
	public CertificateProperties saveAPNSCertificate(
			final String apnsFileKeyString) throws ValidationException {
		this.saveProperty(APNS_FILE_KEY_STRING, apnsFileKeyString);

		return this.fetchCertificateProperties();
	}

	@Override
	public CertificateProperties fetchCertificateProperties() {
		final CertificateProperties properties = certificateProperties.get();

		properties.setApnsCertificate(findProperty(APNS_FILE_KEY_STRING, "")
				.getValue());
		return properties;
	}

	@Override
	public EmailProperties saveEmailProperties(final String senderName,
			final String receiverName, final String subjectTag,
			final String subjectOpened, final String subjectClosed,
			final String content, final String opened, final String closed,
			final String negatedOpened, final String negatedClosed)
			throws ValidationException {
		this.saveProperty(EMAIL_SENDER_NAME, String.valueOf(senderName));
		this.saveProperty(EMAIL_RECEIVER_NAME, String.valueOf(receiverName));
		this.saveProperty(EMAIL_SUBJECT_TAG, String.valueOf(subjectTag));
		this.saveProperty(EMAIL_SUBJECT_OPENED, String.valueOf(subjectOpened));
		this.saveProperty(EMAIL_SUBJECT_CLOSED, String.valueOf(subjectClosed));
		this.saveProperty(EMAIL_CONTENT, String.valueOf(content));
		this.saveProperty(EMAIL_OPENED, String.valueOf(opened));
		this.saveProperty(EMAIL_CLOSED, String.valueOf(closed));
		this.saveProperty(EMAIL_NEGATED_OPENED, String.valueOf(negatedOpened));
		this.saveProperty(EMAIL_NEGATED_CLOSED, String.valueOf(negatedClosed));
		
		return this.fetchEmailProperties();
	}

}
