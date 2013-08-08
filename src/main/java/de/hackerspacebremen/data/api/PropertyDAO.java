package de.hackerspacebremen.data.api;

import de.hackerspacebremen.data.entities.Property;

public interface PropertyDAO extends BasicDAO<Property>{

	Property findByKey(String key);
}
