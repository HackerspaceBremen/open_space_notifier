package de.hackerspacebremen.valueobjects;

public final class PushProperties {

	private boolean gcmEnabled;
	
	private String gcmApiKey;
	
	private boolean apnsEnabled;
	
	private boolean mpnsEnabled;
	
	public PushProperties(){
		gcmEnabled = false;
		gcmApiKey = "";
		apnsEnabled = false;
		mpnsEnabled = false;
	}

	public boolean isGcmEnabled() {
		return gcmEnabled;
	}

	public void setGcmEnabled(boolean gcmEnabled) {
		this.gcmEnabled = gcmEnabled;
	}

	public boolean isApnsEnabled() {
		return apnsEnabled;
	}

	public void setApnsEnabled(boolean apnsEnabled) {
		this.apnsEnabled = apnsEnabled;
	}

	public boolean isMpnsEnabled() {
		return mpnsEnabled;
	}

	public void setMpnsEnabled(boolean mpnsEnabled) {
		this.mpnsEnabled = mpnsEnabled;
	}

	public String getGcmApiKey() {
		return gcmApiKey;
	}

	public void setGcmApiKey(String gcmApiKey) {
		this.gcmApiKey = gcmApiKey;
	}
}
