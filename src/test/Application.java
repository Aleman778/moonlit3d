package test;

import universe.core.Node;
import universe.core.Scene;
import universe.graphics.Image;
import universe.graphics.Shape;
import universe.graphics.ShapeMode;
import universe.graphics.Image.ColorModel;
import universe.util.AssimpHelper;

public class Application extends Node {
	
	@Override
	public void setup() {
		Shape shape = createShape();
		shape.begin();
		shape.vertex(0, 0);
		shape.vertex(1, 0);
		shape.vertex(0, 1);
		shape.vertex(1, 1);
		shape.end();
		
		add(shape);
	}
	
	@Override
	public void render() {
	}
	
	public Shape createTexturedCube(Image image) {
		Shape shape = createShape();
		shape.setMode(ShapeMode.QUADS);
		shape.begin();
		shape.vertex(-1, -1,  1, 0, 0);
		shape.vertex( 1, -1,  1, 1, 0);
		shape.vertex( 1,  1,  1, 1, 1);
		shape.vertex(-1,  1,  1, 0, 1);
		shape.vertex( 1, -1, -1, 0, 0);
		shape.vertex(-1, -1, -1, 1, 0);
		shape.vertex(-1,  1, -1, 1, 1);
		shape.vertex( 1,  1, -1, 0, 1);
		shape.vertex(-1,  1,  1, 0, 0);
		shape.vertex( 1,  1,  1, 1, 0);
		shape.vertex( 1,  1, -1, 1, 1);
		shape.vertex(-1,  1, -1, 0, 1);
		shape.vertex(-1, -1, -1, 0, 0);
		shape.vertex( 1, -1, -1, 1, 0);
		shape.vertex( 1, -1,  1, 1, 1);
		shape.vertex(-1, -1,  1, 0, 1);
		shape.vertex( 1, -1,  1, 0, 0);
		shape.vertex( 1, -1, -1, 1, 0);
		shape.vertex( 1,  1, -1, 1, 1);
		shape.vertex( 1,  1,  1, 0, 1);
		shape.vertex(-1, -1, -1, 0, 0);
		shape.vertex(-1, -1,  1, 1, 0);
		shape.vertex(-1,  1,  1, 1, 1);
		shape.vertex(-1,  1, -1, 0, 1);
		shape.end();
		
		return shape;
	}
}

