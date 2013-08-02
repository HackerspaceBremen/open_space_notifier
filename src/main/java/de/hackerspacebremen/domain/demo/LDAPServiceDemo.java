package de.hackerspacebremen.domain.demo;

import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import com.google.inject.Inject;

import de.hackerspacebremen.common.AppConstants;
import de.hackerspacebremen.domain.api.LDAPService;
import de.hackerspacebremen.domain.val.ValidationException;

public class LDAPServiceDemo implements LDAPService{
	
	/**
     * static attribute used for logging.
     */
    private static final Logger logger = Logger.getLogger(LDAPServiceDemo.class.getName());

    @Inject
	private LDAPService ldapService;
	
	@Override
	public boolean authenticate(final String name, final String password)
			throws ValidationException {
		final Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		final int day = cal.get(Calendar.DAY_OF_MONTH);
		final int month = cal.get(Calendar.MONTH);
		final int year = cal.get(Calendar.YEAR);
		final boolean result;
		logger.info("PROD:" +AppConstants.PROD + "; name: " + name + "; date: " + day + " " + month + " " + year);
		if(!AppConstants.PROD && name.equals("makerfaire") && (day == 2 || day == 3 || day == 10) && month == 7 && year == 2013){
			result = true;
		}else{
			result = ldapService.authenticate(name, password);
		}
		return result;
	}

}
