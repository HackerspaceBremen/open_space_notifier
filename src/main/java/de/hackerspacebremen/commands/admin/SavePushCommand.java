package de.hackerspacebremen.commands.admin;

import java.io.IOException;

import javax.servlet.ServletException;

import com.google.inject.Inject;

import de.hackerspacebremen.commands.WebCommand;
import de.hackerspacebremen.domain.api.PropertyService;
import de.hackerspacebremen.domain.val.ValidationException;

public class SavePushCommand extends WebCommand{

	@Inject
	private PropertyService propertyService;
	
	@Override
	public void process() throws ServletException, IOException {
		final boolean gcmEnabled = req.getParameter("gcm_enable") != null;
		final String gcmKey = req.getParameter("gcm_key");
		final boolean apnsEnabled = req.getParameter("apns_enable") != null;
		final boolean mpnsEnabled = req.getParameter("mpns_enable") != null;
		
		try {
			propertyService.savePushProperties(gcmEnabled, apnsEnabled, mpnsEnabled, gcmKey);
			resp.sendRedirect("/admin/push");
		} catch (ValidationException e) {
			resp.sendRedirect("/admin/push?validError="+e.getErrorCode());
		}
	}
}
