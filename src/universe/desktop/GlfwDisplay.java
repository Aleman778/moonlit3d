package universe.desktop;

import universe.app.*;
import universe.graphics.Renderer;
import universe.opengl.GLProfile;
import universe.opengl.GLRenderer;
import universe.util.Disposable;

import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWWindowPosCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.BufferUtils;

import java.nio.IntBuffer;

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
public class GlfwDisplay extends Display implements Runnable, Disposable {

	private static final IntBuffer xpos = BufferUtils.createIntBuffer(1);
	private static final IntBuffer ypos = BufferUtils.createIntBuffer(1);
	
	private static boolean initialized = false;
	private static final long NULL = 0L;

	private GLFWWindowPosCallback posCallback;
	private GLFWWindowSizeCallback sizeCallback;

	private Object lock = new Object();
	private Renderer renderer = new GLRenderer();
	private GlfwScreen screen = null;
	private GLProfile profile = null;
	private Thread thread;
	private String title;
	private long window = NULL;
	private boolean visible = false;
	private boolean disposed = false;
	private boolean fullscreen = false;
	private int minWidth = -1, minHeight = -1;
	private int maxWidth = -1, maxHeight = -1;
	private int width, height;
	private int x = -1, y = -1;

	/**
	 * Default constructor.
	 */
	public GlfwDisplay() {
		this("");
	}
	
	/**
	 * Constructor.
	 * @param title the title of the window
	 */
	public GlfwDisplay(String title) {
		this(title, 640, 480);
	}
	
	/**
	 * Constructor.
	 * @param width the width of the window
	 * @param height the height of the window
	 */
	public GlfwDisplay(int width, int height) {
		this("", width, height);
	}
	
	/**
	 * Constructor.
	 * @param title the title of the window
	 * @param width the width of the window
	 * @param height the height of the window
	 */
	public GlfwDisplay(String title, int width, int height) {
		init();

		this.title = title;
		this.width = width;
		this.height = height;
		
		setName(title);
	}

	/**
	 * Initializes the GLFW library.
	 */
	private static final void init() {
		if (!initialized) {
			if (glfwInit()) {
				initialized = true;
				glfwSetErrorCallback(GLFWErrorCallback.createThrow());
			} else {
				throw new IllegalStateException("The GLFW library failed to initialize.");
			}
		}
	}
	
	public static final void terminate() {
		glfwTerminate();
	}
	
	/**
	 * This method does two things:
	 *  - Swaps the buffers that are managed by the rendering system.<br>
	 *  - Processes the events stored in the window event queue.<br>
	 */
	@Override
	public final void refresh() {
		glfwPollEvents();
		glfwSwapBuffers(window);
	}
	
	@Override
	public final void run() {
		createWindow();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				renderLoop();
			}
		}).start();

		eventLoop();
	}
	
	private void eventLoop() {
		while (!isClosed()) {
			glfwWaitEvents();
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void renderLoop() {
		glfwMakeContextCurrent(window);
		GL.createCapabilities();
		execSetup();
		
		while (!isClosed()) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			render();
			
			synchronized (lock) {
				if (hasReference()) {
					glfwSwapBuffers(window);
				}
			}
		}
	}

	/**
	 * Destroys the window and free its contexts.
	 */
	@Override
	public void dispose() {
		glfwDestroyWindow(window);
		posCallback.free();
		sizeCallback.free();
		
		disposed = true;
		visible = false;
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
		if (isCreated()) {
			throw new IllegalStateException("The renderer cannot be set after the window has been created.");
		}
		
		switch (renderer) {
		case OPENGL: case PREFERRED:
			this.renderer = new GLRenderer();
			break;
		default:
			
		}
	}
	
	/**
	 * Set the profile for the OpenGL renderer.
	 * <br><b>Note:</b> This method is only available for the OpenGL renderer.
	 * <br><b>Note:</b> By setting the parameter profile to <i>null</i> the profile will be
	 * set to the preferred profile based on the users hardware.
	 * @param profile the OpenGL profile to use
	 */
	public void setProfile(GLProfile profile) {
		if (isActive()) {
			throw new IllegalStateException("The OpenGL Profile cannot be set when window is active.");
		}
		
		this.profile = profile;
	}
	
	/**
	 * Checks if the window should be closed.
	 * @return true if the window has been closed
	 */
	public boolean isClosed() {
		if (!isCreated()) {
			return true;
		}
		
		return glfwWindowShouldClose(window);
	}
	
	/**
	 * Check if the current window is active.
	 * @return true if the window is active.
	 */
	public boolean isActive() {
		return visible && isCreated();
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
		
		if (hasReference()) {
			glfwSetWindowSize(window, width, height);
		}
	}
	
	/**
	 * Set the minimum size limit of the window.
	 * <br><b>Note:</b> Setting the size limit to -1 will 
	 * @param minWidth the minimum width
	 * @param minHeight the minimum height
	 */
	public void setSizeLimit(int minWidth, int minHeight) {
		setSizeLimit(minWidth, minHeight, GLFW_DONT_CARE, GLFW_DONT_CARE);
	}
	
	/**
	 * Set the minimum and maximum size limit of the window.
	 * @param minWidth the minimum width
	 * @param minHeight the minimum height
	 * @param maxWidth the maximum width
	 * @param maxHeight the maximum height
	 */
	public void setSizeLimit(int minWidth, int minHeight, int maxWidth, int maxHeight) {
		this.minWidth = minWidth;
		this.minHeight = minHeight;
		this.maxWidth = maxWidth;
		this.maxHeight = maxHeight;
		
		if (hasReference()) {
			glfwSetWindowSizeLimits(window, minWidth, minHeight, maxWidth, maxHeight);
		}
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
		
		if (hasReference()) {
			glfwSetWindowPos(window, x, y);
		}
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
	 * Enable vertical synchronization 
	 * @param enable
	 */
	public void setVsync(boolean enable) {
		setSwapInterval(enable ? 1 : 0);
	}
	
	/**
	 * <b>From GLFW Documentation:</b><br>
	 * This function sets the swap interval for the current OpenGL or OpenGL ES context,
	 * i.e. the number of screen updates to wait from the time glfwSwapBuffers was called
	 * before swapping the buffers and returning. This is sometimes called vertical
	 * synchronization, vertical retrace synchronization or just vsync.
	 * <br><br>
	 * Contexts that support either of the WGL_EXT_swap_control_tear and
	 * GLX_EXT_swap_control_tear extensions also accept negative swap intervals, which
	 * allow the driver to swap even if a frame arrives a little bit late. You can check
	 * for the presence of these extensions using glfwExtensionSupported.
	 * For more information about swap tearing, see the extension specifications.
	 * <br><br>
	 * A context must be current on the calling thread. Calling this function
	 * without a current context will cause a GLFW_NO_CURRENT_CONTEXT error.
	 * <br><br>
	 * <b>Note:</b> This function does not apply to Vulkan.
	 * @param interval the minimum number of screen updates to wait for until the buffers are swapped by glfwSwapBuffers.
	 * @see <a href="http://www.glfw.org/docs/latest/group__context.html#ga6d4e0cdf151b5e579bd67f13202994ed">GLFW 1.3.2 Documentation</a>
	 */
	public void setSwapInterval(int interval) {
		glfwSwapInterval(interval);
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
		
		if (hasReference()) {
			glfwSetWindowMonitor(window, screen.getMonitor(), 0, 0,
					screen.getWidth(), screen.getHeight(), (int) screen.getRefreshRate());
		}
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
		
		if (hasReference()) {
			glfwSetWindowMonitor(window, NULL, x, y, width, height, GLFW_DONT_CARE);
		}
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
		
		if (this.visible && !isCreated()) {
			thread = new Thread(this, getName());
			thread.start();
			return;
		}
		
		if (hasReference()) {
			if (visible) {
				glfwShowWindow(window);
			} else {
				glfwHideWindow(window);
			}
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
	
	private void createWindow() {
		if (fullscreen) {
			glfwWindowHint(GLFW_REFRESH_RATE, (int) screen.getRefreshRate());
			window = glfwCreateWindow(screen.getWidth(), screen.getHeight(), title, screen.getMonitor(), NULL);
		} else {
			window = glfwCreateWindow(width, height, title, NULL, NULL);
		}
		
		setupCallbacks();
		setupLocation();
		setupProfile();
		setSizeLimit(minWidth, minHeight, maxWidth, maxHeight);
	}
	
	private void setupProfile() {
		if (profile != null) {
			glfwWindowHint(GLFW_OPENGL_PROFILE, profile.isCompatible() ? GLFW_OPENGL_COMPAT_PROFILE : GLFW_OPENGL_CORE_PROFILE);
			glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, profile.getMajor());
			glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, profile.getMinor());
		} else {
			glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_ANY_PROFILE);
			glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 1);
			glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 0);
		}
	}
	
	private void setupCallbacks() {
		GLFWWindowPosCallback poscallback = glfwSetWindowPosCallback(window,(long window, int x, int y) -> {
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
	
	private void setupLocation() {
		if (x == -1 && y == -1) {
			glfwGetWindowPos(window, xpos, ypos);
			x = xpos.get(0);
			y = ypos.get(0);	
		} else {
			glfwSetWindowPos(window, x, y);
		}
	}
	
	private boolean hasReference() {
		return window != NULL;
	}
	 
	private boolean isDisposed() {
		return disposed;
	}
	
	private boolean isCreated() {
		return (window != NULL) && (!isDisposed());
	}
}
