package universe.math;

import java.nio.FloatBuffer;

import universe.util.BufferUtils;

public final class Vector2 {

	/**
	 * The first entry in the two dimensional vector
	 */
	public float x;

	/**
	 * The second entry in the two dimensional vector
	 */
	public float y;
	
	/**
	 * Default Constructor.
	 */
	public Vector2() {
		this(0, 0);
	}
	
	/**
	 * Constructor.
	 * @param x the value of the first entry of the vector
	 * @param y the value of the second entry of the vector
	 */
	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Vector2 by Vector2 addition operation.<br>
	 * <b>Operation description:</b><br>
	 * <code>returnVector = thisVector + parameterVector;</code>
	 * @param vec the vector to add this to
	 * @return the new vector containing the addition of the two vectors
	 */
	public Vector2 add(Vector2 vec) {
		return new Vector2(x + vec.x, y + vec.y);
	}
	
	/**
	 * Vector2 by Vector2 subtraction operation.<br>
	 * <b>Operation description:</b><br>
	 * <code>returnVector = thisVector - parameterVector;</code>
	 * @param vec the vector to subtract this to
	 * @return the new vector containing the subtraction of the two vectors
	 */
	public Vector2 sub(Vector2 vec) {
		return new Vector2(x - vec.x, y - vec.y);
	}
	
	/**
	 * Vector2 by Vector2 dot product operation.<br>
	 * <b>Operation description:</b><br>
	 * <code>returnVector = thisVector . parameterVector;</code>
	 * @param vec the vector to multiply this to
	 * @return the resulting value of the dot product between the two vectors.
	 */
	public float dot(Vector2 vec) {
		return x * vec.x + y * vec.y;
	}
	
	/**
	 * Vector2 by Scalar scaling (or dilation) operation.<br>
	 * <b>Operation description:</b><br>
	 * <code>returnValue = thisVector * parameterScalar;</code>
	 * @param scalar the scaling (or dilation) amount
	 * @return the new scaled vector
	 */
	public Vector2 scale(float scalar) {
		return new Vector2(x * scalar, y * scalar);
	}
	
	/**
	 * Get the normalized (unit or direction) vector.
	 * @return the new vector containing the normalized vector.
	 */
	public Vector2 normalize() {
		float len = magnitude();
		return new Vector2(x / len, y / len);
	}
	
	/**
	 * Get the magnitude (or length) of the vector.
	 * @return the magnitude of the vector
	 */
	public float magnitude() {
		return (float) Math.sqrt(x * x + y * y);
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
		return x * x + y * y;
	}
	
	/**
	 * Get the distance between this and the provided vector.
	 * @param vec the vector used to check the distance with
	 * @return the distance between the two vectors
	 */
	public float distance(Vector2 vec) {
		float sx = x - vec.x;
		float sy = y - vec.y;
		
		return (float) Math.sqrt(sx * sx + sy * sy);
	}

	/**
	 * Get the <b>squared</b> distance between this and the provided vector.
	 * Faster calculation than {@link #distance(Vector2) distance(Vector2)} since the square root
	 * is not calculated. The squared distance can be used when comparing.<br>
	 * <b>Note:</b> do not use this method for calculating the actual distance since it is inaccurate.
	 * Use the regular {@link #distance(Vector2) distance(Vector2)} method instead.
	 * @param vec the vector used to check the distance with
	 * @return the squared distance between the two vectors
	 */
	public float distanceSqr(Vector2 vec) {
		float sx = x - vec.x;
		float sy = y - vec.y;
		
		return sx * sx + sy * sy;
	}
	
	public FloatBuffer toFloatBuffer() {
		FloatBuffer result = BufferUtils.createFloatBuffer(x, y);
		return result;
	}
}
