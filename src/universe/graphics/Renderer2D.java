package universe.graphics;

import universe.core.Node;
import universe.math.Matrix4;
import universe.opengl.GLGraphics;

public class Renderer2D extends Renderer {

	private static final int RENDERER_MAX_VERTICES = 4000;
	private static final int RENDERER_VERTEX_SIZE = 12;
	private static final int RENDERER_VBO_SIZE = RENDERER_MAX_VERTICES * RENDERER_VERTEX_SIZE;
	private static final int RENDERER_IBO_SIZE = RENDERER_MAX_VERTICES * 4;

	private final Graphics graphics;

	private BufferLayout layout;
	private VertexBufferObject vbo;
	private IndexBufferObject ibo;
	private Shader shader;
	private boolean drawing;
	private int count;
	
	public Renderer2D(GLGraphics graphics) {
		this.graphics = graphics;
		this.shader = graphics.shader(Node.PHONG);
		this.vbo = graphics.createVBO(RENDERER_VBO_SIZE, true);
		this.ibo = graphics.createIBO(RENDERER_IBO_SIZE, true);
		this.drawing = false;
		this.count = 0;
		
		layout = new BufferLayout();
		layout.push("position", Node.FLOAT, Float.BYTES, 2, 0, false);
		layout.push("texcoord", Node.FLOAT, Float.BYTES, 2, 2, false);
		layout.push("normal",   Node.FLOAT, Float.BYTES, 3, 4, false);
		
		
		//layout.push("color",    Node.FLOAT, Float.BYTES, 4, 8, false);
	}
	
	public void begin() {
		if (drawing)
			throw new IllegalStateException("Renderer must not be drawing when calling begin()");
		
		drawing = true;
	}
	
	public void end() {
		if (!drawing)
			throw new IllegalStateException("Renderer must be drawing when calling end()");
		
		drawing = false;
	}
	
	private void flush() {
		if (count > 0) {
			shader.enable();
			shader.setMat4("m_model", Matrix4.identity());
			shader.setMat4("m_combined", Matrix4.perspective(60, graphics.display.getAspectRatio(), -1, 10000));
//			shader.setMat4("m_normal", Matrix4.identity());
			ibo.bind();
			vbo.bind();
			graphics.render(ShapeMode.TRIANGLES, vbo, ibo);
			graphics.viewport(0, 0, 1, 1);
			vbo.clear();
			ibo.clear();
			count = 0;
		}
	}
	
	@Override
	public void submit(Renderable renderable) {
		if (!(renderable instanceof Renderable2D))
			throw new IllegalArgumentException(renderable.getClass().getSimpleName() + " data mismatch, expected Renderable2D");
		
		float[] vertices = renderable.vertices();
		short[] indices = renderable.indices();
		
		if (vertices.length > RENDERER_VBO_SIZE)
			throw new IllegalArgumentException("Maximum renderable2d size has exceeded.");
		
		if (renderable.count() + count > RENDERER_MAX_VERTICES)
			flush();
		
		for (int i = 0; i < indices.length; i++) {
			indices[i] += count;
		}
		
		vbo.put(vertices);
		ibo.put(indices);
		
		count += renderable.count();
	}
	
	@Override
	public void present() {
		flush();
	}
}
