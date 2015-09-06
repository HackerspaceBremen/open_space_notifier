package de.hackerspacebremen.commands;

import java.io.IOException;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.hackerspacebremen.domain.SpaceStatusService;
import de.hackerspacebremen.domain.val.ValidationException;
import de.hackerspacebremen.email.StatusEmail;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MailCommand extends WebCommand{

	
	private SpaceStatusService statusService;
	private StatusEmail statusEmail;
	
	@Autowired
	public MailCommand(SpaceStatusService statusService, StatusEmail statusEmail) {
		this.statusService = statusService;
		this.statusEmail = statusEmail;
	}
	
	@Override
	public void process() throws ServletException, IOException {
		final Long statusId = Long.valueOf(this.req.getParameter("statusId"));
		try {
			statusEmail.send(this.statusService.findById(statusId));
		} catch (ValidationException e) {
			log.warn("ValidationException occured: " + e.getMessage());
		}
		
		super.process();
	}	
}