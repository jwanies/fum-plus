package fum.exceptions;

public class EntityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -1510953937322267623L;

	public EntityNotFoundException(Long id) {
		super("Could not find entity with ID " + id);
	}
	
	public EntityNotFoundException(String name) {
		super("Could not find entity with name " + name);
	}
	
}
