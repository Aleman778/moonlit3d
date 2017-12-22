package universe.opengl;

import universe.graphics.Image;
import universe.graphics.Texture;
import universe.graphics.Image.ColorModel;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL32.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL44.*;

import org.lwjgl.opengl.EXTTextureFilterAnisotropic;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class GLTexture extends Texture {

	protected final GLTexParameter params;
	protected final GLGraphics graphics;
	
	protected Image source;
	protected int object;
	
	public GLTexture(GLGraphics graphics, Target target, Sample sample, boolean mipmap,
			boolean anisotropic, boolean multisampled) {
		super(target, sample, mipmap, anisotropic, multisampled);

		this.source = null;
		this.graphics = graphics;
		this.params = new GLTexParameter(glGetTarget(target));
		this.object = glGenTextures();
		
		setSample(sample);
	}
	
	@Override
	public void bind() {
		check();
		
		if (isBound()) {
			return;
		}
		
		glBindTexture(params.target, object);
		setBindState(object);
	}
	
	@Override
	public void unbind() {
		check();
		glBindTexture(params.target, 0);
	}
	
	@Override
	public void dispose() {
		check();
		bind();
		
		glDeleteTextures(object);
		object = -1;
	}
	
	@Override
	public void image(Image image) {
		check();
		bind();
		
		texImage(image, params.lodBaseLevel);
		this.source = image;
	}

	@Override
	public void subImage(Image image, int x) {
		check();
		bind();

		texSubImage(image, params.lodBaseLevel, x, 0, 0);
	}

	@Override
	public void subImage(Image image, int x, int y) {
		check();
		bind();
		
		texSubImage(image, params.lodBaseLevel, x, y, 0);
	}

	@Override
	public void subImage(Image image, int x, int y, int z) {
		check();
		bind();
		
		texSubImage(image, params.lodBaseLevel, x, y, z);
	}
	
	@Override
	public void mipmap(Image image, int level) {
		check();
	
		if (level < 0)
			throw new IllegalArgumentException("Invalid mipmap level (" + level + "), expects a positive value.");
		
		if (level == params.lodBaseLevel)
			throw new IllegalArgumentException("Invalid mipmap level (" + level + ") use the image() function to set the source image.");

		bind();
		texImage(image, level);
		enableMipmapping(true);
	}
	
	private void texImage(Image image, int level) {
		switch (params.target) {
		case GL_TEXTURE_1D:
			glTexImage1D(GL_TEXTURE_1D, level, GL_RGBA8,
					image.getWidth(), 0, glGetColorModel(image.getFormat()),
					GL_UNSIGNED_BYTE, image.getPixels());
			break;
		case GL_TEXTURE_2D:
			glTexImage2D(GL_TEXTURE_2D, level, GL_RGBA8,
					image.getWidth(), image.getHeight(), 0, glGetColorModel(image.getFormat()),
					GL_UNSIGNED_BYTE, image.getPixels());
			break;
		case GL_TEXTURE_2D_MULTISAMPLE:
			throw new NotImplementedException();
		case GL_TEXTURE_3D:
			glTexImage3D(GL_TEXTURE_3D, params.lodBaseLevel, GL_RGBA8,
					image.getWidth(), image.getHeight(), image.getDepth(), 0,
					glGetColorModel(image.getFormat()), GL_UNSIGNED_BYTE, image.getPixels());
			break;
		}
	}
	
	private void texSubImage(Image image, int level, int x, int y, int z) {
		switch (params.target) {
		case GL_TEXTURE_1D:
			glTexSubImage1D(params.target, level, x, image.getWidth(), glGetColorModel(image.getFormat()),
					GL_UNSIGNED_BYTE, image.getPixels());
			break;
		case GL_TEXTURE_2D:
			glTexSubImage2D(params.target, level, x, y, image.getWidth(), image.getHeight(),
					glGetColorModel(image.getFormat()), GL_UNSIGNED_BYTE, image.getPixels());
			break;
		case GL_TEXTURE_2D_MULTISAMPLE:
			throw new NotImplementedException();
		case GL_TEXTURE_3D:
			glTexSubImage3D(params.target, level, x, y, z, image.getWidth(), image.getHeight(), image.getDepth(),
					glGetColorModel(image.getFormat()), GL_UNSIGNED_BYTE, image.getPixels());
		}
	}
	
	@Override
	public void generateMipmaps() {
		check();
		bind();
		
		if (!hasSource()) {
			throw new IllegalStateException("Cannot generate mipmaps, the texture has no attached source image.");
		}
		
		enableMipmapping(true);
		
		int size = Math.max(getWidth(), Math.max(getHeight(), getDepth()));
		int levels = (int) (Math.log(size) / Math.log(2));
		setLodLevels(levels);
		setLodRange(-levels, levels);
		glGenerateMipmap(params.target);
	}
	
	@Override
	public void setLodBias(float bias) {
		check();
		bind();
		
		params.lodBias = params.set(GL_TEXTURE_LOD_BIAS, bias);
	}
	
	@Override
	public void setLodRange(int min, int max) {
		check();
		bind();
		
		params.lodMin = params.set(GL_TEXTURE_MIN_LOD, min);
		params.lodMax = params.set(GL_TEXTURE_MAX_LOD, max);
	}
	
	@Override
	public void setLodLevels(int levels) {
		check();
		bind();
		
		params.lodLevels = params.set(GL_TEXTURE_MAX_LEVEL, levels - 1);
	}
	
	@Override
	public void setMinFilter(Filter filter) {
		check();
		bind();
		
		params.minFilter = params.set(GL_TEXTURE_MIN_FILTER, glGetFilter(filter));
		sample = Sample.CUSTOM;
		
		if (filter == Filter.NEAREST_MIPMAP_NEAREST || filter == Filter.NEAREST_MIPMAP_LINEAR ||
			filter == Filter.LINEAR_MIPMAP_NEAREST  || filter == Filter.LINEAR_MIPMAP_LINEAR) {
			mipmap = true;
		}
	}
	
	@Override
	public void setMagFilter(Filter filter) {
		check();
		bind();
		
		params.magFilter = params.set(GL_TEXTURE_MIN_FILTER, glGetFilter(filter));
		sample = Sample.CUSTOM;
	}
	
	@Override
	public void setMaxAnisotropy(float amount) {
		if (amount < 0) {
			throw new IllegalArgumentException("Invalid anisotropy amount (" + amount + "), expects a positive value.");
		}
		
		if (graphics.extension.anisotropic) {
			float max = graphics.extension.maxAnisotropic;
			params.anisotropy = params.set(EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT, Math.min(amount, max));
			
			anisotropic = (params.anisotropy > 0);
		}
	}
	
	@Override
	public void setSample(Sample sample) {
		check();
		bind();
		
		params.setSample(sample);
	}
	
	@Override
	public void setWrapMode(Wrap wrap, Axis axis) {
		check();
		bind();
	
		boolean wrapS = (axis == Axis.X || axis == Axis.XY || axis == Axis.XZ || axis == Axis.XYZ);
		boolean wrapT = (axis == Axis.Y || axis == Axis.XY || axis == Axis.YZ || axis == Axis.XYZ);
		boolean wrapR = (axis == Axis.Z || axis == Axis.XZ || axis == Axis.YZ || axis == Axis.XYZ);
		
		if (wrapS)
			params.wrapS = params.set(GL_TEXTURE_WRAP_S, glGetWrap(wrap));
		if (wrapT)
			params.wrapT = params.set(GL_TEXTURE_WRAP_T, glGetWrap(wrap));
		if (wrapR)
			params.wrapR = params.set(GL_TEXTURE_WRAP_R, glGetWrap(wrap));
	}
	
	@Override
	public int getWidth() {
		return source.getWidth();
	}
	
	@Override
	public int getHeight() {
		return source.getHeight();
	}
	
	@Override
	public int getDepth() {
		return source.getDepth();
	}
	
	/**
	 * Get the OpenGL reference object.
	 * @return the OpenGL reference object
	 */
	public int object() {
		return object;
	}
	
	/**
	 * OpenGL parameter helper class.
	 */
	private class GLTexParameter {
		
		private final int target;
		
		private int   minFilter    =  GL_NEAREST_MIPMAP_LINEAR;
		private int   magFilter    =  GL_LINEAR;
		private int   wrapS	       =  GL_REPEAT;
		private int   wrapT	       =  GL_REPEAT;
		private int   wrapR	       =  GL_REPEAT;
		private int   lodMin       = -1000;
		private int   lodMax       =  1000;
		private int   lodLevels    =  1000;
		private int   lodBaseLevel =  0;
		private float anisotropy   =  0.0f;
		private float lodBias      =  0.0f;
		
		public GLTexParameter(int target) {
			this.target = target;
		}

		public void setSample(Sample sample) {
			switch (sample) {
			case POINT:
				minFilter = set(GL_TEXTURE_MIN_FILTER, mipmap ? GL_NEAREST_MIPMAP_NEAREST : GL_NEAREST);
				magFilter = set(GL_TEXTURE_MAG_FILTER, GL_NEAREST);
				break;
			case LINEAR:
				minFilter = set(GL_TEXTURE_MIN_FILTER, mipmap ? GL_LINEAR_MIPMAP_NEAREST : GL_LINEAR);
				magFilter = set(GL_TEXTURE_MAG_FILTER, GL_NEAREST);
				break;
			case BILINEAR:
				minFilter = set(GL_TEXTURE_MIN_FILTER, mipmap ? GL_LINEAR_MIPMAP_NEAREST : GL_LINEAR);
				magFilter = set(GL_TEXTURE_MAG_FILTER, GL_LINEAR);
				break;
			case TRILINEAR:
				minFilter = set(GL_TEXTURE_MIN_FILTER, mipmap ? GL_LINEAR_MIPMAP_LINEAR : GL_LINEAR);
				magFilter = set(GL_TEXTURE_MAG_FILTER, GL_LINEAR);
				break;
			case CUSTOM:
				minFilter = set(GL_TEXTURE_MIN_FILTER, mipmap ? GL_LINEAR_MIPMAP_LINEAR : GL_LINEAR);
				break;
			default:
				throw new IllegalArgumentException("Invalid sample option " + sample + ", expected POINT, LINEAR, BILINEAR or TRILINEAR");
			}		
		}
		
		private int set(int pname, int param) {
			glTexParameteri(target, pname, param);
			return param;
		}
		
		private float set(int pname, float param) {
			glTexParameterf(target, pname, param);
			return param;
		}
	}
	
	/**
	 * Get the OpenGL specific target reference.
	 * @param target the texture target
	 * @return the OpenGL target reference
	 */
	protected int glGetTarget(Target target) {
		if (multisampled && !(target == Target.TEXTURE_2D))
			throw new IllegalArgumentException("Multisampling is not available for target " + target + ".");
		
		switch (target) {
		case TEXTURE_1D:
			return GL_TEXTURE_1D;
		case TEXTURE_2D:
			return multisampled ? GL_TEXTURE_2D_MULTISAMPLE : GL_TEXTURE_2D;
		case TEXTURE_3D:
			return GL_TEXTURE_3D;
		case TEXTURE_CUBE_MAP:
			return GL_TEXTURE_CUBE_MAP;
		}
		
		return 0;
	}
	
	/**
	 * Get the OpenGL specific minification / magnification filter reference.
	 * @param filter the minification / magnification filter
	 * @return the OpenGL filter reference
	 */
	protected int glGetFilter(Filter filter) {
    	switch (filter) {
    	case NEAREST:
    		return GL_NEAREST;
    	case LINEAR:
    		return GL_LINEAR;
    	case NEAREST_MIPMAP_NEAREST:
    		return GL_NEAREST_MIPMAP_NEAREST;
    	case NEAREST_MIPMAP_LINEAR:
    		return GL_NEAREST_MIPMAP_LINEAR;
    	case LINEAR_MIPMAP_NEAREST:
    		return GL_LINEAR_MIPMAP_NEAREST;
    	case LINEAR_MIPMAP_LINEAR:
    		return GL_LINEAR_MIPMAP_LINEAR;
    	}
    	
    	return 0;
    }
    
	/**
	 * Get the OpenGL specific color format reference.
	 * @param format the color model
	 * @return the OpenGL format reference
	 */
    protected static final int glGetColorModel(ColorModel format) {
    	switch (format) {
    	case GRAY:
    		return GL_LUMINANCE;
    	case RGB:
    		return GL_RGB;
    	case BGR:
    		return GL_BGR;
    	case RGBA:
    		return GL_RGBA;
    	case BGRA:
    		return GL_BGRA;
    	}
    	
    	return 0;
    }
    
    /**
     * Get the OpenGL specific wrap mode reference.
     * <b>Note: </b> GL_CLAMP is deprecated in gl
     * @param wrap the wrap mode
     * @return the OpenGL wrap mode reference
     */
    protected static final int glGetWrap(Wrap wrap) {
    	switch (wrap) {
    	case REPEAT:
    		return GL_REPEAT;
    	case MIRRORED_REPEAT:
    		return GL_MIRRORED_REPEAT;
    	case CLAMP:
    		return GL_CLAMP_TO_EDGE;
    	case CLAMP_TO_EDGE:
    		return GL_CLAMP_TO_EDGE;
    	case MIRROR_CLAMP_TO_EDGE:
    		return GL_MIRROR_CLAMP_TO_EDGE;
    	}
    	
    	return 0;
    }

	private void setBindState(int state) {
		switch (params.target) {
		case GL_TEXTURE_1D:
			graphics.state.setTexture1d(state);
			break;
		case GL_TEXTURE_2D:
			graphics.state.setTexture2d(state);
			break;
		case GL_TEXTURE_2D_MULTISAMPLE:
			graphics.state.setTexture2dMultisample(state);
			break;
		case GL_TEXTURE_3D:
			graphics.state.setTexture3d(state);
			break;
		case GL_TEXTURE_CUBE_MAP:
			graphics.state.setTextureCubeMap(state);
			break;
		}
	}
	
	private int getBindState() {
		switch (params.target) {
		case GL_TEXTURE_1D:
			return graphics.state.getTexture1d();
		case GL_TEXTURE_2D:
			return graphics.state.getTexture2d();
		case GL_TEXTURE_2D_MULTISAMPLE:
			return graphics.state.getTexture2dMultisample();
		case GL_TEXTURE_3D:
			return graphics.state.getTexture3d();
		case GL_TEXTURE_CUBE_MAP:
			return graphics.state.getTextureCubeMap();
		}
		
		return 0;
	}
	
	private void enableMipmapping(boolean enable) {
		if (mipmap != enable) {
			mipmap = enable;
			setSample(sample);
		}
	}
	
	private boolean hasSource() {
		return (source != null);
	}
	
	private boolean isBound() {
		return (getBindState() == object);
	}
	
    private void check() {
    	if (object == -1)
    		throw new NullPointerException();
    }
}
