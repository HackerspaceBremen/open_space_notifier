package de.hackerspacebremen.presentation.cron;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.hackerspacebremen.domain.api.AuthAttemptService;
import de.hackerspacebremen.presentation.OSNServlet;

@Singleton
public class ClearAuthAttemptsServlet extends OSNServlet{

	/**
	 * generated serialVersionUID.
	 */
	private static final long serialVersionUID = 3363071516338884656L;

	@Inject
	private AuthAttemptService authAttemptService;
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		authAttemptService.clearAttemptAmounts();
	}
}
