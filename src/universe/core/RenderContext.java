package universe.core;

public abstract class RenderContext {
	
	public static final int UNDEFINED = 0;
	
	protected int version;
	protected String versionString;
	protected String renderer;
	protected String vendor;
	
	public RenderContext() {
		version = UNDEFINED;
		vendor = "";
		renderer = "";
		versionString = "";
	}
	
	public abstract void init();
	
	public abstract RenderAPI getApi();
	
	@Override
	public String toString() {
		return "Render Context: " + versionString;
	}
}
