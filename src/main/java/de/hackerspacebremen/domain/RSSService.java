package de.hackerspacebremen.domain;

import java.util.List;

import com.google.inject.Inject;

import de.hackerspacebremen.data.api.SpaceStatusDAO;
import de.hackerspacebremen.data.entities.SpaceStatus;
import de.hackerspacebremen.domain.api.FeedService;
import de.hackerspacebremen.domain.helper.FeedStatusCreator;
import de.hackerspacebremen.domain.helper.FeedType;

public final class RSSService implements FeedService{
	
	@Inject 
	private FeedStatusCreator feedCreator;
	
	@Inject
	private SpaceStatusDAO spaceStatusDAO;
	
	@Override
	public String createFeed(final int maxEntries) {
		final List<SpaceStatus> statusList = this.spaceStatusDAO.findAllOrdered(maxEntries);
		return feedCreator.createFeed(statusList, FeedType.RSS);
	}

	@Override
	public String createFeed() {
		final List<SpaceStatus> statusList = this.spaceStatusDAO.findAllOrdered();
		return feedCreator.createFeed(statusList, FeedType.RSS);
	}
}
