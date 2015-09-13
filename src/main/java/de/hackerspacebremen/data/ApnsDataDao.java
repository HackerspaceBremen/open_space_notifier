package de.hackerspacebremen.data;

import static com.googlecode.objectify.ObjectifyService.ofy;

import org.springframework.stereotype.Repository;

import de.hackerspacebremen.data.entity.ApnsData;

@Repository
public class ApnsDataDao extends AbstractObjectifyDao<ApnsData> {

	public ApnsData findByDeviceId(final String deviceId) {
		return ofy().load().type(ApnsData.class).filter("deviceId", deviceId).first().now();
	}

	protected Class<ApnsData> getAccessedEntity() {
		return ApnsData.class;
	}

	public ApnsData findByToken(final String token) {
		return ofy().load().type(ApnsData.class).filter("token", token).first().now();
	}

}
