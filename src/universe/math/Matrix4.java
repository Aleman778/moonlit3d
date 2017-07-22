package universe.math;

import universe.util.BufferUtils;

import java.nio.FloatBuffer;

import static java.lang.Math.*;

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
	 * Constructor used to create a new copy of the provided matrix.
	 * @param copy the matrix to copy from
	 */
	public Matrix4(Matrix4 copy) {
		this.m00 = copy.m00;
		this.m01 = copy.m01;
		this.m02 = copy.m02;
		this.m03 = copy.m03;
		this.m10 = copy.m10;
		this.m11 = copy.m11;
		this.m12 = copy.m12;
		this.m13 = copy.m13;
		this.m20 = copy.m20;
		this.m21 = copy.m21;
		this.m22 = copy.m22;
		this.m23 = copy.m23;
		this.m30 = copy.m30;
		this.m31 = copy.m31;
		this.m32 = copy.m32;
		this.m33 = copy.m33;
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

	/**
	 * Identity matrix.
	 * @return 
	 */
	public static final Matrix4 identity() {
		Matrix4 result = new Matrix4();
		result.m00 = 1;
		result.m11 = 1;
		result.m22 = 1;

		return result;
	}

	/**
	 * Transformation matrix performs a translation operation.
	 * @param vec the vector components x, y, z position of the translation
	 * @return new translation matrix
	 */
	public static final Matrix4 translation(Vector3 vec) {
		return translation(vec.x, vec.y, vec.z);
	}
	
	/**
	 * Transformation matrix performs a translation operation.
	 * @param x the x position of the translation
	 * @param y the y position of the translation
	 * @param z the z position of the translation
	 * @return new translation matrix
	 */
	public static final Matrix4 translation(float x, float y, float z) {
		Matrix4 result = new Matrix4();
		result.m30 = x;
		result.m31 = y;
		result.m32 = z;
		
		return result;
	}
	
	/**
	 * Transformation matrix performs a rotation by the 
	 * provided euler angles x, y, z, in degrees.
	 * @param x the angle of the pitch
	 * @param y the angle of the yaw
	 * @param z the angle of the roll
	 * @return new rotation matrix
	 */
	public static final Matrix4 rotation(float x, float y, float z) {
		Matrix4 result = new Matrix4();
		x = (float) Math.toRadians(x);
		y = (float) Math.toRadians(y);
		z = (float) Math.toRadians(z);
		
		result.m00 = (float) ( cos(y) * cos(z));
		result.m01 = (float) ( cos(x) * sin(z) + sin(x) * sin(y) * cos(z));
		result.m02 = (float) ( sin(x) * sin(z) - cos(x) * sin(y) * cos(z));
		result.m10 = (float) (-cos(y) * sin(z));
		result.m11 = (float) ( cos(x) * cos(z) - sin(x) * sin(y) * sin(z));
		result.m12 = (float) ( sin(x) * cos(z) + cos(x) * sin(y) * sin(z));
		result.m20 = (float) ( sin(y));
		result.m21 = (float) (-sin(x) * cos(y));
		result.m22 = (float) ( cos(x) * cos(y));

		return result;
	}
	
	/**
	 * Transformation matrix performs a rotation by the provided angle
	 * and about the provided x, y and z axis.
	 * @return new rotation matrix
	 */
	public static final Matrix4 rotation(float angle, float x, float y, float z) {
		return rotation(angle, new Vector3(x, y, z));
	}
	
	/**
	 * Transformation matrix performs a rotation by the provided angle
	 * (in degrees) and about the provided axis.
	 * @return new rotation matrix
	 */
	public static final Matrix4 rotation(float angle, Vector3 axis) {
		return Quaternion.rotation(axis, angle).toMatrix4();
	}

	/**
	 * Transformation matrix performs a rotation operation by the
	 * provided quaternion.
	 * @return new rotation matrix
	 */
	public static final Matrix4 rotation(Quaternion quat) {
		return quat.toMatrix4();
	}

	/**
	 * Transformation matrix performs a scale operation by the
	 * amount in the provided axes x, y and z.
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public static final Matrix4 scale(float x, float y, float z) {
		Matrix4 result = new Matrix4();
		result.m00 = x;
		result.m11 = y;
		result.m22 = z;

		return result;
	}
	
	/**
	 * Transformation matrix performs a projection from a 3d scene
	 * onto a 2d plane.
	 * @param fov the field of view angle
	 * @param aspectRatio the aspect ration of the display use 
	 * @param near the closest viewing limit in the z-direction
	 * @param far the furthest viewing limit in the z-direction
	 * @return the new matrix containing the projection
	 * @see universe.app.Display#getAspectRatio()
	 */
	public static final Matrix4 projection(float fov, float aspectRatio, float near, float far) {
		Matrix4 result = new Matrix4();
		float angle = (float) Math.tan(Math.toRadians(fov / 2.0f));
		float range = near - far;
		
		result.m00 = 1.0f / (angle * aspectRatio);
		result.m11 = 1.0f / angle;
		result.m22 = (-near - far) / range;
		result.m23 = 2.0f * far * near / range;
				
		return result;
	}
	
	public static final Matrix4 lookAt(Vector3 from, Vector3 to, Vector3 up) {
		Matrix4 result = new Matrix4();
		
		Vector3 zaxis = to.sub(from).normal();
		Vector3 xaxis = up.cross(zaxis).normal();
		Vector3 yaxis = zaxis.cross(xaxis);
		
		result.m00 = xaxis.x;
		result.m01 = xaxis.y;
		result.m02 = xaxis.z;
		result.m10 = yaxis.x;
		result.m11 = yaxis.y;
		result.m12 = yaxis.z;
		result.m20 = zaxis.x;
		result.m21 = zaxis.y;
		result.m22 = zaxis.z;
		
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
	 * Matrix3 by Matrix3 multiplication.<br>
	 * <b>Operation description:</b><br>
	 * <code>returnMatrix = thisMatrix * parameterMatrix;</code>
	 * @param right the right operand matrix to multiply by
	 * @return the new resulting matrix from multiplication 
	 */
	public Matrix4 mul(Matrix4 right) {
		Matrix4 result = new Matrix4();
		result.m00 = this.m00 * right.m00 + this.m10 * right.m01 + this.m20 * right.m02 + this.m30 * right.m03;
		result.m01 = this.m01 * right.m00 + this.m11 * right.m01 + this.m21 * right.m02 + this.m31 * right.m03;                                                                            
		result.m02 = this.m02 * right.m00 + this.m12 * right.m01 + this.m22 * right.m02 + this.m32 * right.m03;                                                                          
		result.m03 = this.m03 * right.m00 + this.m13 * right.m01 + this.m23 * right.m02 + this.m33 * right.m03;
		result.m10 = this.m00 * right.m10 + this.m10 * right.m11 + this.m20 * right.m12 + this.m30 * right.m13;
		result.m11 = this.m01 * right.m10 + this.m11 * right.m11 + this.m21 * right.m12 + this.m31 * right.m13;                                                             
		result.m12 = this.m02 * right.m10 + this.m12 * right.m11 + this.m22 * right.m12 + this.m32 * right.m13;                                                            
		result.m13 = this.m03 * right.m10 + this.m13 * right.m11 + this.m23 * right.m12 + this.m33 * right.m13;
		result.m20 = this.m00 * right.m20 + this.m10 * right.m21 + this.m20 * right.m22 + this.m30 * right.m23;
		result.m21 = this.m01 * right.m20 + this.m11 * right.m21 + this.m21 * right.m22 + this.m31 * right.m23;
		result.m22 = this.m02 * right.m20 + this.m12 * right.m21 + this.m22 * right.m22 + this.m32 * right.m23;
		result.m23 = this.m03 * right.m20 + this.m13 * right.m21 + this.m23 * right.m22 + this.m33 * right.m23;
		result.m30 = this.m00 * right.m30 + this.m10 * right.m31 + this.m20 * right.m32 + this.m30 * right.m33;
		result.m31 = this.m01 * right.m30 + this.m11 * right.m31 + this.m21 * right.m32 + this.m31 * right.m33;
		result.m32 = this.m02 * right.m30 + this.m12 * right.m31 + this.m22 * right.m32 + this.m32 * right.m33;
		result.m33 = this.m03 * right.m30 + this.m13 * right.m31 + this.m23 * right.m32 + this.m33 * right.m33;
		
		return result;
	}
	
	/**
	 * Matrix2 by Vector4 multiplication.<br>
	 * <b>Operation description:</b><br>
	 * <code>returnVector = thisMatrix * parameterVector;</code>
	 * @param vec the vector to multiply by
	 * @return the new resulting vector from the multiplication
	 */
	public Vector4 mul(Vector4 vec) {
		Vector4 result = new Vector4();
		result.x = this.m00 * vec.x + this.m01 * vec.y + this.m02 * vec.z + this.m03 * vec.w;
		result.y = this.m10 * vec.x + this.m11 * vec.y + this.m12 * vec.z + this.m13 * vec.w;
		result.z = this.m20 * vec.x + this.m21 * vec.y + this.m22 * vec.z + this.m23 * vec.w;
		result.w = this.m30 * vec.x + this.m31 * vec.y + this.m32 * vec.z + this.m33 * vec.w;
		
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
	public Matrix4 mul(float scalar) {
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
	public float determinantAffine() {
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
	
	public Matrix4 translate(float x, float y, float z) {
		return mul(translation(x, y, z));
	}
	
	public Matrix4 translate(Vector3 vector) {
		return mul(translation(vector));
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
	
	public FloatBuffer toFloatBuffer() {
		FloatBuffer result = BufferUtils.createFloatBuffer(m00, m01, m02, m03,
														   m10, m11, m12, m13,
														   m20, m21, m22, m23,
														   m30, m31, m32, m33);
		return result;
	}

	@Override
	public String toString() {
		return String.format(
				"Matrix4: [%.3f %.3f %.3f %.3f]\n%9s[%.3f %.3f %.3f %.3f]\n%9s[%.3f %.3f %.3f %.3f]\n%9s[%.3f %.3f %.3f %.3f]",
				m00, m01, m02, m03, "", m10, m11, m12, m13, "", m20, m21, m22, m23, "", m30, m31, m32, m33);
	}
}
