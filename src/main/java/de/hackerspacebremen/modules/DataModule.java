package de.hackerspacebremen.modules;

import com.google.inject.AbstractModule;

import de.hackerspacebremen.data.DoorKeyKeeperDB;
import de.hackerspacebremen.data.GCMAuthDB;
import de.hackerspacebremen.data.GCMDataDB;
import de.hackerspacebremen.data.SpaceStatusDB;
import de.hackerspacebremen.data.api.DoorKeyKeeperDAO;
import de.hackerspacebremen.data.api.GCMAuthDAO;
import de.hackerspacebremen.data.api.GCMDataDAO;
import de.hackerspacebremen.data.api.SpaceStatusDAO;

public class DataModule extends AbstractModule{

	@Override
	protected void configure() {
		bind(SpaceStatusDAO.class).to(SpaceStatusDB.class);
		bind(DoorKeyKeeperDAO.class).to(DoorKeyKeeperDB.class);
		bind(GCMDataDAO.class).to(GCMDataDB.class);
		bind(GCMAuthDAO.class).to(GCMAuthDB.class);
	}

}
