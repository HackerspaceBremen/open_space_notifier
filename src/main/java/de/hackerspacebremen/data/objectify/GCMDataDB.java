package de.hackerspacebremen.data.objectify;

import static com.googlecode.objectify.ObjectifyService.ofy;
import de.hackerspacebremen.data.api.GCMDataDAO;
import de.hackerspacebremen.data.entities.GCMData;

public class GCMDataDB extends AbstractObjectifyDB<GCMData> implements GCMDataDAO{

	@Override
	public GCMData findByDeviceId(final String deviceId) {
		return ofy().load().type(GCMData.class).filter("deviceId", deviceId).first().now();
	}

	@Override
	protected Class<GCMData> getAccessedEntity() {
		return GCMData.class;
	}

}
