package de.hackerspacebremen.data;

import static com.googlecode.objectify.ObjectifyService.ofy;

import org.springframework.stereotype.Repository;

import de.hackerspacebremen.data.entity.Property;

@Repository
public class PropertyDao extends AbstractObjectifyDao<Property> {

	public Property findByKey(final String key) {
		return ofy().load().type(Property.class).filter("key", key).first().now();
	}

	protected Class<Property> getAccessedEntity() {
		return Property.class;
	}

}
