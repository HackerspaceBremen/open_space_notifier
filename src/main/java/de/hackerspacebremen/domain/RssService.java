package de.hackerspacebremen.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.hackerspacebremen.data.SpaceStatusDao;
import de.hackerspacebremen.data.entity.SpaceStatus;
import de.hackerspacebremen.domain.helper.FeedStatusCreator;
import de.hackerspacebremen.domain.helper.FeedType;

@Service
public final class RssService {
	
	private FeedStatusCreator feedCreator;
	
	private SpaceStatusDao spaceStatusDAO;

	@Autowired
	public RssService(FeedStatusCreator feedCreator, SpaceStatusDao spaceStatusDAO) {
		this.feedCreator = feedCreator;
		this.spaceStatusDAO = spaceStatusDAO;
		
	}
	
	public String createFeed(final int maxEntries) {
		final List<SpaceStatus> statusList = this.spaceStatusDAO.findAllOrdered(maxEntries);
		return feedCreator.createFeed(statusList, FeedType.RSS);
	}

	public String createFeed() {
		final List<SpaceStatus> statusList = this.spaceStatusDAO.findAllOrdered();
		return feedCreator.createFeed(statusList, FeedType.RSS);
	}
}
