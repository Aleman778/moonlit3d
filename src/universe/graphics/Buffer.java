package universe.graphics;

import universe.util.Disposable;

public abstract class Buffer implements Disposable {

	protected final int capacity;
	
	protected int count;
	protected int position;
	
	/**
	 * Constructor.
	 * @param capacity the maximum number of elements the buffer can hold.
	 */
	public Buffer(int capacity) {	
		this.capacity = capacity;
		this.position = 0;
		this.count = 0;
	}
    
    /**
     * Bind the vertex buffer object.
     */
    public abstract void bind();
    
    /**
     * Unbind the vertex buffer object.
     */
    public abstract void unbind();
    
    /**
     * Get the current position.
     * @return the current position
     */
    public int position() {
		return position;
	}
    
    /**
     * Set the current position
     * @param pos
     */
    public void position(int pos) {
    	this.position = pos;
    }
    
    /**
     * Get the number of elements in the buffer.
     * @return the size of the buffer
     */
    public final int count() {
        return count;
    }
    
    /**
     * Get the size of this buffer in bytes.
     * @return  the size of the buffer
     */
    public abstract long size();
}
