package universe.graphics;

public interface Renderable {

	public void render(Renderer renderer);
	
	public float[] vertices();
	
	public short[] indices();
	
	public int count();
	
	public int size();
}
