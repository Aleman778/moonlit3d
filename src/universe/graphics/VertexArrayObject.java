package universe.graphics;

import universe.util.Disposable;

public abstract class VertexArrayObject implements Disposable {

	public abstract void bind();
	
	public abstract void unbind();

	public abstract void put(Buffer buf, BufferLayout layout);
	
	public abstract void clear();
}
