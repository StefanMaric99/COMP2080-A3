package ca.gbc.comp2080.datastructures;

/**
 * Runtime exceptions thrown when attempting to access the root of an empty
 * tree.
 */
class EmptyTreeException extends RuntimeException {
	public EmptyTreeException(String err) {
		super(err);
	}
}
