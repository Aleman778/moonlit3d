package test;

import org.lwjgl.opengl.GL45;

import universe.desktop.GlfwDisplay;
import universe.graphics.Color;

public class TestApplication extends GlfwDisplay {

	public TestApplication() {
		super("Test Application", 640 ,480);
		
		setVisible(true);
	}

	public void setup() {
		background(Color.DODGERBLUE);
	}
	
	public static void main(String[] args) {
		new TestApplication();
	}
}
