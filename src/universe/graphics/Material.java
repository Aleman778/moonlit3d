package universe.graphics;

public abstract class Material {

	private final Graphics graphics;
	
	public Shader shader;
	
	public Material(Graphics graphics) {
		this.graphics = graphics;
	}
	
	protected abstract void setup();

	public final void enable() {
		check();
		
		shader.enable();
		setup();
	}
	
	public final void disable() {
		check();
		
		shader.disable();
	}
	
	private void check() {
		if (shader == null)
			throw new IllegalStateException(getClass().getCanonicalName() + " does not have a shader attached.");
	}
}
