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
			return "Enum argument out of range.";
		case GL_INVALID_VALUE:
			return "Numeric argument out of range.";
		case GL_INVALID_OPERATION:
			return "Operation illegal in current state.";
		case GL_STACK_OVERFLOW:
			return "Function would cause a stack overflow.";
		case GL_STACK_UNDERFLOW:
			return "Function would cause a stack underflow.";
		case GL_OUT_OF_MEMORY:
			return "Not enough memory left to execute function.";
		case GL_INVALID_FRAMEBUFFER_OPERATION:
			return "Operation performed on incomplete framebuffer.";
		case GL_CONTEXT_LOST:
			return "Context is lost, due to a graphics card reset.";
		case GL_NO_ERROR:
			return "No error has been recorded";
		}
		return "";
	}
}
