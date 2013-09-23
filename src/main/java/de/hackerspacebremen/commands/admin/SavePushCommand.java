package de.hackerspacebremen.commands.admin;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;

import com.google.inject.Inject;

import de.hackerspacebremen.commands.WebCommand;
import de.hackerspacebremen.domain.api.PropertyService;
import de.hackerspacebremen.domain.val.ValidationException;
import de.hackerspacebremen.modules.binding.annotations.Proxy;

public class SavePushCommand extends WebCommand{

	@Inject
	@Proxy
	private PropertyService propertyService;
	
	/**
     * static attribute used for logging.
     */
    private static final Logger logger = Logger.getLogger(SavePushCommand.class.getName());
	
	@Override
	public void process() throws ServletException, IOException {
		final boolean gcmEnabled = req.getParameter("gcm_enable") != null;
		String gcmKey = null;
		if(gcmEnabled){
			gcmKey = req.getParameter("gcm_key");
		}
		final boolean apnsEnabled = req.getParameter("apns_enable") != null;
		final boolean mpnsEnabled = req.getParameter("mpns_enable") != null;
		String apnsPassword = null; 
		if(apnsEnabled){
			apnsPassword = req.getParameter("apns_password");
		}
		
		try {
			logger.info("gcmEnabled: " + gcmEnabled);
			propertyService.savePushProperties(gcmEnabled, apnsEnabled, mpnsEnabled, gcmKey, apnsPassword);
			logger.info("result == SUCCESS");
			req.setAttribute("result", "SUCCESS");
			req.setAttribute("code", Integer.valueOf(0));
			req.getRequestDispatcher("/adminPush.jsp").forward(req, resp);
		} catch (ValidationException e) {
			logger.warning("ValidationException occured with error code: " + e.getErrorCode());
			req.setAttribute("error", e.getMessage());
			req.setAttribute("result", "ERROR");
			req.setAttribute("code", e.getErrorCode());
			req.getRequestDispatcher("/adminPush.jsp").forward(req, resp);
		}
	}
}
