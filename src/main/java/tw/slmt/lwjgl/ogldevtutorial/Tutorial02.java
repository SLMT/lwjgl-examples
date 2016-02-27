package tw.slmt.lwjgl.ogldevtutorial;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryUtil;

public class Tutorial02 {

	// Note that this program should run with VM argument
	// '-XstartOnFirstThread' for OS X

	private static final String WINDOW_TITLE = "Tutorial 2 - Hello Dot!";

	private static int vboId;

	public static void main(String[] args) {
		GLFW.glfwSetErrorCallback(GLFWErrorCallback.createPrint());
		if (GLFW.glfwInit() != GLFW.GLFW_TRUE)
			System.exit(-1);

		long windowHandle = GLFW.glfwCreateWindow(1024, 768, WINDOW_TITLE,
				MemoryUtil.NULL, MemoryUtil.NULL);
		if (windowHandle == MemoryUtil.NULL) {
			GLFW.glfwTerminate();
			System.exit(-1);
		}

		GLFW.glfwMakeContextCurrent(windowHandle);
		GLFW.glfwSwapInterval(1);

		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		//
		// - https://www.lwjgl.org/guide
		GL.createCapabilities();

		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		createVertexBuffer();

		while (GLFW.glfwWindowShouldClose(windowHandle) != GLFW.GLFW_TRUE) {
			IntBuffer widthBuf = MemoryUtil.memAllocInt(1);
			IntBuffer heightBuf = MemoryUtil.memAllocInt(1);

			GLFW.glfwGetFramebufferSize(windowHandle, widthBuf, heightBuf);

			renderScene();

			GLFW.glfwSwapBuffers(windowHandle);
			GLFW.glfwPollEvents();
		}

		GLFW.glfwDestroyWindow(windowHandle);
		GLFW.glfwTerminate();
	}

	private static void createVertexBuffer() {
		// Note that we should use BufferUtil instead of MemoryUtil here.
		// Or an undefined behavior will happen.
		float[] vertices = new float[] { 0.0f, 0.0f, 0.0f };
		FloatBuffer vertBuffer = BufferUtils.createFloatBuffer(vertices.length);
		vertBuffer.put(vertices);
		vertBuffer.rewind();
		
		// A specialized version for generating one buffer
		vboId = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertBuffer, GL15.GL_STATIC_DRAW);
	}

	private static void renderScene() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

		GL20.glEnableVertexAttribArray(0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);

		GL11.glDrawArrays(GL11.GL_POINTS, 0, 1);

		GL20.glDisableVertexAttribArray(0);
	}
}
