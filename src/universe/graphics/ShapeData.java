package universe.graphics;

import java.util.Arrays;
import java.util.HashMap;

public final class ShapeData {
	
	private static final int DEFAULT_NUM_VERTICES = 64;

	private int vertCount      = 0;
	protected int numVertComp  = 0;
	protected int numTexComp   = 0;
	protected int numColorComp = 0;
	
	private float[] positions;
	private float[] texcoords;
	private float[] normals;
	private float[] colors;
	
//	private float[] fillColor;
//	private float[] strokeColor;
//	private float[] strokeWidth;
	
	public boolean stroke 		= true;
	public float strokeWidth 	= 1.0f;
	public Color strokeColor 	= Color.BLACK;
	public StrokeCap strokeCap 	= StrokeCap.BUTT;
	public StrokeJoin strokeJoin = StrokeJoin.MITER;
	
	public boolean fill 	   = true;
	public boolean fillTexture = false;
	public Color fillColor 	   = Color.WHITE;
	
	public boolean tint 	  = false;
	public Color tintColor = Color.WHITE;
	
	private float nx, ny, nz;

//	private HashMap<String, Integer[]> iattribs;
//	private HashMap<String, Double[]> dattribs;
//	private HashMap<String, Float[]> fattribs;
	
	public ShapeData() {
		allocate();
	}
	
	public void addVertex(float px, float py, float pz,
						  float u, float v, float w) {
		addData(positions, numVertComp, px, py, pz);
		addData(texcoords, numTexComp, u, v, w);
		addData(normals, numVertComp, nx, ny, nz);
		addData(colors, numColorComp, fillColor.getRed(),
									  fillColor.getGreen(),
									  fillColor.getBlue(), 
									  fillColor.getAlpha());
		vertCount++;
	}
	
	public void setNormals(float nx, float ny, float nz) {
		this.nx = nx;
		this.ny = ny;
		this.nz = nz;
	}
	
	public void updateVertexBuffer(VertexBufferObject buf) {
		float[] data = new float[getVertSize()];
		int comp = getVertComponents();
		for (int i = 0; i < vertCount; i++) {
			storeVertex(data, i, comp);
		}
		buf.put(data);
	}
	
	public int getVertSize() {
		return positions.length + texcoords.length + normals.length + colors.length;
	}
	
	public int getVertCount() {
		return vertCount;
	}
	
	public int getVertComponents() {
		int result = numVertComp * 2;
		if (fillTexture)
			result += numTexComp;
		else
			result += numColorComp;
		
		return result;
	}
	
	private void addData(float[] buffer, int comp, float... data) {
		int minCapacity = vertCount * comp + comp;
		if (minCapacity > positions.length) {
			buffer = extend(buffer, minCapacity);
		}
		
		for (int i = 0; i < comp; i++) {
			buffer[vertCount * comp + i] = data[i];
		}
	}
	
	private void storeVertex(float[] data, int count, int comp) {
		int offset = count * comp;
		
		for (int i = 0; i < numVertComp; i++) {
			data[offset + i] = positions[count * numVertComp + i];
			data[offset + numVertComp + i] = normals[count * numVertComp + i];
		}
		offset += numVertComp * 2;
		
		if (fillTexture) {
			for (int i = 0; i < numTexComp; i++) {
				data[offset + i] = texcoords[count * numTexComp + i];
			}
		} else {
			for (int i = 0; i < numColorComp; i++) {
				data[offset + i] = colors[count * numColorComp + i];
			}
		}
	}
	
	private float[] extend(float[] arr, int minCapacity) {
		if (minCapacity < 0)
    		throw new OutOfMemoryError();
    	
		int capacity = arr.length;
    	int newCapacity = ((capacity < 164) ?
    					   (capacity + 1) * 2 :
    					   (capacity / 2) * 3);
    	
    	if (newCapacity < 0)
    		newCapacity = Integer.MAX_VALUE;
    	if (newCapacity < minCapacity)
    		newCapacity = minCapacity;
    	
    	capacity = newCapacity;
    	return Arrays.copyOf(arr, capacity);
	}
	
	private void allocate() {
		positions = new float[3 * DEFAULT_NUM_VERTICES];
		texcoords = new float[2 * DEFAULT_NUM_VERTICES];
		normals = new float[3 * DEFAULT_NUM_VERTICES];
		colors = new float[3 * DEFAULT_NUM_VERTICES];

//		iattribs = new HashMap<>();
//		dattribs = new HashMap<>();
//		fattribs = new HashMap<>();
	}
}
