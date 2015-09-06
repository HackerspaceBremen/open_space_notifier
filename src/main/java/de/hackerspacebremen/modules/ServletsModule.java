package de.hackerspacebremen.modules;

import com.google.inject.servlet.ServletModule;

/**
 * @deprecated use spring mvc 
 * @author Steve
 *
 */
@Deprecated
public class ServletsModule extends ServletModule {

	@Override
	protected void configureServlets() {
		// deprecated version 1
		// serve("/cmd/open").with(OpenServlet.class);
		// serve("/cmd/close").with(CloseServlet.class);
		// serve("/cmd/message").with(MessageServlet.class);
		// serve("/status").with(StatusServlet.class);
		// serve("/register").with(GCMRegistryServlet.class);
		// serve("/unregister").with(GCMUnregistryServlet.class);
		// serve("/cmd/gcm").with(GCMTaskServlet.class);
		// serve("/cron/statuscheck").with(StatusCheckServlet.class);

		// version 2
		// serve("/v2/status").with(StatusServlet.class);
		// serve("/v2/status/*").with(StatusServlet.class);
		// serve("/v2/status.rss").with(StatusRSSServlet.class);
		// serve("/v2/status.atom").with(StatusAtomServlet.class);

		// commands
		// serve("/v2/cmd/open").with(OpenServlet.class);
		// serve("/v2/cmd/close").with(CloseServlet.class);
		// serve("/v2/cmd/message").with(MessageServlet.class);
		// serve("/v2/cmd/verifylogin").with(VerifyLoginServlet.class);

		// gcm
		// serve("/v2/gcm/register").with(GCMRegistryServlet.class);
		// serve("/v2/gcm/unregister").with(GCMUnregistryServlet.class);

		// apns
		// serve("/v2/apns/register").with(APNSRegistryServlet.class);
		// serve("/v2/apns/unregister").with(APNSUnregistryServlet.class);

		// cron jobs
		// serve("/v2/cron/statuscheck").with(StatusCheckServlet.class);
		// serve("/v2/cron/clearauthattempts").with(ClearAuthAttemptsServlet.class);

		// tasks
		// serve("/v2/task/gcm").with(GCMTaskServlet.class);
		// serve("/v2/task/apns").with(APNSTaskServlet.class);
		// serve("/v2/task/mail").with(MailTaskServlet.class);

		// jsps
		// serve("/admin").with(AdminServlet.class);
		// serve("/admin/general").with(AdminGeneralServlet.class);
		// serve("/admin/email").with(AdminEmailServlet.class);
		// serve("/admin/push").with(AdminPushServlet.class);
		// serve("/admin/auth").with(AdminAuthServlet.class);
		// serve("/admin/social").with(AdminSocialServlet.class);
		// serve("/admin/automatic").with(AdminAutomaticServlet.class);
		// serve("/admin/certificate").with(AdminCertificateServlet.class);
		// serve("/admin/certificate/apns").with(APNSCertificateServlet.class);
	}
}
