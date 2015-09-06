package de.hackerspacebremen.commands;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.hackerspacebremen.domain.RssService;

@Component
public class RSSCommand extends WebCommand{

	private RssService feedService;

	@Autowired
	public RSSCommand(RssService feedService) {
		this.feedService = feedService;
	}
	
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
