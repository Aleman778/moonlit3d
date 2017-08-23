package universe.opengl;

import universe.graphics.VertexArray;

import static org.lwjgl.opengl.GL30.*;

public class GLVertexArray extends VertexArray {

	private final GLGraphics graphics;
	
	private int object;
	
	public GLVertexArray(GLGraphics graphics) {
		this.graphics = graphics;
		this.object   = glGenVertexArrays();
	}
	
	@Override
	public void bind() {
		check();
		
		if (isBound())
			return;
		
		glBindVertexArray(object);
		graphics.state.arrayBuffer = object;
	}
	
	@Override
	public void unbind() {
		check();
		
		glBindVertexArray(0);
		graphics.state.arrayBuffer = object;
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
