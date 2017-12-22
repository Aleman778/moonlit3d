package universe.opengl;

import universe.graphics.Color;
import universe.graphics.ShaderException;
import universe.graphics.Texture;
import universe.graphics.Shader;

import universe.math.*;

import java.util.ArrayList;
import java.util.HashMap;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL32.*;
import static org.lwjgl.opengl.GL40.*;

/**
 * Shader program with combined shaders used for rendering objects.
 * @author Aleman778
 * @since 1.0
 */ 
public final class GLSLShader extends Shader {

    private final GLGraphics graphics;
    private final HashMap<String, Integer> uniforms;
    private final ArrayList<String> textures;
    
    private int object;
    private boolean ready;

    public GLSLShader(GLGraphics graphics) {
    	this.graphics = graphics;
        this.uniforms = new HashMap<>();
        this.textures = new ArrayList<>();
        this.object   = glCreateProgram();
        this.ready    = false;
    }
    
    /**
     * Attach a shader to this program.
     * @param shader the shader to attach.
     */
    @Override
    public void add(ShaderType type, String source) {
    	int shader = glCreateShader(glGetShaderType(type));
    	glShaderSource(shader, source);
    	glCompileShader(shader);
    	
    	if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
    		String log = glGetShaderInfoLog(shader);
            glDeleteShader(shader);
    		throw new ShaderException("Failed to compile shader:\n" + log);
    	}
    	
    	glAttachShader(object, shader);
        glDeleteShader(shader);
    }
    
    public void setup() {
    	if (ready)
    		return;
    	ready = true;
        glLinkProgram(object);
        glValidateProgram(object);
    }
    
    /**
     * Enable this shader.
     * Note: only enabled shader can be used for rendering.
     */
    @Override
    public void enable() {
    	check();
    	
        if (isEnabled())
            return;
        
        if (!ready)
        	setup();
        
        glUseProgram(object);
        graphics.state.shader = object;
    }
    
    /**
     * Disable this shader.<br>
     * <b>Note:</b> only enabled shader can be used for rendering.
     */
    @Override
    public void disable() {
    	check();
    	
        glUseProgram(0);
        graphics.state.shader = 0;
    }

    /**
     * Disposes the program.<br>
     * <b>Note:</b> program cannot be used after disposing.
     */
    @Override
    public void dispose() {
    	check();
        
        glDeleteProgram(object);
        object = -1;
    }
    
    /**
     * Get the shader program OpenGL reference id.
     * @return the OpenGL shader program reference id
     */
    public int object() {
    	return object;
    }
    
    @Override
    public void setInt(String name, Integer value) {
        enable();
        glUniform1i(getUniformLocation(name), value);
    }
    
    @Override
    public void setFloat(String name, Float value) {
        enable();
        glUniform1f(getUniformLocation(name), value);
    }
    
    @Override
    public void setVec2(String name, Vector2 value) {
        enable();
        glUniform2fv(getUniformLocation(name), value.toFloatBuffer());
    }
    
    @Override
    public void setVec3(String name, Vector3 value) {
        enable();
        glUniform3fv(getUniformLocation(name), value.toFloatBuffer());
    }
    
    @Override
    public void setVec4(String name, Vector4 value) {
        enable();
        glUniform4fv(getUniformLocation(name), value.toFloatBuffer());
    }
    
    @Override
    public void setColor3(String name, Color value) {
        enable();
        glUniform3fv(getUniformLocation(name), value.toFloatBufferRGB());
    }
    
    @Override
    public void setColor4(String name, Color value) {
        enable();
        glUniform4fv(getUniformLocation(name), value.toFloatBuffer());
    }

    @Override
    public void setMat2(String name, Matrix2 value) {
        enable();
        glUniformMatrix4fv(getUniformLocation(name), false, value.toFloatBuffer());
    }
    
    @Override
    public void setMat3(String name, Matrix3 value) {
        enable();
        glUniformMatrix4fv(getUniformLocation(name), false, value.toFloatBuffer());
    }
    
    @Override
    public void setMat4(String name, Matrix4 value) {
        enable();
        glUniformMatrix4fv(getUniformLocation(name), false, value.toFloatBuffer());
    }
    
    @Override
    public void setSampler(String name, Texture texture) {
        enable();
        if (textures.size() > GL_MAX_TEXTURE_UNITS)
        	throw new IllegalStateException("The maximum number of texture units exceeded (" + GL_MAX_TEXTURE_UNITS + ")");


        int unit = getTextureUnit(name);
        graphics.state.setActiveTexture(unit);
        
        texture.bind();
        glUniform1i(getUniformLocation(name), unit);
    }

    public int getAttribIndex(String attribute) {
    	return glGetAttribLocation(object, attribute);
    }
    
    public int getTextureUnit(String name) {
        int unit = textures.indexOf(name);
        if (unit == -1) {
        	textures.add(name);
            return textures.size() - 1;
        }
        	
        return unit;
    }
    
    /**
     * Get the uniform reference from name.
     * @param name the name of the uniform variable
     */
    private int getUniformLocation(String name) {
    	Integer loc = uniforms.get(name);
        if (loc != null)  {
            return loc;
        }
        
        int ref = glGetUniformLocation(object, name);
        if (ref == -1) {
            throw new ShaderException("Uniform \"" + name + "\" is not found in shader.");
        }
        
        return ref;
    }

    private static int glGetShaderType(ShaderType type) {
        switch (type) {
            case VERTEX					: return GL_VERTEX_SHADER;
            case FRAGMENT				: return GL_FRAGMENT_SHADER;
            case GEOMETRY				: return GL_GEOMETRY_SHADER;
            case TESS_CONTROL_SHADER 	: return GL_TESS_CONTROL_SHADER;
            case TESS_EVALUATION_SHADER : return GL_TESS_EVALUATION_SHADER;
        }
        
        return -1;
    }

    private boolean isEnabled() {
        return (graphics.state.shader == object);
    }
    
    private void check() {
    	if (object == 0)
    		throw new ShaderException("Failed to create the shader program.");
    	
    	if (object == -1)
    		throw new NullPointerException();
    }
}
