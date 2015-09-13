package de.hackerspacebremen.domain;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreInputStream;
import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import com.notnoop.exceptions.InvalidSSLConfig;

import de.hackerspacebremen.common.PropertyConstants;
import de.hackerspacebremen.data.ApnsDataDao;
import de.hackerspacebremen.data.entity.ApnsData;
import de.hackerspacebremen.domain.validation.ApnsDataValidation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ApnsDataService {

	private ApnsDataDao apnsDataDAO;
	private PropertyService propertyService;
	private ApnsDataValidation validation;
	
	@Autowired
	public ApnsDataService(ApnsDataDao apnsDataDao, PropertyService propertyService, ApnsDataValidation validation) {
		apnsDataDAO = apnsDataDao;
		this.propertyService = propertyService;
		this.validation = validation;
		
	}
	
	
	public void register(final String deviceId, final String token){
		validation.register(deviceId, token);
		ApnsData apnsData = apnsDataDAO.findByDeviceId(deviceId);
		if(apnsData == null){
			apnsData = new ApnsData();
			apnsData.setDeviceId(deviceId);
			apnsData.setToken(token);
			apnsDataDAO.persist(apnsData);
		}else if(!apnsData.getToken().equals(token)){
			apnsData.setDeviceId(deviceId);
			apnsData.setToken(token);;
			apnsDataDAO.persist(apnsData);
		}
	}

	public void unregister(final String deviceId) {
		validation.unregister(deviceId);
		ApnsData apnsData = apnsDataDAO.findByDeviceId(deviceId);
		apnsDataDAO.delete(apnsData);
	}

	public void sendMessageToDevices(final String statusShort, final String message) {
		validation.sendMessageToDevices(statusShort, message);
		final String blobKeyString = propertyService.findValueByKey(PropertyConstants.APNS_FILE_KEY_STRING);
		final String password = propertyService.findValueByKey(PropertyConstants.APNS_PASSWORD);
		ApnsService service;
		try {
			service = APNS.newService()
			.withCert(new BlobstoreInputStream(new BlobKey(blobKeyString)), password)
			.withSandboxDestination()
			.build();
		
			final List<ApnsData> devices = this.apnsDataDAO.findAll();
			
			String apnsMessage = statusShort;
			if(message!=null && !message.isEmpty()){
				apnsMessage += "\n - \n"+message.substring(0, 180);
			}
			
			for(final ApnsData device : devices){
				log.info("device: {}", device.getDeviceId());
			
				// maybe add sound option!
				// see http://notnoop.github.io/java-apns/apidocs/index.html
				final String payload = APNS.newPayload().alertBody(apnsMessage).build();
				service.push(device.getToken(), payload);
			
			}
			
			this.handleInactiveDevices(service);
		} catch (InvalidSSLConfig | IOException e) {
			log.error("InvalidSSLConfig or IOException occured unexpectedly",e);
		}
	}

	private void handleInactiveDevices(final ApnsService service) {
		final Map<String, Date> inactiveDevices = service.getInactiveDevices();
		for (final String deviceToken : inactiveDevices.keySet()) {
		    final ApnsData apnsData = apnsDataDAO.findByToken(deviceToken);
			apnsDataDAO.delete(apnsData);
		}
	}
}