package universe.graphics;

public class VertexData {
	
	private static final int DEFAULT_NUM_VERTICES = 64;

	private int count = 0;
	private int capacity = 0;
	private boolean allocated = false;
	
	private int numPosComp   = 3;
	private int numTexComp   = 2;
	private int numNormComp  = 3;
	private int numColorComp = 4;
	
	private float[] positions;
	private float[] texcoords;
	private float[] normals;
	private float[] colors;
	
//	private float[] fillColor;
//	private float[] strokeColor;
//	private float[] strokeWidth;
	
	public boolean stroke 		 = true;
	public float strokeWidth 	 = 1.0f;
	public Color strokeColor 	 = Color.BLACK;
	public StrokeCap strokeCap 	 = StrokeCap.BUTT;
	public StrokeJoin strokeJoin = StrokeJoin.MITER;
	
	public boolean fill 	   = true;
	public Color fillColor 	   = Color.WHITE;
	
	public boolean tint 	  = false;
	public Color tintColor    = Color.WHITE;
	
	private float nx = 0.0f, ny = 0.0f, nz = 0.0f;
	
	public VertexData() {
		this(DEFAULT_NUM_VERTICES);
	}
	
	public VertexData(int capacity) {
		this.capacity = capacity;
	}
	
	public void comp(int pos, int tex, int norm, int color) {
		if (numPosComp < 0 || numPosComp > 3)
			throw new IllegalArgumentException("Position vectors can only have 0 to 3 components.");
		if (numTexComp < 0 || numTexComp > 3)
			throw new IllegalArgumentException("Texture coordinate vectors can only have 0 to 3 components.");
		if (numNormComp < 0 || numNormComp > 3)
			throw new IllegalArgumentException("Normal vectors can only have 0 to 3 components.");
		if (numColorComp < 0 || numColorComp > 4)
			throw new IllegalArgumentException("Color vectors can only have 0 to 4 components.");
		
		this.numPosComp   = pos;
		this.numTexComp   = tex;
		this.numNormComp  = norm;
		this.numColorComp = color;
	}
	
	public void addVertex(float x, float y, float z,
						  float u, float v, float w) {
		if (!allocated)
			allocate(capacity);
		
		put(positions, numPosComp, x, y, z);
		put(positions, numTexComp, u, v, w);
		put(positions, numNormComp, nx, ny, nz);
		put(positions, numColorComp, fillColor.getRed(),
				                     fillColor.getGreen(),
				                     fillColor.getBlue(),
				                     fillColor.getAlpha());
		if (count < capacity)
			count++;
	}
	
	private void put(float[] buf, int comp, float... data) {
		if (count + 1 > capacity)
			throw new IndexOutOfBoundsException("Vertex data buffer reached maximum capacity.");
		
		for (int i = 0; i < comp; i++) {
			buf[count * comp + i] = data[i];
		}
	}
	
	private void allocate(int capacity) {
		positions = new float[numPosComp   * capacity];
		texcoords = new float[numTexComp   * capacity];
		normals   = new float[numNormComp  * capacity];
		colors 	  = new float[numColorComp * capacity];

//		iattribs = new HashMap<>();
//		dattribs = new HashMap<>();
//		fattribs = new HashMap<>();
	}

	public float[] data() {
		int comp = comp();
		float[] data = new float[count * comp];
		
		for (int i = 0; i < count; i++) {
			storeVertex(data, i, comp);
		}
		
		return data;
	}
	
	public int count() {
		return count;
	}
	
	public int comp() {
		return numPosComp + numTexComp + numNormComp + numColorComp;
	}
	
	public int capacity() {
		return capacity;
	}
	
	private void storeVertex(float[] data, int index, int comp) {
		int offset = index * comp;
		
		System.out.println("offset: " + offset);
		
		for (int i = 0; i < numPosComp; i++) {
			data[offset + i] = positions[index * numPosComp + i];
		}
		offset += numPosComp;
		for (int i = 0; i < numTexComp; i++) {
			data[offset + i] = texcoords[index * numTexComp + i];
		}
		offset += numTexComp;
		for (int i = 0; i < numNormComp; i++) {
			data[offset + i] = normals[index * numNormComp + i];
		}
		offset += numNormComp;
		for (int i = 0; i < numColorComp; i++) {
			data[offset + i] = colors[index * numColorComp + i];
		}
	}
}
