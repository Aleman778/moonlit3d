package universe.graphics;

/**
 * Shader exception.
 * @author Aleman778
 * @since Universe Core 1.0
 */
public class ShaderException extends RuntimeException {

	private static final long serialVersionUID = -8793503870684932702L;

	/**
     * Constructor.
     */
    public ShaderException() {
    	super();
    }

    /**
     * Constructor.
     * @param message exception message
     */
    public ShaderException(String message) {
        super(message);
    }

    /**
     * Constructor.
     * @param thrwbl throwable exception
     */
    public ShaderException(Throwable thrwbl) {
        super(thrwbl);
    }

    /**
     * Constructor.
     * @param message exception message
     * @param thrwbl throwable exception
     */
    public ShaderException(String message, Throwable thrwbl) {
        super(message, thrwbl);
    }    
}
