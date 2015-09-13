package de.hackerspacebremen.presentation.jsps;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import de.hackerspacebremen.ErrorMessages;
import de.hackerspacebremen.commands.admin.SavePushCommand;
import de.hackerspacebremen.presentation.admin.PushController;

/**
 * @deprecated use {@link PushController} instead
 * @author Steve
 *
 */
@Deprecated
@Singleton
public class AdminPushServlet extends HttpServlet {

	/**
	 * generated serialVersionUID.
	 */
	private static final long serialVersionUID = 1147832045798741863L;

	@Inject
	private Provider<SavePushCommand> savePushCommand;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/adminPush.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		final SavePushCommand cmd = savePushCommand.get();
		cmd.init(req, resp, ErrorMessages.class);
		cmd.process();
	}
}
