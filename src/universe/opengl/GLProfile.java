package universe.opengl;

/**
 * OpenGL context profile.
 * @author Aleman778
 * @see <a href="https://www.khronos.org/opengl/wiki/Core_And_Compatibility_in_Contexts">Khronos Wiki - Core and Compatibility </a>
 */
public class GLProfile {
	
	public static final GLProfile PREFERRED = null;

	/**
	 * OpenGL 3.3.0 core profile
	 */
	public static final GLProfile GL3 = new GLProfile(3, 3, false);

	/**
	 * OpenGL 4.5.0 core profile
	 */
	public static final GLProfile GL4 = new GLProfile(4, 5, false);

	/**
	 * OpenGL 3.3.0 compatible profile
	 */
	public static final GLProfile GL3_COMPATIBLE = new GLProfile(3, 3, true);

	/**
	 * OpenGL 4.5.0 compatible profile
	 */
	public static final GLProfile GL4_COMPATIBLE = new GLProfile(4, 5, true);
	
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
	 * @return
	 */
	public int getMinor() {
		return minor;
	}
	
	/**
	 * Is this profile in c
	 * @return
	 */
	public boolean isCompatible() {
		return compatible;
	}
}
