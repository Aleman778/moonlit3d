package universe.graphics;

import universe.math.Vector3;

public class DirectionalLight extends Light {

	private Vector3 direction;
	
	
	public void setDirection(Vector3 direction) {
		this.direction = direction;
	}
}
