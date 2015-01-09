package de.hackerspacebremen.common;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;


public enum SpaceAPIVersion {

	UNKNOWN("unknown"),

	API_0_08("0.8"),

	API_0_09("0.9"),

	API_0_11("0.11"),

	API_0_12("0.12"),

	API_0_13("0.13");

	@Getter
	private final String value;
	
	private SpaceAPIVersion(final String value){
		this.value = value;
	}
	
	@JsonValue
	@Override
	public String toString() {
		return this.value;
	}

	public static SpaceAPIVersion get(final String apiVersionString) {
		return get(apiVersionString, null);
	}
	
	public static SpaceAPIVersion get(final String apiVersionString, final String backendVersion) {
		SpaceAPIVersion apiVersion;
		switch (apiVersionString) {
		case "":
		case "/":
			apiVersion = getDefaultAPIVersion(backendVersion);
			break;
		case "/0_08":
		case "/0_08/":
			apiVersion = API_0_08;
			break;
		case "/0_09":
		case "/0_09/":
			apiVersion = API_0_09;
			break;
		case "/0_11":
		case "/0_11/":
			apiVersion = API_0_11;
			break;
		case "/0_12":
		case "/0_12/":
			apiVersion = API_0_12;
			break;
		case "/0_13":
		case "/0_13/":
			apiVersion = API_0_13;
			break;
		default:
			apiVersion = UNKNOWN;
		}
		return apiVersion;
	}

	private static SpaceAPIVersion getDefaultAPIVersion(
			final String backendVersion) {
		SpaceAPIVersion apiVersion;
		if(backendVersion == null || backendVersion.equals("v2")){
			apiVersion = API_0_12; 
		}else if(backendVersion.equals("v3")){
			apiVersion = API_0_13; 
		}else{
			apiVersion = UNKNOWN;
		}
		return apiVersion;
	}
}
