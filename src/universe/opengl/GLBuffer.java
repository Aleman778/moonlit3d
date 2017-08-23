package universe.opengl;

import java.nio.ByteBuffer;

import universe.graphics.Buffer;

import static org.lwjgl.opengl.GL15.*;

public class GLBuffer extends Buffer {
	
	private final GLGraphics graphics;
	
	private int object;
	private boolean stored;
	
	public GLBuffer(GLGraphics graphics, Target target, int type, int capacity, boolean dynamic) {
		super(target, type, capacity, dynamic);

		this.graphics = graphics;
		this.object = glGenBuffers();
		this.stored = false;
	}
	
	@Override
	public void put(Number... data) {
		check();
		
		if (isMapped())
			throw new IllegalStateException("This buffer is mapped to a separate buffer.");
		
		bind();
		int offset = position;
		super.put(data);
		
		if (stored) {
			setBufferSubData(offset, data);
		} else {
			setBufferData();
		}
	}
	
	@Override
	public void set(int offset, Number... data) {
		check();
		
		if (isMapped())
			throw new IllegalStateException("The buffer is mapped to a separate buffer.");
		
		bind();
		super.set(offset, data);

		if (stored) {
			setBufferSubData(offset, data);
		} else {
			setBufferData();
		}
	}
	
	@Override
	public void bind() {
		check();
		
		if (isBound())
			return;
		
		glBindBuffer(glGetBufferTarget(), object);
		setBindState(object);
		
		if (!stored)
			setBufferData();
	}

	@Override
	public void unbind() {
		check();
		
		glBindBuffer(glGetBufferTarget(), 0);
		setBindState(0);
	}
	
	@Override
	public ByteBuffer map() {
		check();
		bind();
		
		if (!stored)
			setBufferData();
		
		mapBuffer = glMapBuffer(glGetBufferTarget(), GL_READ_WRITE);
		return mapBuffer;
	}

	@Override
	public void unmap() {
		check();
		bind();
		
		copyBufferData();
		glUnmapBuffer(glGetBufferTarget());
		mapBuffer = null;
	}
	
	@Override
	public void dispose() {
		check();
		
		glDeleteBuffers(object);
		object = -1;
	}
	
	@Override
	protected void resize(int minCapacity) {
		super.resize(minCapacity);
		
		if (stored)
			setBufferData();
	}
	
	private void copyBufferData() {
		int i = 0;
		
		switch (target) {
		case VERTEX_BUFFER:
			while (mapBuffer.hasRemaining()) {
				buffer[i] = mapBuffer.getFloat();
				i++;
			}
			break;
		case INDEX_BUFFER:
			while (mapBuffer.hasRemaining()) {
				buffer[i] = mapBuffer.getShort();
				i++;
			}
			break;
		default:
			return;
		}
	}
	
	private void setBufferSubData(int offset, Number[] data) {
		glBufferSubData(glGetBufferTarget(), offset * Short.BYTES, toShortArrayImpl(data));
	}
	
	private void setBufferData() {
		glBufferData(glGetBufferTarget(), toShortArray(), dynamic ? GL_DYNAMIC_DRAW : GL_STATIC_DRAW);
		stored = true;
	}
	
	private void setBindState(int state) {
		switch (target) {
		case VERTEX_BUFFER:
			graphics.state.arrayBuffer = state;
			break;
		case INDEX_BUFFER:
			graphics.state.elementArrayBuffer = state;
			break;
		}
	}
	
	private int getBindState() {
		switch (target) {
		case VERTEX_BUFFER:
			return graphics.state.arrayBuffer;
		case INDEX_BUFFER:
			return graphics.state.elementArrayBuffer;
		}
		
		return 0;
	}
	
	private int glGetBufferTarget() {
		switch (target) {
		case VERTEX_BUFFER:
			return GL_ARRAY_BUFFER;
		case INDEX_BUFFER:
			return GL_ELEMENT_ARRAY_BUFFER;
		default:
			throw new IllegalArgumentException("Unsupported buffer target for OpenGL (" + target + ")");
		}
	}

	private boolean isBound() {
		return (getBindState() == object);
	}
	
    private void check() {
    	if (object == -1)
    		throw new NullPointerException();
    }
}
