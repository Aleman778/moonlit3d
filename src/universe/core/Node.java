package universe.core;

import java.util.HashSet;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import universe.graphics.*;
import universe.graphics.Graphics.DepthFunc;
import universe.graphics.Graphics.StencilFunc;
import universe.graphics.Texture.Axis;
import universe.graphics.Texture.Wrap;
import universe.math.Quaternion;
import universe.math.Vector2;
import universe.math.Vector3;

public abstract class Node extends NodeEvents {
	
	protected Display display = null;
	protected Transform transform = new Transform();

	private HashSet<Node> children = new HashSet<>();
	private String name = getClass().getSimpleName();
	private Node parent = null;
	private boolean setup = false;
	
	public final void add(Node node) {
		children.add(node);
		node.parent = this;
		node.display = display;
		
		if (display != null) {
			display.addNode(node);	
		}
		node.execSetup();
	}
	
	public final void remove(Node node) {
		children.remove(node);	
		display.remove(node);
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
	
	//Math
	protected final Vector2 vec2(float x, float y) {
		return new Vector2(x, y);
	}
	
	protected final Vector2 vec2(Vector2 vec) {
		return new Vector2(vec);
	}
	
	protected final Vector2 add(Vector2 vec1, Vector2 vec2) {
		return vec1.add(vec2);
	}
	
	//etc.
	
	//Transformation
	public final void translate(Vector3 translation) {
		transform.translate(translation);
	}
	
	public final void translate(Vector2 translation) {
		translate(new Vector3(translation.x, translation.y, 0));
	}
	
	public final void translate(float x, float y, float z) {
		transform.translate(new Vector3(x, y, z));
	}
	
	public final void translate(float x, float y) {
		translate(x, y, 0);
	}
	
	public final void rotate(float r) {
		rotateZ(r);
	}
	
	public final void rotate(Vector3 axis, float angle) {
		transform.rotate(Quaternion.rotation(axis, angle));
	}
	
	public final void rotate(float x, float y, float z) {
		transform.rotate(Quaternion.euler(x, y, z));
	}
	
	public final void rotateX(float x) {
		rotate(Vector3.RIGHT, x);
	}
	
	public final void rotateY(float y) {
		rotate(Vector3.UP, y);
	}
	
	public final void rotateZ(float z) {
		rotate(Vector3.FORWARD, z);	
	}
	
	public final void scale(float f) {
		transform.scale(new Vector3(f, f, f));
	}
	
	public final void scale(float x, float y) {
		transform.scale(new Vector3(x, y, 0));
		
	}
	
	public final void scale(float x, float y, float z) {
		transform.scale(new Vector3(x, y, z));
	}
	
	//Graphics
	protected final void background(Color color) {
		check();
		display.graphics.background(color);
	}
	
	protected final void rect(float x, float y, float w, float h) {
		check();
		display.graphics.rect(x, y, w, h);
	}
	
	protected final void ellipse(float x, float y, float w, float h) {
		check();
		display.graphics.ellipse(x, y, w, h);
	}
	
	protected final void image(Image image) {
		
	}
	
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
	 * Load a predefined shader.
	 * @param shader the predefined shader
	 * @return the loaded shader
	 */
	protected final Shader shader(int shader) {
		check();
		return display.graphics.shader(shader);
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
	/**<b>Type:</b> 4 byte integer.*/
	public static final int INT 		   = 10;
	/**<b>Type:</b> 4 byte unsigned integer.*/
	public static final int UNSIGNED_INT   = 11;
	/**<b>Type:</b> 4 byte floating point.*/
	public static final int FLOAT 		   = 12;
	/**<b>Type:</b> 8 byte double precision floating point.*/
	public static final int DOUBLE 		   = 13;
	/**<b>Type:</b> 8 byte long.*/
	public static final int LONG 		   = 14;
	/**<b>Type:</b> 2 byte short.*/
	public static final int SHORT 		   = 15;
	/**<b>Type:</b> 2 byte unsigned short.*/
	public static final int UNSIGNED_SHORT = 16;
	/**<b>Type:</b> 2 byte character.*/
	public static final int CHAR		   = 17;
	/**<b>Type:</b> boolean.*/
	public static final int BOOLEAN 	   = 18;
	/**<b>Type:</b> 1 byte.*/
	public static final int BYTE           = 19;
	/**<b>Type:</b> vector of 2 floating point numbers.*/
	public static final int VEC2		   = 20;
	/**<b>Type:</b> vector of 3 floating point numbers.*/
	public static final int VEC3           = 21;
	/**<b>Type:</b> vector of 4 floating point numbers.*/
	public static final int VEC4		   = 22;
	/**<b>Type:</b> matrix of 2x2 floating point numbers.*/
	public static final int MAT2		   = 23;
	/**<b>Type:</b> matrix of 3x3 floating point numbers.*/
	public static final int MAT3  		   = 24;
	/**<b>Type:</b> matrix of 4x4 floating point numbers.*/
	public static final int MAT4		   = 25;
	/**<b>Type:</b> quaternion of 4 floating point numbers.*/
	public static final int QUAT		   = 26;
	
	//Rendering hints (number: even = enable, odd = disable)
	// -> Texture hints
	public static final int ENABLE_TEXTURE_MIPMAP       = 100;
	public static final int DISABLE_TEXTURE_MIPMAP      = 101;
	public static final int ENABLE_TEXTURE_ANISOTROPIC  = 102;
	public static final int DISABLE_TEXTURE_ANISOTROPIC = 103;
	
	// -> Buffer hints
	public static final int ENABLE_DEPTH_TEST    = 200;
	public static final int DISABLE_DEPTH_TEST   = 201;
	public static final int ENABLE_STENCIL_TEST  = 202;
	public static final int DISABLE_STENCIL_TEST = 203;
	
	// -> OpenGL only hints
	public static final int ENABLE_GL_DEBUG = 300;
	public static final int DISABLE_GL_DEBUG = 301;
	
	// -> Predefined shaders
	public static final int FLAT 		= 10000;
	public static final int GOURAUD 	= 10001;
	public static final int PHONG 		= 10002;
	public static final int PHONG_BLINN = 10003;
	public static final int TOON 		= 10004;
}
