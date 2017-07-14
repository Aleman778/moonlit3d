package universe.math;

import java.nio.FloatBuffer;

import universe.util.BufferUtils;

public final class Matrix3 {

	/**
	 * Matrix entry.
	 */
	public float m00, m01, m02,
				 m10, m11, m12,
				 m20, m21, m22;
	
	/**
	 * Default Constructor.<br>
	 * <b>Note:</b> The matrix is an identity matrix as default.
	 */
	public Matrix3() {
		m00 = 1;
		m01 = 0;
		m02 = 0;
		m10 = 0;
		m11 = 1;
		m12 = 0;
		m20 = 0;
		m21 = 0;
		m22 = 1;
	}
	
	/**
	 * Constructor.
	 * @param m00 matrix entry row 1, column 1
	 * @param m01 matrix entry row 1, column 2
	 * @param m02 matrix entry row 1, column 3
	 * @param m10 matrix entry row 2, column 1
	 * @param m11 matrix entry row 2, column 2
	 * @param m12 matrix entry row 2, column 3
	 * @param m20 matrix entry row 3, column 1
	 * @param m21 matrix entry row 3, column 2
	 * @param m22 matrix entry row 3, column 3
	 */
	public Matrix3(float m00, float m01, float m02,
				   float m10, float m11, float m12,
				   float m20, float m21, float m22) {
		this.m00 = m00;
		this.m01 = m01;
		this.m02 = m02;
		this.m10 = m10;
		this.m11 = m11;
		this.m12 = m12;
		this.m20 = m20;
		this.m21 = m21;
		this.m22 = m22;
	}
	
	/**
	 * Constructor used to create a new copy of the provided matrix.
	 * @param copy the matrix to copy from
	 */
	public Matrix3(Matrix3 copy) {
		this.m00 = copy.m00;
		this.m01 = copy.m01;
		this.m02 = copy.m02;
		this.m10 = copy.m10;
		this.m11 = copy.m11;
		this.m12 = copy.m12;
		this.m20 = copy.m20;
		this.m21 = copy.m21;
		this.m22 = copy.m22;
	}
	
	/**
	 * Constructor.
	 * @param entries float array containing at least 9 elements
	 */
	public Matrix3(float[] entries) {
		if (entries.length < 9) {
			throw new IllegalArgumentException("A 3x3 matrix requires at least 9 elements as an argument (Found: " + entries.length + ").");
		}
		
		this.m00 = entries[0];
		this.m01 = entries[1];
		this.m02 = entries[2];
		this.m10 = entries[3];
		this.m11 = entries[4];
		this.m12 = entries[5];
		this.m20 = entries[6];
		this.m21 = entries[7];
		this.m22 = entries[8];
	}
	
	public static final Matrix3 identity() {
		Matrix3 result = new Matrix3();
		result.m00 = 1;
		result.m11 = 1;
		result.m22 = 1;
		
		return result;
	}
	
	/**
	 * Rotation in the XY-plane.
	 * @return the standard matrix for the rotation
	 */
	public static final Matrix3 rotationXY(float angle) {
		Matrix3 result = Matrix3.identity();
		result.m00 = (float)  Math.cos(angle);
		result.m01 = (float) -Math.sin(angle);
		result.m10 = (float)  Math.sin(angle);
		result.m11 = (float)  Math.cos(angle);
		
		return result;
	}
	
	/**
	 * Rotation in the XY-plane.
	 * @return the standard matrix for the rotation
	 */
	public static final Matrix3 rotationXZ(float angle) {
		Matrix3 result = Matrix3.identity();
		result.m00 = (float)  Math.cos(angle);
		result.m02 = (float)  Math.sin(angle);
		result.m20 = (float) -Math.sin(angle);
		result.m22 = (float)  Math.cos(angle);
		
		return result;
	}
	
	/**
	 * Rotation in the XY-plane.
	 * @return the standard matrix for the rotation
	 */
	public static final Matrix3 rotationYZ(float angle) {
		Matrix3 result = Matrix3.identity();
		result.m11 = (float)  Math.cos(angle);
		result.m12 = (float) -Math.sin(angle);
		result.m21 = (float)  Math.sin(angle);
		result.m22 = (float)  Math.cos(angle);
		
		return result;
	}

	public static final Matrix3 scale(float x, float y, float z) {
		Matrix3 result = new Matrix3();
		result.m00 = x;
		result.m11 = y;
		result.m22 = z;
		
		return result;
	}

	/**
	 * Matrix3 by Matrix3 addition operation.<br>
	 * <b>Operation description:</b><br>
	 * <code>returnMatrix = thisMatrix + parameterMatrix;</code>
	 * @param mat the matrix to add this to
	 * @return the new matrix containing the addition of the two matrices
	 */
	public Matrix3 add(Matrix3 mat) {
		Matrix3 result = new Matrix3();
		result.m00 = m00 + mat.m00;
		result.m01 = m01 + mat.m01;
		result.m02 = m02 + mat.m02;
		result.m10 = m10 + mat.m10;
		result.m11 = m11 + mat.m11;
		result.m12 = m12 + mat.m12;
		result.m20 = m20 + mat.m20;
		result.m21 = m21 + mat.m21;
		result.m22 = m22 + mat.m22;
		
		return result;
	}
	
	/**
	 * Matrix3 by Matrix3 multiplication.<br>
	 * <b>Operation description:</b><br>
	 * <code>returnMatrix = thisMatrix * parameterMatrix;</code>
	 * @param right the right operand matrix to multiply by
	 * @return the new resulting matrix from multiplication 
	 */
	public Matrix3 mul(Matrix3 right) {
		Matrix3 result = new Matrix3();
		result.m00 = this.m00 * right.m00 + this.m10 * right.m01 + this.m20 * right.m02;
		result.m01 = this.m01 * right.m00 + this.m11 * right.m01 + this.m21 * right.m02;                                                                            
		result.m02 = this.m02 * right.m00 + this.m12 * right.m01 + this.m22 * right.m02;
		result.m10 = this.m00 * right.m10 + this.m10 * right.m11 + this.m20 * right.m12;
		result.m11 = this.m01 * right.m10 + this.m11 * right.m11 + this.m21 * right.m12;                                                             
		result.m12 = this.m02 * right.m10 + this.m12 * right.m11 + this.m22 * right.m12;
		result.m20 = this.m00 * right.m20 + this.m10 * right.m21 + this.m20 * right.m22;
		result.m21 = this.m01 * right.m20 + this.m11 * right.m21 + this.m21 * right.m22;
		result.m22 = this.m02 * right.m20 + this.m11 * right.m21 + this.m22 * right.m22;
		
		return result;
	}

	/**
	 * Matrix3 by Scalar scaling operation.<br>
	 * <b>Operation description:</b><br>
	 * <code>returnMatrix = thisMatrix * parameterScalar;</code>
	 * @param scalar the scaling amount
	 * @return the new matrix containing the addition of the two matrices
	 */
	public Matrix3 mul(float scalar) {
		Matrix3 result = new Matrix3();
		result.m00 = m00 + scalar;
		result.m01 = m01 + scalar;
		result.m02 = m02 + scalar;
		result.m10 = m10 + scalar;
		result.m11 = m11 + scalar;
		result.m12 = m12 + scalar;
		result.m20 = m20 + scalar;
		result.m21 = m21 + scalar;
		result.m22 = m22 + scalar;
		
		return result;
	}
	
	/**
	 * Transposes the matrix.
	 * @return the transposed matrix.
	 */
	public Matrix3 transpose() {
		Matrix3 result = new Matrix3();
		result.m00 = m00;
		result.m01 = m10;
		result.m02 = m20;
		result.m10 = m01;
		result.m11 = m11;
		result.m12 = m21;
		result.m20 = m02;
		result.m21 = m12;
		result.m22 = m22;
		
		return result;
	}
	
	/**
	 * Get the determinant of the matrix.
	 * @return the determinant of the matrix
	 */
	public float determinant() {
		return m00 * (m11 * m22 - m12 * m21)
			 - m01 * (m10 * m22 - m12 * m20)
			 + m02 * (m10 * m21 - m11 * m20);
	}
	
	/**
	 * Inverts the matrix if and only if the determinant is non-zero.
	 * @return the inverted matrix
	 * @throws ArithmeticException if the matrix is singular (determinant is zero)
	 */
	public Matrix3 inverse() throws ArithmeticException {
		Matrix3 result = new Matrix3();
		float det = determinant();
		if (det == 0f) {
			throw new ArithmeticException("Cannot compute the inverse of a singular matrix.");
		}
		
		float reciprocal = 1.0f / det;
		
		result.m00 = (m11 * m22 - m12 * m21) * reciprocal;
		result.m01 = (m02 * m21 - m01 * m22) * reciprocal;
		result.m02 = (m01 * m12 - m02 * m11) * reciprocal;
		result.m10 = (m12 * m20 - m10 * m22) * reciprocal;
		result.m11 = (m00 * m22 - m02 * m20) * reciprocal;
		result.m12 = (m02 * m10 - m00 * m12) * reciprocal;
		result.m20 = (m10 * m21 - m11 * m20) * reciprocal;
		result.m21 = (m01 * m20 - m00 * m21) * reciprocal;
		result.m22 = (m00 * m11 - m01 * m10) * reciprocal;
		
		return result;
	}
	
	/**
	 * Checks if the matrix is orthogonal.
	 * <b>Note:</b> due to rounding errors for floating point numbers some results may be inaccurate.
	 * @return true if the matrix is orthogonal
	 */
	public boolean isOrthogonal() {
		return transpose().equals(inverse());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Matrix3) {
			Matrix3 mat = (Matrix3) obj;
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
	
	public FloatBuffer toFloatBuffer() {
		FloatBuffer result = BufferUtils.createFloatBuffer(m00, m01, m02,
														   m10, m11, m12,
														   m20, m21, m22);
		return result;
	}
	
	@Override
	public String toString() {
		return String.format("Matrix3: [%.3f %.3f %.3f]\n%9s[%.3f %.3f %.3f]\n%9s[%.3f %.3f %.3f]", m00, m01, m02, "", m10, m11, m12, "", m20, m21, m22);
	}
}
