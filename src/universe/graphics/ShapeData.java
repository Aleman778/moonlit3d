package universe.graphics;

import java.util.HashMap;

public class ShapeData {
	
	private static final int DEFAULT_NUM_VERTICES = 64;

	private int count;
	
	private float[] positions;
	private float[] texcoords;
	private float[] normals;
	private float[] colors;
	
	private float[] fillColor;
	private float[] strokeColor;
	private float[] strokeWidth;

	private HashMap<String, Integer[]> iattribs;
	private HashMap<String, Double[]> dattribs;
	private HashMap<String, Float[]> fattribs;
	
	public ShapeData() {
	}
	
	private void allocate() {
		positions = new float[3 * DEFAULT_NUM_VERTICES];
		texcoords = new float[3 * DEFAULT_NUM_VERTICES];
		normals = new float[3 * DEFAULT_NUM_VERTICES];
		colors = new float[3 * DEFAULT_NUM_VERTICES]; 
	}
}
