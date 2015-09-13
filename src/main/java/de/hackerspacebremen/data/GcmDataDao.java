package de.hackerspacebremen.data;

import static com.googlecode.objectify.ObjectifyService.ofy;

import org.springframework.stereotype.Repository;

import de.hackerspacebremen.data.entity.GcmData;

@Repository
public class GcmDataDao extends AbstractObjectifyDao<GcmData>{

	public GcmData findByDeviceId(final String deviceId) {
		return ofy().load().type(GcmData.class).filter("deviceId", deviceId).first().now();
	}

	protected Class<GcmData> getAccessedEntity() {
		return GcmData.class;
	}

}
