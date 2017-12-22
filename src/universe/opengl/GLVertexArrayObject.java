package universe.opengl;

import universe.graphics.Buffer;
import universe.graphics.BufferLayout;
import universe.graphics.VertexArrayObject;
import universe.graphics.BufferLayout.BufferElement;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.util.ArrayList;

public class GLVertexArrayObject extends VertexArrayObject {

	private final GLGraphics graphics;
	
	private int object;
	private int count;
	
	public GLVertexArrayObject(GLGraphics graphics) {
		this.graphics = graphics;
		this.object = glGenVertexArrays();
		this.count = 0;
	}
	
	@Override
	public void bind() {
		check();
		
		if (isBound())
			return;
		
		glBindVertexArray(object);
		graphics.state.vertexArray = object;
	}
	
	@Override
	public void unbind() {
		check();
		
		glBindVertexArray(0);
		graphics.state.vertexArray = object;
	}
	
	@Override
	public void put(Buffer buf, BufferLayout layout) {
		check();
		
		if (layout.empty()) {
			
		}
		
		//Bind Vertex Array
		bind();
		
		//Bind Vertex Buffer
		buf.bind();
		
		//Apply layouts
		ArrayList<BufferElement> elements = layout.elements();
		for (int i = 0; i < elements.size(); i++) {
			BufferElement e = elements.get(count);
			glEnableVertexAttribArray(count);
			glVertexAttribPointer(i, e.count, GLGraphics.glGetType(e.type), e.normalized, layout.stride(), e.offset * e.size);
			count++;
		}
		
	}
	
	@Override
	public void clear() {
		check();
		
		bind();
		
		for (int i = 0; i < count; i++) {
			glDisableVertexAttribArray(i);
		}
		count = 0;
	}
	
	@Override
	public void dispose() {
		check();
		
		glDeleteVertexArrays(object);
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
