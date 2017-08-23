package universe.core;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.InputStream;

/**
 * Abstract file handle.
 * @author Aleman778
 */
public class FileHandle {
	
	protected File file;
	
	public FileHandle(String filename) {
		this.file = new File(filename);
	}
	
	public FileHandle(File file) {
		this.file = file;
	}

	/**
	 * Get a handle to the child with the specified name.
	 * @param child the name of child
	 * @return a handle to the specified child.
	 */
	public FileHandle child(String child) {
		if (file.getPath().isEmpty())
			return new FileHandle(file);
		
		return new FileHandle(new File(file, child));
	}
	
	/**
	 * Get the parent to this file.
	 * @return a handle to the parent file
	 */
	public FileHandle parent() {
		if (file.getPath().isEmpty())
			return new FileHandle(file);
		
		return new FileHandle(file.getParentFile());
	}
	
	/**
	 * Get the sibling to this file.
	 * @param sibling the name of the sibling
	 * @return a handle to the specific sibling
	 */
	public FileHandle sibling(String sibling) {
		if (file.getPath().isEmpty())
			return new FileHandle(file);
		
		return new FileHandle(new File(file.getParentFile(), sibling));
	}
	
	/**
	 * Get the path to this file.<br>
	 * <b>Note:</b> path string uses forward slashes as default.
	 * @return
	 */
	public String path() {
		return file.getPath();
	}
	
	/**
	 * Get an array of all the files in this directory.
	 * @return an array of all the files
	 */
	public FileHandle[] list() {
		File[] files = file.listFiles();
		FileHandle[] result = new FileHandle[files.length];
		
		for (int i = 0; i < files.length; i++) {
			result[i] = child(files[i].getName());
		}
		
		return result;
	}
	
	/**
	 * Get an array of all the files in this directory that
	 * are accepted by the file filter.
	 * @param filter the file filter to use
	 * @return an array of the files that are accepted by the filter 
	 */
	public FileHandle[] list(FileFilter filter) {
		File[] files = file.listFiles(filter);
		FileHandle[] result = new FileHandle[files.length];
		
		for (int i = 0; i < files.length; i++) {
			result[i] = child(files[i].getName());
		}
		
		return result;
	}
	
	/**
	 * Get an array of all the files in this directory that
	 * are accepted by the filename filter.
	 * @param filter the file filter to use
	 * @return an array of the files that are accepted by the filter
	 */
	public FileHandle[] list(FilenameFilter filter) {
		File[] files = file.listFiles(filter);
		FileHandle[] result = new FileHandle[files.length];
		
		for (int i = 0; i < files.length; i++) {
			result[i] = child(files[i].getName());
		}
		
		return result;
	}
	
	/**
	 * Make directories. 
	 */
	public void makeDirs() {
		file.mkdirs();
	}
	
	/**
	 * Delete the file or directory.
	 * @return true if the deletion was a success, otherwise false is returned
	 */
	public boolean delete() {
		return file.delete();
	}
	
	/**
	 * Check if the file exists.
	 * @return true if the file exists, otherwise false is returned
	 */
	public boolean exists() {
		return file.exists();
	}
	
	/**
	 * Get the filename (including the file extension) of this file.
	 * @return the filename of this file
	 */
	public String name() {
		return file.getName();
	}

	/**
	 * Get the name (excluding the file extension) of this file.
	 * @return the name of this file
	 */
	public String nameWithoutExtension() {
		String filename = name();
		int index = filename.lastIndexOf(".");
		if (index == -1)
			return filename;
		
		return filename.substring(0, index);
	}
	
	/**
	 * Get the extension of this file.
	 * @return the extension of this file
	 */
	public String extension() {
		String filename = name();
		int index = filename.lastIndexOf(".");
		if (index == -1)
			return filename;
		
		return filename.substring(index + 1);
	}
	
	/**
	 * Convert the file to a file input stream.
	 * @return the file input stream, null is returned if the file is not found
	 */
	public InputStream toInputStream() {
		try {
			return new FileInputStream(file);
		} catch (FileNotFoundException e) {
			return null;
		}
	}
}
