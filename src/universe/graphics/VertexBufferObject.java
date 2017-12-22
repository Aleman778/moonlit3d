package universe.graphics;

import java.nio.FloatBuffer;

public abstract class VertexBufferObject extends Buffer {

	protected BufferLayout layout;
	protected boolean dynamic;
	
	/**
	 * Creates an empty vertex buffer with a desired maximum capacity.
	 * @param capacity the maximum number of elements the buffer can hold
	 * @param dynamic elements in the buffer can be modified if the dynamic flag is true
	 */
	public VertexBufferObject(int capacity, boolean dynamic) {
		super(capacity);
		
		this.dynamic = dynamic;
		this.layout = null;
	}
	
    /**
     * Put data in the buffer.
     * @param data the data to put
     */
	public abstract void put(float[] data);

    /**
     * Put data in the buffer.
     * @param buffer the data to put
     */
	public abstract void put(FloatBuffer buffer);
	
	/**
	 * Disable the buffers layout data.
	 */
	public abstract void disableLayout();

	/**
	 * Map this buffer's data into the client's address space.
	 */
	public abstract FloatBuffer map();

	/**
	 * Unmaps the buffer.<br>
	 * <b>Note:</b> this call is required for any buffer changes to be rendered. 
	 */
	public abstract void unmap();
}
