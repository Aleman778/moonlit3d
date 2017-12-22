package universe.graphics;

import universe.math.Matrix4;

public class ProjectionCamera extends Camera {
	
	private float fov;
	private float clipNear;
	private float clipFar;
	
	public ProjectionCamera(float fov, float near, float far) {
		this.fov = fov;
		this.clipNear = near;
		this.clipFar = far;
	}
	
	public void setFov(float fov) {
		this.fov = fov;
	}
	
	public float getFov() {
		return fov;
	}
	
	public void setClipFar(float clipFar) {
		this.clipFar = clipFar;
	}
	
	public float getClipFar() {
		return clipFar;
	}
	
	public void setClipNear(float clipNear) {
		this.clipNear = clipNear;
	}
	
	public float getClipNear() {
		return clipNear;
	}
	
	private void updateProjection() {
		projection = Matrix4.perspective(fov, display.getAspectRatio(), clipNear, clipFar);
	}
	
	private void updateView() {
	}
}
