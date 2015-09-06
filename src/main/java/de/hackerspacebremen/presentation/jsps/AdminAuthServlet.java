package de.hackerspacebremen.presentation.jsps;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Singleton;

import de.hackerspacebremen.presentation.admin.AuthController;

/**
 * @deprecated use {@link AuthController} instead
 * @author Steve
 *
 */
@Deprecated
@Singleton
public class AdminAuthServlet extends HttpServlet{

	/**
	 * generated serialVersionUID.
	 */
	private static final long serialVersionUID = 1147832045798741863L;

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getRequestDispatcher("/adminAuth.jsp").forward(req,resp);
	}
}
