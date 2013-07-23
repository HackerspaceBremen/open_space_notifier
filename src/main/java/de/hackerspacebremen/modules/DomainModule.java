package de.hackerspacebremen.modules;

import com.google.inject.AbstractModule;

import de.hackerspacebremen.domain.DoorKeyKeeperServiceImpl;
import de.hackerspacebremen.domain.GCMAuthServiceImpl;
import de.hackerspacebremen.domain.GCMDataServiceImpl;
import de.hackerspacebremen.domain.SpaceStatusServiceImpl;
import de.hackerspacebremen.domain.api.DoorKeyKeeperService;
import de.hackerspacebremen.domain.api.GCMAuthService;
import de.hackerspacebremen.domain.api.GCMDataService;
import de.hackerspacebremen.domain.api.SpaceStatusService;

public class DomainModule extends AbstractModule{

	@Override
	protected void configure() {
		// TODO Validation is missing - see factory
		bind(SpaceStatusService.class).to(SpaceStatusServiceImpl.class);
		bind(DoorKeyKeeperService.class).to(DoorKeyKeeperServiceImpl.class);
		bind(GCMDataService.class).to(GCMDataServiceImpl.class);
		bind(GCMAuthService.class).to(GCMAuthServiceImpl.class);
	}

}
