package de.hackerspacebremen.domain;

import com.google.inject.Inject;

import de.hackerspacebremen.data.api.PropertyDAO;
import de.hackerspacebremen.data.entities.Property;
import de.hackerspacebremen.domain.api.PropertyService;
import de.hackerspacebremen.domain.val.ValidationException;
import de.hackerspacebremen.exceptions.NotCompletelyConfigured;

public class PropertyServiceImpl implements PropertyService{

	@Inject
	private PropertyDAO propertyDAO;
	
	@Override
	public String findValueByKey(final String key) throws ValidationException {
		Property property = propertyDAO.findByKey(key);
		if(property==null){
			property = new Property();
			property.setKey(key);
			property.setValue("DUMMY!!!");
			propertyDAO.persist(property);
			throw new NotCompletelyConfigured();
		}
		return property.getValue();
	}

}
