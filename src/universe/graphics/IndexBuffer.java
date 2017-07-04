package universe.graphics;

import java.util.Arrays;

public abstract class IndexBuffer extends Buffer {

	protected short[] buffer;
	
	public IndexBuffer(int capacity) {
		super(capacity);
		
		buffer = new short[capacity]; 
	}
	
	public void put(short... data) {
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
	
	@Override
	public long size() {
		return count * Float.BYTES;
	}
	
	public short[] toArray() {
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
