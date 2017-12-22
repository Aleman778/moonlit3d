package universe.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class IOUtils {

	public static byte[] toByteArray(InputStream src) {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			
			int len;
			int size = 4096;
			byte[] buf = new byte[size];
			
			while ((len = src.read(buf)) != -1) {
				out.write(buf, 0, len);
			}
			out.flush();
			
			return out.toByteArray();
			
		} catch (IOException ex) {}
		
		return null;
	}
	
	public static ByteBuffer toByteBuffer(InputStream input) {
		return BufferUtils.createByteBuffer(toByteArray(input));
	}
}
