package de.hackerspacebremen.presentation;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import de.hackerspacebremen.MyErrorMessages;
import de.hackerspacebremen.commands.AtomCommand;

@Singleton
public class StatusAtomServlet extends OSNServlet{

	/**
	 * generated serialVersionUID.
	 */
	private static final long serialVersionUID = 3527148897819104240L;

	private final Provider<AtomCommand> atomCommand;
	
	@Inject
	public StatusAtomServlet(final Provider<AtomCommand> atomCommand){
		this.atomCommand = atomCommand;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		final AtomCommand cmd = atomCommand.get();
		cmd.init(req, resp, MyErrorMessages.class);
		cmd.process();
	}
}
