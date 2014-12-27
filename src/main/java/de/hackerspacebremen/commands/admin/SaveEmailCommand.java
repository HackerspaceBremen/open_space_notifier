package de.hackerspacebremen.commands.admin;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;

import com.google.inject.Inject;

import de.hackerspacebremen.commands.WebCommand;
import de.hackerspacebremen.domain.api.PropertyService;
import de.hackerspacebremen.domain.val.ValidationException;
import de.hackerspacebremen.modules.binding.annotations.Proxy;

public class SaveEmailCommand extends WebCommand {

	@Inject
	@Proxy
	private PropertyService propertyService;

	/**
	 * static attribute used for logging.
	 */
	private static final Logger logger = Logger
			.getLogger(SaveEmailCommand.class.getName());

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
		final String negatedOpened = req.getParameter("negated_opened");
		final String negatedClosed = req.getParameter("negated_closed");

		try {
			propertyService.saveEmailProperties(senderName, receiverName,
					subjectTag, subjectOpened, subjectClosed, content, opened,
					closed, negatedOpened, negatedClosed);
			req.setAttribute("result", "SUCCESS");
			req.setAttribute("code", Integer.valueOf(0));
			req.getRequestDispatcher("/adminEmail.jsp").forward(req, resp);
		} catch (ValidationException e) {
			logger.warning("ValidationException occured with error code: "
					+ e.getErrorCode());
			req.setAttribute("error", e.getMessage());
			req.setAttribute("result", "ERROR");
			req.setAttribute("code", e.getErrorCode());
			req.getRequestDispatcher("/adminEmail.jsp").forward(req, resp);
		}
	}
}
