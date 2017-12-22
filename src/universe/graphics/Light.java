package universe.graphics;

import universe.core.Node;
import universe.math.Vector3;

public class Light extends Node {
	
	private Color ambient  = Color.WHITE;
	private Color diffuse  = Color.WHITE;
	private Color specular = Color.WHITE;
	
	public void setAmbient(Color ambient) {
		this.ambient = ambient;
	}
	
	public Color getAmbient() {
		return ambient;
	}
	
	public void setDiffuse(Color diffuse) {
		this.diffuse = diffuse;
	}
	
	public Color getDiffuse() {
		return diffuse;
	}
	
	public void setSpecular(Color specular) {
		this.specular = specular;
	}
	
	public Color getSpecular() {
		return specular;
	}
}
