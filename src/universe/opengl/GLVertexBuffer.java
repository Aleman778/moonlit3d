package universe.opengl;

import universe.graphics.VertexAttribute;
import universe.graphics.VertexBuffer;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

public final class GLVertexBuffer extends VertexBuffer {
	
	private static int glBoundArrayBuffer = 0;
	
	private int ref;
	private boolean stored;
	private boolean dynamic;
	private FloatBuffer map;
	
	public GLVertexBuffer(int capacity, boolean dynamic) {
		super(capacity);
		
		this.ref 	 = glGenBuffers();
		this.map     = null;
		this.stored  = false;
		this.dynamic = dynamic;
	}
	
	@Override
	public void put(float... data) {
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
				glBufferSubData(GL_ARRAY_BUFFER, offset * Float.BYTES, data);
			}
		}
	}
	
	@Override
	public void attribute(VertexAttribute attrib) {
		bind();
		super.attribute(attrib);
		glVertexAttribPointer(attrib.getLocation(), attrib.getCount(), GL_FLOAT, false,
							  attrib.getOffset() * Float.BYTES, attrib.getPointer() * Float.BYTES);
		glEnableVertexAttribArray(attrib.getLocation());
	}
	
	public void map() {
		check();
		bind();
		
		if (!stored) {
			store();
		}
		
		map = glMapBuffer(GL_ARRAY_BUFFER, GL_READ_WRITE).asFloatBuffer();
	}
	
	public void unmap() {
		check();
		glUnmapBuffer(GL_ARRAY_BUFFER);
		map = null;
	}
	
	@Override
	public void bind() {
		check();
		
		if (isBound()) {
			return;
		}
		
		glBindBuffer(GL_ARRAY_BUFFER, ref);
		glBoundArrayBuffer = ref;
	}
	
	@Override
	public void unbind() {
		glBindBuffer(GL_ARRAY_BUFFER, 0);
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
		glBufferData(GL_ARRAY_BUFFER, buffer, dynamic ? GL_DYNAMIC_DRAW : GL_STATIC_DRAW);
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
