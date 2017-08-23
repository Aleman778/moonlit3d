package universe.opengl;

/**
 * OpenGL context profile.
 * @author Aleman778
 * @see <a href="https://www.khronos.org/opengl/wiki/Core_And_Compatibility_in_Contexts">Khronos Wiki - Core and Compatibility </a>
 */
public class GLProfile {
	
	private final int major;
	private final int minor;
	private final boolean compatible;
	
	/**
	 * Constructor.
	 * @param major the major version number
	 * @param minor the minor version number
	 */
	public GLProfile(int major, int minor, boolean compatible) {
		this.major = major;
		this.minor = minor;
		this.compatible = compatible;
	}
	
	/**
	 * Get the major version number.
	 * @return the major version
	 */
	public int getMajor() {
		return major;
	}
	
	/**
	 * Get the minor version number.
	 * @return the minor version
	 */
	public int getMinor() {
		return minor;
	}
	
	/**
	 * Check if this profile in compatibility mode.
	 * @return compatibility flag
	 */
	public boolean isCompatible() {
		return compatible;
	}

	/**
	 * The preferred OpenGL profile
	 */
	public static final GLProfile PREFERRED = null;

	/**
	 * OpenGL 3.3.0 core profile
	 */
	public static final GLProfile GL33 = new GLProfile(3, 3, false);

	/**
	 * OpenGL 4.5.0 core profile
	 */
	public static final GLProfile GL45 = new GLProfile(4, 5, false);

	/**
	 * OpenGL 3.3.0 compatible profile
	 */
	public static final GLProfile GL33_COMPATIBLE = new GLProfile(3, 3, true);

	/**
	 * OpenGL 4.5.0 compatible profile
	 */
	public static final GLProfile GL44_COMPATIBLE = new GLProfile(4, 5, true);
}
