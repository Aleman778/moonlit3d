package universe.opengl;

public class VertexAttribute {

	private String name;
	private int index;
	private int type;
	private int size;
	private int stride;
	private int pointer;
	private boolean normalized;
	
	public VertexAttribute(String name, int type, int size) {
		this.name = name;
		this.type = type;
		this.size = size;
	}
	
	public String getName() {
		return name;
	}

	public int getIndex() {
		return index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public int getSize() {
		return size;
	}
	
	public int getType() {
		return type;
	}
	
	public int getStride() {
		return stride;
	}
	
	public void setStride(int stride) {
		this.stride = stride;
	}
	
	public int getPointer() {
		return pointer;
	}
	
	public void setPointer(int pointer) {
		this.pointer = pointer;
	}
	
	public boolean isNormalized() {
		return normalized;
	}
}
