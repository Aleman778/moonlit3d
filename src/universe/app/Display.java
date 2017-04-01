package universe.app;

/**
 * The display interface provides required methods for
 * working with different display implementations.
 * @since 1.0
 * @author Aleman778
 */
public interface Display {
	
	/**
	 * The update method is used to provide support for
	 * the display implementation to be updated.
	 */
	public void update();
	
	/**
	 * The refresh method is used to provide support for
	 * the display implementation to be refreshed.
	 */
	public void refresh();

	/**
	 * Get the primary screen information object.
	 * @return primary screen
	 */
	public Screen getScreen();
	
	/**
	 * Get an array of all the active screens information object.
	 * @return all active screens
	 */
	public Screen[] getScreens();
}
