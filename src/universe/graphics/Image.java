package universe.graphics;

public class Image {

	private final int width, height, depth;
	private int[] pixels;
	private int numDimensions;
	private ColorModel model;
	
	/**
	 * Constructor.
	 * Creates a 1-dimensional image.
	 * @param width the width of the image
	 * @param model the color model of the image
	 */
	public Image(int width, ColorModel model) {
		this(width, 1, 1, model);
		numDimensions = 1;
	}
	
	/**
	 * Constructor.
	 * Creates a 2-dimensional image.
	 * @param width the width of the image
	 * @param height the height of the image
	 * @param model the color model of the image
	 */
	public Image(int width, int height, ColorModel model) {
		this(width, height, 1, model);
		numDimensions = 2;
	}
	
	/**
	 * Constructor.
	 * Creates a 3-dimensional image.
	 * @param width the width of the image
	 * @param height the height of the image
	 * @param depth the depth of the image
	 * @param model the color model of the image
	 */
	public Image(int width, int height, int depth, ColorModel model) {
		this.width 	= width;
		this.height	= height;
		this.depth = depth;
		this.model = model;
		this.pixels = new int[getArraySize()];
		this.numDimensions = 3;
	}
	
	private int getArraySize() {
		return (int) Math.ceil((width * height * depth * model.components) / 4.0);
	}

	public void set(int x, Color color) {
		set(x, 0, 0, color);
	}
	
	public void set(int x, int y, Color color) {
		set(x, y, 0, color);
	}
	
	public void set(int x, int y, int z, Color color) {
		checkBounds(x, y, z);
		
		int pixel = color.getRedInt();
			pixel = (pixel << 8) + color.getGreenInt();
			pixel = (pixel << 8) + color.getBlueInt();
			pixel = (pixel << 8) + color.getAlphaInt();

		pixels[x + y * width + z * width * height] = pixel;
	}

	public void set(int x, int pixel) {
		set(x, 0, 0, pixel);
	}
	
	public void set(int x, int y, int pixel) {
		set(x, y, 0, pixel);
	}
	
	public void set(int x, int y, int z, int pixel) {
		checkBounds(x, y, z);
		
		int bit = x + y * width * getNumBits();
		int index = bit / Integer.SIZE;
		int offset = bit % Integer.SIZE;
		pixels[index] = pixel ;
	}
	
	public int[] getPixels() {
		return pixels;
	}
	
	public void setPixels(int[] pixels) {
		this.pixels = pixels;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getDepth() {
		return depth;
	}
	
	public int getNumDimensions() {
		return numDimensions;
	}
	
	public ColorModel getFormat() {
		return model;
	}

	public int getNumBits() {
		return model.getNumBits();
	}
	
	public int getNumComponents() {
		return model.getNumComponents();
	}
	
	/**
	 * Image color format
	 */
	public enum ColorModel {
		
		CUSTOM(32, 0), BINARY(1, 1),  GRAY(8, 1), GRAY_ALPHA(16, 2), RGB(24, 3), BGR(24, 3), RGBA(32, 4), BGRA(32, 4);

		private int bits;
		private int components;
		
		private ColorModel(int bits, int components) {
			this.bits = bits;
			this.components = components;
		}
		
		public int getNumComponents() {
			return components;
		}
		
		public int getNumBits() {
			return bits;
		}
	}
	
	private void checkBounds(int x, int y, int z) {
		if (x < 0 || x > width)
			throw new IllegalArgumentException("The value x = " + x + " is out of bounds expects a value from 0 to " + width);
		if (y < 0 || y > height)
			throw new IllegalArgumentException("The value y = " + y + " is out of bounds expects a value from 0 to " + height);
		if (y < 0 || y > height)
			throw new IllegalArgumentException("The value y = " + z + " is out of bounds expects a value from 0 to " + depth);
	}
}
