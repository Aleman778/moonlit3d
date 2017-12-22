package universe.graphics;

public abstract class Renderable2D implements Renderable {
	
	private static final int DEFAULT_CAPACITY = 10;
	
	/**
	 * Vertex data for one vertex.
	 * The 2D vertex has 6 floats:<br>
	 * <ul>
	 * 	<li>Position: x, y</li>
	 *  <li>Texture Coordinate: u, v</li>
	 *  <li>Normal: x, y</li>
	 * </ul>
	 * @author Aleman778
	 *
	 */
	class Vertex {
		public static final int SIZE = 6;
		
		private float px, py;
		private float u, v;
		private float nx, ny, nz;
		
		public Vertex pos(float x, float y) {
			this.px = x;
			this.py = y;
			return this;
		}
		
		public Vertex tex(float u, float v) {
			this.u = u;
			this.v = v;
			return this;
		}
		
		public Vertex norm(float x, float y, float z) {
			this.nx = x;
			this.ny = y;
			this.nz = z;
			return this;
		}
	}
	
	protected Vertex[] vertices;
	protected short[] indices;

	@Override
	public void render(Renderer renderer) {
		renderer.submit(this);
	}
	
	@Override
	public float[] vertices() {
		int size = Vertex.SIZE * vertices.length;
		float[] data = new float[size];
		for (int i = 0; i < vertices.length; i++) {
			Vertex v = vertices[i];
			data[i * Vertex.SIZE + 0] = v.px;
			data[i * Vertex.SIZE + 1] = v.py;
			data[i * Vertex.SIZE + 2] = v.u;
			data[i * Vertex.SIZE + 3] = v.v;
			data[i * Vertex.SIZE + 4] = v.nx;
			data[i * Vertex.SIZE + 5] = v.ny;
		}
		return data;
	}
	
	@Override
	public short[] indices() {
		return indices;
	}
	
	@Override
	public int size() {
		return vertices.length;
	}
}
