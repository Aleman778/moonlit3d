package universe.opengl;

import java.nio.ShortBuffer;

import universe.graphics.IndexBuffer;

import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL15.GL_READ_WRITE;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glBufferSubData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL15.glMapBuffer;
import static org.lwjgl.opengl.GL15.glUnmapBuffer;

public class GLIndexBuffer extends IndexBuffer {
	
	private static int glBoundArrayBuffer = 0;
	
	private int ref;
	private boolean stored;
	private boolean dynamic;
	private ShortBuffer map;
	
	public GLIndexBuffer(int capacity, boolean dynamic) {
		super(capacity);
		
		this.ref 	 = glGenBuffers();
		this.map     = null;
		this.stored  = false;
		this.dynamic = dynamic;
	}
	
	@Override
	public void put(short... data) {
		check();
		
		int offset = position;
		
		bind();
		super.put(data);
		
		if (!stored) {
			store();
		} else {
			if (isMapped()) {
				map.position(offset);
				map.put(data);
			} else {
				glBufferSubData(GL_ELEMENT_ARRAY_BUFFER, offset * Short.BYTES, data);
			}
		}
	}
	
	public void map() {
		check();
		bind();
		
		if (!stored) {
			store();
		}
		
		map = glMapBuffer(GL_ELEMENT_ARRAY_BUFFER, GL_READ_WRITE).asShortBuffer();
	}
	
	public void unmap() {
		check();
		glUnmapBuffer(GL_ELEMENT_ARRAY_BUFFER);
		map = null;
	}
	
	@Override
	public void bind() {
		check();
		
		if (isBound()) {
			return;
		}
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ref);
		glBoundArrayBuffer = ref;
	}
	
	@Override
	public void unbind() {
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
	}

	public boolean isBound() {
		return (glBoundArrayBuffer == ref);
	}
	
	@Override
	public void dispose() {
		check();
		
		glDeleteBuffers(ref);
		ref = -1;
	}
	
	private void store() {
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, dynamic ? GL_DYNAMIC_DRAW : GL_STATIC_DRAW);
		stored = true;
	}
	
    private void check() {
    	if (ref == -1)
    		throw new NullPointerException();
    }
    
	public boolean isMapped() {
		return (map != null);
	}
}
