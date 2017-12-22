package test;

public class ModelApp {

	public void setup() {
		display("Application", 640, 480);
		display("Application2", 640, 480);
		
		//Location and orientation
		location(CENTER);
		orientation(PORTRAIT);
		
		//Multithreading
		threading(THREAD_MULTI);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private void display(String title, int w, int h) {
	}
	
	private void canvas(int w, int h) {
	}

	private void location(int loc) {
	}
	
	private void location(int x, int y) {
	}
	
	private void orientation(int orientation) {
		
	}
	
	private void threading(int type) {
		
	}
	
	
	public static final int CENTER = 0;
	public static final int PORTRAIT = 0;
	public static final int LANDSCAPE = 0;
	public static final int BOTH = 0;
	public static final int THREAD_SINGLE = 0;
	public static final int THREAD_MULTI = 0;
	public static final int THREAD_QUEUE = 0;
}
