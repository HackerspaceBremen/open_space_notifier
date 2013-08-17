package de.hackerspacebremen.commands;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;

import de.hackerspacebremen.domain.api.FeedService;
import de.hackerspacebremen.modules.binding.annotations.Atom;

public class RSSCommand extends WebCommand{

	@Inject
	@Atom
	private FeedService feedService;
	
	@Override
	public void process() throws ServletException, IOException {

		final String maxParam = req.getParameter("max");
		
		final String result;
		if(maxParam == null || maxParam.isEmpty()){
			result = feedService.createFeed();
		}else{
			final int maxEntries = Integer.parseInt(maxParam);
			result = feedService.createFeed(maxEntries);
		}
		
		resp.setHeader("Content-Type", "text/javascript; charset=UTF-8");
		resp.setStatus(HttpServletResponse.SC_OK);
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().append(result);
		
	}
}
