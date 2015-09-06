package de.hackerspacebremen.presentation.cron;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.hackerspacebremen.domain.AuthAttemptService;
import de.hackerspacebremen.presentation.v2.CronJobV2Controller;


/**
 * @deprecated use {@link CronJobV2Controller#clearAuthAttempts()} instead
 * @author Steve
 *
 */
@Deprecated
@Singleton
public class ClearAuthAttemptsServlet extends HttpServlet{

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
