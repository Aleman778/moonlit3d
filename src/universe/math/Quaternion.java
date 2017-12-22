package universe.math;

/**
 * Four dimensional complex number.
 * @author Aleman778
 */
public class Quaternion {
    
    /**
     * The i component.
     */
    public float x;
    
    /**
     * The j component.
     */
    public float y;
    
    /**
     * The k component.
     */
    public float z;
    
    /**
     * The real component.
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
     * @param x the i component
     * @param y the j component
     * @param z the k component
     * @param w the real component 
     */
    public Quaternion(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
    
    /**
	 * Constructor used to create a new copy of the provided quaternion.
	 * @param copy the quaternion to copy from
	 */
    public Quaternion(Quaternion copy) {
        this.x = copy.x;
        this.y = copy.y;
        this.z = copy.z;
        this.w = copy.w;
    }
    
    /**
     * Set the quaternion based on the euler angles in <b>radians</b>.
     * @param x the rotation around the x-axis in <b>radians</b>
     * @param y the rotation around the y-axis in <b>radians</b>
     * @param z the rotation around the z-axis in <b>radians</b>
     * @return the resulting quaternion
     */
    public static Quaternion euler(float x, float y, float z) {
    	Quaternion result = new Quaternion();

        float sinx = (float) Math.sin(x * 0.5f);
        float cosx = (float) Math.cos(x * 0.5f);
        float siny = (float) Math.sin(y * 0.5f);
        float cosy = (float) Math.cos(y * 0.5f);
        float sinz = (float) Math.sin(z * 0.5f);
        float cosz = (float) Math.cos(z * 0.5f);
        
        result.w = cosy * cosz * cosx - siny * sinz * sinx;
        result.x = siny * sinz * cosx + cosy * cosz * sinx;
        result.y = siny * cosz * cosx + cosy * sinz * sinx;
        result.z = cosy * sinz * cosx - siny * cosz * sinx;
        
        return result;
    }
    
    /**
     * Get the axis angle rotation quaternion, angle is represented in <b>radians</b>
     * @param axis the axis to rotate about
     * @param angle the angle of the rotation
     * @return the new quaternion representing an orientation
     */
    public static Quaternion rotation(Vector3 axis, float angle) {
    	Quaternion result = new Quaternion();
    	result.x = axis.x * (float) Math.sin(angle * 0.5f);
    	result.y = axis.y * (float) Math.sin(angle * 0.5f);
    	result.z = axis.z * (float) Math.sin(angle * 0.5f);
    	result.w = 			(float) Math.cos(angle * 0.5f);
    	return result;
    }
    
    /**
     * Get the conjugate of the provided conjugate
     * @param quat the quaternion
     * @return
     */
    public static Quaternion conjugate(Quaternion quat) {
    	return new Quaternion(-quat.x, -quat.y, -quat.z, quat.w);
    }
	
	/**
	 * Quaternion by Quaternion multiplication operation.<br>
	 * <b>Operation description:</b><br>
	 * <code>returnValue = thisQuaternion . parameterQuaternion;</code>
	 * @param vec the vector to multiply this to
	 * @return the resulting value of the dot product between the two quaternions.
	 */
	public Quaternion mul(Quaternion quat) {
    	Quaternion result = new Quaternion();
    	result.x =  x * quat.w + y * quat.z - z * quat.y + w * quat.x;
    	result.y = -x * quat.z + y * quat.w + z * quat.x + w * quat.y;
    	result.z =  x * quat.y - y * quat.x + z * quat.w + w * quat.z;
    	result.w = -x * quat.x - y * quat.y - z * quat.z + w * quat.w;
    	return result;
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
    
    public Matrix4 toMatrix4() {
    	Matrix4 result = new Matrix4();

    	float inbLen = 1.0f / (float) Math.sqrt(x * x + y * y + z * z + w * w);
    	float qx = x * inbLen;
    	float qy = y * inbLen;
    	float qz = z * inbLen;
    	float qw = w * inbLen;
    	
    	result.m00 = 1.0f - 2.0f * qy * qy - 2.0f * qz * qz;
    	result.m01 = 2.0f * qx * qy - 2.0f * qz * qw;
        result.m02 = 2.0f * qx * qz + 2.0f * qy * qw;
        result.m10 = 2.0f * qx * qy + 2.0f * qz * qw;
        result.m11 = 1.0f - 2.0f * qx * qx - 2.0f * qz * qz;
        result.m12 = 2.0f * qy * qz - 2.0f * qx * qw;
        result.m20 = 2.0f * qx * qz - 2.0f * qy * qw;
        result.m21 = 2.0f * qy * qz + 2.0f * qx * qw;
    	result.m22 = 1.0f - 2.0f * qx * qx - 2.0f * qy * qy;
    	
    	return result.transpose();
    }

    @Override
    public String toString() {
        return String.format("Quaternion: [x = %f, y = %f, z = %f, w = %f]", x, y, z, w);
    }
}
