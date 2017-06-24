package universe.app;

/**
 * The display interface provides required methods for
 * working with different display implementations.
 * @since 1.0
 * @author Aleman778
 */
public abstract class Display extends SceneNode {
	
	/**
	 * The refresh method is used to provide support for
	 * the display implementation to be refreshed.
	 */
	public abstract void refresh();
	
	/**
	 * Set the specific renderer api to use
	 * @param renderer
	 */
	public abstract void setRenderer(RenderApi renderer);

	/**
	 * Get the primary screen information object.
	 * @return primary screen
	 */
	public abstract Screen getScreen();
	
	/**
	 * Get an array of all the active screens information object.
	 * @return all active screens
	 */
	public abstract Screen[] getScreens();
}
