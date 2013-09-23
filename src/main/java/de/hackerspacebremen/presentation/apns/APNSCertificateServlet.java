package de.hackerspacebremen.presentation.apns;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import de.hackerspacebremen.MyErrorMessages;
import de.hackerspacebremen.commands.admin.SaveAPNSCertificateCommand;
import de.hackerspacebremen.presentation.OSNServlet;

@Singleton
public class APNSCertificateServlet extends OSNServlet{

	/**
	 * generated serialVersionUID.
	 */
	private static final long serialVersionUID = -6074781175135441913L;
	
	private final Provider<SaveAPNSCertificateCommand> saveAPNSCertificateCommand;
	
	@Inject
	public APNSCertificateServlet(final Provider<SaveAPNSCertificateCommand> saveAPNSCertificateCommand){
		this.saveAPNSCertificateCommand = saveAPNSCertificateCommand;
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		final SaveAPNSCertificateCommand cmd = saveAPNSCertificateCommand.get();
		cmd.init(req, resp, MyErrorMessages.class);
		cmd.process();
	}

}
