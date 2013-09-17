package de.hackerspacebremen.valueobjects;

public final class PushProperties {

	private boolean gcmEnabled;
	
	private String gcmApiKey;
	
	private boolean apnsEnabled;
	
	private boolean mpnsEnabled;
	
	private boolean mailEnabled;
	
	public PushProperties(){
		gcmEnabled = false;
		gcmApiKey = "";
		apnsEnabled = false;
		mpnsEnabled = false;
		mailEnabled = false;
	}

	public boolean isGcmEnabled() {
		return gcmEnabled;
	}

	public void setGcmEnabled(final boolean gcmEnabled) {
		this.gcmEnabled = gcmEnabled;
	}

	public boolean isApnsEnabled() {
		return apnsEnabled;
	}

	public void setApnsEnabled(final boolean apnsEnabled) {
		this.apnsEnabled = apnsEnabled;
	}

	public boolean isMpnsEnabled() {
		return mpnsEnabled;
	}

	public void setMpnsEnabled(final boolean mpnsEnabled) {
		this.mpnsEnabled = mpnsEnabled;
	}

	public String getGcmApiKey() {
		return gcmApiKey;
	}

	public void setGcmApiKey(final String gcmApiKey) {
		this.gcmApiKey = gcmApiKey;
	}

	public boolean isMailEnabled() {
		return mailEnabled;
	}

	public void setMailEnabled(final boolean mailEnabled) {
		this.mailEnabled = mailEnabled;
	}
}
