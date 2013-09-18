package de.hackerspacebremen.domain.api;

import de.hackerspacebremen.domain.val.ValidationException;
import de.hackerspacebremen.valueobjects.PushProperties;

public interface PropertyService {

	PushProperties fetchPushProperties();
	
	PushProperties savePushProperties(final boolean gcmEnabled, 
			final boolean apnsEnabled, final boolean mpnsEnabled,
			final String gcmKey, 
			final String apnsFileKeyString, final String apnsPassword) 
					throws ValidationException;
	
	String findValueByKey(String key) throws ValidationException;
}
