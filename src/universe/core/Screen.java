package universe.core;

/**
 * Screen helper class holds information general information about a screen.
 * @author Aleman778
 */
public interface Screen {
	
	/**
	 * Returns the width, in screen coordinates of the screen.
	 * @return the width
	 */
	public int getWidth();

	/**
	 * Returns the height, in screen coordinates of the screen.
	 * @return the height
	 */
	public int getHeight();

	/**
	 * The bit depth of the red channel.
	 * @return the bit depth
	 */
	public int getRedBits();

	/**
	 * The bit depth of the green channel.
	 * @return the bit depth
	 */
	public int getGreenBits();

	/**
	 * The bit depth of the blue channel.
	 * @return the bit depth
	 */
	public int getBlueBits();
	
	/**
	 * The screens refresh rate.
	 * @return refresh rate
	 */
	public double getRefreshRate();
}
