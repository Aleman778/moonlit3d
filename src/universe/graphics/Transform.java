package universe.graphics;

import universe.math.Matrix4;
import universe.math.Quaternion;
import universe.math.Vector3;

public class Transform {

	private Matrix4 local;
	private Matrix4 world;
	 
	private Vector3 translation;
	private Quaternion rotation;
	private Vector3 scale;
	private boolean dirty;
	
	/**
	 * Default Constructor
	 */
	public Transform() {
		translation = new Vector3();
		rotation = new Quaternion();
		scale = new Vector3(1, 1, 1);
	}
	
	public Transform(Transform copy) {
		translation = new Vector3(copy.translation);
		rotation = new Quaternion(copy.rotation);
		scale = new Vector3(copy.scale);
	}
	
	public Matrix4 local() {
		local = Matrix4.createScale(scale);
		local = local.translate(translation);
		local = local.rotate(rotation);
		
		return local;
	}
	
	public void translate(Vector3 translation) {
		this.translation = this.translation.add(translation);
		this.dirty = true;
	}
	
	public void rotate(Quaternion rotation) {
		this.rotation = this.rotation.mul(rotation);
		this.dirty = true;
	}
	
	public void scale(Vector3 scale) {
		this.scale = this.scale.mul(scale);
		this.dirty = true;
	}
	
	public Vector3 getTranslation() {
		return new Vector3(translation);
	}
	
	public void setTranslation(Vector3 translation) {
		this.translation = translation;
		this.dirty = true;
	}

	public Quaternion getRotation() {
		return new Quaternion(rotation);
	}
	
	public void setRotation(Quaternion rotation) {
		this.rotation = rotation;
		this.dirty = true;
	}

	public Vector3 getScale() {
		return new Vector3(scale);
	}
	
	public void setScale(Vector3 scale) {
		this.scale = scale;
		this.dirty = true;
	}
}
