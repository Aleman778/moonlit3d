package universe.desktop;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import universe.core.Display;
import universe.core.FileHandle;
import universe.core.Files;

public class GlfwFiles extends Files {

	public static final String external = System.getProperty("user.home") + File.separator;
	public static final String local = new File("").getAbsolutePath() + File.separator;
	
	public GlfwFiles(Display display) {
		super(display);
		
	}
	
	/**
	 * UNFINISHED
	 */
	public String loadFile(String filename) {
		String contents = "";
		
		try {
			InputStreamReader reader = new InputStreamReader(display.files.createInput(filename));
			BufferedReader stream = new BufferedReader(reader);
			
			String line;
			while ((line = stream.readLine()) != null) {
				contents += line + "\n";
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return contents;
	}

	@Override
	public InputStream createInput(String filename) {
		if (filename == null)
			return null;
		
		if (filename.isEmpty())
			return null;
		
		//Check for URL input
		if (filename.contains(":")) {
			try {
				InputStream input = createInputUrl(filename);
				if (input != null)
					return input;
			} catch(IOException ex) {
				return null;
			}
		}
		
		//Resource Streams
		InputStream input;
		
		ClassLoader loader = getClass().getClassLoader();
		input = loader.getResourceAsStream(filename);
		if (input != null) {
			String clazz = input.getClass().getName();
			if (!clazz.equals("sun.plugin.cache.EmptyInputStream")) {
				return input;
			}
		}
		
		//Local Folder
		input = local(filename).toInputStream();
		if (input != null) {
			return input;
		}
		
		return null;
	}
	
	private InputStream createInputUrl(String filename) throws IOException {
		try {
			URL url = new URL(filename);
			URLConnection connection = url.openConnection();
			
			if (connection instanceof HttpURLConnection) {
				HttpURLConnection httpConnection = (HttpURLConnection) connection;
				httpConnection.setInstanceFollowRedirects(true);
				
				int response = httpConnection.getResponseCode();
				if (response >= 300 && response < 400) {
					createInputUrl(httpConnection.getHeaderField("Location"));
				}
				
				return httpConnection.getInputStream();
			} else if (connection instanceof JarURLConnection) {
				return url.openStream();
			}
		} catch (MalformedURLException | FileNotFoundException ex) { }
		
		return null;
	}
	
	@Override
	public FileHandle createFile(String filename) {

		ClassLoader loader = getClass().getClassLoader();
		URL resource = loader.getResource(filename);
		if (resource != null) {
			return new FileHandle(resource.getPath());
		}
		
		return local(filename);
	}

	@Override
	public FileHandle internal(String filename) {
		return new FileHandle(filename);
	}

	@Override
	public FileHandle external(String filename) {
		return new FileHandle(external + filename);
	}

	@Override
	public FileHandle local(String filename) {
		return new FileHandle(local + filename);
	}
}
