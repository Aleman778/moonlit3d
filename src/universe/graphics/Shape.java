package universe.graphics;

import java.util.HashMap;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import universe.core.Node;

public abstract class Shape extends Node {
	
	protected final Graphics graphics;
	
	protected Buffer vertices;
	protected Buffer indices;
	protected HashMap<String, Buffer> buffers;
	
	protected boolean stroke 		= true;
	protected float strokeWidth 	= 1.0f;
	protected Color strokeColor 	= Color.BLACK;
	protected StrokeCap strokeCap 	= StrokeCap.BUTT;
	protected StrokeJoin strokeJoin = StrokeJoin.MITER;

	protected boolean fill 	  = true;
	protected Color fillColor = Color.WHITE;

	protected boolean tint 	  = false;
	protected Color tintColor = Color.WHITE;
	
	protected float nx, ny, nz;
	
	protected ShapeMode mode;
	protected Material material;
	protected boolean dynamic;
	protected boolean open;
	
	public Shape(Graphics graphics, ShapeMode mode, boolean dynamic) {
		this.open = false;
		this.mode = mode;
		this.dynamic = dynamic;
		this.graphics = graphics;
		this.material = new StandardMaterial(graphics);

		attrib("position", Node.FLOAT, 3);
		attrib("normal", Node.FLOAT, 3);
	}
	
	public abstract void bind();
	
	public abstract void unbind();
	
	public void begin() {
		this.open = true;
	}
	
	public void end() {
		if (!open)
			throw new IllegalStateException("The shape is not open, you need to first call the open() method.");
		
		this.open = false;
	}
	
	public int count() {
		return 0;
	}
	
	public void fill(Color color) {
		fill = true;
		fillColor = color;
	}
	
	public void fill(Texture texture) {
		fill = true;
		throw new NotImplementedException();
	}
	
	public void noFill() {
		fill = false;
	}
	
	
	
	public void stroke(Color color) {
		stroke = true;
		strokeColor = color;
	}
	
	public void strokeCap(StrokeCap cap) {
		strokeCap = cap;
	}
	
	public void strokeJoin(StrokeJoin join) {
		strokeJoin = join;
	}
	
	public void noStroke() {
		stroke = false;
	}
	
	public void setMode(ShapeMode mode) {
		this.mode = mode;
	}
	
	public final void vertex(float px, float py) {
		vertices.put(px, py, nx, ny);
	}

	public final void vertex(float px, float py, float pz) {
		vertices.put(px, py, pz, nx, ny, nz);
	}
	
	public final void vertex(float px, float py, float pz, float u) {
		vertices.put(px, py, pz, u);
	}
	
	public final void vertex(float px, float py, float pz, float u, float v) {
		vertices.put(px, py, pz, u);
	}
	
	public final void vertex(float px, float py, float pz, float u, float v, float w) {
		vertices.put(px, py, pz, u);
	}
	
	public void vertexImpl(float px, float py, float pz, float u, float v, float w) {
		
	}
	
	public void normal(float nx, float ny, float nz) {
		this.nx = nx;
		this.ny = ny;
		this.nz = nz;
	}
	
	public abstract void attrib(String name, int type, int count);
	
	
	private void checkOpen() {
		if (!open)
			throw new IllegalStateException("Shape is not opened for editing, call first the begin() method.");
	}
}
