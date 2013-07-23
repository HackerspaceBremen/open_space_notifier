package de.hackerspacebremen.modules;

import com.google.inject.servlet.ServletModule;

import de.hackerspacebremen.ActivateServlet;
import de.hackerspacebremen.AuthKeeperServlet;
import de.hackerspacebremen.CloseServlet;
import de.hackerspacebremen.CreateKeeperServlet;
import de.hackerspacebremen.GCMRegistryServlet;
import de.hackerspacebremen.GCMServlet;
import de.hackerspacebremen.GCMUnregistryServlet;
import de.hackerspacebremen.MessageServlet;
import de.hackerspacebremen.OpenServlet;
import de.hackerspacebremen.StatusCheckServlet;
import de.hackerspacebremen.StatusServlet;

public class ServletsModule extends ServletModule {

	@Override
	protected void configureServlets() {
		serve("/cmd/update").with(OpenServlet.class);
		serve("/cmd/close").with(CloseServlet.class);
		serve("/cmd/message").with(MessageServlet.class);
		serve("/status").with(StatusServlet.class);
		serve("/cmd/createkeeper").with(CreateKeeperServlet.class);
		serve("/register").with(GCMRegistryServlet.class);
		serve("/unregister").with(GCMUnregistryServlet.class);
		serve("/cmd/authkeeper").with(AuthKeeperServlet.class);
		serve("/cmd/gcm").with(GCMServlet.class);
		serve("/activate").with(ActivateServlet.class);
		serve("/cron/statuscheck").with(StatusCheckServlet.class);
	}
}
