package fum.validation;

import java.util.List;

public class UnexpectedAttributeException extends RuntimeException {

	private static final long serialVersionUID = -7697570305656498384L;

	public UnexpectedAttributeException(List<String> attributes) {
		super("The following attributes were unexpected: " + attributes.toString());
	}
	
}
