package de.hackerspacebremen.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.hackerspacebremen.data.entities.SpaceStatus;
import de.hackerspacebremen.data.objectify.SpaceStatusDao;
import de.hackerspacebremen.domain.helper.FeedStatusCreator;
import de.hackerspacebremen.domain.helper.FeedType;

@Service
public class AtomService {

	private FeedStatusCreator feedCreator;
	private SpaceStatusDao spaceStatusDao;
	
	@Autowired
	public AtomService(FeedStatusCreator feedCreator, SpaceStatusDao spaceStatusDao) {
		this.feedCreator = feedCreator;
		this.spaceStatusDao = spaceStatusDao;
	}
	
	public String createFeed(int maxEntries) {
		final List<SpaceStatus> statusList = this.spaceStatusDao.findAllOrdered(maxEntries);
		return feedCreator.createFeed(statusList, FeedType.ATOM);
	}

	public String createFeed() {
		final List<SpaceStatus> statusList = this.spaceStatusDao.findAllOrdered();
		return feedCreator.createFeed(statusList, FeedType.ATOM);
	}

}
