package de.hackerspacebremen.data;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import org.springframework.stereotype.Repository;

import de.hackerspacebremen.data.entity.SpaceStatus;

@Repository
public class SpaceStatusDao extends AbstractObjectifyDao<SpaceStatus> {

	public SpaceStatus findCurrentStatus() {
		return ofy().load().type(SpaceStatus.class).order("-time").first().now();
	}

	protected Class<SpaceStatus> getAccessedEntity() {
		return SpaceStatus.class;
	}

	public List<SpaceStatus> findAllOrdered() {
		return ofy().load().type(SpaceStatus.class).order("-time").list();
	}

	public List<SpaceStatus> findAllOrdered(int maxEntries) {
		return ofy().load().type(SpaceStatus.class).order("-time").limit(maxEntries).list();
	}
}
