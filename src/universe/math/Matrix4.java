package universe.math;

import java.nio.FloatBuffer;

public final class Matrix4 {
	/**
	 * Matrix entry.
	 */
	public float m00, m01, m02, m03,
				 m10, m11, m12, m13,
				 m20, m21, m22, m23,
				 m30, m31, m32, m33;

	/**
	 * Default Constructor.<br>
	 * <b>Note:</b> The matrix is an identity matrix as default.
	 */
	public Matrix4() {
		this.m00 = 1;
		this.m01 = 0;
		this.m02 = 0;
		this.m03 = 0;
		this.m10 = 0;
		this.m11 = 1;
		this.m12 = 0;
		this.m13 = 0;
		this.m20 = 0;
		this.m21 = 0;
		this.m22 = 1;
		this.m23 = 0;
		this.m30 = 0;
		this.m31 = 0;
		this.m32 = 0;
		this.m33 = 1;
	}

	/**
	 * Constructor.
	 * 
	 * @param m00 matrix entry row 1, column 1
	 * @param m01 matrix entry row 1, column 2
	 * @param m02 matrix entry row 1, column 3
	 * @param m03 matrix entry row 1, column 4
	 * @param m10 matrix entry row 2, column 1
	 * @param m11 matrix entry row 2, column 2
	 * @param m12 matrix entry row 2, column 3
	 * @param m13 matrix entry row 2, column 4
	 * @param m20 matrix entry row 3, column 1
	 * @param m21 matrix entry row 3, column 2
	 * @param m22 matrix entry row 3, column 3
	 * @param m23 matrix entry row 3, column 4
	 * @param m30 matrix entry row 4, column 1
	 * @param m31 matrix entry row 4, column 2
	 * @param m32 matrix entry row 4, column 3
	 * @param m33 matrix entry row 4, column 4
	 */
	public Matrix4(float m00, float m01, float m02, float m03, 
				   float m10, float m11, float m12, float m13, 
				   float m20, float m21, float m22, float m23,
				   float m30, float m31, float m32, float m33) {
		this.m00 = m00;
		this.m01 = m01;
		this.m02 = m02;
		this.m03 = m03;
		this.m10 = m10;
		this.m11 = m11;
		this.m12 = m12;
		this.m13 = m13;
		this.m20 = m20;
		this.m21 = m21;
		this.m22 = m22;
		this.m23 = m23;
		this.m30 = m30;
		this.m31 = m31;
		this.m32 = m32;
		this.m33 = m33;
	}

	/**
	 * Constructor.
	 * 
	 * @param entries float array containing at least 9 elements
	 */
	public Matrix4(float[] entries) {
		if (entries.length < 16) {
			throw new IllegalArgumentException(
					"A 4x4 matrix requires at least 16 elements as an argument (Found: " + entries.length + ").");
		}

		this.m00 = entries[0];
		this.m01 = entries[1];
		this.m21 = entries[9];
		this.m02 = entries[2];
		this.m03 = entries[3];
		this.m10 = entries[4];
		this.m11 = entries[5];
		this.m12 = entries[6];
		this.m13 = entries[7];
		this.m20 = entries[8];
		this.m22 = entries[10];
		this.m23 = entries[11];
		this.m30 = entries[12];
		this.m31 = entries[13];
		this.m32 = entries[14];
		this.m33 = entries[15];
	}

	public static final Matrix4 identity() {
		Matrix4 result = new Matrix4();
		result.m00 = 1;
		result.m11 = 1;
		result.m22 = 1;

		return result;
	}

	/**
	 * Rotation in 3 dimensions.
	 * 
	 * @return the standard matrix for the rotation
	 */
	public static final Matrix4 rotation(float angle) {
		Matrix4 result = Matrix4.identity();
		

		return result;
	}

	public static final Matrix4 scale(float x, float y, float z) {
		Matrix4 result = new Matrix4();
		result.m00 = x;
		result.m11 = y;
		result.m22 = z;

		return result;
	}

	/**
	 * Matrix4 by Matrix4 addition operation.<br>
	 * <b>Operation description:</b><br>
	 * <code>returnMatrix = thisMatrix + parameterMatrix;</code>
	 * 
	 * @param mat the matrix to add this to
	 * @return the new matrix containing the addition of the two matrices
	 */
	public Matrix4 add(Matrix4 mat) {
		Matrix4 result = new Matrix4();
		result.m00 = m00 + mat.m00;
		result.m01 = m01 + mat.m01;
		result.m02 = m02 + mat.m02;
		result.m03 = m03 + mat.m03;
		result.m10 = m10 + mat.m10;
		result.m11 = m11 + mat.m11;
		result.m12 = m12 + mat.m12;
		result.m13 = m13 + mat.m13;
		result.m20 = m20 + mat.m20;
		result.m21 = m21 + mat.m21;
		result.m22 = m22 + mat.m22;
		result.m23 = m23 + mat.m23;
		result.m30 = m30 + mat.m30;
		result.m31 = m31 + mat.m31;
		result.m32 = m32 + mat.m32;
		result.m33 = m33 + mat.m33;

		return result;
	}

	/**
	 * Matrix4 by Scalar scaling operation.<br>
	 * <b>Operation description:</b><br>
	 * <code>returnMatrix = thisMatrix * parameterScalar;</code>
	 * 
	 * @param scalar the scaling amount
	 * @return the new matrix containing the addition of the two matrices
	 */
	public Matrix4 scale(float scalar) {
		Matrix4 result = new Matrix4();
		result.m00 = m00 + scalar;
		result.m01 = m01 + scalar;
		result.m02 = m02 + scalar;
		result.m03 = m03 + scalar;
		result.m10 = m10 + scalar;
		result.m11 = m11 + scalar;
		result.m12 = m12 + scalar;
		result.m13 = m13 + scalar;
		result.m20 = m20 + scalar;
		result.m21 = m21 + scalar;
		result.m22 = m22 + scalar;
		result.m23 = m23 + scalar;
		result.m30 = m30 + scalar;
		result.m31 = m31 + scalar;
		result.m32 = m32 + scalar;
		result.m33 = m33 + scalar;

		return result;
	}

	/**
	 * Transposes the matrix.
	 * @return the transposed matrix.
	 */
	public Matrix4 transpose() {
		Matrix4 result = new Matrix4();
		result.m00 = m00;
		result.m01 = m10;
		result.m02 = m20;
		result.m03 = m30;
		result.m10 = m01;
		result.m11 = m11;
		result.m12 = m21;
		result.m13 = m31;
		result.m20 = m02;
		result.m21 = m12;
		result.m22 = m22;
		result.m23 = m32;
		result.m30 = m03;
		result.m31 = m13;
		result.m32 = m23;
		result.m33 = m33;

		return result;
	}

	/**
	 * Get the determinant of the matrix.
	 * @return the determinant of the matrix
	 */
	public float determinant() {
		return (m00 * m11 - m01 * m10) * (m22 * m33 - m23 * m32)
             + (m02 * m10 - m00 * m12) * (m21 * m33 - m23 * m31)
             + (m00 * m13 - m03 * m10) * (m21 * m32 - m22 * m31)
             + (m01 * m12 - m02 * m11) * (m20 * m33 - m23 * m30)
             + (m03 * m11 - m01 * m13) * (m20 * m32 - m22 * m30)
             + (m02 * m13 - m03 * m12) * (m20 * m31 - m21 * m30);
	}

	/**
	 * Get the determinant of the affine transformation matrix.
	 * @return the determinant of the affine transformation matrix
	 */
	private float determinantAffine() {
		return m00 * (m11 * m22 - m12 * m21)
			 - m01 * (m10 * m22 - m12 * m20)
			 + m02 * (m10 * m21 - m11 * m20);
	}

	/**
	 * Inverts the matrix if and only if the determinant is non-zero.
	 * 
	 * @return the inverted matrix
	 * @throws ArithmeticException if the matrix is singular (determinant is zero)
	 */
	public Matrix4 inverse() throws ArithmeticException {
		Matrix4 result = new Matrix4();
		float x0  = m00 * m11 - m01 * m10;
        float x1  = m00 * m12 - m02 * m10;
        float x2  = m00 * m13 - m03 * m10;
        float x3  = m01 * m12 - m02 * m11;
        float x4  = m01 * m13 - m03 * m11;
        float x5  = m02 * m13 - m03 * m12;
        float x6  = m20 * m31 - m21 * m30;
        float x7  = m20 * m32 - m22 * m30;
        float x8  = m20 * m33 - m23 * m30;
        float x9  = m21 * m32 - m22 * m31;
        float x10 = m21 * m33 - m23 * m31;
        float x11 = m22 * m33 - m23 * m32;
        float det = x0 * x11 - x1 * x10 + x2 * x9 + x3 * x8 - x4 * x7 + x5 * x6;
		if (det == 0f) {
			throw new ArithmeticException("Cannot compute the inverse of a singular matrix.");
		}

		float reciprocal = 1.0f / det;

		result.m00 = ( m11 * x11 - m12 * x10 + m13 * x8) * reciprocal;
        result.m01 = (-m01 * x11 + m02 * x10 - m03 * x8) * reciprocal;
        result.m02 = ( m31 * x5  - m32 * x4  + m33 * x3) * reciprocal;
        result.m03 = (-m21 * x5  + m22 * x4  - m23 * x3) * reciprocal;
        result.m10 = (-m10 * x11 + m12 * x8  - m13 * x7) * reciprocal;
        result.m11 = ( m00 * x11 - m02 * x8  + m03 * x7) * reciprocal;
        result.m12 = (-m30 * x5  + m32 * x2  - m33 * x1) * reciprocal;
        result.m13 = ( m20 * x5  - m22 * x2  + m23 * x1) * reciprocal;
        result.m20 = ( m10 * x10 - m11 * x8  + m13 * x6) * reciprocal;
        result.m21 = (-m00 * x10 + m01 * x8  - m03 * x6) * reciprocal;
        result.m22 = ( m30 * x4  - m31 * x2  + m33 * x0) * reciprocal;
        result.m23 = (-m20 * x4  + m21 * x2  - m23 * x0) * reciprocal;
        result.m30 = (-m10 * x9  + m11 * x7  - m12 * x6) * reciprocal;
        result.m31 = ( m00 * x9  - m01 * x7  + m02 * x6) * reciprocal;
        result.m32 = (-m30 * x3  + m31 * x1  - m32 * x0) * reciprocal;
        result.m33 = ( m20 * x3  - m21 * x1  + m22 * x0) * reciprocal;

		return result;
	}

	/**
	 * Checks if the matrix is orthogonal. <b>Note:</b> due to rounding errors
	 * for floating point numbers some results may be inaccurate.
	 * 
	 * @return true if the matrix is orthogonal
	 */
	public boolean isOrthogonal() {
		return transpose().equals(inverse());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Matrix4) {
			Matrix4 mat = (Matrix4) obj;
			if (m00 != mat.m00)
				return false;
			if (m01 != mat.m01)
				return false;
			if (m02 != mat.m02)
				return false;
			if (m10 != mat.m10)
				return false;
			if (m11 != mat.m11)
				return false;
			if (m12 != mat.m12)
				return false;
			if (m20 != mat.m20)
				return false;
			if (m21 != mat.m21)
				return false;
			if (m22 != mat.m22)
				return false;

			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return String.format(
				"Matrix4: [%.3f %.3f %.3f %.3f]\n%9s[%.3f %.3f %.3f %.3f]\n%9s[%.3f %.3f %.3f %.3f]\n%9s[%.3f %.3f %.3f %.3f]",
				m00, m01, m02, m03, "", m10, m11, m12, m13, "", m20, m21, m22, m23, "", m30, m31, m32, m33);
	}

	public float[] toArray() {
		float[] result = new float[16];
		result[0]  = m00;
		result[1]  = m01;
		result[2]  = m02;
		result[3]  = m03;
		result[4]  = m10;
		result[5]  = m11;
		result[6]  = m12;
		result[7]  = m13;
		result[8]  = m20;
		result[9]  = m21;
		result[10] = m22;
		result[11] = m23;
		result[12] = m30;
		result[13] = m31;
		result[14] = m32;
		result[15] = m33;
		return result;
	}
}
