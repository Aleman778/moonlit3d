package universe.opengl;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL45.*;

public class GLException extends IllegalStateException {

	private static final long serialVersionUID = -2266850507182986123L;
	
	public GLException(int errcode) {
		super(getErrorMessage(errcode));
	}

	private static String getErrorMessage(int errcode) {
		switch (errcode) {
		case GL_INVALID_ENUM:
			return "(GL_INVALID_ENUM) Enum argument out of range.";
		case GL_INVALID_VALUE:
			return "(GL_INVALID_VALUE) Numeric argument out of range.";
		case GL_INVALID_OPERATION:
			return "(GL_INVALID_OPERATION) Operation illegal in current state.";
		case GL_STACK_OVERFLOW:
			return "(GL_STACK_OVERFLOW) Function would cause a stack overflow.";
		case GL_STACK_UNDERFLOW:
			return "(GL_STACK_UNDERFLOW) Function would cause a stack underflow.";
		case GL_OUT_OF_MEMORY:
			return "(GL_OUT_OF_MEMORY) Not enough memory left to execute function.";
		case GL_INVALID_FRAMEBUFFER_OPERATION:
			return "(GL_INVALID_FRAMEBUFFER_OPERATION: Operation performed on incomplete framebuffer.";
		case GL_CONTEXT_LOST:
			return "(GL_CONTEXT_LOST) Context is lost, due to a graphics card reset.";
		case GL_NO_ERROR:
			return "No error has been recorded";
		}
		return "";
	}
}
