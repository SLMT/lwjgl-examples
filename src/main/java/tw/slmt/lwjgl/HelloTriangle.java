package tw.slmt.lwjgl;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glVertex3f;
import static org.lwjgl.opengl.GL11.glViewport;

import java.nio.IntBuffer;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;

public class HelloTriangle {
	
	// Note that this program should run with VM argument
	// '-XstartOnFirstThread' for OS X
	
	public static void main(String[] args)
	{
		GLFW.glfwSetErrorCallback(GLFWErrorCallback.createPrint());
		if (GLFW.glfwInit() != GLFW.GLFW_TRUE)
			System.exit(-1);
		
		long windowHandle = GLFW.glfwCreateWindow(640, 480, "Hello Triangle", MemoryUtil.NULL, MemoryUtil.NULL);
		if (windowHandle == MemoryUtil.NULL) {
			GLFW.glfwTerminate();
			System.exit(-1);
		}
		
		GLFW.glfwMakeContextCurrent(windowHandle);
		GLFW.glfwSwapInterval(1);
		GLFW.glfwSetKeyCallback(windowHandle, new GLFWKeyCallback() {

			@Override
			public void invoke(long window, int key, int scancode, int action,
					int mods) {
				System.out.println("Press key " + ((char) key));
			}
			
		});
		
		// This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
		GL.createCapabilities();
		
		while (GLFW.glfwWindowShouldClose(windowHandle) != GLFW.GLFW_TRUE)
	    {
			IntBuffer widthBuf = MemoryUtil.memAllocInt(1);
			IntBuffer heightBuf = MemoryUtil.memAllocInt(1);
			
	        GLFW.glfwGetFramebufferSize(windowHandle, widthBuf, heightBuf);
			
	        draw(widthBuf.get(), heightBuf.get());
	        
	        GLFW.glfwSwapBuffers(windowHandle);
	        GLFW.glfwPollEvents();
	    }
		
		GLFW.glfwDestroyWindow(windowHandle);
		GLFW.glfwTerminate();
	}
	
	private static void draw(int width, int height) {
		float ratio = (float) width / (float) height;
		float time = (float) GLFW.glfwGetTime();
		
		glViewport(0, 0, width, height);
        glClear(GL_COLOR_BUFFER_BIT);
        
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(-ratio, ratio, -1.f, 1.f, 1.f, -1.f);
        
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        glRotatef(time * 50.f, 0.f, 1.f, 0.f);
        
        glBegin(GL_TRIANGLES);
        glColor3f(1.f, 0.f, 0.f);
        glVertex3f(-0.6f, -0.4f, 0.f);
        glColor3f(0.f, 1.f, 0.f);
        glVertex3f(0.6f, -0.4f, 0.f);
        glColor3f(0.f, 0.f, 1.f);
        glVertex3f(0.f, 0.6f, 0.f);
        glEnd();
	}
}
