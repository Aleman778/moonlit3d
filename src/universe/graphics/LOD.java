package universe.graphics;

public class LOD {

	private float bias;
	private float max, min;
	private int maxLevels;
	private int baseLevel;
	
	/**
	 * Default Constructor
	 */
	public LOD() {
		this(0, 1, -1, 1, 0);
	}
	
	public LOD(float bias, float max, float min, int maxLevel, int baseLevel) {
		this.bias = bias;
		this.max = max;
		this.min = min;
		this.maxLevels = maxLevel;
		this.baseLevel = baseLevel;
	}
	
	public void setBias(float bias) {
		this.bias = bias;
	}
	
	public float getBias() {
		return bias;
	}
	
	public void setMax(float max) {
		this.max = max;
	}
	
	public float getMax() {
		return max;
	}
	
	public void setMin(float min) {
		this.min = min;
	}
	
	public float getMin() {
		return min;
	}
	
	public void setMaxLevel(int maxLevels) {
		this.maxLevels = maxLevels;
	}
	
	public int getMaxLevels() {
		return maxLevels;
	}
	
	public void setBaseLevel(int baseLevel) {
		this.baseLevel = baseLevel;
	}
	
	public int getBaseLevel() {
		return baseLevel;
	}
}
