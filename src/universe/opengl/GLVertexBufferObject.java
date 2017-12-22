package universe.opengl;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import universe.graphics.BufferLayout;
import universe.graphics.VertexBufferObject;
import universe.graphics.BufferLayout.BufferElement;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

public class GLVertexBufferObject extends VertexBufferObject {

	private final GLGraphics graphics;

	private int object;
	private int usage;
	
	/**
	 * Creates an empty vertex buffer with a desired maximum capacity.
	 * @param graphics the graphics processor being used in this thread
	 * @param capacity the maximum number of elements the buffer can hold
	 * @param dynamic elements in the buffer can be modified if the dynamic flag is true
	 */
	public GLVertexBufferObject(GLGraphics graphics, int capacity, boolean dynamic) {
		super(capacity, dynamic);

		this.usage = dynamic ? GL_DYNAMIC_DRAW : GL_STATIC_DRAW;
		this.object = glGenBuffers();
		this.graphics = graphics;
		
		glBindBuffer(GL_ARRAY_BUFFER, object);
		graphics.state.arrayBuffer = object;
		glBufferData(GL_ARRAY_BUFFER, capacity * Float.BYTES, usage);
	}
	
	/**
	 * Creates a buffer containing the provided data.
	 * @param graphics the graphics processor being used in this thread
	 * @param data the array of data to store in the buffer
	 * @param dynamic elements in the buffer can be modified if the dynamic flag is true
	 */
	public GLVertexBufferObject(GLGraphics graphics, float[] data, boolean dynamic) {
		super(data.length, dynamic);

		this.usage = dynamic ? GL_DYNAMIC_DRAW : GL_STATIC_DRAW;
		this.object = glGenBuffers();
		this.graphics = graphics;
		this.position = data.length;
		this.count = data.length;
		
		glBindBuffer(GL_ARRAY_BUFFER, object);
		graphics.state.arrayBuffer = object;
		glBufferData(GL_ARRAY_BUFFER, data, usage);
	}
	
	/**
	 * Creates a buffer containing copies of the buffer data in the provided float buffer.
	 * @param graphics the graphics processor being used in this thread
	 * @param buffer the buffer data to copy from
	 * @param dynamic elements in the buffer can be modified if the dynamic flag is true
	 */
	public GLVertexBufferObject(GLGraphics graphics, FloatBuffer buffer, boolean dynamic) {
		super(buffer.remaining(), dynamic);

		this.usage = dynamic ? GL_DYNAMIC_DRAW : GL_STATIC_DRAW;
		this.object = glGenBuffers();
		this.graphics = graphics;
		this.position = buffer.remaining();
		this.count = buffer.remaining();
		
		glBindBuffer(GL_ARRAY_BUFFER, object);
		graphics.state.arrayBuffer = object;
		glBufferData(GL_ARRAY_BUFFER, buffer, usage);
	}
	
	@Override
	public void bind() {
		check();
		
		if (isBound())
			return;
		
		glBindBuffer(GL_ARRAY_BUFFER, object);
		graphics.state.arrayBuffer = object;
	}

	@Override
	public void unbind() {
		check();
		
		if (layout != null) {
			for (int i = 0; i < layout.elements().size(); i++) {
				glDisableVertexAttribArray(i);
			}			
		}
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		graphics.state.arrayBuffer = 0;
	}
	
	@Override
	public void put(float[] data) {
		bind(); 
		
		glBufferSubData(GL_ARRAY_BUFFER, position * Float.BYTES, data);

		position += data.length;
		if (position > count)
			count = position;
	}

	@Override
	public void put(FloatBuffer buffer) {
		bind();
		
		int length = buffer.remaining();
		
		glBufferSubData(GL_ARRAY_BUFFER, position * Float.BYTES, buffer);

		position += length;
		if (position > count)
			count = position;
	}
	
	@Override
	public void disableLayout() {
		for (int i = 0; i < layout.count(); i++) {
			glDisableVertexAttribArray(i);
		}
	}
		
	@Override
	public FloatBuffer map() {
		check();
		bind();
		
		System.out.println("> " + graphics.state.arrayBuffer + " == " + object);
		glBindBuffer(GL_ARRAY_BUFFER, object);
		return glMapBuffer(GL_ARRAY_BUFFER, GL_READ_WRITE).asFloatBuffer();
	}

	@Override
	public void unmap() {
		check();
		bind();
		
		glUnmapBuffer(GL_ARRAY_BUFFER);
	}

	@Override
	public void resize(int size) {
		capacity = size;
		
		bind();
		glBufferData(GL_ARRAY_BUFFER, size, usage);
	}
	
	@Override
	public void dispose() {
		check();
		
		glDeleteBuffers(object);
		object = -1;
	}
	
	private boolean isBound() {
		return (graphics.state.arrayBuffer == object);
	}
	
    private void check() {
    	if (object == -1)
    		throw new NullPointerException();
    }
}
