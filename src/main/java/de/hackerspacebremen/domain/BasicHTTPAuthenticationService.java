package de.hackerspacebremen.domain;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.hackerspacebremen.domain.val.AuthServiceValidation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public final class BasicHTTPAuthenticationService {

	private AuthServiceValidation authServiceValidation;

	@Autowired
	public BasicHTTPAuthenticationService(AuthServiceValidation authServiceValidation) {
		this.authServiceValidation = authServiceValidation;
	}
	
	public boolean authenticate(final String login, final String pass){
		authServiceValidation.authenticate(login, pass);
		
		boolean result = false;
		
		if(pass== null ||pass.isEmpty()){
			log.info("password is empty!!!");
		}
		try {
		    URL url = new URL("https://api.hackerspace-bremen.de/osn-login");
		    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		    final String authString = Base64.encodeBase64String((login+":"+pass).getBytes("UTF-8"));
		    connection.setRequestProperty("Accept-Charset", "UTF-8");
		    connection.setRequestProperty("Authorization",
		    		"Basic "+authString);
		    
		    result = connection.getResponseCode() == HttpURLConnection.HTTP_OK;
        } catch (MalformedURLException e) {
		    log.warn("MalformedURLException occured", e);
		} catch (IOException e) {
		    log.warn("IOException occured",e);
		}
		return result;
	}
}
