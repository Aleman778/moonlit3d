package universe.desktop;

import universe.app.*;
import universe.opengl.GLProfile;
import universe.opengl.GLRenderContext;
import universe.util.Disposable;

import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFWErrorCallback;

import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.glfw.GLFW.*;

/**
 * Desktop window implementation using the GLFW.
 * <h1>What is GLFW?</h1>
 * <b>GLFW</b> is an Open Source, multi-platform library
 * for OpenGL, OpenGL ES and Vulkan development on
 * the desktop. It provides a simple API for creating
 * windows, contexts and surfaces, receiving input and events.<br>
 * @since 1.0
 * @author Aleman778
 * @see <a href="http://www.glfw.org/">http://www.glfw.org/</a>
 */
public class GlfwDisplay implements Display, Disposable {
	
	private static boolean initialized = false;

	private RenderContext context;
	private GlfwScreen screen;
	private String title;
	private long window;
	private boolean visible;
	private boolean fullscreen;
	private int width, height;
	private int x, y;

	/**
	 * Default constructor.
	 */
	public GlfwDisplay() {
		this("Universe Engine");
	}
	
	/**
	 * Constructor.
	 * @param title the title of the window
	 */
	public GlfwDisplay(String title) {
		this(title, null);
	}
	
	/**
	 * Constructor.
	 * @param title the title of the window
	 * @param profile the OpenGL profile
	 */
	public GlfwDisplay(String title, GLProfile profile) {
		this(title, profile, 640, 480);
	}
	
	/**
	 * Constructor.
	 * @param title the title of the window
	 * @param width the width of the window
	 * @param height the height of the window
	 */
	public GlfwDisplay(String title, GLProfile profile, int width, int height) {
		init();

		if (profile != null) {
			glfwWindowHint(GLFW_OPENGL_PROFILE, profile.isCompatible() ? GLFW_OPENGL_COMPAT_PROFILE : GLFW_OPENGL_CORE_PROFILE);
			glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, profile.getMajor());
			glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, profile.getMinor());
		} else {
			glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_ANY_PROFILE);
			glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 1);
			glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 0);
		}
		
		this.window = glfwCreateWindow(width, height, title, NULL, NULL);
		this.title = title;
		this.width = width;
		this.height = height;
		this.fullscreen = false;
		this.visible = true;
		this.context = null;
		this.screen = null;
		
		int[] xpos = new int[1];
		int[] ypos = new int[1];
		glfwGetWindowPos(window, xpos, ypos);
		this.x = xpos[0];
		this.y = ypos[0];
		
		glfwSetWindowPosCallback(window,(long window, int x, int y) -> {
			if (!fullscreen) {
				this.x = x;
				this.y = y;
			}
		});
		
		glfwSetWindowSizeCallback(window, (long window, int w, int h) -> {
			if (!fullscreen) {
				this.width = w;
				this.height = h;
			}
		});
	}

	/**
	 * Initializes the GLFW library.
	 */
	private static void init() {
		if (!initialized) {
			if (glfwInit()) {
				initialized = true;
				glfwSetErrorCallback(GLFWErrorCallback.createThrow());
			} else {
				throw new IllegalStateException("The GLFW library failed to initialize.");
			}
		}
	}
	
	/**
	 * Processes the events stored in the window event queue.
	 */
	@Override
	public void update() {
		glfwPollEvents();
	}

	/**
	 * Swaps the buffers that are managed by the rendering system.<br>
	 * <b>Note:</b> the window must have a rendering context.
	 */
	@Override
	public void refresh() {
		glfwSwapBuffers(window);
	}

	/**
	 * Destroys the window and its contexts.
	 */
	@Override
	public void dispose() {
		glfwDestroyWindow(window);
	}
	
	/**
	 * <b>From GLFW Documentation:</b><br>
	 * "This function restores the specified window if it was previously
	 * iconified (minimized) or maximized. If the window is already restored,
	 * this function does nothing. If the specified window is a full screen
	 * window, the resolution chosen for the window is restored on the
	 * selected monitor."
	 * @see <a href="http://www.glfw.org/docs/latest/group__window.html#ga52527a5904b47d802b6b4bb519cdebc7">Glfw 1.3.2 Documentation</a>
	 */
	public void restore() {
		glfwRestoreWindow(window);
	}

	@Override
	public void setRenderer(RenderApi renderer) {
		glfwMakeContextCurrent(window);
		
		switch (renderer) {
		case OPENGL: case PREFERRED:
			context = new GLRenderContext();
			break;
			
		default:
			throw new IllegalStateException("Unsupported rendering api: " + renderer.getName());
		}
	}
	
	/**
	 * Checks if the window should be closed.
	 * @return true if the window has been closed
	 */
	public boolean isClosed() {
		return glfwWindowShouldClose(window);
	}
	
	/**
	 * Get the window title.
	 * @return title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Set the window title.
	 */
	public void setTitle(String title) {
		this.title = title;
		glfwSetWindowTitle(window, title);
	}
	
	/**
	 * Get the width of the window.
	 * @return the window width
	 */
	public int getWidth() {
		if (screen != null) {
			return screen.getWidth();
		}
		
		return width;
	}

	/**
	 * Get the height of the window.
	 * @return the window height
	 */
	public int getHeight() {
		if (screen != null) {
			return screen.getHeight();
		}
		
		return height;
	}
	
	/**
	 * Set the size of the window.
	 * @param width the new width of the window
	 * @param height the new height of the window
	 */
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
		
		glfwSetWindowSize(window, width, height);
	}

	/**
	 * Set the minimum size limit of the window.
	 * @param minWidth the minimum width
	 * @param minHeight the minimum height
	 */
	public void setSizeLimit(int minWidth, int minHeight) {
		glfwSetWindowSizeLimits(window, minWidth, minHeight, GLFW_DONT_CARE, GLFW_DONT_CARE);
	}
	
	/**
	 * Set the minimum and maximum size limit of the window.
	 * @param minWidth the minimum width
	 * @param minHeight the minimum height
	 * @param maxWidth the maximum width
	 * @param maxHeight the maximum height
	 */
	public void setSizeLimit(int minWidth, int minHeight, int maxWidth, int maxHeight) {
		glfwSetWindowSizeLimits(window, minWidth, minHeight, maxWidth, maxHeight);
	}

	/**
	 * Get the size of the window.
	 */
	public int[] getSize() {	
		return new int[] {width, height};
	}
	
	/**
	 * Get the x location of the window.
	 * @return the x location
	 */
	public int getX() {
		if (fullscreen) {
			return 0;
		}
		
		return x;
	}
	
	/**
	 * Get the y location of the window
	 * @return the y location
	 */
	public int getY() {
		if (fullscreen) {
			return 0;
		}
		
		return y;
	}
	
	/**
	 * Set the location of the window.
	 * @param x the x location 
	 * @param y the y location
	 */
	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
		glfwSetWindowPos(window, x, y);
	}
	
	/**
	 * Sets the location of the window relative to the provided screen.
	 * @param screen the relative screen
	 */
	public void setLocation(Screen screen) {
		int x = screen.getWidth() / 2 - width / 2;
		int y = screen.getHeight() / 2 - height / 2;
		setLocation(x, y);
	}
	
	/**
	 * Check if the window is in fullscreen mode.
	 * @return true if the window is in fullscreen mode
	 */
	public boolean isFullscreen() {
		return fullscreen;
	}

	/**
	 * Sets the window in fullscreen mode on the provided screen.
	 * @param screen the screen to use
	 */
	public void setFullscreenMode(GlfwScreen screen) {
		if (this.fullscreen) {
			return;
		}
		this.fullscreen = true;
		this.visible = true;
		this.screen = screen;
		
		glfwSetWindowMonitor(window, screen.getMonitor(), 0, 0,
				screen.getWidth(), screen.getHeight(), (int) screen.getRefreshRate());
	}
	
	/**
	 * Sets the window in fullscreen mode on the primary screen.
	 */
	public void setFullscreenMode() {
		setFullscreenMode(getScreen());
	}
	
	/**
	 * Sets the window in windowed mode.
	 */
	public void setWindowedMode() {
		if (!this.fullscreen) {
			return;
		}
		this.fullscreen = false;
		this.screen = null;
		
		GlfwScreen screen = getScreen();
		glfwSetWindowMonitor(window, NULL, x, y, width, height, (int) screen.getRefreshRate());
	}
	
	/**
	 * Checks if the window is visible.
	 * @return true if the window is visible
	 */
	public boolean isVisible() {
		return visible;
	}
	
	/**
	 * Set the visibility of the screen
	 * @param visible the visibility flag
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
		
		if (visible) {
			glfwShowWindow(window);
		} else {
			glfwHideWindow(window);
		}
	}
	
	/**
	 * Set the decorated attribute.
	 * @param iconified flag
	 */
	public void setDecorated(boolean decorated) {
		glfwSetWindowAttrib(window, GLFW_DECORATED, decorated ? GLFW_TRUE : GLFW_FALSE);
	}
	
	/**
	 * Checks if the window is decorated.
	 * @return floating attribute
	 */
	public boolean isDecorated() {
		return glfwGetWindowAttrib(window, GLFW_DECORATED) == GLFW_TRUE;
	}
	
	/**
	 * Checks if the window is floating.
	 * @return floating attribute
	 */
	public boolean isFloating() {
		return glfwGetWindowAttrib(window, GLFW_FLOATING) == GLFW_TRUE;
	}
	
	/**
	 * Set the floating attribute.
	 * @param floating flag
	 */
	public void setFloating(boolean floating) {
		glfwSetWindowAttrib(window, GLFW_FLOATING, floating ? GLFW_TRUE : GLFW_FALSE);
	}

	/**
	 * Checks if the window is resizable.
	 * @return floating attribute
	 */
	public boolean isResizable() {
		return glfwGetWindowAttrib(window, GLFW_RESIZABLE) == GLFW_TRUE;
	}
	
	/**
	 * Set the resizable attribute.
	 * @param resizeable flag
	 */
	public void setResizeable(boolean resizable) {
		glfwSetWindowAttrib(window, GLFW_RESIZABLE, resizable ? GLFW_TRUE : GLFW_FALSE);
	}

	/**
	 * Checks if the window is minimized (iconified).
	 * @return floating attribute
	 */
	public boolean isMinimized() {
		return glfwGetWindowAttrib(window, GLFW_ICONIFIED) == GLFW_TRUE;
	}
	
	/**
	 * Set the minimized (iconified) attribute.
	 * @param iconified flag
	 */
	public void setMinimized() {
		glfwIconifyWindow(window);
	}
	
	/**
	 * Checks if the window is maximized.
	 * @return floating attribute
	 */
	public boolean isMaximized() {
		return glfwGetWindowAttrib(window, GLFW_MAXIMIZED) == GLFW_TRUE;
	}

	/**
	 * Set the maximized attribute.
	 * @param maximized flag
	 */
	public void setMaximized() {
		glfwMaximizeWindow(window);
	}
	

	@Override
	public GlfwScreen getScreen() {
		return new GlfwScreen(glfwGetPrimaryMonitor());
	}

	@Override
	public GlfwScreen[] getScreens() {
		PointerBuffer buff = glfwGetMonitors();
		GlfwScreen[] result = new GlfwScreen[buff.limit()];
		for (int i = 0; i < result.length; i++) {
			result[i] = new GlfwScreen(buff.get(i));
		}
		return result;
	}
}
