package universe.graphics;

import universe.math.*;
import universe.util.Disposable;

/**
 * Abstract shader program used for combining shaders in order to render objects.<br>
 * <b>Note:</b> the shader program has to be {@link #enable() enabled} in order to render objects using it.
 * @author Aleman778
 * @since Universe Core 1.0
 */
public abstract class Shader implements Disposable {
	
    /**
     * Constructor.
     */
    public Shader() {
    }
    
    /**
     * Add a shader to this program
     * @param type the type of shader. The type can be any of the following:<br>
     * VERTEX, FRAGMENT, GEOMETRY, TESS_CONTROL_SHADER, TESS_EVALUATION_SHADER
     * @param source the shader source code
     */
    public abstract void add(ShaderType type, String source);
    
    /**
     * Enables this shader.<br>
     * <b>Note:</b> only enabled shader can be used for rendering.
     */
    public abstract void enable();
    
    /**
     * Disables this shader.<br>
     * <b>Note:</b> only enabled shader can be used for rendering.
     */
    public abstract void disable();

    /**
     * Disposes the program.<br>
     * <b>Note:</b> program cannot be used after disposing.
     */
    @Override
    public abstract void dispose();
    
    /**
     * Set shader uniform variable value.
     * @param name the variable name
     * @param value the value of the uniform
     */
    public abstract void setInt(String name, Integer value);
    
    /**
     * Set shader uniform variable value.
     * @param name the variable name
     * @param value the value of the uniform
     */
    public abstract void setFloat(String name, Float value);

    /**
     * Set shader uniform variable value.
     * @param name the variable name
     * @param vector the value of the uniform
     */
    public abstract void setVec2(String name, Vector2 vector);
    
    /**
     * Set shader uniform variable value.
     * @param name the variable name
     * @param vector the value of the uniform
     */
    public abstract void setVec3(String name, Vector3 vector);
    
    /**
     * Set shader uniform variable value.
     * @param name the variable name
     * @param vector the value of the uniform
     */
    public abstract void setVec4(String name, Vector4 vector);
    
    /**
     * Set shader uniform variable value.
     * @param name the variable name
     * @param color the value of the uniform
     */
    public abstract void setColor3(String name, Color color);
    
    /**
     * Set shader uniform variable value.
     * @param name the variable name
     * @param color the value of the uniform
     */
    public abstract void setColor4(String name, Color color);

    /**
     * Set shader uniform variable value.
     * @param name the variable name
     * @param matrix the value of the uniform
     */
    public abstract void setMat2(String name, Matrix2 matrix);
    
    /**
     * Set shader uniform variable value.
     * @param name the variable name
     * @param matrix the value of the uniform
     */
    public abstract void setMat3(String name, Matrix3 matrix);
    
    /**
     * Set shader uniform variable value.
     * @param name the variable name
     * @param matrix the value of the uniform
     */
    public abstract void setMat4(String name, Matrix4 matrix);
    
    /**
     * Set the shader uniform variable value
     * @param name the variable name
     * @param texture the value of the uniform
     */
    public abstract void setSampler(String name, Texture texture);
    
    public abstract int getAttribIndex(String name);
    
    /**
     * Different types of shaders.
     */
    public enum ShaderType {
        VERTEX, FRAGMENT, GEOMETRY,
        TESS_CONTROL_SHADER, TESS_EVALUATION_SHADER;
    }
}
