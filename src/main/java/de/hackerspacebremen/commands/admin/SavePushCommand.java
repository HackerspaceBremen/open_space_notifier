package de.hackerspacebremen.commands.admin;

import java.io.IOException;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.hackerspacebremen.commands.WebCommand;
import de.hackerspacebremen.domain.PropertyService;
import de.hackerspacebremen.domain.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SavePushCommand extends WebCommand{

	private PropertyService propertyService;
	
	@Autowired
	public SavePushCommand(PropertyService propertyService) {
		this.propertyService = propertyService;
	}
	
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
			log.info("gcmEnabled: " + gcmEnabled);
			propertyService.savePushProperties(gcmEnabled, apnsEnabled, mpnsEnabled, gcmKey, apnsPassword);
			log.info("result == SUCCESS");
			req.setAttribute("result", "SUCCESS");
			req.setAttribute("code", Integer.valueOf(0));
			req.getRequestDispatcher("/adminPush.jsp").forward(req, resp);
		} catch (ValidationException e) {
			log.warn("ValidationException occured with error code: {}",e.getErrorCode());
			req.setAttribute("error", e.getMessage());
			req.setAttribute("result", "ERROR");
			req.setAttribute("code", e.getErrorCode());
			req.getRequestDispatcher("/adminPush.jsp").forward(req, resp);
		}
	}
}
