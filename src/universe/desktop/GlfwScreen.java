package universe.desktop;

import universe.app.Screen;

import org.lwjgl.glfw.GLFWVidMode;

import static org.lwjgl.glfw.GLFW.*;


public class GlfwScreen implements Screen {

	private GLFWVidMode mode;
	private long monitor;
	
	public GlfwScreen(long monitor) {
		this.mode = glfwGetVideoMode(monitor);
		this.monitor = monitor;
	}

	@Override
	public int getWidth() {
		return mode.width();
	}

	@Override
	public int getHeight() {
		return mode.height();
	}

	@Override
	public int getRedBits() {
		return mode.redBits();
	}

	@Override
	public int getGreenBits() {
		return mode.greenBits();
	}

	@Override
	public int getBlueBits() {
		return mode.blueBits();
	}

	@Override
	public double getRefreshRate() {
		return mode.refreshRate();
	}
	
	public long getMonitor() {
		return monitor;
	}
	
	@Override
	public String toString() {
		return glfwGetMonitorName(monitor);
	}
}
