package fum.validation;

import fum.data.objects.Attributes;

public class InvalidAttributeValueException extends RuntimeException {

	private static final long serialVersionUID = -5089736612247594277L;

	public InvalidAttributeValueException(Attributes attribute) {
		super("The value of the attribute " + attribute.getAttribute() + " did not conform to the pattern " + attribute.getPattern());
	}
	
}
