package de.hackerspacebremen.presentation;

import de.hackerspacebremen.ErrorMessages;
import de.hackerspacebremen.commands.resultobject.BasicResultObject;

public class BasicOsnRestController {
	
	private ErrorMessages errorMessages;

	public BasicOsnRestController(ErrorMessages errorMessages) {
		this.errorMessages = errorMessages;
	}

	protected BasicResultObject error(int code) {
		return new BasicResultObject(code, errorMessages.getMessage(code));
	}
}
