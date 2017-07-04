package universe.graphics;

public class VertexAttribute {

	private int count;
	private int offset;
	private int pointer;
	private int location;
	
	/**
	 * Constructor.
	 * @param count the number of elements in the vertex attribute
	 * @param offset the offset between consecutive vertex attributes
	 * @param pointer the offset of the first element in the vertex attribute
	 * @param location the location of the shader input
	 */
	public VertexAttribute(int location, int count, int offset, int pointer) {
		this.count    = count;
		this.offset   = offset;
		this.pointer  = pointer;
		this.location = location;
	}
	
	public int getCount() {
		return count;
	}
	
	public int getOffset() {
		return offset;
	}
	
	public int getPointer() {
		return pointer;
	}
	
	public int getLocation() {
		return location;
	}
	
	public void setLocation(int location) {
		this.location = location;
	}
}
