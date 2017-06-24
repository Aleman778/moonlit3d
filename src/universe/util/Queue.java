package universe.util;

public interface Queue<E> {

	public boolean add(E item);
	
	public boolean insert(E item);
	
	public E get();
	
	public E peek();
	
	public E next();
	
	public E poll();
	
	public boolean hasNext();
}
