package de.hackerspacebremen.domain;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreInputStream;
import com.google.inject.Inject;
import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import com.notnoop.exceptions.InvalidSSLConfig;

import de.hackerspacebremen.common.PropertyConstants;
import de.hackerspacebremen.data.api.APNSDataDAO;
import de.hackerspacebremen.data.entities.APNSData;
import de.hackerspacebremen.domain.api.APNSDataService;
import de.hackerspacebremen.domain.api.PropertyService;
import de.hackerspacebremen.domain.val.ValidationException;

public class APNSDataServiceImpl implements APNSDataService{

	@Inject
	private APNSDataDAO apnsDataDAO;
	
	@Inject PropertyService propertyService;
	
	/**
     * static attribute used for logging.
     */
    private static final Logger logger = Logger.getLogger(APNSDataServiceImpl.class.getName());
	
	@Override
	public void register(final String deviceId, final String token)
			throws ValidationException {
		APNSData apnsData = apnsDataDAO.findByDeviceId(deviceId);
		if(apnsData == null){
			apnsData = new APNSData();
			apnsData.setDeviceId(deviceId);
			apnsData.setToken(token);
			apnsDataDAO.persist(apnsData);
		}else if(!apnsData.getToken().equals(token)){
			apnsData.setDeviceId(deviceId);
			apnsData.setToken(token);;
			apnsDataDAO.persist(apnsData);
		}
	}

	@Override
	public void unregister(final String deviceId) throws ValidationException {
		APNSData apnsData = apnsDataDAO.findByDeviceId(deviceId);
		apnsDataDAO.delete(apnsData);
	}

	@Override
	public void sendMessageToDevices(final String statusShort, final String message) throws 
			ValidationException {
		final String blobKeyString = propertyService.findValueByKey(PropertyConstants.APNS_FILE_KEY_STRING);
		final String password = propertyService.findValueByKey(PropertyConstants.APNS_PASSWORD);
		ApnsService service;
		try {
			service = APNS.newService()
			.withCert(new BlobstoreInputStream(new BlobKey(blobKeyString)), password)
			.withSandboxDestination()
			.build();
		
			final List<APNSData> devices = this.apnsDataDAO.findAll();
			
			String apnsMessage = statusShort;
			if(message!=null && !message.isEmpty()){
				apnsMessage += "\n - \n"+message.substring(0, 180);
			}
			
			for(final APNSData device : devices){
			
				// maybe add sound option!
				// see http://notnoop.github.io/java-apns/apidocs/index.html
				final String payload = APNS.newPayload().alertBody(apnsMessage).build();
				service.push(device.getToken(), payload);
			
			}
			
			this.handleInactiveDevices(service);
		} catch (InvalidSSLConfig | IOException e) {
			logger.severe("InvalidSSLConfig or IOException occured unexpectedly: " + e.getMessage());
		}
	}

	private void handleInactiveDevices(final ApnsService service) {
		final Map<String, Date> inactiveDevices = service.getInactiveDevices();
		for (final String deviceToken : inactiveDevices.keySet()) {
		    final APNSData apnsData = apnsDataDAO.findByToken(deviceToken);
			apnsDataDAO.delete(apnsData);
		}
	}
}