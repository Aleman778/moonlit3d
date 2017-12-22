package universe.opengl;

import static org.lwjgl.opengl.GL15.*;

import java.nio.ShortBuffer;
import universe.graphics.IndexBufferObject;

public class GLIndexBufferObject extends IndexBufferObject {

	private final GLGraphics graphics;

	private int object;
	private int usage;

	/**
	 * Creates an empty vertex buffer with a desired maximum capacity.
	 * @param graphics the graphics processor being used in this thread
	 * @param capacity the maximum number of elements the buffer can hold
	 * @param dynamic elements in the buffer can be modified if the dynamic flag is true
	 */
	public GLIndexBufferObject(GLGraphics graphics, int capacity, boolean dynamic) {
		super(capacity, dynamic);

		this.usage = dynamic ? GL_DYNAMIC_DRAW : GL_STATIC_DRAW;
		this.object = glGenBuffers();
		this.graphics = graphics;
		
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, object);
		graphics.state.elementArrayBuffer = object;
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, capacity * Float.BYTES, usage);
	}

	/**
	 * Creates a buffer containing the provided data.
	 * @param graphics the graphics processor being used in this thread
	 * @param data the array of data to store in the buffer
	 * @param dynamic elements in the buffer can be modified if the dynamic flag is true
	 */
	public GLIndexBufferObject(GLGraphics graphics, short[] data, boolean dynamic) {
		super(data.length, dynamic);

		this.usage = dynamic ? GL_DYNAMIC_DRAW : GL_STATIC_DRAW;
		this.object = glGenBuffers();
		this.graphics = graphics;
		this.position = data.length;
		this.count = data.length;
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, object);
		graphics.state.elementArrayBuffer = object;
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, data, usage);
	}

	/**
	 * Creates a buffer containing copies of the buffer data in the provided short buffer.
	 * @param graphics the graphics processor being used in this thread
	 * @param buffer the buffer data to copy from
	 * @param dynamic elements in the buffer can be modified if the dynamic flag is true
	 */
	public GLIndexBufferObject(GLGraphics graphics, ShortBuffer buffer, boolean dynamic) {
		super(buffer.remaining(), dynamic);

		this.usage = dynamic ? GL_DYNAMIC_DRAW : GL_STATIC_DRAW;
		this.object = glGenBuffers();
		this.graphics = graphics;
		this.position = buffer.remaining();
		this.count = buffer.remaining();
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, object);
		graphics.state.elementArrayBuffer = object;
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, usage);
	}
	
	@Override
	public void bind() {
		check();
		
		if (isBound())
			return;
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, object);
		graphics.state.elementArrayBuffer = object;
	}

	@Override
	public void unbind() {
		check();
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		graphics.state.elementArrayBuffer = 0;
	}
	@Override
	public void put(short[] data) {
		bind();
		
		glBufferSubData(GL_ELEMENT_ARRAY_BUFFER, position * Float.BYTES, data);

		position += data.length;
		if (position > count)
			count = position;
	}

	@Override
	public void put(ShortBuffer buffer) {
		bind();
		
		int length = buffer.remaining();
		
		glBufferSubData(GL_ELEMENT_ARRAY_BUFFER, position * Float.BYTES, buffer);

		position += length;
		if (position > count)
			count = position;
	}
		
	@Override
	public ShortBuffer map() {
		check();
		bind();
		
		return glMapBuffer(GL_ELEMENT_ARRAY_BUFFER, GL_READ_WRITE).asShortBuffer();
	}

	@Override
	public void unmap() {
		check();
		bind();
		
		glUnmapBuffer(GL_ELEMENT_ARRAY_BUFFER);
	}

	@Override
	public void resize(int size) {
		capacity = size;
		
		bind();
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, size, usage);
	}
	
	@Override
	public void dispose() {
		check();
		
		glDeleteBuffers(object);
		object = -1;
	}
	
	private boolean isBound() {
		return (graphics.state.elementArrayBuffer == object);
	}
	
    private void check() {
    	if (object == -1)
    		throw new NullPointerException();
    }
}
