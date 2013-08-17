package de.hackerspacebremen.presentation;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import de.hackerspacebremen.MyErrorMessages;
import de.hackerspacebremen.commands.RSSCommand;

@Singleton
public final class StatusRSSServlet extends OSNServlet{

	/**
	 * generated serialVersionUID.
	 */
	private static final long serialVersionUID = 503720739837717764L;

	private final Provider<RSSCommand> rssCommand;
	
	@Inject
	public StatusRSSServlet(final Provider<RSSCommand> rssCommand){
		this.rssCommand = rssCommand;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		final RSSCommand cmd = rssCommand.get();
		cmd.init(req, resp, MyErrorMessages.class);
		cmd.process();
	}
}
