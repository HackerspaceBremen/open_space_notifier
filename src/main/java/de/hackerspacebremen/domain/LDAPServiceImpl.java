package de.hackerspacebremen.domain;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

import org.apache.commons.codec.binary.Base64;

import de.hackerspacebremen.domain.api.LDAPService;

public final class LDAPServiceImpl implements LDAPService {

	/**
     * static attribute used for logging.
     */
    private static final Logger logger = Logger.getLogger(LDAPServiceImpl.class.getName());
	
	public boolean authenticate(final String login, final String pass){
		boolean result = false;
		
		if(pass== null ||pass.isEmpty()){
			logger.info("password is empty!!!");
		}
		try {
		    URL url = new URL("https://api.hackerspace-bremen.de/auth.php");
		    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		    final String authString = Base64.encodeBase64String((login+":"+pass).getBytes("UTF-8"));
		    connection.setRequestProperty("Accept-Charset", "UTF-8");
		    connection.setRequestProperty("Authorization",
		    		"Basic "+authString);
		    
		    result = connection.getResponseCode() == HttpURLConnection.HTTP_OK;
        } catch (MalformedURLException e) {
		    logger.warning("MalformedURLException occured: " + e.getMessage());
		} catch (IOException e) {
		    logger.warning("IOException occured: " + e.getMessage());
		}
		return result;
	}
}
