package de.hackerspacebremen.commands.resultobjects.v13.radioshow;

import com.fasterxml.jackson.annotation.JsonValue;

public enum StreamEncoder {

	MP3("mp3"),
	
	OGG("ogg");
	
	private String value;
	
	private StreamEncoder(final String value){
		this.value = value;
	}

	@JsonValue
	private String getValue(){
		return this.value;
	}
}
