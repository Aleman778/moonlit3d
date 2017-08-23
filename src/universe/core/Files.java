package universe.core;

import java.io.InputStream;

public abstract class Files {

	protected final Display display;
	
	public Files(Display display) {
		this.display = display;
	}
	
	public abstract String loadFile(String filename);
	
	public abstract InputStream createInput(String filename);
	
	public abstract FileHandle createFile(String filename);
	
	public abstract FileHandle internal(String filename);
	
	public abstract FileHandle external(String filename);
	
	public abstract FileHandle local(String filename);
	
	/**
	 * Get the file extension of the filename, works also for URL addresses.
	 * @param filename the filename to get the extension from
	 * @return the extension of the provided filename, the full filename is returned if no file extension was found
	 */
	public String getExtension(String filename) {
		int dot = filename.lastIndexOf(".");
		if (dot == -1)
			return filename;
		
		String extension = filename.substring(dot + 1);
		
		//Strip any URL parameters i.e. image.jpg?param=value&param2=value2 etc.
		int question = extension.indexOf("?");
		if (question != -1)
			return extension.substring(0, question);
		
		return extension;
	}
}
