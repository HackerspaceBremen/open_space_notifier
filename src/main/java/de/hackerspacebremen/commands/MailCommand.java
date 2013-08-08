package de.hackerspacebremen.commands;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;

import com.google.inject.Inject;

import de.hackerspacebremen.domain.api.SpaceStatusService;
import de.hackerspacebremen.domain.val.ValidationException;
import de.hackerspacebremen.email.StatusEmail;
import de.hackerspacebremen.modules.binding.annotations.Proxy;


public class MailCommand extends WebCommand{

	/**
     * static attribute used for logging.
     */
    private static final Logger logger = Logger.getLogger(MailCommand.class.getName());
	
	@Inject
	@Proxy
	private SpaceStatusService statusService;
	
	@Inject
	private StatusEmail statusEmail;
	
	@Override
	public void process() throws ServletException, IOException {
		final Long statusId = Long.valueOf(this.req.getParameter("statusId"));
		try {
			statusEmail.send(this.statusService.findById(statusId));
		} catch (ValidationException e) {
			logger.warning("ValidationException occured: " + e.getMessage());
		}
		
		super.process();
	}	
}