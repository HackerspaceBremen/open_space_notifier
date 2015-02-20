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
import java.util.logging.Logger;

import com.google.inject.Inject;
import com.google.inject.Provider;

import de.hackerspacebremen.data.api.PropertyDAO;
import de.hackerspacebremen.data.entities.Property;
import de.hackerspacebremen.domain.api.PropertyService;
import de.hackerspacebremen.domain.val.ValidationException;
import de.hackerspacebremen.exceptions.NotCompletelyConfigured;
import de.hackerspacebremen.valueobjects.properties.CertificateProperties;
import de.hackerspacebremen.valueobjects.properties.DataProperty;
import de.hackerspacebremen.valueobjects.properties.EmailProperties;
import de.hackerspacebremen.valueobjects.properties.GeneralProperties;
import de.hackerspacebremen.valueobjects.properties.PushProperties;

public class PropertyServiceImpl implements PropertyService {

	/**
     * static attribute used for logging.
     */
    private static final Logger logger = Logger.getLogger(PropertyServiceImpl.class.getName());
	
	@Inject
	private PropertyDAO propertyDAO;

	@Inject
	private Provider<PushProperties> pushProperties;

	@Inject
	private Provider<EmailProperties> emailProperties;
	
	@Inject
	private Provider<GeneralProperties> generalProperties;

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

	public <P> P fetchProperties(final Class<P> propertyClass){
		P properties = null;
		try {
			properties = propertyClass.getConstructor().newInstance();
			this.fillProperties(properties);
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			logger.severe("Exception occured while using Refelction: " + e.getMessage());
		}
		
		return properties;
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
		return properties;
	}
	
	@Override
	public GeneralProperties fetchGeneralProperties() {
		final GeneralProperties properties = generalProperties.get();
		try {
			this.fillProperties(properties);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			logger.severe("Exception occured while using Refelction: " + e.getMessage());
		}
		return properties;
	}
	
	private <P> void fillProperties(final P properties) throws IllegalArgumentException, IllegalAccessException{
		final Field[] fields = properties.getClass().getDeclaredFields();
		
		for(final Field field : fields){
			if(field.isAnnotationPresent(DataProperty.class)){
				field.setAccessible(true);
				final DataProperty dataProperty = field.getAnnotation(DataProperty.class);
				final String propertyAsString = this.findProperty(dataProperty.key(), dataProperty.defaultValue()).getValue();
				if(field.getType() == boolean.class){
					field.set(properties, Boolean.parseBoolean(propertyAsString));
				}else if(field.getType() == double.class){
					field.set(properties, Double.parseDouble(propertyAsString));
				}else{
					field.set(properties, propertyAsString);
				}
				
				field.setAccessible(false);
			}
		}
	}
	
	@Override
	public EmailProperties fetchEmailProperties() {
		final EmailProperties properties = emailProperties.get();
		try {
			this.fillProperties(properties);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			logger.severe("Exception occured while using Refelction: " + e.getMessage());
		}
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
	public EmailProperties saveEmailProperties(final boolean mailEnabled,
			final String senderName, final String receiverName,
			final String subjectTag, final String subjectOpened,
			final String subjectClosed, final String message,
			final String content, final String opened, final String closed,
			final String negatedOpened, final String negatedClosed)
			throws ValidationException {
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

		return this.fetchEmailProperties();
	}

	@Override
	public GeneralProperties saveGeneralProperties(String spaceName,
			String url, String locationAddress, double locationLatitude,
			double locationLongitude, String twitter, String facebookUrl,
			String googlePlusUrl, String identicaUrl, String foursquareUrl,
			String email, String mailinglist, String issueMail, String phone,
			String sip, String irc, String jabber) {
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
		
		return this.fetchGeneralProperties();
	}

}
