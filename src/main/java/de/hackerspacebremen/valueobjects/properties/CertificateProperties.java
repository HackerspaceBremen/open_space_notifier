package de.hackerspacebremen.valueobjects.properties;

public final class CertificateProperties {
	
	private String apnsCertificate;
	
	public CertificateProperties(){
		apnsCertificate = "";
	}
	
	public String getApnsCertificate() {
		return apnsCertificate;
	}

	public void setApnsCertificate(String apnsCertificate) {
		this.apnsCertificate = apnsCertificate;
	}
}
