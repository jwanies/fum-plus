package fum.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionAdvice {

	@ResponseBody
	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String foodUnitNotFoundHandler(EntityNotFoundException ex) {
		return ex.getMessage();
	}
	
	@ResponseBody
	@ExceptionHandler(MissingAttributeException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	String missingAttributeHandler(MissingAttributeException ex) {
		return ex.getMessage();
	}
	
	@ResponseBody
	@ExceptionHandler(UnexpectedAttributeException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	String unexpectedAttributeHandler(UnexpectedAttributeException ex) {
		return ex.getMessage();
	}

	@ResponseBody
	@ExceptionHandler(DuplicateException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	String unexpectedAttributeHandler(DuplicateException ex) {
		return ex.getMessage();
	}
}
