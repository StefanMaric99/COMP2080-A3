package ca.gbc.comp2080.datastructures;

public interface Iterator {
	public boolean hasNext();

	public Object next() throws NoSuchElementException;
}
