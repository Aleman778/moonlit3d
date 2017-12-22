package universe.graphics;

import universe.core.Node;
import universe.math.Matrix4;

public class Camera extends Node {
	
	protected Matrix4 projection;
	protected Matrix4 view;
	protected Matrix4 combined;
	protected float viewportX, viewportY;
	protected float viewportW, viewportH;
	
	public Camera() {
		this(0.0f, 0.0f, 1.0f, 1.0f);
	}
	
	public Camera(float x, float y, float w, float h) {
		viewportX = x;
		viewportY = y;
		viewportW = w;
		viewportH = h;
		projection = new Matrix4();
		view = new Matrix4();
	}

	
	public void viewport(float x, float y, float w, float h) {
		viewportX = x;
		viewportY = y;
		viewportW = w;
		viewportH = h;
		projection = new Matrix4();
		view = new Matrix4();
	}
	
	public float getViewportX() {
		return viewportX;
	}
	
	public float getViewportY() {
		return viewportY;
	}
	
	public float getViewportW() {
		return viewportW;
	}
	
	public float getViewportH() {
		return viewportH;
	}
}
