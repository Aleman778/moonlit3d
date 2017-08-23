package universe.math;

import java.nio.FloatBuffer;

import universe.util.BufferUtils;

public final class Vector3 {

	public static final Vector3 ZERO = new Vector3();
	public static final Vector3 UP = new Vector3(0, 1, 0);
	

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
	 * Constructor used to create a new copy of the provided vector.
	 * @param copy the vector to copy from
	 */
	public Vector3(Vector3 copy) {
		this.x = copy.x;
		this.y = copy.y;
		this.z = copy.z;
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
	public Vector3 mul(float scalar) {
		return new Vector3(x * scalar, y * scalar, z * scalar);
	}
	
	/**
	 * Get the normalized (unit or direction) vector.
	 * @return the new vector containing the normalized vector.
	 */
	public Vector3 normal() {
		float len = magnitude();
		return new Vector3(x / len, y / len, z / len);
	}
	
	/**
	 * Get the magnitude (or length) of the vector.
	 * @return the magnitude of the vector
	 */
	public float magnitude() {
		return (float) Math.sqrt(x * x + y * y + z * z);
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
		return x * x + y * y + z * z;
	}
	
	/**
	 * Get the distance between this and the provided vector.
	 * @param vec the vector used to check the distance with
	 * @return the distance between the two vectors
	 */
	public float distance(Vector3 vec) {
		float sx = x - vec.x;
		float sy = y - vec.y;
		float sz = z - vec.z;
		
		return (float) Math.sqrt(sx * sx + sy * sy + sz * sz);
	}

	/**
	 * Get the <b>squared</b> distance between this and the provided vector.
	 * Faster calculation than {@link #distance(Vector3) distance(Vector3)} since the square root
	 * is not calculated. The squared distance can be used when comparing.<br>
	 * <b>Note:</b> do not use this method for calculating the actual distance since it is inaccurate.
	 * Use the regular {@link #distance(Vector3) distance(Vector3)} method instead.
	 * @param vec the vector used to check the distance with
	 * @return the squared distance between the two vectors
	 */
	public float distanceSqr(Vector3 vec) {
		float sx = x - vec.x;
		float sy = y - vec.y;
		float sz = z - vec.z;
		
		return sx * sx + sy * sy + sz * sz;
	}
	
	/**
	 * Convert the vector to a float buffer
	 * @return the new float buffer containing the data in order
	 * @see java.nio.FloatBuffer
	 */
	public FloatBuffer toFloatBuffer() {
		FloatBuffer result = BufferUtils.createFloatBuffer(x, y, z);
		return result;
	}
}
