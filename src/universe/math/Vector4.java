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
	 * Constructor used to create a new copy of the provided vector.
	 * @param copy the vector to copy from
	 */
	public Vector4(Vector4 copy) {
		this.x = copy.x;
		this.y = copy.y;
		this.z = copy.z;
		this.w = copy.w;
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
	public Vector4 mul(float scalar) {
		return new Vector4(x * scalar, y * scalar, z * scalar, w * scalar);
	}
	
	/**
	 * Get the normalized (unit or direction) vector.
	 * @return the new vector containing the normalized vector.
	 */
	public Vector4 normal() {
		float len = magnitude();
		return new Vector4(x / len, y / len, z / len, w / len);
	}
	
	/**
	 * Get the magnitude (or length) of the vector.
	 * @return the magnitude of the vector
	 */
	public float magnitude() {
		return (float) Math.sqrt(x * x + y * y + z * z + w * w);
	}

	/**
	 * Get the <b>squared</b> magnitude (or length) squared of the vector.<br>
	 * Faster calculation than {@link #magnitude() magnitude()} since the square root
	 * is not calculated. The squared magnitude can be used when comparing.<br>
	 * <b>Note:</b> do not use this method for calculating the actual magnitude of this vector
	 * since it is inaccurate. Use the regular {@link #magnitude() magnitude()} method instead.
	 * @return the squared magnitude of the vector
	 */
	public float magnitudeSqr() {
		return x * x + y * y + z * z + w * w;
	}
	
	/**
	 * Get the distance between this and the provided vector.
	 * @param vec the vector used to check the distance with
	 * @return the distance between the two vectors
	 */
	public float distance(Vector4 vec) {
		float sx = x - vec.x;
		float sy = y - vec.y;
		float sz = z - vec.z;
		float sw = w - vec.w;
		
		return (float) Math.sqrt(sx * sx + sy * sy + sz * sz + sw * sw);
	}

	/**
	 * Get the <b>squared</b> distance between this and the provided vector.
	 * Faster calculation than {@link #distance(Vector4) distance(Vector4)} since the square root
	 * is not calculated. The squared distance can be used when comparing.<br>
	 * <b>Note:</b> do not use this method for calculating the actual distance since it is inaccurate.
	 * Use the regular {@link #distance(Vector4) distance(Vector4)} method instead.
	 * @param vec the vector used to check the distance with
	 * @return the squared distance between the two vectors
	 */
	public float distanceSqr(Vector4 vec) {
		float sx = x - vec.x;
		float sy = y - vec.y;
		float sz = z - vec.z;
		float sw = w - vec.w;
		
		return sx * sx + sy * sy + sz * sz + sw * sw;
	}
	
	/**
	 * Convert the vector to a float buffer
	 * @return the new float buffer containing the data in order
	 * @see java.nio.FloatBuffer
	 */
	public FloatBuffer toFloatBuffer() {
		FloatBuffer result = BufferUtils.createFloatBuffer(x, y, z, w);
		return result;
	}
}
