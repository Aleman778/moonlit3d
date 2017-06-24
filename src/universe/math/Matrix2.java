package universe.math;

public final class Matrix2 {

	/**
	 * Matrix entry.
	 */
	public float m00, m01,
				 m10, m11;
	
	/**
	 * Default Constructor.<br>
	 * <b>Note:</b> The matrix is an identity matrix as default.
	 */
	public Matrix2() {
		m00 = 1;
		m01 = 0;
		m10 = 0;
		m11 = 1;
	}
	
	/**
	 * Constructor,
	 * @param m00 matrix entry row 1, column 1
	 * @param m01 matrix entry row 1, column 2
	 * @param m10 matrix entry row 2, column 1
	 * @param m11 matrix entry row 2, column 2
	 */
	public Matrix2(float m00, float m01, float m10, float m11) {
		this.m00 = m00;
		this.m01 = m01;
		this.m10 = m10;
		this.m11 = m11;
		
	}
	
	public static final Matrix2 rotation(float angle) {
		Matrix2 result = new Matrix2();
		result.m00 = (float)  Math.cos(angle);
		result.m01 = (float) -Math.sin(angle);
		result.m10 = (float)  Math.sin(angle);
		result.m11 = (float)  Math.cos(angle);
		
		return result;
	}

	public static final Matrix2 scale(float x, float y) {
		Matrix2 result = new Matrix2();
		result.m00 = x;
		result.m11 = y;
		
		return result;
	}

	/**
	 * Matrix2 by Matrix2 addition operation.<br>
	 * <b>Operation description:</b><br>
	 * <code>returnMatrix = thisMatrix + parameterMatrix;</code>
	 * @param mat the matrix to add this to
	 * @return the new matrix containing the addition of the two matrices
	 */
	public Matrix2 add(Matrix2 mat) {
		Matrix2 result = new Matrix2();
		result.m00 = m00 + mat.m00;
		result.m01 = m01 + mat.m01;
		result.m10 = m10 + mat.m10;
		result.m11 = m11 + mat.m11;
		
		return result;
	}

	/**
	 * Matrix2 by Scalar scaling operation.<br>
	 * <b>Operation description:</b><br>
	 * <code>returnMatrix = thisMatrix * parameterScalar;</code>
	 * @param scalar the scaling amount
	 * @return the new matrix containing the addition of the two matrices
	 */
	public Matrix2 scale(float scalar) {
		Matrix2 result = new Matrix2();
		result.m00 = m00 * scalar;
		result.m01 = m01 * scalar;
		result.m10 = m10 * scalar;
		result.m11 = m11 * scalar;
		
		return result;
	}
	
	/**
	 * Transposes the matrix.
	 * @return the transposed matrix.
	 */
	public Matrix2 transpose() {
		Matrix2 result = new Matrix2();
		result.m00 = m00;
		result.m01 = m10;
		result.m10 = m01;
		result.m11 = m11;
		
		return result;
	}
	
	/**
	 * Get the determinant of the matrix.
	 * @return the determinant of the matrix
	 */
	public float determinant() {
		return (m00 * m11) - (m01 * m10);
	}
	
	/**
	 * Inverts the matrix if and only if the determinant is non-zero.
	 * @return the inverted matrix
	 * @throws ArithmeticException if the matrix is singular (determinant is zero)
	 */
	public Matrix2 inverse() throws ArithmeticException {
		Matrix2 result = new Matrix2();
		float det = determinant();
		if (det == 0f) {
			throw new ArithmeticException("Cannot compute the inverse of a singular matrix.");
		}
		
		float reciprocal = 1.0f / det;
		
		result.m00 =  m11 * reciprocal;
		result.m01 = -m01 * reciprocal;
		result.m10 = -m10 * reciprocal;
		result.m11 =  m00 * reciprocal;
		
		return result;
	}
	
	/**
	 * Checks if the matrix is orthogonal.
	 * @return true if the matrix is orthogonal
	 */
	public boolean isOrthogonal() {
		return transpose().equals(inverse());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Matrix2) {
			Matrix2 mat = (Matrix2) obj;
			if (m00 != mat.m00)
				return false;
			if (m01 != mat.m01)
				return false;
			if (m10 != mat.m10)
				return false;
			if (m11 != mat.m11)
				return false;
			
			return true;
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		return String.format("Matrix2: [%.3f %.3f]\n%9s[%.3f %.3f]", m00, m01, "", m10, m11);
	}
}
