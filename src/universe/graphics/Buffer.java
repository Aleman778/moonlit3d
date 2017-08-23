package universe.graphics;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

import universe.util.Disposable;

public abstract class Buffer implements Disposable {
	
	protected final Target target;
	
	protected Number[] buffer;
	protected ByteBuffer mapBuffer;
	protected boolean immutable;
	protected boolean dynamic;
	protected boolean mapped;
	protected int capacity;
	protected int position;
	protected int count;
	protected int type;

	public Buffer(Target target, int type, int capacity, boolean dynamic) {		
		this.buffer = new Number[capacity];
		this.target = target;
		this.type = type;
		this.capacity = capacity;
		this.dynamic = dynamic;
		this.immutable = false;
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
	 * Map this buffer's data into the client's address space.
	 */
	public abstract ByteBuffer map();

	/**
	 * Unmaps the buffer.<br>
	 * <b>Note:</b> this call is required for any buffer changes to be rendered. 
	 */
	public abstract void unmap();
    
    /**
     * Put data in the buffer.
     * @param data the data to put
     */
    public void put(Number... data) {
    	checkPosition(position + data.length);
    	
		for (int i = 0; i < data.length; i++) {
			buffer[position++] = data[i];
		}
    }
    
    /**
     * Set the data in the buffer at a specific offset.
     * @param offset the buffers offset value
     * @param data the data to set
     */
    public void set(int offset, Number... data) {
    	checkPosition(offset + data.length);
    	
		for (int i = 0; i < data.length; i++) {
			buffer[offset++] = data[i];
		}
		
		if (offset > position)
			position = offset;
    }
    
    /**
     * Check if the buffer is mapped.
     * @return true if the buffer is mapped, otherwise false is returned
     */
    public boolean isMapped() {
    	return (mapBuffer != null);
    }
    
    /**
     * Get the number of elements in the buffer.
     * @return the number of elements
     */
    public int count() {
		return count;
	}
    
    /**
     * Get an integer array of the buffer.
     * @return an integer array containing the buffer data
     */
	public int[] toIntArray() {
		return toIntArrayImpl(buffer);
	}

    /**
     * Get an float array of the buffer.
     * @return an float array containing the buffer data
     */
	public float[] toFloatArray() {
		return toFloatArrayImpl(buffer);
	}

    /**
     * Get an short array of the buffer.
     * @return an short array containing the buffer data
     */
	public short[] toShortArray() {
		return toShortArrayImpl(buffer);
	}
    
    /**
     * Increase the capacity of the buffer.
     * @param newCapacity
     */
    protected void resize(int minCapacity) {
    	if (immutable)
    		throw new IllegalStateException("The buffer is immutable.");
    	
    	if (minCapacity < 0)
    		throw new OutOfMemoryError();
    	
    	int newCapacity = ((capacity < 152) ?
    					   (capacity + 1) * 2 :
    					   (capacity / 2) * 3);
    	
    	if (newCapacity < 0)
    		newCapacity = Integer.MAX_VALUE;
    	if (newCapacity < minCapacity)
    		newCapacity = minCapacity;
    	
    	capacity = newCapacity;
    	buffer = Arrays.copyOf(buffer, capacity);
    }
    
    private int checkPosition(int position) {
		if (position > capacity)
			resize(position);

		if (position > count)
			count = position;
		
		return position;
    }

	protected int[] toIntArrayImpl(Number[] input) {
		int[] result = new int[input.length];
		for (int i = 0; i < result.length; i++) {
			if (input[i] == null)
				result[i] = 0;
			else
				result[i] = input[i].intValue();
		}
		return result;
	}

	protected float[] toFloatArrayImpl(Number[] input) {
		float[] result = new float[input.length];
		for (int i = 0; i < result.length; i++) {
			if (input[i] == null)
				result[i] = 0.0f;
			else
				result[i] = input[i].floatValue();
		}
		return result;
	}

	protected short[] toShortArrayImpl(Number[] input) {
		short[] result = new short[input.length];
		for (int i = 0; i < result.length; i++) {
			if (input[i] == null)
				result[i] = 0;
			else
				result[i] = input[i].shortValue();
		}
		return result;
	}
	
	/**
	 * Buffer target
	 */
	public enum Target {
		VERTEX_BUFFER, INDEX_BUFFER
	}
}
