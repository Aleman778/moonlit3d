package universe.graphics;

import universe.core.Node;
import universe.math.Matrix4;

public class SpriteBatch extends Renderer {

	private static final int MAX_NUM_TRIANGLES = 2000;
	private static final int MAX_NUM_VERTICES = MAX_NUM_TRIANGLES * 3;
	private static final int DEFAULT_CAPACITY = MAX_NUM_VERTICES * 12;
	
	private final Graphics graphics;
	private final VertexBufferObject bufVertices;
	private final IndexBufferObject bufIndices;
	private final int capacity;
	
	private Shader shader;
	private Texture texture;
	private Matrix4 combined;
	private boolean drawing;
	private int count;
	
	public boolean stroke 		 = true;
	public float strokeWidth 	 = 1.0f;
	public Color strokeColor 	 = Color.BLACK;
	public StrokeCap strokeCap 	 = StrokeCap.BUTT;
	public StrokeJoin strokeJoin = StrokeJoin.MITER;
	
	public boolean fill 	   = true;
	public Color fillColor 	   = Color.WHITE;
	
	public boolean tint 	  = false;
	public Color tintColor    = Color.WHITE;
	
	private float nx = 0.0f, ny = 0.0f, nz = 0.0f;
	
	public SpriteBatch(Graphics graphics) {
		this(graphics, DEFAULT_CAPACITY);
	}
	
	public SpriteBatch(Graphics graphics, int capacity) {
		this.drawing = false;
		this.capacity = capacity;
		this.graphics = graphics;
		this.combined = Matrix4.identity();
		this.shader = graphics.shader(Node.PHONG);
		this.bufVertices = graphics.createVBO(DEFAULT_CAPACITY, true);
		this.bufIndices = graphics.createIBO(MAX_NUM_VERTICES, true);
	}
	
	public void begin() {
		if (drawing)
			throw new IllegalStateException("You must not be drawing when calling begin().");
		
		drawing = true;
	}
	
	public void end() {
		if (!drawing)
			throw new IllegalStateException("You must be drawing when calling end().");
		
		flush();
		drawing = false;
	}
	
	public void flush() {
		if (count > 0) {
			shader.enable();
			shader.setMat4("m_combined", combined);
			shader.setMat4("m_model", Matrix4.identity());
			graphics.render(ShapeMode.TRIANGLES, bufVertices, bufIndices);
			bufVertices.clear();
			bufIndices.clear();
			count = 0;
		}
	}
	
	@Override
	public void submit(Renderable renderable) {
		
	}
	
	@Override
	public void present() {
		flush();
	}
}
