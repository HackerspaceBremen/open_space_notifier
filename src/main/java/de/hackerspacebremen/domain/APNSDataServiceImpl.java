package de.hackerspacebremen.domain;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreInputStream;
import com.google.inject.Inject;
import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;

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
	public void sendMessageToDevices(final String message) throws IOException,
			ValidationException {
		final String blobKeyString = propertyService.findValueByKey(PropertyConstants.APNS_FILE_KEY_STRING);
		final String password = propertyService.findValueByKey(PropertyConstants.APNS_PASSWORD);
		final ApnsService service =
			    APNS.newService()
			    .withCert(new BlobstoreInputStream(new BlobKey(blobKeyString)), password)
			    .withSandboxDestination()
			    .build();
		
		final List<APNSData> devices = this.apnsDataDAO.findAll();
		
		for(final APNSData device : devices){
		
			// maybe add sound option!
			// see http://notnoop.github.io/java-apns/apidocs/index.html
			final String payload = APNS.newPayload().alertBody(message).build();
			service.push(device.getToken(), payload);
		
		}
		
		this.handleInactiveDevices(service);
	}

	private void handleInactiveDevices(final ApnsService service) {
		final Map<String, Date> inactiveDevices = service.getInactiveDevices();
		for (final String deviceToken : inactiveDevices.keySet()) {
		    final APNSData apnsData = apnsDataDAO.findByToken(deviceToken);
			apnsDataDAO.delete(apnsData);
		}
	}
}