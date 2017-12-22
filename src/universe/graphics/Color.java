package universe.graphics;

import java.nio.FloatBuffer;
import java.util.HashMap;

import universe.util.BufferUtils;

public final class Color {

	/**
	 * The red component of the color.
	 */
	private float red;

	/**
	 * The green component of the color.
	 */
	private float green;

	/**
	 * The blue component of the color.
	 */
	private float blue;

	/**
	 * The alpha component of the color.
	 */
	private float alpha;

	public Color(float red, float green, float blue) {
		this(red, green, blue, 1.0f);
	}

	public Color(float red, float green, float blue, float alpha) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
	}

	public static Color color(float red, float green, float blue) {
		return new Color(red, green, blue);
	}

	public static Color color(float red, float green, float blue, float alpha) {
		return new Color(red, green, blue, alpha);
	}

	public static Color rgb(int red, int green, int blue) {
		return rgba(red, green, blue, 1.0f);
	}

	public static Color rgba(int red, int green, int blue, float alpha) {
		if (red < 0 || red > 255) {
			throw new IllegalArgumentException(
					"The red color component (" + red + ") expects a value from 0 to 255.");
		}
		if (green < 0 || green > 255) {
			throw new IllegalArgumentException(
					"The green color component (" + green + ") expects a value from 0 to 255.");
		}
		if (blue < 0 || blue > 255) {
			throw new IllegalArgumentException(
					"The blue color component (" + blue + ") expects a value from 0 to 255.");
		}
		if (alpha < 0 || alpha > 1) {
			throw new IllegalArgumentException(
					"The alpha color component (" + alpha + ") expects a value from 0 to 1.");
		}

		return new Color(red / 255f, green / 255f, blue / 255f, alpha);
	}
	
	public static Color hsl(int hue, float saturation, float lightness) {
		return hsl(hue, saturation, lightness, 1.0f);
	}
	
	public static Color hsl(int hue, float saturation, float lightness, float alpha) {
		if (hue < 0 || hue > 360) {
			throw new IllegalArgumentException(
					"The hue color component (" + hue + ") expects a value from 0 to 360.");
		}
		if (saturation < 0 || saturation > 1) {
			throw new IllegalArgumentException(
					"The saturation color component (" + saturation + ") expects a value from 0 to 1.");
		}
		if (lightness < 0 || lightness > 1) {
			throw new IllegalArgumentException(
					"The lightness color component (" + lightness + ") expects a value from 0 to 1.");
		}
		if (alpha < 0 || alpha > 1) {
			throw new IllegalArgumentException(
					"The alpha color component (" + alpha + ") expects a value from 0 to 1.");
		}
		
		float[] data = HSLtoRGB(hue, saturation, lightness);
		return new Color(data[0], data[1], data[2], alpha);
	}
	
	private static float[] HSLtoRGB(int hue, float saturation, float lightness) {
		float red, green, blue;
		
		if (saturation == 0) {
			red = green = blue = lightness; // Achromatic
		} else {
			float c = (1 - Math.abs(2 * lightness - 1)) * saturation;
			float h = hue / 60.0f;
			float x = c * (1 - Math.abs(h % 2 - 1));
			float m = lightness - 0.5f * c;
			
			switch ((int) h) {
			case 0:
				red = c + m; green = x + m; blue = m;
				break;
			case 1:
				red = x + m; green = c + m; blue = m;
				break;
			case 2:
				red = m; green = c + m; blue = x + m;
				break;
			case 3:
				red = m; green = x + m; blue = c + m;
				break;
			case 4:
				red = x + m; green = m; blue = c + m;
				break;
			case 5:
				red = c + m; green = m; blue = x + m;
				break;
			default:
				red = m; green = m; blue = m;
			}
		}
		
		return new float[] {red, green, blue}; 
	}

	public static Color web(String color) {
		String data = color.toLowerCase().trim();

		if (data.startsWith("#")) {
			return parseHex(data.substring(1));
		} else if (data.startsWith("0x")) {
			return parseHex(data.substring(2));
		} else if (data.startsWith("rgba")) {
			return parseRGB(data.substring(5, data.length() - 1), true);
		} else if (data.startsWith("rgb")) {
			return parseRGB(data.substring(4, data.length() - 1), false);
		} else if (data.startsWith("hsla")) {
			return parseHSL(data.substring(5, data.length() - 1), true);
		} else if (data.startsWith("hsl")) {
			return parseHSL(data.substring(4, data.length() - 1), false);
		} else {
			return ColorMap.get(color);
		}
	}

	private static Color parseHex(String hex) {
		try {
			int len = hex.length();
			int red = 0, green = 0, blue = 0, alpha = 0;

			if (len == 3) {
				red = Integer.parseInt(hex.substring(0, 1));
				green = Integer.parseInt(hex.substring(1, 2));
				blue = Integer.parseInt(hex.substring(2, 3));
				return Color.color(red / 15f, green / 15f, blue / 15f, 1.0f);
			} else if (len == 4) {
				red = Integer.parseInt(hex.substring(0, 1));
				green = Integer.parseInt(hex.substring(1, 2));
				blue = Integer.parseInt(hex.substring(2, 3));
				alpha = Integer.parseInt(hex.substring(3, 4));
				return Color.color(red / 15f, green / 15f, blue / 15f, alpha / 15f);
			} else if (len == 6) {
				red = Integer.parseInt(hex.substring(0, 2), 16);
				green = Integer.parseInt(hex.substring(2, 4), 16);
				blue = Integer.parseInt(hex.substring(4, 6), 16);
				alpha = 255;
				return Color.rgba(red, green, blue, alpha / 255f);
			} else if (len == 8) {
				red = Integer.parseInt(hex.substring(0, 2), 16);
				green = Integer.parseInt(hex.substring(2, 4), 16);
				blue = Integer.parseInt(hex.substring(4, 6), 16);
				alpha = Integer.parseInt(hex.substring(6, 8), 16);
				return Color.rgba(red, green, blue, alpha / 255f);
			}

		} catch (NumberFormatException e) {
		}

		throw new IllegalArgumentException("Invalid hexadecimal color specification (" + hex + ").");
	}
	
	private static Color parseRGB(String rgb, boolean hasAlpha) {
		try {
			int red = 0, green = 0, blue = 0;
			float alpha = 1.0f;
			
			String[] data = rgb.split(",");
			red   = (int) parseComponent(data[0].trim(), COMPONENT);
			green = (int) parseComponent(data[1].trim(), COMPONENT);
			blue  = (int) parseComponent(data[2].trim(), COMPONENT);
			if (hasAlpha) {
				alpha = parseComponent(data[3].trim(), ALPHA);
			}
			
			return Color.rgba(red, green, blue, alpha);
		} catch (NumberFormatException | NullPointerException e) {
		}
		
		throw new IllegalArgumentException("Invalid rgb color specification (" + rgb + ").");
	}
	
	private static Color parseHSL(String hsl, boolean hasAlpha) {
		try {
			int hue = 0;
			float saturation= 0, lightness = 0, alpha = 1.0f;
			
			String[] data = hsl.split(",");
			hue 	   = (int) parseComponent(data[0].trim(), ANGLE);
			saturation =       parseComponent(data[1].trim(), PERCENT);
			lightness  =       parseComponent(data[2].trim(), PERCENT);
			if (hasAlpha) {
				alpha = parseComponent(data[3].trim(), ALPHA);
			}
			
			return Color.hsl(hue, saturation, lightness, alpha);
		} catch (NumberFormatException | NullPointerException e) {
		}
		
		throw new IllegalArgumentException("Invalid rgb color specification (" + hsl + ").");
	}
	
	private static final int COMPONENT = 0;
	private static final int PERCENT   = 1;
	private static final int ANGLE     = 2;
	private static final int ALPHA     = 3;
	
	private static float parseComponent(String component, int type) {
		if (type == COMPONENT) {
			if (component.endsWith("%")) {
				return Integer.parseInt(component.substring(0, component.length() - 1)) * 2.55f;
			} else {
				return Integer.parseInt(component);
			}
		} else if (type == PERCENT) {
			if (!component.endsWith("%")) {
				throw new NumberFormatException();
			}

			return Integer.parseInt(component.substring(0, component.length() - 1)) / 100.0f;
		} else if (type == ANGLE) {
			return Integer.parseInt(component);
		} else {
			return Float.parseFloat(component);
		}
	}
	
	public float getRed() {
		return red;
	}

	public int getRedInt() {
		return (int) (red * 255);
	}
	
	public void setRed(float red) {
		this.red = red;
	}
	
	public float getGreen() {
		return green;
	}

	public int getGreenInt() {
		return (int) (green * 255);
	}
	
	public void setGreen(float green) {
		this.green = green;
	}
	
	public float getBlue() {
		return blue;
	}

	public int getBlueInt() {
		return (int) (blue * 255);
	}
	
	public void setBlue(float blue) {
		this.blue = blue;
	}

	public int getAlphaInt() {
		return (int) (alpha * 255);
	}
	
	public float getAlpha() {
		return alpha;
	}
	
	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}
	
	/**
	 * Convert the rgba color components to a float buffer
	 * @return the new float buffer containing the data in order
	 * @see java.nio.FloatBuffer
	 */
	public FloatBuffer toFloatBuffer() {
		FloatBuffer result = BufferUtils.createFloatBuffer(red, green, blue, alpha);
		return result;
	}

	/**
	 * Convert the rgb color components to a float buffer
	 * @return the new float buffer containing the data in order
	 * @see java.nio.FloatBuffer
	 */
	public FloatBuffer toFloatBufferRGB() {
		FloatBuffer result = BufferUtils.createFloatBuffer(red, green, blue);
		return result;
	}
	
	@Override
	public String toString() {
		return "Color: red(" + red + "), green(" + green + "), blue(" + blue + "), alpha(" + alpha + ").";
	}

	/**
	 * A fully transparent color with a hex value of #00000000.
	 */
	public static final Color TRANSPARENT = new Color(0f, 0f, 0f, 0f);

	/**
	 * The color alice blue with a hex value of #F0F8FF.
	 */
	public static final Color ALICEBLUE = new Color(0.9411765f, 0.972549f, 1.0f);

	/**
	 * The color antique white with a hex value of #FAEBD7.
	 */
	public static final Color ANTIQUEWHITE = new Color(0.98039216f, 0.92156863f, 0.84313726f);

	/**
	 * The color aqua with a hex value of #00FFFF.
	 */
	public static final Color AQUA = new Color(0.0f, 1.0f, 1.0f);

	/**
	 * The color aquamarine with a hex value of #7FFFD4.
	 */
	public static final Color AQUAMARINE = new Color(0.49803922f, 1.0f, 0.83137256f);

	/**
	 * The color azure with a hex value of #F0FFFF.
	 */
	public static final Color AZURE = new Color(0.9411765f, 1.0f, 1.0f);

	/**
	 * The color beige with a hex value of #F5F5DC.
	 */
	public static final Color BEIGE = new Color(0.9607843f, 0.9607843f, 0.8627451f);

	/**
	 * The color bisque with a hex value of #FFE4C4.
	 */
	public static final Color BISQUE = new Color(1.0f, 0.89411765f, 0.76862746f);

	/**
	 * The color black with a hex value of #000000.
	 */
	public static final Color BLACK = new Color(0.0f, 0.0f, 0.0f);

	/**
	 * The color blanched almond with a hex value of #FFEBCD.
	 */
	public static final Color BLANCHEDALMOND = new Color(1.0f, 0.92156863f, 0.8039216f);

	/**
	 * The color blue with a hex value of #0000FF.
	 */
	public static final Color BLUE = new Color(0.0f, 0.0f, 1.0f);

	/**
	 * The color blue violet with a hex value of #8A2BE2.
	 */
	public static final Color BLUEVIOLET = new Color(0.5411765f, 0.16862746f, 0.8862745f);

	/**
	 * The color brown with a hex value of #A52A2A.
	 */
	public static final Color BROWN = new Color(0.64705884f, 0.16470589f, 0.16470589f);

	/**
	 * The color burly wood with a hex value of #DEB887.
	 */
	public static final Color BURLYWOOD = new Color(0.87058824f, 0.72156864f, 0.5294118f);

	/**
	 * The color cadet blue with a hex value of #5F9EA0.
	 */
	public static final Color CADETBLUE = new Color(0.37254903f, 0.61960787f, 0.627451f);

	/**
	 * The color chartreuse with a hex value of #7FFF00.
	 */
	public static final Color CHARTREUSE = new Color(0.49803922f, 1.0f, 0.0f);

	/**
	 * The color chocolate with a hex value of #D2691E.
	 */
	public static final Color CHOCOLATE = new Color(0.8235294f, 0.4117647f, 0.11764706f);
	

	/**
	 * The color coral with a hex value of #FF7F50.
	 */
	public static final Color CORAL = new Color(1.0f, 0.49803922f, 0.3137255f);

	/**
	 * The color cornflower blue with a hex value of #6495ED.
	 */
	public static final Color CORNFLOWERBLUE = new Color(0.39215687f, 0.58431375f, 0.92941177f);

	/**
	 * The color cornsilk with a hex value of #FFF8DC.
	 */
	public static final Color CORNSILK = new Color(1.0f, 0.972549f, 0.8627451f);

	/**
	 * The color crimson with a hex value of #DC143C.
	 */
	public static final Color CRIMSON = new Color(0.8627451f, 0.078431375f, 0.23529412f);

	/**
	 * The color cyan with a hex value of #00FFFF.
	 */
	public static final Color CYAN = new Color(0.0f, 1.0f, 1.0f);

	/**
	 * The color dark blue with a hex value of #00008B.
	 */
	public static final Color DARKBLUE = new Color(0.0f, 0.0f, 0.54509807f);

	/**
	 * The color dark cyan with a hex value of #008B8B.
	 */
	public static final Color DARKCYAN = new Color(0.0f, 0.54509807f, 0.54509807f);

	/**
	 * The color dark goldenrod with a hex value of #B8860B.
	 */
	public static final Color DARKGOLDENROD = new Color(0.72156864f, 0.5254902f, 0.043137256f);

	/**
	 * The color dark gray with a hex value of #A9A9A9.
	 */
	public static final Color DARKGRAY = new Color(0.6627451f, 0.6627451f, 0.6627451f);

	/**
	 * The color dark green with a hex value of #006400.
	 */
	public static final Color DARKGREEN = new Color(0.0f, 0.39215687f, 0.0f);

	/**
	 * The color dark grey with a hex value of #A9A9A9.
	 */
	public static final Color DARKGREY = DARKGRAY;

	/**
	 * The color dark khaki with a hex value of #BDB76B.
	 */
	public static final Color DARKKHAKI = new Color(0.7411765f, 0.7176471f, 0.41960785f);

	/**
	 * The color dark magenta with a hex value of #8B008B.
	 */
	public static final Color DARKMAGENTA = new Color(0.54509807f, 0.0f, 0.54509807f);

	/**
	 * The color dark olive green with a hex value of #556B2F.
	 */
	public static final Color DARKOLIVEGREEN = new Color(0.33333334f, 0.41960785f, 0.18431373f);

	/**
	 * The color dark orange with a hex value of #FF8C00.
	 */
	public static final Color DARKORANGE = new Color(1.0f, 0.54901963f, 0.0f);

	/**
	 * The color dark orchid with a hex value of #9932CC.
	 */
	public static final Color DARKORCHID = new Color(0.6f, 0.19607843f, 0.8f);

	/**
	 * The color dark red with a hex value of #8B0000.
	 */
	public static final Color DARKRED = new Color(0.54509807f, 0.0f, 0.0f);

	/**
	 * The color dark salmon with a hex value of #E9967A.
	 */
	public static final Color DARKSALMON = new Color(0.9137255f, 0.5882353f, 0.47843137f);

	/**
	 * The color dark sea green with a hex value of #8FBC8F.
	 */
	public static final Color DARKSEAGREEN = new Color(0.56078434f, 0.7372549f, 0.56078434f);

	/**
	 * The color dark slate blue with a hex value of #483D8B.
	 */
	public static final Color DARKSLATEBLUE = new Color(0.28235295f, 0.23921569f, 0.54509807f);

	/**
	 * The color dark slate gray with a hex value of #2F4F4F.
	 */
	public static final Color DARKSLATEGRAY = new Color(0.18431373f, 0.30980393f, 0.30980393f);

	/**
	 * The color dark slate grey with a hex value of #2F4F4F.
	 */
	public static final Color DARKSLATEGREY = DARKSLATEGRAY;

	/**
	 * The color dark turquoise with a hex value of #00CED1.
	 */
	public static final Color DARKTURQUOISE = new Color(0.0f, 0.80784315f, 0.81960785f);

	/**
	 * The color dark violet with a hex value of #9400D3.
	 */
	public static final Color DARKVIOLET = new Color(0.5803922f, 0.0f, 0.827451f);

	/**
	 * The color deep pink with a hex value of #FF1493.
	 */
	public static final Color DEEPPINK = new Color(1.0f, 0.078431375f, 0.5764706f);

	/**
	 * The color deep sky blue with a hex value of #00BFFF.
	 */
	public static final Color DEEPSKYBLUE = new Color(0.0f, 0.7490196f, 1.0f);

	/**
	 * The color dim gray with a hex value of #696969.
	 */
	public static final Color DIMGRAY = new Color(0.4117647f, 0.4117647f, 0.4117647f);

	/**
	 * The color dim grey with a hex value of #696969.
	 */
	public static final Color DIMGREY = DIMGRAY;

	/**
	 * The color dodger blue with a hex value of #1E90FF.
	 */
	public static final Color DODGERBLUE = new Color(0.11764706f, 0.5647059f, 1.0f);

	/**
	 * The color firebrick with a hex value of #B22222.
	 */
	public static final Color FIREBRICK = new Color(0.69803923f, 0.13333334f, 0.13333334f);

	/**
	 * The color floral white with a hex value of #FFFAF0.
	 */
	public static final Color FLORALWHITE = new Color(1.0f, 0.98039216f, 0.9411765f);

	/**
	 * The color forest green with a hex value of #228B22.
	 */
	public static final Color FORESTGREEN = new Color(0.13333334f, 0.54509807f, 0.13333334f);

	/**
	 * The color fuchsia with a hex value of #FF00FF.
	 */
	public static final Color FUCHSIA = new Color(1.0f, 0.0f, 1.0f);

	/**
	 * The color gainsboro with a hex value of #DCDCDC.
	 */
	public static final Color GAINSBORO = new Color(0.8627451f, 0.8627451f, 0.8627451f);

	/**
	 * The color ghost white with a hex value of #F8F8FF.
	 */
	public static final Color GHOSTWHITE = new Color(0.972549f, 0.972549f, 1.0f);

	/**
	 * The color gold with a hex value of #FFD700.
	 */
	public static final Color GOLD = new Color(1.0f, 0.84313726f, 0.0f);

	/**
	 * The color goldenrod with a hex value of #DAA520.
	 */
	public static final Color GOLDENROD = new Color(0.85490197f, 0.64705884f, 0.1254902f);

	/**
	 * The color gray with a hex value of #808080.
	 */
	public static final Color GRAY = new Color(0.5019608f, 0.5019608f, 0.5019608f);

	/**
	 * The color green with a hex value of #008000.
	 */
	public static final Color GREEN = new Color(0.0f, 0.5019608f, 0.0f);

	/**
	 * The color green yellow with a hex value of #ADFF2F.
	 */
	public static final Color GREENYELLOW = new Color(0.6784314f, 1.0f, 0.18431373f);

	/**
	 * The color grey with a hex value of #808080.
	 */
	public static final Color GREY = GRAY;

	/**
	 * The color honeydew with a hex value of #F0FFF0.
	 */
	public static final Color HONEYDEW = new Color(0.9411765f, 1.0f, 0.9411765f);

	/**
	 * The color hot pink with a hex value of #FF69B4.
	 */
	public static final Color HOTPINK = new Color(1.0f, 0.4117647f, 0.7058824f);

	/**
	 * The color indian red with a hex value of #CD5C5C.
	 */
	public static final Color INDIANRED = new Color(0.8039216f, 0.36078432f, 0.36078432f);

	/**
	 * The color indigo with a hex value of #4B0082.
	 */
	public static final Color INDIGO = new Color(0.29411766f, 0.0f, 0.50980395f);

	/**
	 * The color ivory with a hex value of #FFFFF0.
	 */
	public static final Color IVORY = new Color(1.0f, 1.0f, 0.9411765f);

	/**
	 * The color khaki with a hex value of #F0E68C.
	 */
	public static final Color KHAKI = new Color(0.9411765f, 0.9019608f, 0.54901963f);

	/**
	 * The color lavender with a hex value of #E6E6FA.
	 */
	public static final Color LAVENDER = new Color(0.9019608f, 0.9019608f, 0.98039216f);

	/**
	 * The color lavender blush with a hex value of #FFF0F5.
	 */
	public static final Color LAVENDERBLUSH = new Color(1.0f, 0.9411765f, 0.9607843f);

	/**
	 * The color lawn green with a hex value of #7CFC00.
	 */
	public static final Color LAWNGREEN = new Color(0.4862745f, 0.9882353f, 0.0f);

	/**
	 * The color lemon chiffon with a hex value of #FFFACD.
	 */
	public static final Color LEMONCHIFFON = new Color(1.0f, 0.98039216f, 0.8039216f);

	/**
	 * The color light blue with a hex value of #ADD8E6.
	 */
	public static final Color LIGHTBLUE = new Color(0.6784314f, 0.84705883f, 0.9019608f);

	/**
	 * The color light coral with a hex value of #F08080.
	 */
	public static final Color LIGHTCORAL = new Color(0.9411765f, 0.5019608f, 0.5019608f);

	/**
	 * The color light cyan with a hex value of #E0FFFF.
	 */
	public static final Color LIGHTCYAN = new Color(0.8784314f, 1.0f, 1.0f);

	/**
	 * The color light goldenrod yellow with a hex value of #FAFAD2.
	 */
	public static final Color LIGHTGOLDENRODYELLOW = new Color(0.98039216f, 0.98039216f, 0.8235294f);

	/**
	 * The color light gray with a hex value of #D3D3D3.
	 */
	public static final Color LIGHTGRAY = new Color(0.827451f, 0.827451f, 0.827451f);

	/**
	 * The color light green with a hex value of #90EE90.
	 */
	public static final Color LIGHTGREEN = new Color(0.5647059f, 0.93333334f, 0.5647059f);

	/**
	 * The color light grey with a hex value of #D3D3D3.
	 */
	public static final Color LIGHTGREY = LIGHTGRAY;

	/**
	 * The color light pink with a hex value of #FFB6C1.
	 */
	public static final Color LIGHTPINK = new Color(1.0f, 0.7137255f, 0.75686276f);

	/**
	 * The color light salmon with a hex value of #FFA07A.
	 */
	public static final Color LIGHTSALMON = new Color(1.0f, 0.627451f, 0.47843137f);

	/**
	 * The color light sea green with a hex value of #20B2AA.
	 */
	public static final Color LIGHTSEAGREEN = new Color(0.1254902f, 0.69803923f, 0.6666667f);

	/**
	 * The color light sky blue with a hex value of #87CEFA.
	 */
	public static final Color LIGHTSKYBLUE = new Color(0.5294118f, 0.80784315f, 0.98039216f);

	/**
	 * The color light slate gray with a hex value of #778899.
	 */
	public static final Color LIGHTSLATEGRAY = new Color(0.46666667f, 0.53333336f, 0.6f);

	/**
	 * The color light slate grey with a hex value of #778899.
	 */
	public static final Color LIGHTSLATEGREY = LIGHTSLATEGRAY;

	/**
	 * The color light steel blue with a hex value of #B0C4DE.
	 */
	public static final Color LIGHTSTEELBLUE = new Color(0.6901961f, 0.76862746f, 0.87058824f);

	/**
	 * The color light yellow with a hex value of #FFFFE0.
	 */
	public static final Color LIGHTYELLOW = new Color(1.0f, 1.0f, 0.8784314f);

	/**
	 * The color lime with a hex value of #00FF00.
	 */
	public static final Color LIME = new Color(0.0f, 1.0f, 0.0f);

	/**
	 * The color lime green with a hex value of #32CD32.
	 */
	public static final Color LIMEGREEN = new Color(0.19607843f, 0.8039216f, 0.19607843f);

	/**
	 * The color linen with a hex value of #FAF0E6.
	 */
	public static final Color LINEN = new Color(0.98039216f, 0.9411765f, 0.9019608f);

	/**
	 * The color magenta with a hex value of #FF00FF.
	 */
	public static final Color MAGENTA = new Color(1.0f, 0.0f, 1.0f);

	/**
	 * The color maroon with a hex value of #800000.
	 */
	public static final Color MAROON = new Color(0.5019608f, 0.0f, 0.0f);

	/**
	 * The color medium aquamarine with a hex value of #66CDAA.
	 */
	public static final Color MEDIUMAQUAMARINE = new Color(0.4f, 0.8039216f, 0.6666667f);

	/**
	 * The color medium blue with a hex value of #0000CD.
	 */
	public static final Color MEDIUMBLUE = new Color(0.0f, 0.0f, 0.8039216f);

	/**
	 * The color medium orchid with a hex value of #BA55D3.
	 */
	public static final Color MEDIUMORCHID = new Color(0.7294118f, 0.33333334f, 0.827451f);

	/**
	 * The color medium purple with a hex value of #9370DB.
	 */
	public static final Color MEDIUMPURPLE = new Color(0.5764706f, 0.4392157f, 0.85882354f);

	/**
	 * The color medium sea green with a hex value of #3CB371.
	 */
	public static final Color MEDIUMSEAGREEN = new Color(0.23529412f, 0.7019608f, 0.44313726f);

	/**
	 * The color medium slate blue with a hex value of #7B68EE.
	 */
	public static final Color MEDIUMSLATEBLUE = new Color(0.48235294f, 0.40784314f, 0.93333334f);

	/**
	 * The color medium spring green with a hex value of #00FA9A.
	 */
	public static final Color MEDIUMSPRINGGREEN = new Color(0.0f, 0.98039216f, 0.6039216f);

	/**
	 * The color medium turquoise with a hex value of #48D1CC.
	 */
	public static final Color MEDIUMTURQUOISE = new Color(0.28235295f, 0.81960785f, 0.8f);

	/**
	 * The color medium violet red with a hex value of #C71585.
	 */
	public static final Color MEDIUMVIOLETRED = new Color(0.78039217f, 0.08235294f, 0.52156866f);

	/**
	 * The color midnight blue with a hex value of #191970.
	 */
	public static final Color MIDNIGHTBLUE = new Color(0.09803922f, 0.09803922f, 0.4392157f);

	/**
	 * The color mint cream with a hex value of #F5FFFA.
	 */
	public static final Color MINTCREAM = new Color(0.9607843f, 1.0f, 0.98039216f);

	/**
	 * The color misty rose with a hex value of #FFE4E1.
	 */
	public static final Color MISTYROSE = new Color(1.0f, 0.89411765f, 0.88235295f);

	/**
	 * The color moccasin with a hex value of #FFE4B5.
	 */
	public static final Color MOCCASIN = new Color(1.0f, 0.89411765f, 0.70980394f);

	/**
	 * The color navajo white with a hex value of #FFDEAD.
	 */
	public static final Color NAVAJOWHITE = new Color(1.0f, 0.87058824f, 0.6784314f);

	/**
	 * The color navy with a hex value of #000080.
	 */
	public static final Color NAVY = new Color(0.0f, 0.0f, 0.5019608f);

	/**
	 * The color old lace with a hex value of #FDF5E6.
	 */
	public static final Color OLDLACE = new Color(0.99215686f, 0.9607843f, 0.9019608f);

	/**
	 * The color olive with a hex value of #808000.
	 */
	public static final Color OLIVE = new Color(0.5019608f, 0.5019608f, 0.0f);

	/**
	 * The color olive drab with a hex value of #6B8E23.
	 */
	public static final Color OLIVEDRAB = new Color(0.41960785f, 0.5568628f, 0.13725491f);

	/**
	 * The color orange with a hex value of #FFA500.
	 */
	public static final Color ORANGE = new Color(1.0f, 0.64705884f, 0.0f);

	/**
	 * The color orange red with a hex value of #FF4500.
	 */
	public static final Color ORANGERED = new Color(1.0f, 0.27058825f, 0.0f);

	/**
	 * The color orchid with a hex value of #DA70D6.
	 */
	public static final Color ORCHID = new Color(0.85490197f, 0.4392157f, 0.8392157f);

	/**
	 * The color pale goldenrod with a hex value of #EEE8AA.
	 */
	public static final Color PALEGOLDENROD = new Color(0.93333334f, 0.9098039f, 0.6666667f);

	/**
	 * The color pale green with a hex value of #98FB98.
	 */
	public static final Color PALEGREEN = new Color(0.59607846f, 0.9843137f, 0.59607846f);

	/**
	 * The color pale turquoise with a hex value of #AFEEEE.
	 */
	public static final Color PALETURQUOISE = new Color(0.6862745f, 0.93333334f, 0.93333334f);

	/**
	 * The color pale violet red with a hex value of #DB7093.
	 */
	public static final Color PALEVIOLETRED = new Color(0.85882354f, 0.4392157f, 0.5764706f);

	/**
	 * The color papaya whip with a hex value of #FFEFD5.
	 */
	public static final Color PAPAYAWHIP = new Color(1.0f, 0.9372549f, 0.8352941f);

	/**
	 * The color peach puff with a hex value of #FFDAB9.
	 */
	public static final Color PEACHPUFF = new Color(1.0f, 0.85490197f, 0.7254902f);

	/**
	 * The color peru with a hex value of #CD853F.
	 */
	public static final Color PERU = new Color(0.8039216f, 0.52156866f, 0.24705882f);

	/**
	 * The color pink with a hex value of #FFC0CB.
	 */
	public static final Color PINK = new Color(1.0f, 0.7529412f, 0.79607844f);

	/**
	 * The color plum with a hex value of #DDA0DD.
	 */
	public static final Color PLUM = new Color(0.8666667f, 0.627451f, 0.8666667f);

	/**
	 * The color powder blue with a hex value of #B0E0E6.
	 */
	public static final Color POWDERBLUE = new Color(0.6901961f, 0.8784314f, 0.9019608f);

	/**
	 * The color purple with a hex value of #800080.
	 */
	public static final Color PURPLE = new Color(0.5019608f, 0.0f, 0.5019608f);

	/**
	 * The color red with a hex value of #FF0000.
	 */
	public static final Color RED = new Color(1.0f, 0.0f, 0.0f);

	/**
	 * The color rosy brown with a hex value of #BC8F8F.
	 */
	public static final Color ROSYBROWN = new Color(0.7372549f, 0.56078434f, 0.56078434f);

	/**
	 * The color royal blue with a hex value of #4169E1.
	 */
	public static final Color ROYALBLUE = new Color(0.25490198f, 0.4117647f, 0.88235295f);

	/**
	 * The color saddle brown with a hex value of #8B4513.
	 */
	public static final Color SADDLEBROWN = new Color(0.54509807f, 0.27058825f, 0.07450981f);

	/**
	 * The color salmon with a hex value of #FA8072.
	 */
	public static final Color SALMON = new Color(0.98039216f, 0.5019608f, 0.44705883f);

	/**
	 * The color sandy brown with a hex value of #F4A460.
	 */
	public static final Color SANDYBROWN = new Color(0.95686275f, 0.6431373f, 0.3764706f);

	/**
	 * The color sea green with a hex value of #2E8B57.
	 */
	public static final Color SEAGREEN = new Color(0.18039216f, 0.54509807f, 0.34117648f);

	/**
	 * The color sea shell with a hex value of #FFF5EE.
	 */
	public static final Color SEASHELL = new Color(1.0f, 0.9607843f, 0.93333334f);

	/**
	 * The color sienna with a hex value of #A0522D.
	 */
	public static final Color SIENNA = new Color(0.627451f, 0.32156864f, 0.1764706f);

	/**
	 * The color silver with a hex value of #C0C0C0.
	 */
	public static final Color SILVER = new Color(0.7529412f, 0.7529412f, 0.7529412f);

	/**
	 * The color sky blue with a hex value of #87CEEB.
	 */
	public static final Color SKYBLUE = new Color(0.5294118f, 0.80784315f, 0.92156863f);

	/**
	 * The color slate blue with a hex value of #6A5ACD.
	 */
	public static final Color SLATEBLUE = new Color(0.41568628f, 0.3529412f, 0.8039216f);

	/**
	 * The color slate gray with a hex value of #708090.
	 */
	public static final Color SLATEGRAY = new Color(0.4392157f, 0.5019608f, 0.5647059f);

	/**
	 * The color slate grey with a hex value of #708090.
	 */
	public static final Color SLATEGREY = SLATEGRAY;

	/**
	 * The color snow with a hex value of #FFFAFA.
	 */
	public static final Color SNOW = new Color(1.0f, 0.98039216f, 0.98039216f);

	/**
	 * The color spring green with a hex value of #00FF7F.
	 */
	public static final Color SPRINGGREEN = new Color(0.0f, 1.0f, 0.49803922f);

	/**
	 * The color steel blue with a hex value of #4682B4.
	 */
	public static final Color STEELBLUE = new Color(0.27450982f, 0.50980395f, 0.7058824f);

	/**
	 * The color tan with a hex value of #D2B48C.
	 */
	public static final Color TAN = new Color(0.8235294f, 0.7058824f, 0.54901963f);

	/**
	 * The color teal with a hex value of #008080.
	 */
	public static final Color TEAL = new Color(0.0f, 0.5019608f, 0.5019608f);

	/**
	 * The color thistle with a hex value of #D8BFD8.
	 */
	public static final Color THISTLE = new Color(0.84705883f, 0.7490196f, 0.84705883f);

	/**
	 * The color tomato with a hex value of #FF6347.
	 */
	public static final Color TOMATO = new Color(1.0f, 0.3882353f, 0.2784314f);

	/**
	 * The color turquoise with a hex value of #40E0D0.
	 */
	public static final Color TURQUOISE = new Color(0.2509804f, 0.8784314f, 0.8156863f);

	/**
	 * The color violet with a hex value of #EE82EE.
	 */
	public static final Color VIOLET = new Color(0.93333334f, 0.50980395f, 0.93333334f);

	/**
	 * The color wheat with a hex value of #F5DEB3.
	 */
	public static final Color WHEAT = new Color(0.9607843f, 0.87058824f, 0.7019608f);

	/**
	 * The color white with a hex value of #FFFFFF.
	 */
	public static final Color WHITE = new Color(1.0f, 1.0f, 1.0f);

	/**
	 * The color white smoke with a hex value of #F5F5F5.
	 */
	public static final Color WHITESMOKE = new Color(0.9607843f, 0.9607843f, 0.9607843f);

	/**
	 * The color yellow with a hex value of #FFFF00.
	 */
	public static final Color YELLOW = new Color(1.0f, 1.0f, 0.0f);

	/**
	 * The color yellow green with a hex value of #9ACD32.
	 */
	public static final Color YELLOWGREEN = new Color(0.6039216f, 0.8039216f, 0.19607843f);

	private static final class ColorMap {
		private static final HashMap<String, Color> colors = initColors();
		
		private static Color get(String name) {
			Color color = colors.get(name);
			if (color == null)
				throw new IllegalArgumentException("Invalid html standard color name (" + name + ").");
			return color;
		}

		private static HashMap<String, Color> initColors() {
			HashMap<String, Color> colors = new HashMap<String, Color>(256);

			colors.put("aliceblue", ALICEBLUE);
			colors.put("antiquewhite", ANTIQUEWHITE);
			colors.put("aqua", AQUA);
			colors.put("aquamarine", AQUAMARINE);
			colors.put("azure", AZURE);
			colors.put("beige", BEIGE);
			colors.put("bisque", BISQUE);
			colors.put("black", BLACK);
			colors.put("blanchedalmond", BLANCHEDALMOND);
			colors.put("blue", BLUE);
			colors.put("blueviolet", BLUEVIOLET);
			colors.put("brown", BROWN);
			colors.put("burlywood", BURLYWOOD);
			colors.put("cadetblue", CADETBLUE);
			colors.put("chartreuse", CHARTREUSE);
			colors.put("chocolate", CHOCOLATE);
			colors.put("coral", CORAL);
			colors.put("cornflowerblue", CORNFLOWERBLUE);
			colors.put("cornsilk", CORNSILK);
			colors.put("crimson", CRIMSON);
			colors.put("cyan", CYAN);
			colors.put("darkblue", DARKBLUE);
			colors.put("darkcyan", DARKCYAN);
			colors.put("darkgoldenrod", DARKGOLDENROD);
			colors.put("darkgray", DARKGRAY);
			colors.put("darkgreen", DARKGREEN);
			colors.put("darkgrey", DARKGREY);
			colors.put("darkkhaki", DARKKHAKI);
			colors.put("darkmagenta", DARKMAGENTA);
			colors.put("darkolivegreen", DARKOLIVEGREEN);
			colors.put("darkorange", DARKORANGE);
			colors.put("darkorchid", DARKORCHID);
			colors.put("darkred", DARKRED);
			colors.put("darksalmon", DARKSALMON);
			colors.put("darkseagreen", DARKSEAGREEN);
			colors.put("darkslateblue", DARKSLATEBLUE);
			colors.put("darkslategray", DARKSLATEGRAY);
			colors.put("darkslategrey", DARKSLATEGREY);
			colors.put("darkturquoise", DARKTURQUOISE);
			colors.put("darkviolet", DARKVIOLET);
			colors.put("deeppink", DEEPPINK);
			colors.put("deepskyblue", DEEPSKYBLUE);
			colors.put("dimgray", DIMGRAY);
			colors.put("dimgrey", DIMGREY);
			colors.put("dodgerblue", DODGERBLUE);
			colors.put("firebrick", FIREBRICK);
			colors.put("floralwhite", FLORALWHITE);
			colors.put("forestgreen", FORESTGREEN);
			colors.put("fuchsia", FUCHSIA);
			colors.put("gainsboro", GAINSBORO);
			colors.put("ghostwhite", GHOSTWHITE);
			colors.put("gold", GOLD);
			colors.put("goldenrod", GOLDENROD);
			colors.put("gray", GRAY);
			colors.put("green", GREEN);
			colors.put("greenyellow", GREENYELLOW);
			colors.put("grey", GREY);
			colors.put("honeydew", HONEYDEW);
			colors.put("hotpink", HOTPINK);
			colors.put("indianred", INDIANRED);
			colors.put("indigo", INDIGO);
			colors.put("ivory", IVORY);
			colors.put("khaki", KHAKI);
			colors.put("lavender", LAVENDER);
			colors.put("lavenderblush", LAVENDERBLUSH);
			colors.put("lawngreen", LAWNGREEN);
			colors.put("lemonchiffon", LEMONCHIFFON);
			colors.put("lightblue", LIGHTBLUE);
			colors.put("lightcoral", LIGHTCORAL);
			colors.put("lightcyan", LIGHTCYAN);
			colors.put("lightgoldenrodyellow", LIGHTGOLDENRODYELLOW);
			colors.put("lightgray", LIGHTGRAY);
			colors.put("lightgreen", LIGHTGREEN);
			colors.put("lightgrey", LIGHTGREY);
			colors.put("lightpink", LIGHTPINK);
			colors.put("lightsalmon", LIGHTSALMON);
			colors.put("lightseagreen", LIGHTSEAGREEN);
			colors.put("lightskyblue", LIGHTSKYBLUE);
			colors.put("lightslategray", LIGHTSLATEGRAY);
			colors.put("lightslategrey", LIGHTSLATEGREY);
			colors.put("lightsteelblue", LIGHTSTEELBLUE);
			colors.put("lightyellow", LIGHTYELLOW);
			colors.put("lime", LIME);
			colors.put("limegreen", LIMEGREEN);
			colors.put("linen", LINEN);
			colors.put("magenta", MAGENTA);
			colors.put("maroon", MAROON);
			colors.put("mediumaquamarine", MEDIUMAQUAMARINE);
			colors.put("mediumblue", MEDIUMBLUE);
			colors.put("mediumorchid", MEDIUMORCHID);
			colors.put("mediumpurple", MEDIUMPURPLE);
			colors.put("mediumseagreen", MEDIUMSEAGREEN);
			colors.put("mediumslateblue", MEDIUMSLATEBLUE);
			colors.put("mediumspringgreen", MEDIUMSPRINGGREEN);
			colors.put("mediumturquoise", MEDIUMTURQUOISE);
			colors.put("mediumvioletred", MEDIUMVIOLETRED);
			colors.put("midnightblue", MIDNIGHTBLUE);
			colors.put("mintcream", MINTCREAM);
			colors.put("mistyrose", MISTYROSE);
			colors.put("moccasin", MOCCASIN);
			colors.put("navajowhite", NAVAJOWHITE);
			colors.put("navy", NAVY);
			colors.put("oldlace", OLDLACE);
			colors.put("olive", OLIVE);
			colors.put("olivedrab", OLIVEDRAB);
			colors.put("orange", ORANGE);
			colors.put("orangered", ORANGERED);
			colors.put("orchid", ORCHID);
			colors.put("palegoldenrod", PALEGOLDENROD);
			colors.put("palegreen", PALEGREEN);
			colors.put("paleturquoise", PALETURQUOISE);
			colors.put("palevioletred", PALEVIOLETRED);
			colors.put("papayawhip", PAPAYAWHIP);
			colors.put("peachpuff", PEACHPUFF);
			colors.put("peru", PERU);
			colors.put("pink", PINK);
			colors.put("plum", PLUM);
			colors.put("powderblue", POWDERBLUE);
			colors.put("purple", PURPLE);
			colors.put("red", RED);
			colors.put("rosybrown", ROSYBROWN);
			colors.put("royalblue", ROYALBLUE);
			colors.put("saddlebrown", SADDLEBROWN);
			colors.put("salmon", SALMON);
			colors.put("sandybrown", SANDYBROWN);
			colors.put("seagreen", SEAGREEN);
			colors.put("seashell", SEASHELL);
			colors.put("sienna", SIENNA);
			colors.put("silver", SILVER);
			colors.put("skyblue", SKYBLUE);
			colors.put("slateblue", SLATEBLUE);
			colors.put("slategray", SLATEGRAY);
			colors.put("slategrey", SLATEGREY);
			colors.put("snow", SNOW);
			colors.put("springgreen", SPRINGGREEN);
			colors.put("steelblue", STEELBLUE);
			colors.put("tan", TAN);
			colors.put("teal", TEAL);
			colors.put("thistle", THISTLE);
			colors.put("tomato", TOMATO);
			colors.put("transparent", TRANSPARENT);
			colors.put("turquoise", TURQUOISE);
			colors.put("violet", VIOLET);
			colors.put("wheat", WHEAT);
			colors.put("white", WHITE);
			colors.put("whitesmoke", WHITESMOKE);
			colors.put("yellow", YELLOW);
			colors.put("yellowgreen", YELLOWGREEN);

			return colors;
		}

	}
}
