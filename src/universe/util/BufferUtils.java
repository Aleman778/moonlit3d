package universe.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public final class BufferUtils {

    private BufferUtils() {}
    
    public static ByteBuffer createByteBuffer(byte... data) {
        ByteBuffer result = ByteBuffer.allocateDirect(data.length).order(ByteOrder.nativeOrder());
        result.put(data).flip();
        return result;
    }
    
    public static IntBuffer createIntBuffer(int... data) {
        IntBuffer result = ByteBuffer.allocateDirect(data.length * Integer.BYTES).order(ByteOrder.nativeOrder()).asIntBuffer();
        result.put(data).flip();
        return result;
    }
    
    public static FloatBuffer createFloatBuffer(float... data) {
        FloatBuffer result = ByteBuffer.allocateDirect(data.length * Float.BYTES).order(ByteOrder.nativeOrder()).asFloatBuffer();
        result.put(data).flip();
        return result;
    }
    
    public static DoubleBuffer createDoubleBuffer(double... data) {
        DoubleBuffer result = ByteBuffer.allocateDirect(data.length * Double.BYTES).order(ByteOrder.nativeOrder()).asDoubleBuffer();
        result.put(data).flip();
        return result;
    }
}
