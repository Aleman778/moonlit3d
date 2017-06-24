package universe.math;

public class Quaternion {
    
    /**
     * The x component.
     */
    public float x;
    
    /**
     * The y component.
     */
    public float y;
    
    /**
     * The z component.
     */
    public float z;
    
    /**
     * The w component.
     */
    public float w;

    /**
     * Constructor.
     * Creates an identity quaternion.
     */
    public Quaternion() {
        this(0, 0, 0, 1);
    }

    /**
     * Constructor.
     * @param x the x component
     * @param y the y component
     * @param z the z component
     * @param w the w component 
     */
    public Quaternion(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
    
    /**
     * Constructor.
     * Copy a specific quaternion from the provided argument.
     * @param quat the quaternion to copy from
     */
    public Quaternion(Quaternion quat) {
        set(quat.x, quat.y, quat.z, quat.w);
    }
    
    /**
     * Set the quaternion based on the euler angles in <b>degres</b>.
     * @param x the rotation around the x-axis in <b>degrees</b>
     * @param y the rotation around the y-axis in <b>degrees</b>
     * @param z the rotation around the z-axis in <b>degrees</b>
     * @return the resulting quaternion
     */
    public static Quaternion euler(float x, float y, float z) {
        float rx = (float) Math.toRadians(x);
        float ry = (float) Math.toRadians(y);
        float rz = (float) Math.toRadians(z);
        
        return eulerRad(rx, ry, rz);
    }
    
    /**
     * Set the quaternion based on the euler angles in <b>radians</b>.
     * @param x the rotation around the x-axis in <b>radians</b>
     * @param y the rotation around the y-axis in <b>radians</b>
     * @param z the rotation around the z-axis in <b>radians</b>
     * @return the resulting quaternion
     */
    public static Quaternion eulerRad(float x, float y, float z) {
    	Quaternion result = new Quaternion();
    	
        float sx = (float) Math.sin(x * 0.5f);
        float cx = (float) Math.cos(x * 0.5f);
        float sy = (float) Math.sin(y * 0.5f);
        float cy = (float) Math.cos(y * 0.5f);
        float sz = (float) Math.sin(z * 0.5f);
        float cz = (float) Math.cos(z * 0.5f);
        
        result.x = (cx * cy * cz) + (sx * sy * sz);
        result.y = (sx * cy * cz) - (cx * sy * sz);
        result.z = (cx * sy * cz) + (sx * cy * sz);
        result.w = (cx * cy * sz) - (sx * sy * cz);
        
        return result;
    }
    
    /**
     * Get the axis angle rotation quaternion, angle is represented in <b>degrees</b>
     * @param axis the axis to rotate about
     * @param angle the angle of the rotation
     * @return the new quaternion representing an orientation
     */
    public static Quaternion rotation(Vector3 axis, float angle) {
        return rotationRad(axis, (float) Math.toRadians(angle));
    }
    
    /**
     * Get the axis angle rotation quaternion, angle is represented in <b>radians</b>
     * @param axis the axis to rotate about
     * @param angle the angle of the rotation
     * @return the new quaternion representing an orientation
     */
    public static Quaternion rotationRad(Vector3 axis, float angle) {
    	Quaternion result = new Quaternion();
    	result.x = axis.x * (float) Math.sin(angle * 0.5f);
    	result.y = axis.y * (float) Math.sin(angle * 0.5f);
    	result.z = axis.z * (float) Math.sin(angle * 0.5f);
    	result.w = 			(float) Math.cos(angle * 0.5f);
    	return result;
    }
	
	/**
	 * Quaternion by Quaternion dot product operation.<br>
	 * <b>Operation description:</b><br>
	 * <code>returnValue = thisQuaternion . parameterQuaternion;</code>
	 * @param vec the vector to multiply this to
	 * @return the resulting value of the dot product between the two quaternions.
	 */
	public float dot(Quaternion quat) {
		return x * quat.x + y * quat.y + z * quat.z + w * quat.w;
	}

    /**
     * Set the quaternion components.
     * @param x the x component
     * @param y the y component
     * @param z the z component
     * @param w the w component
     */
    public void set(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
    
    public Matrix4 toMatrix() {
    	Matrix4 result = new Matrix4();
    	float xSq = x * x;
    	float ySq = y * y;
    	float zSq = z * z;
    	float wSq = w * w;
    	float temp1 = x * y;
    	float temp2 = z * w;
    	float invSq = 1 / (xSq + ySq + zSq + wSq);

    	result.m00 = ( xSq - ySq - zSq + wSq) * invSq;
    	result.m11 = (-xSq + ySq - zSq + wSq) * invSq;
    	result.m22 = (-xSq - ySq + zSq + wSq) * invSq;
    	
    	result.m10 = (temp1 + temp2) * invSq;
    	result.m01 = (temp1 - temp2) * invSq;
    	
    	temp1 = x * z;
    	temp2 = y * w;
    	result.m20 = (temp1 - temp2) * invSq;
    	result.m02 = (temp1 + temp2) * invSq;
    	
    	temp1 = y * z;
    	temp2 = x * w;
    	result.m21 = (temp1 + temp2) * invSq;
    	result.m12 = (temp1 - temp2) * invSq;
    	
    	return result;
    }

    @Override
    public String toString() {
        return String.format("Quaternion: [x = %f, y = %f, z = %f, w = %f]", x, y, z, w);
    }
}
