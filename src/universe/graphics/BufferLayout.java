package universe.graphics;

import java.util.ArrayList;

public class BufferLayout {

	private int count;
	private int stride;
	private ArrayList<BufferElement> elements;
	
	public BufferLayout()  {
		elements = new ArrayList<>();
		stride = 0;
		count = 0;
	}
	
	public void push(String name, int type, int size, int count, int offset, boolean normalized) {
		push(new BufferElement(name, type, size, count, offset, normalized));
		
		this.stride += count * size;
		this.count++;
	}
	
	private void push(BufferElement element) {
		elements.add(element);
	}
	
	public class BufferElement {
		
		public final String name;
		public final int type;
		public final int size;
		public final int count;
		public final int offset;
		public final boolean normalized;
		
		public BufferElement(String name, int type, int size, int count, int offset, boolean normalized) {
			this.name = name;
			this.type = type;
			this.size = size;
			this.count = count;
			this.offset = offset;
			this.normalized = normalized;
		}
	}
	
	public ArrayList<BufferElement> elements() {
		return elements;
	}
	
	public int stride() {
		return stride;
	}
	
	public int count() {
		return count;
	}
	
	public boolean empty() {
		return count == 0;
	}
}
