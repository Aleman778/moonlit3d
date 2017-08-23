package universe.core;

import java.util.HashSet;

import universe.graphics.*;
import universe.graphics.Graphics.DepthFunc;
import universe.graphics.Graphics.StencilFunc;
import universe.graphics.Texture.Axis;
import universe.graphics.Texture.Wrap;

public abstract class Node extends NodeEvents {
	
	protected Display display = null;

	private HashSet<Node> children = new HashSet<>();
	private String name = getClass().getSimpleName();
	private Transform transform = new Transform();
	private Node parent = null;
	private boolean setup = false;
	
	public final void add(Node node) {
		children.add(node);
		node.parent = this;
		node.display = display;
		node.execSetup();
	}
	
	public final void remove(Node node) {
		children.remove(node);	
		node.parent = null;
	}
	
	public final boolean contains(Node node) {
		return children.contains(node);
	}
	
	public final Node[] children() {
		return children.toArray(new Node[children.size()]);
	}
	
	public final Node parent() {
		return parent;
	}
	
	
	
	public final boolean isLeaf() {
		return children.isEmpty();
	}
	
	public final boolean isRoot() {
		return (parent == null);
	}
	
	public final void setName(String name) {
		this.name = name;
	}
	
	public final String getName() {
		return name;
	}
	
	protected final void execSetup() {
		if (setup) {
			return;
		}
		
		if (isRoot() || parent.setup) {
			setup();
			setup = true;
			
			for (Node node : children) {
				node.execSetup();
			}
		}
	}
	
	protected final void execMousePressed(int button) {
		mousePressed(button);
		
		for (Node node : children) {
			node.execMousePressed(button);
		}
	}
	
	protected final void execMouseReleased(int button) {
		mouseReleased(button);
		
		for (Node node : children) {
			node.execMouseReleased(button);
		}
	}
	
	protected final void execMouseMoved(int x, int y) {
		mouseMoved(x, y);
		
		for (Node node : children) {
			node.execMouseMoved(x, y);
		}
	}
	
	protected final void execMouseScrolled(float xoffset, float yoffset) {
		mouseScrolled(xoffset, yoffset);
		
		for (Node node : children) {
			node.execMouseScrolled(xoffset, yoffset);
		}
	}
	
	//Graphics
	
	/**
	 * Create a new texture.
	 * @param image the source image of the texture
	 * @return the created texture
	 */
	protected final Texture createTexture(Image image) {
		check();
		return display.graphics.texture(image);
	}
	
	/**
	 * Load a texture.
	 * @param filename the filename of the source image
	 * @return the loaded texture
	 */
	protected final Texture loadTexture(String filename) {
		check();
		return display.graphics.loadTexture(filename);
	}
	
	/**
	 * Set the texture wrapping mode.
	 * @param wrap the wrap mode
	 * @param axis the axes to wrap
	 */
	protected final void textureWrap(Wrap wrap, Axis axis) {
		check();
		display.graphics.textureMode(wrap, axis);
	}
	
	/**
	 * Set the texture anisotropy amount.
	 * @param amount the amount of anisotropic filtering.
	 */
	protected final void textureAnisotropy(float amount) {
		check();
		display.graphics.textureAnisotropy(amount);
	}
	
	/**
	 * Load an image.
	 * @param filename the filename of the image
	 * @return the loaded image
	 */
	protected final Image loadImage(String filename) {
		check();
		return display.graphics.loadImage(filename);
	}

	/**
	 * Load a shader.
	 * @param fragment the filename of the <b>fragment</b> shader
	 * @return the loaded shader
	 */
	protected final Shader loadShader(String fragment) {
		check();
		return display.graphics.loadShader(fragment);
	}

	/**
	 * Load a shader.
	 * @param fragment the filename of the <b>fragment</b> shader
	 * @param vertex the filename of the <b>vertex</b> shader
	 * @return the loaded shader
	 */
	protected final Shader loadShader(String fragment, String vertex) {
		check();
		return display.graphics.loadShader(fragment, vertex);
	}
	
	/**
	 * Load a shape.
	 * @param filename the filename of the shape
	 * @return the loaded shape
	 */
	protected final Shape loadShape(String filename) {
		check();
		return display.graphics.loadShape(filename);
	}
	
	/**
	 * Create an empty shape.
	 * @return an empty shape
	 */
	protected final Shape createShape() {
		check();
		return display.graphics.createShape();
		
	}
	
	/**
	 * Set the depth buffer function.
	 * @param func the depth function
	 */
	protected final void setDepthFunc(DepthFunc func) {
		check();
		display.getGraphics().setDepthFunc(func);
	}

	/**
	 * Set the stencil buffer function with the provided reference and mask.
	 * @param func the stencil function
	 * @param ref the reference value used by
	 * @param mask the stencil bits to be affected
	 */
	protected final void setStencilFunc(StencilFunc func, int ref, int mask) {
		check();
		display.getGraphics().setStencilFunc(func, ref, mask);
	}
	
	/**
	 * Set hints for the renderer.
	 * <ul>
	 * 		<li><b>ENABLE/DISABLE_TEXTURE_MIPMAP:</b><br>
	 * 			Enable/Disable the use of texture mipmapping, this is used to
	 * 			remove texture minification aliasing.</li>
	 * 
	 * 		<li><b>ENABLE/DISABLE_TEXTURE_ANISOTROPIC:</b><br>
	 * 			Enable/Disable the use of anisotropic texture filtering, this
	 * 			will improve the texture quality of the mipmapping.</li>
	 * 
	 * 		<li><b>ENABLE/DISABLE_DEPTH_TEST:</b><br>
	 * 			Enable/Disable depth testing.</li>
	 * 
	 * 		<li><b>ENABLE/DISABLE_STENCIL_TEST:</b><br>
	 * 			Enable/Disable stencil testing.</li>
	 * </ul>
	 * @param hint
	 */
	protected final void hint(int hint) {
		check();
		display.graphics.hint(hint);
	}
	
	//Files
	
	protected final String loadFile(String filename) {
		return display.files.loadFile(filename);
	} 
	
	private void check() {
		if (display == null)
			throw new IllegalStateException("This node has no display owner.");
		if (display.getGraphics() == null)
			throw new IllegalStateException("Graphics context is invalid.");
	}
	
	//Data Types
	public static final int INT 	= 10;
	public static final int FLOAT 	= 11;
	public static final int DOUBLE 	= 12;
	public static final int BOOLEAN = 13;
	public static final int SHORT 	= 14;
	public static final int LONG 	= 15;
	
	//Rendering hints
	
	//Texture hints
	public static final int ENABLE_TEXTURE_MIPMAP       = 100;
	public static final int DISABLE_TEXTURE_MIPMAP      = 101;
	public static final int ENABLE_TEXTURE_ANISOTROPIC  = 102;
	public static final int DISABLE_TEXTURE_ANISOTROPIC = 103;
	
	//Buffer hints
	public static final int ENABLE_DEPTH_TEST    = 200;
	public static final int DISABLE_DEPTH_TEST   = 201;
	public static final int ENABLE_STENCIL_TEST  = 202;
	public static final int DISABLE_STENCIL_TEST = 203;
}
