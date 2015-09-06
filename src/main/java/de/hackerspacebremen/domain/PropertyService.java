package de.hackerspacebremen.domain;

import static de.hackerspacebremen.common.PropertyConstants.APNS_ENABLED;
import static de.hackerspacebremen.common.PropertyConstants.APNS_FILE_KEY_STRING;
import static de.hackerspacebremen.common.PropertyConstants.APNS_PASSWORD;
import static de.hackerspacebremen.common.PropertyConstants.EMAIL_CLOSED;
import static de.hackerspacebremen.common.PropertyConstants.EMAIL_CONTENT;
import static de.hackerspacebremen.common.PropertyConstants.EMAIL_MESSAGE;
import static de.hackerspacebremen.common.PropertyConstants.EMAIL_NEGATED_CLOSED;
import static de.hackerspacebremen.common.PropertyConstants.EMAIL_NEGATED_OPENED;
import static de.hackerspacebremen.common.PropertyConstants.EMAIL_OPENED;
import static de.hackerspacebremen.common.PropertyConstants.EMAIL_RECEIVER_NAME;
import static de.hackerspacebremen.common.PropertyConstants.EMAIL_SENDER_NAME;
import static de.hackerspacebremen.common.PropertyConstants.EMAIL_SUBJECT_CLOSED;
import static de.hackerspacebremen.common.PropertyConstants.EMAIL_SUBJECT_OPENED;
import static de.hackerspacebremen.common.PropertyConstants.EMAIL_SUBJECT_TAG;
import static de.hackerspacebremen.common.PropertyConstants.GCM_ENABLED;
import static de.hackerspacebremen.common.PropertyConstants.GCM_KEY;
import static de.hackerspacebremen.common.PropertyConstants.GENERAL_CONTACT_EMAIL;
import static de.hackerspacebremen.common.PropertyConstants.GENERAL_CONTACT_IRC;
import static de.hackerspacebremen.common.PropertyConstants.GENERAL_CONTACT_ISSUE_MAIL;
import static de.hackerspacebremen.common.PropertyConstants.GENERAL_CONTACT_JABBER;
import static de.hackerspacebremen.common.PropertyConstants.GENERAL_CONTACT_MAILINGLIST;
import static de.hackerspacebremen.common.PropertyConstants.GENERAL_CONTACT_PHONE;
import static de.hackerspacebremen.common.PropertyConstants.GENERAL_CONTACT_SIP;
import static de.hackerspacebremen.common.PropertyConstants.GENERAL_LOCATION_ADDRESS;
import static de.hackerspacebremen.common.PropertyConstants.GENERAL_LOCATION_LATITUDE;
import static de.hackerspacebremen.common.PropertyConstants.GENERAL_LOCATION_LONGITUDE;
import static de.hackerspacebremen.common.PropertyConstants.GENERAL_SOCIAL_FACEBOOK;
import static de.hackerspacebremen.common.PropertyConstants.GENERAL_SOCIAL_FOURSQUARE;
import static de.hackerspacebremen.common.PropertyConstants.GENERAL_SOCIAL_GPLUS;
import static de.hackerspacebremen.common.PropertyConstants.GENERAL_SOCIAL_IDENTICA;
import static de.hackerspacebremen.common.PropertyConstants.GENERAL_SOCIAL_TWITTER;
import static de.hackerspacebremen.common.PropertyConstants.GENERAL_SPACE_NAME;
import static de.hackerspacebremen.common.PropertyConstants.GENERAL_URL;
import static de.hackerspacebremen.common.PropertyConstants.MAIL_ENABLED;
import static de.hackerspacebremen.common.PropertyConstants.MPNS_ENABLED;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.hackerspacebremen.data.entities.Property;
import de.hackerspacebremen.data.objectify.PropertyDao;
import de.hackerspacebremen.domain.val.PropertyServiceValidation;
import de.hackerspacebremen.domain.val.ValidationException;
import de.hackerspacebremen.exceptions.NotCompletelyConfigured;
import de.hackerspacebremen.valueobjects.properties.CertificateProperties;
import de.hackerspacebremen.valueobjects.properties.DataProperty;
import de.hackerspacebremen.valueobjects.properties.EmailProperties;
import de.hackerspacebremen.valueobjects.properties.GeneralProperties;
import de.hackerspacebremen.valueobjects.properties.PushProperties;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PropertyService {

	private PropertyDao propertyDao;
	private PropertyServiceValidation validation;

	@Autowired
	public PropertyService(PropertyDao propertyDao, PropertyServiceValidation propertyServiceValidation) {
		this.propertyDao = propertyDao;
		this.validation = propertyServiceValidation;
	}

	public String findValueByKey(final String key) {

		validation.findValueByKey(key);
		Property property = propertyDao.findByKey(key);
		if (property == null) {
			property = new Property();
			property.setKey(key);
			property.setValue("");
			propertyDao.persist(property);
			throw new NotCompletelyConfigured();
		}
		return property.getValue();
	}

	public <P> P fetchProperties(final Class<P> propertyClass) {
		P properties = null;
		try {
			properties = propertyClass.getConstructor().newInstance();
			this.fillProperties(properties);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			log.error("Exception occured while using Refelction", e);
		}

		return properties;
	}

	private <P> void fillProperties(final P properties) throws IllegalArgumentException, IllegalAccessException {
		final Field[] fields = properties.getClass().getDeclaredFields();

		for (final Field field : fields) {
			if (field.isAnnotationPresent(DataProperty.class)) {
				field.setAccessible(true);
				final DataProperty dataProperty = field.getAnnotation(DataProperty.class);
				final String propertyAsString = this.findProperty(dataProperty.key(), dataProperty.defaultValue())
						.getValue();
				if (field.getType() == boolean.class) {
					field.set(properties, Boolean.parseBoolean(propertyAsString));
				} else if (field.getType() == double.class) {
					field.set(properties, Double.parseDouble(propertyAsString));
				} else {
					field.set(properties, propertyAsString);
				}

				field.setAccessible(false);
			}
		}
	}

	private Property findProperty(final String key, final String defaultValue) {
		Property property = propertyDao.findByKey(key);
		if (property == null) {
			property = new Property();
			property.setKey(key);
			property.setValue(defaultValue);
			property = propertyDao.persist(property);
		}
		return property;
	}

	public PushProperties savePushProperties(boolean gcmEnabled, boolean apnsEnabled, boolean mpnsEnabled,
			String gcmKey, final String apnsPassword) {

		final String apnsCertificate = this.findValueByKey("project.apns.file.key");
		validation.validateGcm(gcmEnabled, gcmKey);
		validation.validateApns(apnsEnabled, apnsPassword, apnsCertificate);

		this.saveProperty(GCM_ENABLED, String.valueOf(gcmEnabled));
		this.saveProperty(APNS_ENABLED, String.valueOf(apnsEnabled));
		this.saveProperty(MPNS_ENABLED, String.valueOf(mpnsEnabled));
		this.saveProperty(GCM_KEY, gcmKey);
		this.saveProperty(APNS_PASSWORD, apnsPassword);

		return this.fetchProperties(PushProperties.class);
	}

	private void saveProperty(final String key, final String value) {
		final Property property = propertyDao.findByKey(key);
		property.setValue(value);
		propertyDao.persist(property);
	}

	public CertificateProperties saveAPNSCertificate(final String apnsFileKeyString) throws ValidationException {
		validation.saveAPNSCertificate(apnsFileKeyString);
		
		this.saveProperty(APNS_FILE_KEY_STRING, apnsFileKeyString);

		return this.fetchProperties(CertificateProperties.class);
	}

	public EmailProperties saveEmailProperties(final boolean mailEnabled, final String senderName,
			final String receiverName, final String subjectTag, final String subjectOpened, final String subjectClosed,
			final String message, final String content, final String opened, final String closed,
			final String negatedOpened, final String negatedClosed) throws ValidationException {
		// TODO validation
		
		this.saveProperty(MAIL_ENABLED, String.valueOf(mailEnabled));
		this.saveProperty(EMAIL_SENDER_NAME, String.valueOf(senderName));
		this.saveProperty(EMAIL_RECEIVER_NAME, String.valueOf(receiverName));
		this.saveProperty(EMAIL_SUBJECT_TAG, String.valueOf(subjectTag));
		this.saveProperty(EMAIL_SUBJECT_OPENED, String.valueOf(subjectOpened));
		this.saveProperty(EMAIL_SUBJECT_CLOSED, String.valueOf(subjectClosed));
		this.saveProperty(EMAIL_MESSAGE, String.valueOf(message));
		this.saveProperty(EMAIL_CONTENT, String.valueOf(content));
		this.saveProperty(EMAIL_OPENED, String.valueOf(opened));
		this.saveProperty(EMAIL_CLOSED, String.valueOf(closed));
		this.saveProperty(EMAIL_NEGATED_OPENED, String.valueOf(negatedOpened));
		this.saveProperty(EMAIL_NEGATED_CLOSED, String.valueOf(negatedClosed));

		return this.fetchProperties(EmailProperties.class);
	}

	public GeneralProperties saveGeneralProperties(String spaceName, String url, String locationAddress,
			double locationLatitude, double locationLongitude, String twitter, String facebookUrl, String googlePlusUrl,
			String identicaUrl, String foursquareUrl, String email, String mailinglist, String issueMail, String phone,
			String sip, String irc, String jabber) {
		// TODO validation
		
		this.saveProperty(GENERAL_CONTACT_EMAIL, email);
		this.saveProperty(GENERAL_CONTACT_IRC, irc);
		this.saveProperty(GENERAL_CONTACT_ISSUE_MAIL, issueMail);
		this.saveProperty(GENERAL_CONTACT_JABBER, jabber);
		this.saveProperty(GENERAL_CONTACT_MAILINGLIST, mailinglist);
		this.saveProperty(GENERAL_CONTACT_PHONE, phone);
		this.saveProperty(GENERAL_CONTACT_SIP, sip);
		this.saveProperty(GENERAL_LOCATION_ADDRESS, locationAddress);
		this.saveProperty(GENERAL_LOCATION_LATITUDE, String.valueOf(locationLatitude));
		this.saveProperty(GENERAL_LOCATION_LONGITUDE, String.valueOf(locationLongitude));
		this.saveProperty(GENERAL_SOCIAL_FACEBOOK, facebookUrl);
		this.saveProperty(GENERAL_SOCIAL_FOURSQUARE, foursquareUrl);
		this.saveProperty(GENERAL_SOCIAL_GPLUS, googlePlusUrl);
		this.saveProperty(GENERAL_SOCIAL_IDENTICA, identicaUrl);
		this.saveProperty(GENERAL_SOCIAL_TWITTER, twitter);
		this.saveProperty(GENERAL_SPACE_NAME, spaceName);
		this.saveProperty(GENERAL_URL, url);

		return this.fetchProperties(GeneralProperties.class);
	}

}
