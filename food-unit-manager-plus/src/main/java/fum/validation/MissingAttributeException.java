package fum.validation;

public class MissingAttributeException extends RuntimeException {

	private static final long serialVersionUID = -9155908808816182870L;

	public MissingAttributeException(String attributes) {
		super("The following attribute needs to be provided: " + attributes);
	}
	
}
