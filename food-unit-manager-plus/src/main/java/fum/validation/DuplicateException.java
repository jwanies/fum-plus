package fum.validation;

public class DuplicateException extends RuntimeException {

	private static final long serialVersionUID = 1662198386228790354L;

	public DuplicateException(String duplicateField) {
		super(duplicateField + " already exists");
	}
	
}
