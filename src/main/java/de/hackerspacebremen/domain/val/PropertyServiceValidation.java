package de.hackerspacebremen.domain.val;

import com.google.inject.Inject;

import de.hackerspacebremen.domain.api.PropertyService;

public class PropertyServiceValidation extends Validation 
	implements PropertyService{
	
	@Inject
	private PropertyService propertyService;

	@Override
	public String findValueByKey(final String key) throws ValidationException {
		this.validateIfEmpty(key, 23);
		return propertyService.findValueByKey(key);
	}

}
