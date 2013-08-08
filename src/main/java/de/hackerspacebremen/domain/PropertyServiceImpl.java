package de.hackerspacebremen.domain;

import com.google.inject.Inject;

import de.hackerspacebremen.data.api.PropertyDAO;
import de.hackerspacebremen.data.entities.Property;
import de.hackerspacebremen.domain.api.PropertyService;
import de.hackerspacebremen.domain.val.ValidationException;

public class PropertyServiceImpl implements PropertyService{

	@Inject
	private PropertyDAO propertyDAO;
	
	@Override
	public String findValueByKey(final String key) throws ValidationException {
		final Property property = propertyDAO.findByKey(key);
		if(property==null){
			// TODO create dummy value
		}
		return property.getValue();
	}

}
