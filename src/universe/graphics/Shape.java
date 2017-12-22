package universe.graphics;

import java.util.HashMap;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import universe.core.Node;
import universe.opengl.AttributeMap;
import universe.opengl.VertexAttribute;

public abstract class Shape extends Node {
	
	private static final IllegalArgumentException exceptionTexcoordNotSpecified =
			new IllegalArgumentException("Texture coordinates has to be specified for a textured shape.");
	private static final IllegalArgumentException exceptionTexcoordUnnecessary =
			new IllegalArgumentException("Texture coordinates are specified for a non textured shape.");
	private static final IllegalStateException exceptionShapeClosed =
			new IllegalStateException("The shape is not open, you need to first call the open() method.");
	
	protected final Graphics graphics;

	protected HashMap<String, Buffer> buffers;
	protected VertexBufferObject vertices;
	protected IndexBufferObject indices;
	protected ShapeData data;	
	protected ShapeMode mode;
	protected Material material;
	protected AttributeMap attribs;
	protected boolean dynamic;
	protected boolean open;
	
	public Shape(Graphics graphics, ShapeMode mode, boolean dynamic) {
		this.open = false;
		this.mode = mode;
		this.dynamic = dynamic;
		this.graphics = graphics;
		this.attribs = new AttributeMap(material.shader);
		this.data = new ShapeData();
	}
	
	public abstract void bind();
	
	public abstract void unbind();
	
	public void begin() {
		this.open = true;
	}
	
	public void end() {
		if (!open)
			throw exceptionShapeClosed;
		
		this.open = false;
	}
	
	public int count() {
		return data.getVertCount();
	}
	
	public void fill(Color color) {
		data.fill = true;
		data.fillColor = color;
		data.numColorComp = 4;
	}
	
	public void fill(Texture texture) {
		data.fill = true;
		data.fillTexture = true;
		material.texture(texture);
	}
	
	public void noFill() {
		data.fill = false;
	}
	
	public void stroke(Color color) {
		data.stroke = true;
		data.strokeColor = color;
	}

	public void noStroke() {
		data.stroke = false;
	}
	
	public void strokeCap(StrokeCap cap) {
		data.strokeCap = cap;
	}
	
	public void strokeJoin(StrokeJoin join) {
		data.strokeJoin = join;
	}
	
	public void tint(Color color) {
		data.tint = true;
		data.fillColor = color;
	}
	
	public void noTint() {
		data.tint = false;
	}
	
	public void material(Material material) {
		this.material = material;
	}
	
	public void setMode(ShapeMode mode) {
		this.mode = mode;
	}
	
	public ShapeMode getMode() {
		return mode;
	}
	
	public Material getMaterial() {
		return material;
	}
	
	public Shader getShader() {
		return material.getShader();
	}
	
	public final void vertex(float px, float py) {
		checkOpen();
		
		if (data.fillTexture)
			throw exceptionTexcoordNotSpecified;
		
		attrib("position", FLOAT, 2);
		attrib("normal", FLOAT, 2);
		attrib("color", FLOAT, 4);
		data.numVertComp = 2;
		data.addVertex(px, py, 0, 0, 0, 0);
	}

	public final void vertex(float px, float py, float pz) {
		checkOpen();
		if (data.fillTexture)
			throw exceptionTexcoordNotSpecified;

		attrib("position", FLOAT, 3);
		attrib("normal", FLOAT, 3);
		attrib("color", FLOAT, 4);
		data.numVertComp = 3;
		data.addVertex(px, py, pz, 0, 0, 0);
	}
	
	public final void vertex(float px, float py, float pz, float u) {
		checkOpen();
		if (!data.fillTexture)
			throw exceptionTexcoordUnnecessary;

		attrib("position", FLOAT, 3);
		attrib("normal", FLOAT, 3);
		attrib("texcoord", FLOAT, 1);
		data.numVertComp = 3;
		data.numTexComp = 1;
		data.addVertex(px, py, pz, u, 0, 0);
	}
	
	public final void vertex(float px, float py, float pz, float u, float v) {
		checkOpen();
		if (!data.fillTexture)
			throw exceptionTexcoordUnnecessary;

		attrib("position", FLOAT, 3);
		attrib("normal", FLOAT, 3);
		attrib("texcoord", FLOAT, 2);
		data.numVertComp = 3;
		data.numTexComp = 2;
		data.addVertex(px, py, pz, u, v, 0);
	}
	
	public final void vertex(float px, float py, float pz, float u, float v, float w) {
		checkOpen();
		if (!data.fillTexture)
			throw exceptionTexcoordUnnecessary;

		attrib("position", FLOAT, 3);
		attrib("normal", FLOAT, 3);
		attrib("texcoord", FLOAT, 3);
		data.numVertComp = 3;
		data.numTexComp = 3;
		data.addVertex(px, py, pz, u, v, w);
	}
	
	public void normal(float nx, float ny) {
		checkOpen();
		data.setNormals(nx, ny, 0);
	}
	
	public void normal(float nx, float ny, float nz) {
		checkOpen();
		data.setNormals(nx, ny, nz);
	}
	
	public abstract void attrib(String name, int type, int count);
	
	public abstract void bindAttrib(VertexAttribute a);
	
	private void checkOpen() {
		if (!open)
			throw exceptionShapeClosed;
	}
}
