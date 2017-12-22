package universe.graphics;

import java.nio.ShortBuffer;

public abstract class IndexBufferObject extends Buffer {

	protected boolean dynamic;
	
	public IndexBufferObject(int capacity, boolean dynamic) {
		super(capacity);
		
		this.dynamic = dynamic;
	}

    /**
     * Put data in the buffer.
     * @param data the data to put
     */
	public abstract void put(short[] data);

    /**
     * Put data in the buffer.
     * @param buffer  the data to put
     */
	public abstract void put(ShortBuffer buffer);

	/**
	 * Map this buffer's data into the client's address space.
	 */
	public abstract ShortBuffer map();

	/**
	 * Unmaps the buffer.<br>
	 * <b>Note:</b> this call is required for any buffer changes to be rendered. 
	 */
	public abstract void unmap();
}
