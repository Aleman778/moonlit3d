package universe.util;

import java.util.NoSuchElementException;

/**
 * An iterator is used to iterate trough an collection of elements.
 * 
 * @author Aleman778
 * @param <E> the type of elements in the iteration
 */
public interface Iterator<E> {

	/**
	 * Get the next element in the iterator.
	 * @return the next element in the iterator
	 * @throws NoSuchElementException if there are no more elements in the iterator
	 */
	public E next() throws NoSuchElementException;
	
	/**
	 * Removes the next element in the iterator.
	 */
	public void remove();
	
	/**
	 * Check if the iteration has a next element.
	 * @return true if there is a next element, otherwise false
	 */
	public boolean hasNext();
}
