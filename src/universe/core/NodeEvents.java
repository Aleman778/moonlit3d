 package universe.core;

import universe.util.Disposable;

public class NodeEvents implements Disposable {
	
	protected int mouseX = 0;
	protected int mouseY = 0;
	protected int key = 0;
	
	public void preload() {}
	
	public void setup() {}
	
	public void update() {}
	
	public void draw() {}
	
	public void mousePressed(int button) {}
	
	public void mouseReleased(int button) {}
	
	public void mouseClicked(int button) {}

	public void mouseMoved(int x, int y) {}
	
	public void mouseDragged(int button, int x, int y, int dx, int dy) {}
	
	public void mouseScrolled(float xoffset, float yoffset) {}
	
	public void keyDown(int key) {}
	
	public void keyUp(int key) {}
	
	public void keyTyped(int key) {}
	
	public void dispose() {}
}
