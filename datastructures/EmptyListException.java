package ca.gbc.comp2080.datastructures;

public class EmptyListException extends RuntimeException {
	public EmptyListException() {
		super();
	}

	public EmptyListException(String s) {
		super(s);
	}
}
