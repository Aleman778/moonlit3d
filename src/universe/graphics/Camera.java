package universe.graphics;

import java.awt.Rectangle;

import universe.core.Node;
import universe.math.Matrix4;
import universe.math.Quaternion;
import universe.math.Vector3;

public class Camera extends Node {
	
	protected Matrix4 view;
	protected Matrix4 projection;
	protected Rectangle viewport;
	
	public Camera() {
		view = new Matrix4();
		projection = new Matrix4();
		viewport = new Rectangle(0, 0, 1, 1);
	}
}
