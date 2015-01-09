package de.hackerspacebremen.data.objectify;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import de.hackerspacebremen.data.api.SpaceStatusDAO;
import de.hackerspacebremen.data.entities.SpaceStatus;

public class SpaceStatusDB extends AbstractObjectifyDB<SpaceStatus> 
	implements SpaceStatusDAO{

	@Override
	public SpaceStatus findCurrentStatus() {
		return ofy().load().type(SpaceStatus.class).order("-time").first().now();
	}

	@Override
	protected Class<SpaceStatus> getAccessedEntity() {
		return SpaceStatus.class;
	}

	@Override
	public List<SpaceStatus> findAllOrdered() {
		return ofy().load().type(SpaceStatus.class).order("-time").list();
	}

	@Override
	public List<SpaceStatus> findAllOrdered(int maxEntries) {
		return ofy().load().type(SpaceStatus.class).order("-time").limit(maxEntries).list();
	}
}
