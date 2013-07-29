package de.hackerspacebremen.data.objectify;

import de.hackerspacebremen.data.api.GCMAuthDAO;
import de.hackerspacebremen.data.entities.GCMAuth;

public class GCMAuthDB extends AbstractObjectifyDB<GCMAuth>
	implements GCMAuthDAO{

	@Override
	protected Class<GCMAuth> getAccessedEntity() {
		return GCMAuth.class;
	}

}
