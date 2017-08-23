package universe.opengl;

import universe.graphics.Graphics;
import universe.graphics.Shape;
import universe.graphics.ShapeMode;
import universe.opengl.GLGraphics.AttributeMap;

public class GLShape extends Shape {
	
	private AttributeMap attribs;
	
	public GLShape(Graphics graphics, ShapeMode mode, boolean dynamic) {
		super(graphics, mode, dynamic);
		
		attribs = new AttributeMap();
		
	}

	@Override
	public void bind() {
	}

	@Override
	public void unbind() {
	}

	@Override
	public void attrib(String name, int type, int count) {
	}
}
