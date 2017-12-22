package universe.graphics;

import universe.util.Disposable;

public abstract class Buffer implements Disposable {
	
	protected int capacity;
	protected int position;
	protected int count;

	/**
	 * Constructor.
	 * @param capacity the maximal 
	 */
	public Buffer(int capacity) {
		this.capacity = capacity;
		this.position = 0;
		this.count = 0;
	}
	
	/**
	 * Bind the dcbuffer object.
     */
    public abstract void bind();

    /**
     * Unbind the buffer object.
     */	
    public abstract void unbind();
    
    /**
     * Resize the buffer
     * @param minCapacity
     */
    public abstract void resize(int size);

    /**
     * Clear the contents of the buffer.
     */
	public void clear() {
		position = 0;
		count = 0;
	}
	
	/**
	 * Get the total capacity of the buffer.
	 * @return the capacity
	 */
	public int capacity() {
		return capacity;
	}
	
	/**
	 * Get the number of elements in the buffer. 
	 * @return the count
	 */
	public int count() {
		return count;
	}

	/**
	 * Check if the buffer is empty.
	 * @return true if the buffer is empty, false otherwise.
	 */
	public boolean empty() {
		return count == 0;
	}
}
