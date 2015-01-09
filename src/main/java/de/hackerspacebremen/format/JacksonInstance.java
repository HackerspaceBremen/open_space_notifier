package de.hackerspacebremen.format;

import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.hackerspacebremen.commands.resultobjects.AbstractResult;

public final class JacksonInstance {

	private static final Logger logger = Logger.getLogger(JacksonInstance.class
			.getName());

	private final ObjectMapper mapper = new ObjectMapper();

	public <R extends AbstractResult> String write(final Object object) {
		String result = null;
		try {
			result = mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			logger.severe("JsonProcessingException occured: " + e.getMessage());
		}

		return result;
	}
}
