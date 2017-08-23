package test;

import universe.core.Node;
import universe.desktop.GlfwDisplay;

/**
 * TEST CLASS IS NOT INTENDED TO BE USED IN THE FINAL RELEASE
 * @author Aleman778
 */
public class Main {
	
	public static void main(String[] args) {
		GlfwDisplay display = new GlfwDisplay("Universe Engine", 1280, 720) {
			
			@Override
			public void setup() {
			}
			
			@Override
			public void render() {
				for (Node node : children()) {
					node.render();
				}
			}
		};
		
		//Basic Func Test
		display.add(new Application());
		display.setVisible(true);
	}
}

