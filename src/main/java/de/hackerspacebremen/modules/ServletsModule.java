package de.hackerspacebremen.modules;

import com.google.inject.servlet.ServletModule;

import de.hackerspacebremen.presentation.CloseServlet;
import de.hackerspacebremen.presentation.GCMRegistryServlet;
import de.hackerspacebremen.presentation.GCMServlet;
import de.hackerspacebremen.presentation.GCMUnregistryServlet;
import de.hackerspacebremen.presentation.MessageServlet;
import de.hackerspacebremen.presentation.OpenServlet;
import de.hackerspacebremen.presentation.StatusCheckServlet;
import de.hackerspacebremen.presentation.StatusServlet;

public class ServletsModule extends ServletModule {

	@Override
	protected void configureServlets() {
		serve("/cmd/open").with(OpenServlet.class);
		serve("/cmd/close").with(CloseServlet.class);
		serve("/cmd/message").with(MessageServlet.class);
		serve("/status").with(StatusServlet.class);
//		serve("/cmd/createkeeper").with(CreateKeeperServlet.class);
		serve("/register").with(GCMRegistryServlet.class);
		serve("/unregister").with(GCMUnregistryServlet.class);
		//serve("/cmd/authkeeper").with(AuthKeeperServlet.class);
		serve("/cmd/gcm").with(GCMServlet.class);
		//serve("/activate").with(ActivateServlet.class);
		serve("/cron/statuscheck").with(StatusCheckServlet.class);
	}
}
