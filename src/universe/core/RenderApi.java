package universe.core;

public enum RenderAPI {	
	
	PREFERRED("Preferred"),
	OPENGL("OpenGL"),
	OPENGLES("OpenGLES"),
	WEBGL("WebGL");
	
	
	private final String name;
	
	private RenderAPI(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return "Render API " + getName();
	}
}
