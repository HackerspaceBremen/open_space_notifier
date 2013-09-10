package de.hackerspacebremen.data.api;

import de.hackerspacebremen.data.entities.APNSData;

public interface APNSDataDAO extends BasicDAO<APNSData>{

	APNSData findByDeviceId(final String deviceId);
}
