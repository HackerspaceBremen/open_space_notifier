package de.hackerspacebremen.data.objectify;

import static com.googlecode.objectify.ObjectifyService.ofy;
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
}
