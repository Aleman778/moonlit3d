package universe.math;

import java.nio.FloatBuffer;

import universe.util.BufferUtils;

public final class Vector4 {

	/**
	 * The first entry in the two dimensional vector
	 */
	public float x;

	/**
	 * The second entry in the two dimensional vector
	 */
	public float y;

	/**
	 * The third entry in the two dimensional vector
	 */
	public float z;
	
	/**
	 * The fourth entry in the two dimensional vector
	 */
	public float w;
	
	/**
	 * Default Constructor.
	 */
	public Vector4() {
		this(0, 0, 0, 0);
	}
	
	/**
	 * Constructor.
	 * @param x the value of the first entry of the vector
	 * @param y the value of the second entry of the vector
	 * @param y the value of the third entry of the vector
	 */
	public Vector4(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	/**
	 * Vector4 by Vector4 addition operation.<br>
	 * <b>Operation description:</b><br>
	 * <code>returnVector = thisVector + parameterVector;</code>
	 * @param vec the vector to add this to
	 * @return the new vector containing the addition of the two vectors
	 */
	public Vector4 add(Vector4 vec) {
		return new Vector4(x + vec.x, y + vec.y, z + vec.z, w + vec.w);
	}
	
	/**
	 * Vector4 by Vector4 subtraction operation.<br>
	 * <b>Operation description:</b><br>
	 * <code>returnVector = thisVector - parameterVector;</code>
	 * @param vec the vector to subtract this to
	 * @return the new vector containing the subtraction of the two vectors
	 */
	public Vector4 sub(Vector4 vec) {
		return new Vector4(x - vec.x, y - vec.y, z - vec.z, w - vec.w);
	}
	
	/**
	 * Vector4 by Vector4 dot product operation.<br>
	 * <b>Operation description:</b><br>
	 * <code>returnValue = thisVector . parameterVector;</code>
	 * @param vec the vector to multiply this to
	 * @return the resulting value of the dot product between the two vectors.
	 */
	public float dot(Vector4 vec) {
		return x * vec.x + y * vec.y + z * vec.z + w * vec.w;
	}
	
	/**
	 * Vector4 by Scalar scaling (or dilation) operation.<br>
	 * <b>Operation description:</b><br>
	 * <code>returnVector = thisVector * parameterScalar;</code>
	 * @param scalar the scaling (or dilation) amount
	 * @return the new scaled vector
	 */
	public Vector4 scale(float scalar) {
		return new Vector4(x * scalar, y * scalar, z * scalar, w * scalar);
	}
	
	public FloatBuffer toFloatBuffer() {
		FloatBuffer result = BufferUtils.createFloatBuffer(x, y, z, w);
		return result;
	}
}
