package de.hackerspacebremen.presentation;

import javax.servlet.http.HttpServlet;

import com.googlecode.objectify.ObjectifyService;

import de.hackerspacebremen.data.entities.GCMAuth;
import de.hackerspacebremen.data.entities.GCMData;
import de.hackerspacebremen.data.entities.Property;
import de.hackerspacebremen.data.entities.SpaceStatus;

public class OSNServlet extends HttpServlet {

	/**
	 * generated serial Version uid.
	 */
	private static final long serialVersionUID = 8845403043267437745L;

	static {
        ObjectifyService.register(SpaceStatus.class);
        ObjectifyService.register(GCMData.class);
        ObjectifyService.register(GCMAuth.class);
        ObjectifyService.register(Property.class);
    }
}
