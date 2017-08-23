package universe.graphics;

import universe.core.Display;
import universe.core.Scene;
import universe.graphics.Texture.Axis;
import universe.graphics.Texture.Sample;
import universe.graphics.Texture.Wrap;

public abstract class Graphics {

	//Main Display
	protected final Display display;
	
	//Hint values
	protected boolean textureMipmap 	 = false;
	protected boolean textureMultisample = false;
	protected boolean textureAnisotropic = false;
	protected float   textureAnisotropy  = 4.0f;
	protected Sample  textureSample      = Sample.TRILINEAR;
	protected Wrap    textureWrapS       = Wrap.REPEAT;
	protected Wrap    textureWrapT       = Wrap.REPEAT;
	protected Wrap    textureWrapR       = Wrap.REPEAT;
	
	//Color buffer
	protected Color background  = Color.DARKGRAY;
	protected boolean redMask 	= true;
	protected boolean greenMask = true;
	protected boolean blueMask 	= true;
	protected boolean alphaMask = true;
	
	//Depth buffer
	protected boolean depthTest = false;
	protected boolean depthMask = true;
	protected DepthFunc depthFunc = DepthFunc.LESS;
	
	//Stencil buffer
	protected boolean stencilTest = false;
	protected int stencilMask = 0xFFFFFFFF;
	protected StencilFunc stencilFunc = StencilFunc.KEEP;

	/**
	 * Constructor.
	 * @param display the owner
	 */
	public Graphics(Display display) {
		this.display = display;
	}
	
	public abstract void init();
	
	public abstract void clear();

	public abstract Texture texture(Image image);
	
	public abstract Texture loadTexture(String filename);
	
	public abstract void textureMode(Wrap wrap, Axis axis);
	
	public abstract void textureAnisotropy(float amount);
	
	public abstract Image loadImage(String filename);
	
	public abstract Scene loadScene(String filename);	
	
	public abstract Shader loadShader(String fragment);
	
	public abstract Shader loadShader(String fragment, String vertex);

	public abstract Shape loadShape(String filename);
	
	public abstract Shape createShape();
	
	public abstract void hint(int hint);
	
	/**
	 * Set the clear color. 
	 * @param colorClear the clear color
	 */
	public void setBackground(Color background) {
		this.background = background;
	}
	
	/**
	 * Enable or disable the writing to each color component of the color buffer.
	 * @param red enable the red buffer (or channel)
	 * @param green enable the green buffer (or channel)
	 * @param blue enable the blue buffer (or channel)
	 * @param alpha enable the alpha buffer (or channel)
	 */
	public void setColorMask(boolean red, boolean green, boolean blue, boolean alpha) {
		this.redMask = red;
		this.greenMask = green;
		this.blueMask = blue;
		this.alphaMask = alpha;
	}
	
	/**
	 * Set the depth testing flag.
	 * @param enable enable depth testings
	 */
	public void setDepthTest(boolean enable) {
		this.depthTest = enable;
	}
	
	/**
	 * Set the depth buffer function.
	 * @param func the depth function
	 */
	public void setDepthFunc(DepthFunc func) {
		this.depthFunc = func;
	}
	
	/**
	 * Enable or disable the writing to the depth buffer.
	 * @param mask enable depth buffer writing
	 */
	public void setDepthMask(boolean mask) {
		this.depthMask = mask;
	}
	
	/**
	 * Set the stencil testing flag,
	 * @param enable enable stencil testing
	 */
	public void setStencilTest(boolean enable) {
		this.stencilTest = enable;
	}
	
	/**
	 * Set the stencil buffer function with the provided reference and mask.
	 * @param func the stencil function
	 * @param ref the reference value used by
	 * @param mask the stencil bits to be affected
	 */
	public void setStencilFunc(StencilFunc func, int ref, int mask) {
		this.stencilFunc = func;
	}

	/**
	 * Get the clear color.
	 * @return the clear color
	 */
	public Color getColorClear() {
		return background;
	}
	
	public abstract String version();
	
	public abstract String vendor();
	
	/**
	 * Depth Buffer Function
	 */
	public enum DepthFunc {
		/**
		 * The depth test <b>never</b>always passes.
		 */
		ALWAYS,
		
		/**
		 * The depth test <b>never</b> passes.
		 */
		NEVER,

		/**
		 * The depth test passes when the fragment's depth
		 * value is <b>less</b> than the stored depth value.
		 */
		LESS,
		
		/**
		 * The depth test passes when the fragment's depth
		 * value is <b>equal</b> to the stored depth value.
		 */
		EQUAL,
		
		/**
		 * The depth test passes when the fragment's depth
		 * value is <b>less than or equal</b> to the stored depth value.
		 */
		LEQUAL,
		
		/**
		 * The depth test passes when the fragment's depth
		 * value is <b>grater</b> than the stored depth value.
		 */
		GREATER,
		
		/**
		 * The depth test passes when the fragment's depth
		 * value is <b>not equal</b> than the stored depth value.
		 */
		NOTEQUAL,
		
		/**
		 * The depth test passes when the fragment's depth
		 * value is <b>grater than or equal</b> to the stored depth value.
		 */
		GEQUAL
	}
	
	public enum StencilFunc {
		/**
		 * Keeps the currently stored value.
		 */
		KEEP,
		
		/**
		 * The stencil value is set to zero.
		 */
		ZERO,
		
		/**
		 * The stencil value is replaced by a provided reference value passed in 
		 * the function {@link #setStencilFunc(StencilFunc, int, int)}. 
		 */
		REPLACE,
		INCR,
		INCR_WRAP,
		DECR,
		DECR_WRAP,
		INVERT 
	}
}
