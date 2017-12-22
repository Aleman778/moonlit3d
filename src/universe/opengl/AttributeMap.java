package universe.opengl;

import java.util.HashMap;

import universe.graphics.Shader;
import universe.graphics.Shape;

public class AttributeMap extends HashMap<String, VertexAttribute> {

	private Shader shader;
	private int pointer = 0;
	
	public AttributeMap(Shader shader) {
		super();
		
		this.shader = shader;
	}
	
	public VertexAttribute put(String name, int type, int size) {
		return put(name, new VertexAttribute(name, type, size));
	}
	
	@Override
	public VertexAttribute put(String name, VertexAttribute attrib) {
		VertexAttribute other = get(attrib.getName());
		if (other == null) {
			attrib.setIndex(shader.getAttribIndex(name));
			attrib.setPointer(pointer);
			pointer += attrib.getSize();
			return super.put(name, attrib);
		}
		
		if ((attrib.getSize() != other.getSize()) ||
			(attrib.getType() != other.getType())) {
			throw new IllegalArgumentException("Vertex attribute arguments cannot be changed.");
		}
		
		return other;
	}
	
	@Override
	public VertexAttribute get(Object key) {
		VertexAttribute attrib = super.get(key);
		if (attrib == null)
			return null;
		
		attrib.setStride(pointer);
		return attrib;
	}
	
	public void enable(Shape shape) {
		Shader shader = shape.getShader(); //New shader
		
		for (Entry<String, VertexAttribute> entry : entrySet()) {
			VertexAttribute attrib = entry.getValue();
			if (this.shader != shader)
				attrib.setIndex(shader.getAttribIndex(entry.getKey()));
			
			shape.bindAttrib(attrib);
		}

		this.shader = shader;
	}
}
