package universe.graphics;

public class Rectangle extends Renderable2D {
	
	private float x, y;
	private float w, h;
	
	public Rectangle() {
		this(0, 0, 0, 0);
	}
	
	public Rectangle(float x, float y, float w, float h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		
		vertices = new Vertex[4];
		vertices[0] = new Vertex().pos(x, y).tex(0, 0).norm(0, 1, 0);
		vertices[1] = new Vertex().pos(x+w, y).tex(1, 0).norm(0, 1, 0);
		vertices[2] = new Vertex().pos(x, y+h).tex(0, 1).norm(0, 1, 0);
		vertices[3] = new Vertex().pos(x+w, y+h).tex(1, 1).norm(0, 1, 0);
		
		indices = new short[6];
		indices[0] = 0;
		indices[0] = 1;
		indices[0] = 2;
		indices[0] = 1;
		indices[0] = 2;
		indices[0] = 3;
	}
	
	@Override
	public int count() {
		return 4;
	}
}
