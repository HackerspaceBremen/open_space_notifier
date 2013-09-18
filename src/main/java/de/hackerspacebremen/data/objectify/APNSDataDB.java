package de.hackerspacebremen.data.objectify;

import static com.googlecode.objectify.ObjectifyService.ofy;
import de.hackerspacebremen.data.api.APNSDataDAO;
import de.hackerspacebremen.data.entities.APNSData;

public class APNSDataDB extends AbstractObjectifyDB<APNSData> implements APNSDataDAO{

	@Override
	public APNSData findByDeviceId(final String deviceId) {
		return ofy().load().type(APNSData.class).filter("deviceId", deviceId).first().now();
	}

	@Override
	protected Class<APNSData> getAccessedEntity() {
		return APNSData.class;
	}

	@Override
	public APNSData findByToken(final String token) {
		return ofy().load().type(APNSData.class).filter("token", token).first().now();
	}

}
