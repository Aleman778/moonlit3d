package universe.util;

public interface Cache<E> {

	public void add(E item);
	
	public void remove(E item);
	
	public void remove(int index);
	
}
