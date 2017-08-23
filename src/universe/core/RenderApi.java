package universe.core;

public enum RenderApi {	
	
	PREFERRED("Preferred"),
	OPENGL("OpenGL");
	
	
	private final String name;
	
	private RenderApi(String name) {
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
