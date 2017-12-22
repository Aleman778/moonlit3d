package universe.opengl;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL15.*;

import universe.graphics.Shader;
import universe.graphics.Shape;
import universe.graphics.ShapeMode;
import universe.math.Matrix4;

public final class GLShape extends Shape {
	
	private final GLGraphics graphics;
	
	private float x = 0;
	
	public GLShape(GLGraphics graphics, ShapeMode mode, boolean dynamic) {
		super(graphics, mode, dynamic);
		this.graphics = graphics;
	}

	@Override
	public void bind() {
		vertices.bind();
		attribs.enable(this);
	}

	@Override
	public void unbind() {
	}
	
	@Override
	public void draw() {
		x += 1;
		Shader shader = material.getShader();
		
		material.enable();
		shader.setMat4("m_model", transform.local());
		shader.setMat4("m_view", Matrix4.identity());
		shader.setMat4("m_projection", Matrix4.perspective(60, display.getAspectRatio(), -1, 10000));
		
		bind();
		graphics.render(this);
	}
	
	@Override
	public void begin() {
		super.begin();
	}
	
	@Override
	public void end() {
		super.end();
		
		int usage = dynamic ? GL_DYNAMIC_DRAW : GL_STATIC_DRAW;
		vertices = new GLVertexBufferObject(graphics, data.getVertSize(), true);
		data.updateVertexBuffer(vertices);
	}

	@Override
	public void attrib(String name, int type, int size) {
		attribs.put(name, type, size);
	}
	
	@Override
	public void bindAttrib(VertexAttribute a) {
		//System.out.println(a.getName() + ": " + a.getPointer() + ", " + a.getSize() + ", " + a.getStride());
		glEnableVertexAttribArray(a.getIndex());
		glVertexAttribPointer(a.getIndex(),  a.getSize(), GLGraphics.glGetType(a.getType()),
							  a.isNormalized(), a.getStride() * GLGraphics.glGetSizeOf(a.getType()),
							  a.getPointer() * GLGraphics.glGetSizeOf(a.getType()));
	}
}
