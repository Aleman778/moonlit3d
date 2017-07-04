package universe.opengl;

import universe.app.RenderApi;
import universe.app.RenderContext;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;

import static org.lwjgl.opengl.GL11.*;

public final class GLRenderContext extends RenderContext {

	public static final int OPENGL11 = 1;
	public static final int OPENGL12 = 2;
	public static final int OPENGL13 = 3;
	public static final int OPENGL14 = 4;
	public static final int OPENGL15 = 5;
	public static final int OPENGL20 = 6;
	public static final int OPENGL21 = 7;
	public static final int OPENGL30 = 8;
	public static final int OPENGL31 = 9;
	public static final int OPENGL32 = 10;
	public static final int OPENGL33 = 11;
	public static final int OPENGL40 = 12;
	public static final int OPENGL41 = 13;
	public static final int OPENGL42 = 14;
	public static final int OPENGL43 = 15;
	public static final int OPENGL44 = 16;
	public static final int OPENGL45 = 17;
	
	private boolean forwardCompatible; 
	
	private GLCapabilities capabilities;
	private String extension;
	
	public GLRenderContext() {
		this(false);
	}
	
	public GLRenderContext(boolean forwardCompatible) {
		this.forwardCompatible = forwardCompatible;
	}
	
	public void make() {
		capabilities = GL.createCapabilities(forwardCompatible);
		
		if (capabilities.OpenGL45) {
			version = OPENGL45;
		} else if (capabilities.OpenGL44) {
			version = OPENGL44;
		} else if (capabilities.OpenGL43) {
			version = OPENGL43;
		} else if (capabilities.OpenGL42) {
			version = OPENGL42;
		} else if (capabilities.OpenGL41) {
			version = OPENGL41;
		} else if (capabilities.OpenGL40) {
			version = OPENGL40;
		} else if (capabilities.OpenGL33) {
			version = OPENGL33;
		} else if (capabilities.OpenGL32) {
			version = OPENGL32;
		} else if (capabilities.OpenGL31) {
			version = OPENGL31;
		} else if (capabilities.OpenGL30) {
			version = OPENGL30;
		} else if (capabilities.OpenGL21) {
			version = OPENGL21;
		} else if (capabilities.OpenGL20) {
			version = OPENGL20;
		} else if (capabilities.OpenGL15) {
			version = OPENGL15;
		} else if (capabilities.OpenGL14) {
			version = OPENGL14;
		} else if (capabilities.OpenGL13) {
			version = OPENGL13;
		} else if (capabilities.OpenGL12) {
			version = OPENGL12;
		} else if (capabilities.OpenGL11) {
			version = OPENGL11;
		} else {
			version = UNDEFINED;
		}
		
		versionString = glGetString(GL_VERSION);
		extension     = glGetString(GL_EXTENSIONS);
		renderer      = glGetString(GL_RENDERER);
		vendor 	 	  = glGetString(GL_VENDOR);
		
		System.out.println("Version: " + version + ", " + versionString);
	}
	
	@Override
	public RenderApi getApi() {
		return RenderApi.OPENGL;
	}
	
	public boolean hasExtension(String ext) {
		return extension.contains(ext);
	}
}
