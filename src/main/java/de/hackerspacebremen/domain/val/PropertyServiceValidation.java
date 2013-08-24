package de.hackerspacebremen.domain.val;

import com.google.inject.Inject;

import de.hackerspacebremen.domain.api.PropertyService;
import de.hackerspacebremen.valueobjects.PushProperties;

public class PropertyServiceValidation extends Validation 
	implements PropertyService{
	
	@Inject
	private PropertyService propertyService;

	@Override
	public String findValueByKey(final String key) throws ValidationException {
		this.validateIfEmpty(key, 23);
		return propertyService.findValueByKey(key);
	}

	@Override
	public PushProperties fetchPushProperties() {
		return propertyService.fetchPushProperties();
	}

	@Override
	public PushProperties savePushProperties(final boolean gcmEnabled,
			final boolean apnsEnabled, final boolean mpnsEnabled, 
			final String gcmKey) throws ValidationException{
		if(gcmEnabled){
			this.validateIfEmpty(gcmKey, 23);
		}
		return propertyService.savePushProperties(gcmEnabled, apnsEnabled, mpnsEnabled, gcmKey);
	}

}
