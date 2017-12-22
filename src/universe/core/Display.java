package universe.core;

import java.util.HashSet;
import java.util.List;

import universe.graphics.Camera;
import universe.graphics.Color;
import universe.graphics.Graphics;
import universe.graphics.Shape;

/**
 * The display interface provides required methods for
 * working with different display implementations.
 * @since 1.0
 * @author Aleman778
 */
public abstract class Display extends Node {
	
	private static final Camera DEFAULT_CAMERA = new Camera();
	
	public Graphics graphics;
	public Files files;

	private HashSet<Node> nodes = new HashSet<>();
	private HashSet<Camera> cameras = new HashSet<>();

	protected void addNode(Node node) {
		if (node.display != this)
			return;

		nodes.add(node);
		
		if (node instanceof Camera)
			cameras.add((Camera) node);
	}
	
	protected void removeNode(Node node) {
		if (node.display != this)
			return;
		
		if (node instanceof Camera) {
			cameras.remove(node);
		} else {
			nodes.remove(node);
		}
	}
	
	protected void updateImpl() {
		update();

		for (Node node : nodes) {
			node.update();
		}
	}
	
	protected void drawImpl() {
		graphics.clear();
		
		if (cameras.isEmpty()) {
			renderImpl(DEFAULT_CAMERA);
		} else {
			for (Camera camera : cameras) {
				renderImpl(camera);
			}
		}
	}
	
	protected void renderImpl(Camera camera) {
		float vx = camera.getViewportX();
		float vy = camera.getViewportY();
		float vw = camera.getViewportW();
		float vh = camera.getViewportH();
		graphics.viewport(vx, vy, vw, vh);
		graphics.prepare();
		
		draw();
		
		for (Node node : nodes) {
			node.draw();
		}
		
		graphics.present();
	}
	
	@Override
	public void dispose() {
		for (Node node: nodes) {
			node.dispose();
		}
	}
	
	/**
	 * Set the specific renderer api to use.
	 * @param renderer the renderer to use
	 */
	public abstract void setRenderer(RenderAPI renderer);
	
	/**
	 * Get the graphics renderer used by this window.
	 * @return the current graphics renderer
	 */
	public abstract Graphics getGraphics();
	
	/**
	 * Get the width (in pixels) of the display.
	 * @return the width of the display
	 */
	public abstract int getWidth();
	
	/**
	 * Get the height (in pixels) of the display.
	 * @return the height of the display
	 */
	public abstract int getHeight();
	
	/**
	 * Get the aspect ratio (width / height) of the display
	 * @return the aspect ratio of the display
	 */
	public final float getAspectRatio() {
		return (float) getWidth() / (float) getHeight();
	}

	/**
	 * Get the primary screen information object.
	 * @return primary screen
	 */
	public abstract Screen getScreen();
	
	/**
	 * Get an array of all the active screens information object.
	 * @return all active screens
	 */
	public abstract Screen[] getScreens();
}
