package universe.opengl;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import universe.core.Display;
import universe.core.FileHandle;
import universe.core.Node;
import universe.util.BufferUtils;
import universe.graphics.Image.ColorModel;
import universe.graphics.Shader.ShaderType;
import universe.graphics.*;

import org.lwjgl.opengl.EXTTextureFilterAnisotropic;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.opengl.GLUtil;
import org.lwjgl.opengl.GL;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.stb.STBImage.*;

public final class GLGraphics extends Graphics {

	//Buffers
	private static final IntBuffer xBuffer 	    = BufferUtils.createEmptyIntBuffer(1);
	private static final IntBuffer yBuffer 	    = BufferUtils.createEmptyIntBuffer(1);
	private static final IntBuffer formatBuffer = BufferUtils.createEmptyIntBuffer(1);

	public GLState state;
	public GLExtension extension;
	
	private GLCapabilities capabilities;
	private GLVertexArrayObject vao;
	private Renderer2D renderer2d;
	
	/**
	 * Constructor.
	 * @param display the owner
	 */
	public GLGraphics(Display display) {
		super(display);
	}
	
	@Override
	public void init() {
		capabilities = GL.createCapabilities();
		extension = new GLExtension(capabilities);
		state = new GLState();
		renderer2d = new Renderer2D(this);
		vao = new GLVertexArrayObject(this);
		
		GLUtil.setupDebugMessageCallback(); // ### DEBUG ### Remove later
	}
	
	@Override
	public void prepare() {
		renderer2d.begin();
	}
	
	@Override
	public void present() {
		renderer2d.present();
		renderer2d.end();
	}
	
	@Override
	public void clear() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
		glClearColor(clearColor.getRed(), clearColor.getGreen(), clearColor.getBlue(), clearColor.getAlpha());
	}

	@Override
	public Texture loadTexture(String filename) {
		return texture(loadImage(filename));
	}

	@Override
	public Texture texture(Image image) {
		GLTexture texture = new GLTexture(this, Texture.Target.TEXTURE_2D,
				textureSample, textureMipmap, textureAnisotropic, textureMultisample);
		texture.image(image);
		
		if (textureAnisotropic)
			texture.setMaxAnisotropy(textureAnisotropy);
		
		return texture;
	}
	
	@Override
	public void textureMode(Texture.Wrap wrap, Texture.Axis axis) {
		boolean wrapS = (axis == Texture.Axis.X || axis == Texture.Axis.XY || axis == Texture.Axis.XZ || axis == Texture.Axis.XYZ);
		boolean wrapT = (axis == Texture.Axis.Y || axis == Texture.Axis.XY || axis == Texture.Axis.YZ || axis == Texture.Axis.XYZ);
		boolean wrapR = (axis == Texture.Axis.Z || axis == Texture.Axis.XZ || axis == Texture.Axis.YZ || axis == Texture.Axis.XYZ);
		
		if (wrapS)
			textureWrapS = wrap;
		if (wrapT)
			textureWrapT = wrap;
		if (wrapR)
			textureWrapR = wrap;
	}
	
	@Override
	public void textureAnisotropy(float value) {
		textureAnisotropy = value;
	}
	
	@Override
	public Image loadImage(String filename) {
		if (filename == null)
			throw new IllegalArgumentException("Image filename is null.");
		
		if (filename.isEmpty())
			throw new IllegalArgumentException("Image filename is empty.");
		
		return loadImage(filename, null);
	}
	
	private Image loadImage(String filename, String extension) {
		if (extension == null) {
			extension = display.files.getExtension(filename);
		}
		extension = extension.toLowerCase();

		if (extension.equals("jpg") || extension.equals("bmp") ||
			extension.equals("gif") || extension.equals("png") ||
			extension.equals("jpeg") || extension.equals("wbmp")) {
			return loadImageJava(filename);
		}
		return loadImageStb(filename);
	}
	
	private Image loadImageStb(String filename) {
		FileHandle input = display.files.createFile(filename);
		if (input == null)
			throw new RuntimeException("Image file: " + filename + " could not be found.");
		
		ByteBuffer byteBuffer = stbi_load(input.path(), xBuffer, yBuffer, formatBuffer, STBI_rgb_alpha);
		String failure = stbi_failure_reason();
		if (failure != null) {
			throw new RuntimeException(failure);
		}
		
		IntBuffer buffer = byteBuffer.asIntBuffer();
		Image result = new Image(xBuffer.get(0), yBuffer.get(0), stbi_get_format(formatBuffer.get(0)));
		
		int i = 0;
		int[] pixels = result.getPixels();
		while (buffer.hasRemaining()) {
			pixels[i++] = buffer.get();
		}
		
		stbi_image_free(byteBuffer);
		return result;
	}
	
	private Image loadImageJava(String filename) {
		InputStream input = display.files.createInput(filename);
		if (input == null)
			throw new RuntimeException("Image file: " + filename + " could not be found.");
		
		try {
			BufferedImage image = ImageIO.read(input);
			Image result = new Image(image.getWidth(), image.getHeight(), ColorModel.RGBA);
			
			int[] pixels = result.getPixels();
			image.getRGB(0, 0, image.getWidth(), image.getHeight(), result.getPixels(), 0, image.getWidth());
			
	    	for (int i = 0; i < result.getWidth() * result.getHeight(); i++) {
	            int a = (pixels[i] & 0xFF000000) >> 24;
	            int r = (pixels[i] & 0xFF0000) >> 16;
	            int g = (pixels[i] & 0xFF00) >> 8;
	            int b = (pixels[i] & 0xFF);
	
	            pixels[i] = a << 24 | b << 16 | g << 8 | r;
	        }
	    	
	    	return result;
    	} catch (IOException e) {
		}
		
		throw new IllegalArgumentException("image " + filename + " is not found.");
	}

	@Override
	public Shader loadShader(String fragment) {
		GLSLShader shader = new GLSLShader(this);
		shader.add(ShaderType.VERTEX, display.files.loadFile("test/shaders/baseVert.glsl"));
		shader.add(ShaderType.FRAGMENT, display.files.loadFile(fragment));
		shader.setup();
		
		return shader;
	}

	@Override
	public Shader loadShader(String fragment, String vertex) {
		GLSLShader shader = new GLSLShader(this);
		shader.add(ShaderType.VERTEX, display.files.loadFile(vertex));
		shader.add(ShaderType.FRAGMENT, display.files.loadFile(fragment));
		shader.setup();
		
		return shader;
	}
	
	@Override
	public Shader shader(int shader) {
		switch (shader) {
		case Node.PHONG:
			return loadShader("test/shaders/phongFrag.glsl", "test/shaders/phongVert.glsl");
		}
		
		return null; 
	}
	
	@Override
	public Shape loadShape(String filename) {
		return null;
	}
	
	@Override
	public Shape createShape() {
		return new GLShape(this, ShapeMode.POLYGON, false);
	}

	@Override
	public VertexBufferObject createVBO(int capacity, boolean dynamic) {
		return new GLVertexBufferObject(this, capacity, dynamic);
	}

	@Override
	public VertexBufferObject createVBO(float[] data, boolean dynamic) {
		return new GLVertexBufferObject(this, data, dynamic);
	}

	@Override
	public VertexBufferObject createVBO(FloatBuffer data, boolean dynamic) {
		return new GLVertexBufferObject(this, data, dynamic);
	}

	@Override
	public IndexBufferObject createIBO(int capacity, boolean dynamic) {
		return new GLIndexBufferObject(this, capacity, dynamic);
	}

	@Override
	public IndexBufferObject createIBO(short[] data, boolean dynamic) {
		return new GLIndexBufferObject(this, data, dynamic);
	}

	@Override
	public IndexBufferObject createIBO(ShortBuffer data, boolean dynamic) {
		return new GLIndexBufferObject(this, data, dynamic);
	}

	@Override
	public VertexArrayObject createVAO() {
		return new GLVertexArrayObject(this);
	}
	
	@Override
	public void viewport(float x, float y, float w, float h) {
		glViewport((int) (x * display.getWidth()), (int) (y * display.getHeight()), 
				   (int) (w * display.getWidth()), (int) (h * display.getHeight()));
	}
	
	@Override
	public void render(Shape shape) {
		glDrawArrays(glGetShapeMode(shape.getMode()), 0, shape.count());
	}
	
	@Override
	public void background(Color color) {
		this.clearColor = color;
	}
	
	@Override
	public void background(float r, float g, float b, float a) {
		background(new Color(r, g, b, a));
	}
	
	@Override
	public void rect(float x, float y, float w, float h) {
		Rectangle rect = new Rectangle(x, y, w, h);
		rect.render(renderer2d);
	}
	
	@Override
	public void ellipse(float x, float y, float w, float h) {
	}
	
	@Override
	public void render(ShapeMode mode, Buffer vbo, Buffer ibo) {
		if (ibo == null) {
			drawArrays(mode, vbo);
			
		} else {
			if (ibo.empty())
				drawArrays(mode, vbo);
			
			drawElements(mode, vbo, ibo);
		}
	}
	
	@Override
	public void setDepthTest(boolean enable) {
		this.depthTest = enable;
		
		if (enable) {
			glEnable(GL_DEPTH_TEST);
		} else {
			glDisable(GL_DEPTH_TEST);
		}
	}
	
	@Override
	public void setDepthFunc(DepthFunc func) {
		this.depthFunc = func;
		
		glDepthFunc(glGetDepthFunc(func));
	}
	
	@Override
	public void setDepthMask(boolean mask) {
		this.depthMask = mask;
		
		glDepthMask(mask);
	}
	
	@Override
	public void setStencilTest(boolean enable) {
		this.stencilTest = enable;
		
		if (enable) {
			glEnable(GL_STENCIL_TEST);
		} else {
			glDisable(GL_STENCIL_TEST);
		}
	}
	
	@Override
	public void setStencilFunc(StencilFunc func, int ref, int mask) {
		this.stencilFunc = func;
		
		glStencilFunc(glGetStencilFunc(func), ref, mask);
	}
	
	@Override
	public void hint(int hint) {
		switch (hint) {
		//Texture
		case Node.ENABLE_TEXTURE_MIPMAP:
			textureMipmap = true;
			break;
		case Node.DISABLE_TEXTURE_MIPMAP:
			textureMipmap = false;
			break;
		case Node.ENABLE_TEXTURE_ANISOTROPIC:
			textureAnisotropic = true;
			break;
		case Node.DISABLE_TEXTURE_ANISOTROPIC:
			textureAnisotropic = true;
			break;
		
		//OpenGL Buffers
		case Node.ENABLE_DEPTH_TEST:
			setDepthTest(true);
			break;
		case Node.DISABLE_DEPTH_TEST:
			setDepthTest(false);
			break;
		case Node.ENABLE_STENCIL_TEST:
			setStencilTest(true);
			break;
		case Node.DISABLE_STENCIL_TEST:
			setStencilTest(false);
			break;
		}
	}
	
	private void drawArrays(ShapeMode mode, Buffer vbo) {
		glDrawArrays(glGetShapeMode(mode), 0, vbo.count());
	}
	
	private void drawElements(ShapeMode mode, Buffer vbo, Buffer ibo) {
		glDrawElements(glGetShapeMode(mode), ibo.count(), GL_UNSIGNED_SHORT, 0L);
	}
	
	private static ColorModel stbi_get_format(int format) {
		switch (format) {
		case STBI_grey:
			return ColorModel.GRAY;
		case STBI_grey_alpha:
			return ColorModel.GRAY_ALPHA;
		case STBI_rgb:
			return ColorModel.RGB;
		case STBI_rgb_alpha:
			return ColorModel.RGBA;
		default:
			return ColorModel.CUSTOM;
		}
	}

	@Override
	public String version() {
		if (capabilities == null)
			return "Unknown";
		
		return glGetString(GL_VERSION);
	}
	
	@Override
	public String vendor() {
		if (capabilities == null)
			return "Unknown";
		
		return glGetString(GL_VENDOR);
	}
	
	public GLCapabilities getCapabilities() {
		return capabilities;
	}
	
	/**
	 * OpenGL extensions.
	 * @author Aleman778
	 */
	protected class GLExtension {
		
		public final boolean anisotropic;
		public final float maxAnisotropic;
		
		public GLExtension(GLCapabilities cap) {
			anisotropic = cap.GL_EXT_texture_filter_anisotropic;
			if (anisotropic)
				maxAnisotropic = glGetFloat(EXTTextureFilterAnisotropic.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT);
			else
				maxAnisotropic = 0;
		}
	}
	
	/**
	 * OpenGL state containing binding data etc.
	 */
	protected class GLState {
		
		public static final int MAX_TEXTURE_UNITS = 32;
		
		//Buffer states
		public int arrayBuffer        = 0;
		public int elementArrayBuffer = 0;
		public int vertexArray        = 0;
		
		//Shader states
		public int shader = 0;
		
		//Texture unit states
		private int activeTexture = 0;
		private GLTexUnit[] texUnits;
		
		/**
		 * Constructor. Initializes some data, requires a OpenGL context.
		 */
		public GLState() {
			texUnits = new GLTexUnit[MAX_TEXTURE_UNITS];
			for (int i = 0; i < texUnits.length; i++) {
				texUnits[i] = new GLTexUnit();
			}
		}
		
		public void setActiveTexture(int unit) {
			activeTexture = unit;
			glActiveTexture(GL_TEXTURE0 + unit);
		}
		
		public void setTexture1d(int texture) {
			texUnits[activeTexture].texture1d = texture;
		}
		
		public int getTexture1d() {
			return texUnits[activeTexture].texture1d;
		}
		
		public void setTexture2d(int texture) {
			texUnits[activeTexture].texture2d = texture;
		}
		
		public int getTexture2d() {
			return texUnits[activeTexture].texture2d;
		}
		
		public void setTexture2dMultisample(int texture) {
			texUnits[activeTexture].texture2dMultisample = texture;
		}
		
		public int getTexture2dMultisample() {
			return texUnits[activeTexture].texture2dMultisample;
		}
		
		public void setTexture3d(int texture) {
			texUnits[activeTexture].texture3d = texture;
		}
		
		public int getTexture3d() {
			return texUnits[activeTexture].texture3d;
		}
		
		public void setTextureCubeMap(int texture) {
			texUnits[activeTexture].textureCubeMap = texture;
		}
		
		public int getTextureCubeMap() {
			return texUnits[activeTexture].textureCubeMap;
		}
		
		class GLTexUnit {
			public int texture1d            = 0;
			public int texture2d            = 0;
			public int texture2dMultisample = 0;
			public int texture3d            = 0;
			public int textureCubeMap       = 0;
		}
	}
	
	private static final int glGetShapeMode(ShapeMode mode) {
		switch (mode) {
		case POINTS: 		 return GL_POINT;
		case LINES: 	 	 return GL_LINES;
		case LINE_STRIP: 	 return GL_LINE_STRIP;
		case LINE_LOOP: 	 return GL_LINE_LOOP;
		case TRIANGLES: 	 return GL_TRIANGLES;
		case TRIANGLE_STRIP: return GL_TRIANGLE_STRIP;
		case TRIANGLE_FAN:   return GL_TRIANGLE_FAN;
		case QUAD_STRIP: 	 return GL_QUAD_STRIP;
		case QUADS: 		 return GL_QUADS;
		case POLYGON: 		 return GL_POLYGON;
		}
		
		return 0;
	}
	
	private static final int glGetDepthFunc(DepthFunc func) {
		switch (func) {
		case ALWAYS:   return GL_ALWAYS;
		case NEVER:    return GL_NEVER;
		case LESS: 	   return GL_LESS;
		case EQUAL:    return GL_EQUAL;
		case LEQUAL:   return GL_LEQUAL;
		case GREATER:  return GL_GREATER;
		case NOTEQUAL: return GL_NOTEQUAL;
		case GEQUAL:   return GL_GEQUAL;
		}
		
		return 0;
	}
	
	private static final int glGetStencilFunc(StencilFunc func) {
		switch (func) {
		case KEEP: 		return GL_KEEP;
		case ZERO: 		return GL_ZERO;
		case REPLACE: 	return GL_REPLACE;
		case INCR: 		return GL_INCR;
		case INCR_WRAP: return GL_INCR_WRAP;
		case DECR: 		return GL_DECR;
		case DECR_WRAP: return GL_DECR_WRAP;
		case INVERT: 	return GL_INVERT;
		}
		
		return 0;
	}
	
	public static final int glGetType(int type) {
		switch (type) {
		case Node.INT: 			  return GL_INT;
		case Node.UNSIGNED_INT:   return GL_UNSIGNED_INT;
		case Node.FLOAT: 		  return GL_FLOAT;
		case Node.DOUBLE: 		  return GL_DOUBLE;
		case Node.SHORT: 		  return GL_SHORT;
		case Node.UNSIGNED_SHORT: return GL_UNSIGNED_SHORT;
		}
		
		return 0;
	}
	
	public static final int glGetSizeOf(int type) {
		switch (type) {
		case Node.INT: return Integer.BYTES;
		case Node.UNSIGNED_INT: return Integer.BYTES;
		case Node.FLOAT: return Float.BYTES;
		case Node.DOUBLE: return Double.BYTES;
		case Node.SHORT: return Short.BYTES;
		case Node.UNSIGNED_SHORT: return Short.BYTES;
		case Node.LONG: return Long.BYTES;
		}
		
		return 0;
	}
}
