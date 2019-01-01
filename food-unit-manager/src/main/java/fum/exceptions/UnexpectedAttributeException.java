package fum.exceptions;

import java.util.Set;

public class UnexpectedAttributeException extends RuntimeException {

	private static final long serialVersionUID = 2278373402245914318L;

	public UnexpectedAttributeException(Set<String> attributes) {
		super("The following attributes were unexpected: " + attributes.toString());
	}
	
}
