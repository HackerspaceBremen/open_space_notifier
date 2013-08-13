package de.hackerspacebremen.modules;

import com.google.inject.AbstractModule;

import de.hackerspacebremen.data.api.GCMAuthDAO;
import de.hackerspacebremen.data.api.GCMDataDAO;
import de.hackerspacebremen.data.api.PropertyDAO;
import de.hackerspacebremen.data.api.SpaceStatusDAO;
import de.hackerspacebremen.data.objectify.GCMAuthDB;
import de.hackerspacebremen.data.objectify.GCMDataDB;
import de.hackerspacebremen.data.objectify.PropertyDB;
import de.hackerspacebremen.data.objectify.SpaceStatusDB;

public class DataModule extends AbstractModule{

	@Override
	protected void configure() {
		bind(SpaceStatusDAO.class).to(SpaceStatusDB.class);
		bind(GCMDataDAO.class).to(GCMDataDB.class);
		bind(GCMAuthDAO.class).to(GCMAuthDB.class);
		bind(PropertyDAO.class).to(PropertyDB.class);
	}

}
