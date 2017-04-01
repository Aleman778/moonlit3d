package universe.util;

/**
 * Used to represent objects that have extra memory and is not
 * automatically garbage collected. The implemented dispose 
 * method should free all unused memory in order to avoid memory leaks.
 * @since 1.0
 * @author Aleman778
 */
public interface Disposable {

	/**
	 * The dispose method should free all unused memory.
	 */
	public void dispose();
}
