package universe.math;

import java.nio.FloatBuffer;

import universe.util.BufferUtils;

public final class Vector3 {

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
	 * Default Constructor.
	 */
	public Vector3() {
		this(0, 0, 0);
	}
	
	/**
	 * Constructor.
	 * @param x the value of the first entry of the vector
	 * @param y the value of the second entry of the vector
	 * @param y the value of the third entry of the vector
	 */
	public Vector3(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Vector3 by Vector3 addition operation.<br>
	 * <b>Operation description:</b><br>
	 * <code>returnVector = thisVector + parameterVector;</code>
	 * @param vec the vector to add this to
	 * @return the new vector containing the addition of the two vectors
	 */
	public Vector3 add(Vector3 vec) {
		return new Vector3(x + vec.x, y + vec.y, z + vec.z);
	}
	
	/**
	 * Vector3 by Vector3 subtraction operation.<br>
	 * <b>Operation description:</b><br>
	 * <code>returnVector = thisVector - parameterVector;</code>
	 * @param vec the vector to subtract this to
	 * @return the new vector containing the subtraction of the two vectors
	 */
	public Vector3 sub(Vector3 vec) {
		return new Vector3(x - vec.x, y - vec.y, z - vec.z);
	}
	
	/**
	 * Vector3 by Vector3 dot product operation.<br>
	 * <b>Operation description:</b><br>
	 * <code>returnValue = thisVector . parameterVector;</code>
	 * @param vec the vector to multiply this to
	 * @return the resulting value of the dot product between the two vectors.
	 */
	public float dot(Vector3 vec) {
		return x * vec.x + y * vec.y + z * vec.z;
	}
	
	/**
	 * Vector3 by Vector3 cross product operation.<br>
	 * <b>Operation description:</b><br>
	 * <code>returnVector = thisVector X parameterVector;</code>
	 * @param vec the vector to multiply this to
	 * @return the new vector containing the cross product between the two vectors.
	 */
	public Vector3 cross(Vector3 vec) {
		return new Vector3(y * vec.z - z * vec.y, z * vec.x - x * vec.z, x * vec.y - y * vec.x);
	}
	
	/**
	 * Vector3 by Scalar scaling (or dilation) operation.<br>
	 * <b>Operation description:</b><br>
	 * <code>returnVector = thisVector * parameterScalar;</code>
	 * @param scalar the scaling (or dilation) amount
	 * @return the new scaled vector
	 */
	public Vector3 scale(float scalar) {
		return new Vector3(x * scalar, y * scalar, z * scalar);
	}
	
	public FloatBuffer toFloatBuffer() {
		FloatBuffer result = BufferUtils.createFloatBuffer(x, y, z);
		return result;
	}
}
