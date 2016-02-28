package tw.slmt.lwjgl.examples.ogldev.tutorial01;

import java.nio.IntBuffer;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

public class Tutorial01 {

	// Note that this program should run with VM argument
	// '-XstartOnFirstThread' for OS X

	private static final String WINDOW_TITLE = "Tutorial 1 - Open A Window";

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

		while (GLFW.glfwWindowShouldClose(windowHandle) != GLFW.GLFW_TRUE) {
			IntBuffer widthBuf = MemoryUtil.memAllocInt(1);
			IntBuffer heightBuf = MemoryUtil.memAllocInt(1);

			GLFW.glfwGetFramebufferSize(windowHandle, widthBuf, heightBuf);

			renderScene(widthBuf.get(), heightBuf.get());

			GLFW.glfwSwapBuffers(windowHandle);
			GLFW.glfwPollEvents();
		}

		GLFW.glfwDestroyWindow(windowHandle);
		GLFW.glfwTerminate();
	}

	private static void renderScene(int width, int height) {
		float ratio = (float) width / (float) height;
		float time = (float) GLFW.glfwGetTime();

		GL11.glViewport(0, 0, width, height);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(-ratio, ratio, -1.f, 1.f, 1.f, -1.f);

		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glRotatef(time * 50.f, 0.f, 1.f, 0.f);

		GL11.glBegin(GL11.GL_TRIANGLES);
		GL11.glColor3f(1.f, 0.f, 0.f);
		GL11.glVertex3f(-0.6f, -0.4f, 0.f);
		GL11.glColor3f(0.f, 1.f, 0.f);
		GL11.glVertex3f(0.6f, -0.4f, 0.f);
		GL11.glColor3f(0.f, 0.f, 1.f);
		GL11.glVertex3f(0.f, 0.6f, 0.f);
		GL11.glEnd();
	}
}
