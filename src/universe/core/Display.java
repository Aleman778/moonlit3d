package universe.core;

import universe.graphics.Graphics;

/**
 * The display interface provides required methods for
 * working with different display implementations.
 * @since 1.0
 * @author Aleman778
 */
public abstract class Display extends Node {
	
	public Graphics graphics;
	public Files files;
	
	/**
	 * The refresh method is used to provide support for
	 * the display implementation to be refreshed.
	 */
	public abstract void refresh();
	
	/**
	 * Set the specific renderer api to use.
	 * @param renderer the renderer to use
	 */
	public abstract void setRenderer(RenderApi renderer);
	
	/**
	 * Get the graphics renderer used by this window.
	 * @return the current graphics renderer
	 */
	public abstract Graphics getGraphics();
	
	/**
	 * Get the width (in pixels) of the display.
	 * @return the width of the display
	 */
	public abstract int getWidth();
	
	/**
	 * Get the height (in pixels) of the display.
	 * @return the height of the display
	 */
	public abstract int getHeight();
	
	/**
	 * Get the aspect ratio (width / height) of the display
	 * @return the aspect ratio of the display
	 */
	public final float getAspectRatio() {
		return (float) getWidth() / (float) getHeight();
	}

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
