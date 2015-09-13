package de.hackerspacebremen.presentation;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import de.hackerspacebremen.ErrorMessages;
import de.hackerspacebremen.commands.RSSCommand;
import de.hackerspacebremen.presentation.v2.StatusV2Controller;

/**
 * @deprecated use {@link StatusV2Controller#rss()} instead
 * @author Steve
 *
 */
@Deprecated
@Singleton
public final class StatusRSSServlet extends HttpServlet {

	/**
	 * generated serialVersionUID.
	 */
	private static final long serialVersionUID = 503720739837717764L;

	private final Provider<RSSCommand> rssCommand;

	@Inject
	public StatusRSSServlet(final Provider<RSSCommand> rssCommand) {
		this.rssCommand = rssCommand;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		final RSSCommand cmd = rssCommand.get();
		cmd.init(req, resp, ErrorMessages.class);
		cmd.process();
	}
}
