package de.hackerspacebremen.domain.helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedOutput;

import de.hackerspacebremen.common.AppConstants;
import de.hackerspacebremen.data.entities.SpaceStatus;

public final class FeedStatusCreator {
	
	/**
     * static attribute used for logging.
     */
    private static final Logger logger = Logger.getLogger(FeedStatusCreator.class.getName());

	public String createFeed(final List<SpaceStatus> statusList, final FeedType type){
		final SyndFeed feed = new SyndFeedImpl();
	    feed.setFeedType(type.toString());
	    // TODO get title, link and description from database | see #24
	    feed.setTitle("Hackerspace Bremen - Open Space Notifier");
	    feed.setLink("http://www.hackerspace-bremen.de");
	    feed.setDescription("Lists the status changes of "
	    		+ "the hackerspace in Bremen(Germany).");
	    
	    final List<SyndEntry> entries = new ArrayList<SyndEntry>(statusList.size());
	      
	    for(int i=0; i<statusList.size(); i++){
	    	final SpaceStatus status = statusList.get(i);
	    	final SyndEntry entry = new SyndEntryImpl();
	    	if(status.getStatus().equals(AppConstants.OPEN)){
	    		// TODO get this from datastore
	    		entry.setTitle("Der Space wurde geöffnet");
	    	}else{
	    		// TODO get this from datastore
	    		entry.setTitle("Der Space wurde geschlossen");
	    	}
	    	entry.setPublishedDate(new Date(status.getTime()));
	    	final SyndContent description = new SyndContentImpl();
	    	description.setType("text/plain");
	    	if(status.getMessage()!=null){
	    		description.setValue(status.getMessage().getValue().trim().replace('\0','.'));
	    	}
	    	entry.setDescription(description);
	    	entries.add(entry);
	    }
	    
	    feed.setEntries(entries);
	    final SyndFeedOutput output = new SyndFeedOutput();
	    String result = "";
	    try{
	    	result = output.outputString(feed);
	    }catch(FeedException e){
	    	logger.warning("FeedException occured: " + e.getMessage());
	    }
	    return result;
	}
	
}
