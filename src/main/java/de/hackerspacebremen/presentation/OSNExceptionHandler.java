package de.hackerspacebremen.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import de.hackerspacebremen.MyErrorMessages;
import de.hackerspacebremen.commands.resultobjects.BasicResultObject;
import de.hackerspacebremen.domain.val.ValidationException;

@ControllerAdvice
public class OSNExceptionHandler {
	
	private MyErrorMessages errorMessages;
	
	@Autowired
	public OSNExceptionHandler(MyErrorMessages errorMessages) {
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
}
