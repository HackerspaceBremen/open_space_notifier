package de.hackerspacebremen.presentation.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import de.hackerspacebremen.MyErrorMessages;
import de.hackerspacebremen.commands.VerifyLoginCommand;
import de.hackerspacebremen.presentation.v2.CommandV2Controller;

/**
 * @deprecated use {@link CommandV2Controller#verifyLogin()} instead
 * @author Steve
 *
 */
@Deprecated
@Singleton
public class VerifyLoginServlet extends HttpServlet {

	/**
	 * generated serialVersionUID.
	 */
	private static final long serialVersionUID = 6098562305576732071L;

	private final Provider<VerifyLoginCommand> verifyLoginCommand;
	
	@Inject
	public VerifyLoginServlet(final Provider<VerifyLoginCommand> verifyLoginCommand){
		this.verifyLoginCommand = verifyLoginCommand;
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		final VerifyLoginCommand cmd = verifyLoginCommand.get();
		cmd.init(req, resp, MyErrorMessages.class);
		cmd.process();
	}
}
