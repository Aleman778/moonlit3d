package universe.graphics;

import java.util.HashMap;

public abstract class Material {

	protected final Graphics graphics;
	protected final Shader shader;
	protected final HashMap<String, Texture> textures;
	
	protected Texture mainTexture;
	
	public Material(Graphics graphics, Shader shader) {
		this.graphics = graphics;
		this.shader = shader;
		this.textures = new HashMap<>();
	}
	
	public void texture(Texture texture) {
		mainTexture = texture;
	}
	
	public void texture(String name, Texture texture) {
		textures.put(name, texture);
	}
	
	public void enable() {
		shader.enable();
		setup();
	}
	
	public void disable() {
		shader.disable();
	}
	
	public Shader getShader() {
		return shader;
	}
	
	public abstract void setup();
}
