package de.hackerspacebremen.modules;

import com.google.inject.servlet.ServletModule;

import de.hackerspacebremen.presentation.StatusAtomServlet;
import de.hackerspacebremen.presentation.StatusRSSServlet;
import de.hackerspacebremen.presentation.StatusServlet;
import de.hackerspacebremen.presentation.apns.APNSCertificateServlet;
import de.hackerspacebremen.presentation.apns.APNSRegistryServlet;
import de.hackerspacebremen.presentation.apns.APNSUnregistryServlet;
import de.hackerspacebremen.presentation.command.CloseServlet;
import de.hackerspacebremen.presentation.command.MessageServlet;
import de.hackerspacebremen.presentation.command.OpenServlet;
import de.hackerspacebremen.presentation.cron.StatusCheckServlet;
import de.hackerspacebremen.presentation.gcm.GCMRegistryServlet;
import de.hackerspacebremen.presentation.gcm.GCMUnregistryServlet;
import de.hackerspacebremen.presentation.jsps.AdminAuthServlet;
import de.hackerspacebremen.presentation.jsps.AdminAutomaticServlet;
import de.hackerspacebremen.presentation.jsps.AdminCertificateServlet;
import de.hackerspacebremen.presentation.jsps.AdminEmailServlet;
import de.hackerspacebremen.presentation.jsps.AdminPushServlet;
import de.hackerspacebremen.presentation.jsps.AdminServlet;
import de.hackerspacebremen.presentation.jsps.AdminSocialServlet;
import de.hackerspacebremen.presentation.tasks.APNSTaskServlet;
import de.hackerspacebremen.presentation.tasks.GCMTaskServlet;
import de.hackerspacebremen.presentation.tasks.MailTaskServlet;

public class ServletsModule extends ServletModule {

	@Override
	protected void configureServlets() {
		// deprecated version 1
		serve("/cmd/open").with(OpenServlet.class);
		serve("/cmd/close").with(CloseServlet.class);
		serve("/cmd/message").with(MessageServlet.class);
		serve("/status").with(StatusServlet.class);
		serve("/register").with(GCMRegistryServlet.class);
		serve("/unregister").with(GCMUnregistryServlet.class);
		serve("/cmd/gcm").with(GCMTaskServlet.class);
		serve("/cron/statuscheck").with(StatusCheckServlet.class);
		
		// version 2
		serve("/v2/status").with(StatusServlet.class);
		serve("/v2/status.rss").with(StatusRSSServlet.class);
		serve("/v2/status.atom").with(StatusAtomServlet.class);
		
		// commands
		serve("/v2/cmd/open").with(OpenServlet.class);
		serve("/v2/cmd/close").with(CloseServlet.class);
		serve("/v2/cmd/message").with(MessageServlet.class);
		
		// gcm
		serve("/v2/gcm/register").with(GCMRegistryServlet.class);
		serve("/v2/gcm/unregister").with(GCMUnregistryServlet.class);
		
		//apns
		serve("/v2/apns/register").with(APNSRegistryServlet.class);
		serve("/v2/apns/unregister").with(APNSUnregistryServlet.class);
		
		// cron jobs
		serve("/v2/cron/statuscheck").with(StatusCheckServlet.class);
		
		// tasks
		serve("/v2/task/gcm").with(GCMTaskServlet.class);
		serve("/v2/task/apns").with(APNSTaskServlet.class);
		serve("/v2/task/mail").with(MailTaskServlet.class);
		
		// jsps
		serve("/admin").with(AdminServlet.class);
		serve("/admin/email").with(AdminEmailServlet.class);
		serve("/admin/push").with(AdminPushServlet.class);
		serve("/admin/auth").with(AdminAuthServlet.class);
		serve("/admin/social").with(AdminSocialServlet.class);
		serve("/admin/automatic").with(AdminAutomaticServlet.class);
		serve("/admin/certificate").with(AdminCertificateServlet.class);
		serve("/admin/certificate/apns").with(APNSCertificateServlet.class);
	}
}
