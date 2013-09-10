package de.hackerspacebremen.domain;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;

import de.hackerspacebremen.data.api.APNSDataDAO;
import de.hackerspacebremen.data.entities.APNSData;
import de.hackerspacebremen.domain.api.APNSDataService;
import de.hackerspacebremen.domain.val.ValidationException;

public class APNSDataServiceImpl implements APNSDataService{

	@Inject
	private APNSDataDAO apnsDataDAO;
	
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
		final ApnsService service =
			    APNS.newService()
			    // TODO certificate
			    //.withCert("/path/to/certificate.p12", "MyCertPassword")
			    .withSandboxDestination()
			    .build();
		
		final List<APNSData> devices = this.apnsDataDAO.findAll();
		
		for(final APNSData device : devices){
		
			// payload can be more than just the alert body
			// see http://notnoop.github.io/java-apns/apidocs/index.html
			final String payload = APNS.newPayload().alertBody("Can't be simpler than this!").build();
			// get this from the database
			// TODO this service isn't final yet, so no messages are pushed
//			service.push(device.getToken(), payload);
		
		}
		
		this.handleInactiveDevices(service);
	}

	private void handleInactiveDevices(final ApnsService service) {
		final Map<String, Date> inactiveDevices = service.getInactiveDevices();
		for (String deviceToken : inactiveDevices.keySet()) {
		    // Date inactiveAsOf = inactiveDevices.get(deviceToken);
		    // TODO remove from database?
		}
	}

}