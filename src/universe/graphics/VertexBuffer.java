package universe.graphics;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class VertexBuffer extends Buffer {
	
	protected float[] buffer;
	private ArrayList<VertexAttribute> attributes;
	
	public VertexBuffer(int capacity) {
		super(capacity);
		
		buffer = new float[capacity];
		attributes = new ArrayList<>();
	}
	
	public void put(float... data) {
		if (position + data.length > capacity) {
			throw new IndexOutOfBoundsException("Buffer exceeded the maximum capacity.");
		}
		
		for (int i = 0; i < data.length; i++) {
			buffer[position++] = data[i];
		}
		
		if (position > count)
			count = position;
	}
	
	public abstract void map();

	public abstract void unmap();
	
	public void attribute(int location, int count, int offset, int pointer) {
		attribute(new VertexAttribute(location, count, offset, pointer));
	}
	
	public void attribute(VertexAttribute attrib) {
		attributes.add(attrib);
	}
	
	@Override
	public long size() {
		return count * Float.BYTES;
	}
	
	public float[] toArray()   {
		return Arrays.copyOf(buffer, count);
	}
	
	@Override
	public String toString() {
		String result = String.format("ArrayBuffer(%d, cap=%d): [", count, capacity);
		for (int i = 0; i < count; i++) {
			result += buffer[i];
			if (i < count - 1)
				result += ", ";
		}
		result += "]";
		return result;
	}
}
