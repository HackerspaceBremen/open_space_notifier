package de.hackerspacebremen.data.objectify;

import de.hackerspacebremen.data.api.PropertyDAO;
import de.hackerspacebremen.data.entities.Property;
import static com.googlecode.objectify.ObjectifyService.ofy;

public class PropertyDB extends AbstractObjectifyDB<Property> 
	implements PropertyDAO{

	@Override
	public Property findByKey(final String key) {
		return ofy().load().type(Property.class).filter("key", key).first().now();
	}

	@Override
	protected Class<Property> getAccessedEntity() {
		return Property.class;
	}

}
