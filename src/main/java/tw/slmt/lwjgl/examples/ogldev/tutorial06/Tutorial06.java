package tw.slmt.lwjgl.examples.ogldev.tutorial06;

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

import tw.slmt.lwjgl.examples.ogldev.OgldevUtil;

public class Tutorial06 {

	// Note that this program should run with VM argument
	// '-XstartOnFirstThread' for OS X

	private static final String WINDOW_TITLE = "Tutorial 6 - Translation Transformation";
	private static final String VERT_SHADER_FILE = OgldevUtil.RESOURCE_DIR_PATH + "/tutorial06/shader.vs";
	private static final String FRAG_SHADER_FILE = OgldevUtil.RESOURCE_DIR_PATH + "/tutorial06/shader.fs";

	private static int vboId;
	private static int worldLocation;
	private static float scale = 0.0f;
	private static FloatBuffer worldMatBuf = BufferUtils.createFloatBuffer(4 * 4);

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
		
		compileShaders();

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
		float[][] vertices = new float[3][];
		vertices[0] = new float[] { -1.0f, -1.0f, 0.0f };
		vertices[1] = new float[] { 1.0f, -1.0f, 0.0f };
		vertices[2] = new float[] { 0.0f, 1.0f, 0.0f };
		FloatBuffer vertBuffer = BufferUtils.createFloatBuffer(3 * 3);
		for (int i = 0; i < vertices.length; i++)
			vertBuffer.put(vertices[i]);
		vertBuffer.rewind();

		// A specialized version for generating one buffer
		vboId = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertBuffer, GL15.GL_STATIC_DRAW);
	}

	private static void addShader(int shaderProgram, String shaderText,
			int shaderType) {
		int shaderObj = GL20.glCreateShader(shaderType);
		if (shaderObj == 0)
			throw new RuntimeException("Error creating shader type "
					+ shaderType + "\n");
		
		// A Java-Single-String version of glShaderSource
		GL20.glShaderSource(shaderObj, shaderText);
		GL20.glCompileShader(shaderObj);
		int success = GL20.glGetShaderi(shaderObj, GL20.GL_COMPILE_STATUS);
		if (success == 0) { 
			String infoLog = GL20.glGetShaderInfoLog(shaderObj);
			System.err.print(infoLog);
			System.exit(1);
		}
		
		GL20.glAttachShader(shaderProgram, shaderObj);
	}

	private static void compileShaders() {
		int shaderProgram = GL20.glCreateProgram();
		if (shaderProgram == 0)
			throw new RuntimeException("Error creating shader program\n");

		String vs = OgldevUtil.readFile(VERT_SHADER_FILE);
		String fs = OgldevUtil.readFile(FRAG_SHADER_FILE);

		if (vs == null)
			throw new RuntimeException("Error reading vertex shader files\n");
		if (fs == null)
			throw new RuntimeException("Error reading fragment shader files\n");
		
		addShader(shaderProgram, vs, GL20.GL_VERTEX_SHADER);
		addShader(shaderProgram, fs, GL20.GL_FRAGMENT_SHADER);
		
		int success;
		GL20.glLinkProgram(shaderProgram);
		success = GL20.glGetProgrami(shaderProgram, GL20.GL_LINK_STATUS);
		if (success == 0) { 
			String infoLog = GL20.glGetProgramInfoLog(shaderProgram);
			System.err.print(infoLog);
			System.exit(1);
		}
		
		GL20.glValidateProgram(shaderProgram);
		success = GL20.glGetProgrami(shaderProgram, GL20.GL_VALIDATE_STATUS);
		if (success == 0) { 
			String infoLog = GL20.glGetProgramInfoLog(shaderProgram);
			System.err.print(infoLog);
			System.exit(1);
		}
		
		GL20.glUseProgram(shaderProgram);
		
		worldLocation = GL20.glGetUniformLocation(shaderProgram, "world");
	}
	
	// Note that we are not using GLUT here.
	// Therefore, we do not need to register this function
	// as the description on OGLDev website.
	private static void renderScene() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		
		scale += 0.01f;
		
		float[] worldMatrix = new float[] {
			1.0f, 0.0f, 0.0f, (float) Math.sin(scale),
			0.0f, 1.0f, 0.0f, 0.0f,
			0.0f, 0.0f, 1.0f, 0.0f,
			0.0f, 0.0f, 0.0f, 1.0f
		};
		worldMatBuf.rewind();
		worldMatBuf.put(worldMatrix);
		worldMatBuf.rewind();
		
		GL20.glUniformMatrix4fv(worldLocation, true, worldMatBuf);

		GL20.glEnableVertexAttribArray(0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);

		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 3);

		GL20.glDisableVertexAttribArray(0);
	}
}
