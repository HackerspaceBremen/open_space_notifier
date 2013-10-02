package de.hackerspacebremen.data.entities;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import de.hackerspacebremen.data.annotations.Entity;
import de.hackerspacebremen.data.annotations.FormatPart;

@com.googlecode.objectify.annotation.Entity
@Entity(name="APNSData")
@Cache
public final class APNSData implements BasicEntity {

	@Id
	@FormatPart(key="APNS1")
	private Long id;
	
	@FormatPart(key="APNS2")
	@Index
	private String deviceId;
	
	@FormatPart(key="APNS3")
	private String token;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
