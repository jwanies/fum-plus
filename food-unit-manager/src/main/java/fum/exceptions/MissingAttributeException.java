package fum.exceptions;

import java.util.Set;

public class MissingAttributeException extends RuntimeException {

	private static final long serialVersionUID = -9155908808816182870L;

	public MissingAttributeException(Set<String> attributes) {
		super("The following attributes need to be provided: " + attributes.toString());
	}
	
}
