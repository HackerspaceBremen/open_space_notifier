package de.hackerspacebremen.domain.api;

import de.hackerspacebremen.domain.val.ValidationException;

public interface PropertyService {

	String findValueByKey(String key) throws ValidationException;
}
