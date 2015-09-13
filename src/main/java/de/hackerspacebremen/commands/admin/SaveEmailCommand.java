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
public class SaveEmailCommand extends WebCommand {

	private PropertyService propertyService;

	@Autowired
	public SaveEmailCommand(PropertyService propertyService) {
		this.propertyService = propertyService;
	}

	@Override
	public void process() throws ServletException, IOException {
		final String senderName = req.getParameter("sender_name");
		final String receiverName = req.getParameter("receiver_name");
		final String subjectTag = req.getParameter("subject_tag");
		final String subjectOpened = req.getParameter("subject_opened");
		final String subjectClosed = req.getParameter("subject_closed");
		final String content = req.getParameter("content");
		final String opened = req.getParameter("opened");
		final String closed = req.getParameter("closed");
		final String message = req.getParameter("message");
		final String negatedOpened = req.getParameter("negated_opened");
		final String negatedClosed = req.getParameter("negated_closed");
		final boolean mailEnabled = req.getParameter("mail_enable") != null;

		try {
			propertyService.saveEmailProperties(mailEnabled, senderName, receiverName, subjectTag, subjectOpened,
					subjectClosed, message, content, opened, closed, negatedOpened, negatedClosed);
			req.setAttribute("result", "SUCCESS");
			req.setAttribute("code", Integer.valueOf(0));
			req.getRequestDispatcher("/adminEmail.jsp").forward(req, resp);
		} catch (ValidationException e) {
			log.warn("ValidationException occured with error code: {}", e.getErrorCode());
			req.setAttribute("error", e.getMessage());
			req.setAttribute("result", "ERROR");
			req.setAttribute("code", e.getErrorCode());
			req.getRequestDispatcher("/adminEmail.jsp").forward(req, resp);
		}
	}
}
