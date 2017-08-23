package universe.graphics;

import universe.core.Node;
import universe.math.Vector3;

public class Light extends Node {
	
	private Vector3 ambient;
	private Vector3 diffuse;
	private Vector3 specular;
	
	public Light() {
		ambient = new Vector3();
		diffuse = new Vector3();
		specular = new Vector3();
	}
	
	public void setAmbient(Vector3 ambient) {
		this.ambient = ambient;
	}
	
	public Vector3 getAmbient() {
		return ambient;
	}
	
	public void setDiffuse(Vector3 diffuse) {
		this.diffuse = diffuse;
	}
	
	public Vector3 getDiffuse() {
		return diffuse;
	}
	
	public void setSpecular(Vector3 specular) {
		this.specular = specular;
	}
	
	public Vector3 getSpecular() {
		return specular;
	}
}
