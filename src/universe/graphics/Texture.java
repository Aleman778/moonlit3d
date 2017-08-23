package universe.graphics;

import universe.util.Disposable;

public abstract class Texture implements Disposable {
	
	protected final Target target;
	
	protected Sample sample;
	protected boolean mipmap;
	protected boolean anisotropic;
	protected boolean multisampled;
	
	/**
	 * Constructor.
	 * @param target the texture target
	 * @param sample the texture sampling method
	 * @param mipmap enable the use of mipmap
	 * @param anisotropic enable the use of anisotropic texture filtering
	 * @param multisampled enable the use of multisampling
	 */
	public Texture(Target target, Sample sample, boolean mipmap,
			boolean anisotropic, boolean multisampled) {
		this.target = target;
		this.sample = sample;
		this.mipmap = mipmap;
		this.anisotropic = anisotropic;
		this.multisampled = multisampled;
	}
	
	/**
	 * Bind this texture.
	 */
	public abstract void bind();
	
	/**
	 * Unbind this texture.
	 */
	public abstract void unbind();
	
	/**
	 * Set the source image of the texture.
	 * @param image the source image to use
	 */
	public abstract void image(Image image);

	/**
	 * Modify the 2d source image of this texture.
	 * @param image the sub image to use
	 * @param x the x offset
	 * @param y the y offset
	 */
	public abstract void subImage(Image image, int x);
	
	/**
	 * Modify the 2d source image of this texture.
	 * @param image the sub image to use
	 * @param x the x offset
	 * @param y the y offset
	 */
	public abstract void subImage(Image image, int x, int y);

	/**
	 * Modify the 2d source image of this texture.
	 * @param image the sub image to use
	 * @param x the x offset
	 * @param y the y offset
	 */
	public abstract void subImage(Image image, int x, int y, int z);
	
	/**
	 * Set a specific mipmap level image of this texture.
	 * @param image the mipmap image to use
	 * @param level the mipmap level
	 */
	public abstract void mipmap(Image image, int level);
	
	/**
	 * Automatically generate mipmaps of the source image.
	 */
	public abstract void generateMipmaps();
	
	/**
	 * Set the level-of-detail (LOD) bias
	 * @param bias the LOD bias
	 */
	public abstract void setLodBias(float bias);
	
	/**
	 * Set the level-of-detail (LOD) range (min / max).
	 * @param min the minimum LOD range
	 * @param max the maximum LOD range
	 */
	public abstract void setLodRange(int min, int max);
	
	/**
	 * Set the number or level-of-detail (LOD) levels. 
	 * @param levels the maximum LOD levels
	 */
	public abstract void setLodLevels(int levels);
	
	/**
	 * Set the texture minification filter option.
	 * @param filter the min filter option
	 */
	public abstract void setMinFilter(Filter filter);
	
	/**
	 * Set the texture magnification filter option.
	 * @param filter the mag filter option
	 */
	public abstract void setMagFilter(Filter filter);
	
	/**
	 * Set the maximum amount of anisotropic texture filtering.
	 * @param amount the maximum anisotropy
	 */
	public abstract void setMaxAnisotropy(float amount);

	/**
	 * Set the texture wrapping mode.
	 * @param wrap the wrap mode
	 * @param axis 
	 */
	public abstract void setWrapMode(Wrap wrap, Axis axis);
	
	/**
	 * Set the texture sampling option.<br>
	 * There are four sampling algorithms:
	 * <ul>
	 * <li>
	 * 		<b>POINT Sampling:</b>
	 * 		<p>
	 * 		Nearest-neighbor interpolation rounds each pixel to the closest source pixel grid.<br>
	 * 		<b>Note:</b> magnifying a texture causes the final pixel to appear large and blocky.<br>
	 * 		<b>Note:</b> minifying a texture causes aliasing and noise.<br>
	 * 		This option is useful when displaying pixel art and you want to preserve the hard edges.
	 * 		</p>
	 * </li>
	 * <br>
	 * <li>
	 * 		<b>LINEAR Sampling:</b>
	 * 		<p>
	 * 		Linear filtering calculates each pixel by interpolating the four nearest pixels around the location.
	 * 		<b>Note:</b> use mipmapping in order to avoid aliasing and noise effect.
	 * 		</p>
	 * </li>
	 * <br>
	 * <li>
	 * 		<b>BILINEAR Sampling:</b>
	 * 		<p>
	 * 		Bilinear filtering calculates each pixel by combining the weighted average of four nearest pixels around the location.
	 * 		The weighted is determined by the distance from the camera to the pixel location.
	 * 		</p> 
	 * </li>
	 * <br>
	 * <li>
	 * 		<b>TRILINEAR Sampling:</b>
	 * 		<p>
	 * 		Trilinear filtering uses a texture lookup and <b>bilinear</b> filtering on the two closest mipmap levels and then
	 * 		linearly interpolating the result.
	 * 		<b>Note:</b> without mipmapping the algorithm reverts to <b>bilinear</b> filtering.
	 * 		</p>
	 * </li>
	 * </ul>
	 * @param sample one of the sampling algorithm described above
	 */
	public abstract void setSample(Sample sample);
	
	/**
	 * Get the width of the source image.
	 * @return the width of the source image
	 */
	public abstract int getWidth();
	
	/**
	 * Get the height of the source image.
	 * @return the height of the source image
	 */
	public abstract int getHeight();
	
	/**
	 * Get the depth of the source image.
	 * @return the depth of the source image
	 */
	public abstract int getDepth();
	
	/**
	 * Texture definition target parameter.
	 */
	public enum Target {
		TEXTURE_1D, TEXTURE_2D, TEXTURE_3D, TEXTURE_CUBE_MAP;
	}

	/**
	 * Texture sampling options.
	 */
	public enum Sample {
		POINT, LINEAR, BILINEAR, TRILINEAR, CUSTOM
	}
	
	/**
	 * Texture minification / magnification filtering options.
	 */
	public enum Filter {
		NEAREST, LINEAR, NEAREST_MIPMAP_NEAREST, NEAREST_MIPMAP_LINEAR, LINEAR_MIPMAP_NEAREST, LINEAR_MIPMAP_LINEAR
	}
	
	/**
	 * Texture wrapping mode.
	 */
	public enum Wrap {
		REPEAT, MIRRORED_REPEAT, CLAMP, CLAMP_TO_EDGE, MIRROR_CLAMP_TO_EDGE, 
	}
	
	public enum Axis {
		X, Y, Z, XY, YZ, XZ, XYZ
	}
}
