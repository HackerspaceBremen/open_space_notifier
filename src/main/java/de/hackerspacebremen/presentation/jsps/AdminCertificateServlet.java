package de.hackerspacebremen.presentation.jsps;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Singleton;

import de.hackerspacebremen.presentation.admin.CertificateController;

/**
 * @deprecated use {@link CertificateController} instead
 * @author Steve
 *
 */
@Deprecated
@Singleton
public class AdminCertificateServlet extends HttpServlet{

	/**
	 * generated serialVersionUID.
	 */
	private static final long serialVersionUID = -4242857292922705690L;

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getRequestDispatcher("/adminCertificate.jsp").forward(req,resp);
	}
}
