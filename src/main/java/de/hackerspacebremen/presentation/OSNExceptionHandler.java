package de.hackerspacebremen.presentation;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import de.hackerspacebremen.ErrorMessages;
import de.hackerspacebremen.commands.resultobject.BasicResultObject;
import de.hackerspacebremen.domain.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class OSNExceptionHandler {

	private ErrorMessages errorMessages;

	@Autowired
	public OSNExceptionHandler(ErrorMessages errorMessages) {
		this.errorMessages = errorMessages;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ValidationException.class)
	@ResponseBody
	public BasicResultObject handleValidationException(ValidationException ex) {
		int errorCode = ex.getErrorCode();
		BasicResultObject error = new BasicResultObject();
		error.setError(errorMessages.getMessage(errorCode));
		error.setErrorCode(errorCode);
		return error;
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public BasicResultObject handleUnknownException(Exception ex) {
		BasicResultObject error = new BasicResultObject();
		log.error(ex.getClass().getSimpleName() + " occured: " + ex.getMessage());
		error.setError("Unknown Exception(" + ex.getClass().getSimpleName() + ") occured: " + ex.getMessage());
		error.setErrorCode(-1);
		error.setStackTrace(ExceptionUtils.getStackTrace(ex));

		return error;

	}
}
