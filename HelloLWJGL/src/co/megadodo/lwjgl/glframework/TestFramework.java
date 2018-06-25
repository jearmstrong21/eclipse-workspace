package co.megadodo.lwjgl.glframework;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.io.File;
import java.nio.*;
import java.util.Scanner;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL21.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL31.*;
import static org.lwjgl.opengl.GL32.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.opengl.GL40.*;
import static org.lwjgl.opengl.GL41.*;
import static org.lwjgl.opengl.GL42.*;
import static org.lwjgl.opengl.GL43.*;
import static org.lwjgl.opengl.GL44.*;
import static org.lwjgl.opengl.GL45.*;
import static org.lwjgl.opengl.GL46.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class TestFramework {

	public static void main(String[] args) {

		if (!glfwInit()) {
			System.out.println("GLFW not init.");
			return;
		}

		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		long window = glfwCreateWindow(500, 500, "Window", NULL, NULL);
		if (window == NULL) {
			System.out.println("Window not create.");
			return;
		}

		glfwMakeContextCurrent(window);
		glfwSwapInterval(1);
		glfwShowWindow(window);

		GL.createCapabilities();

		Utilities.printGLInfo();

		ShaderProgram shader = new ShaderProgram();
		shader.gen();
		shader.attach(ShaderProgram.compileShaderFiles(GL_VERTEX_SHADER, "vertex", "Shaders/shader.vert"));
		shader.attach(ShaderProgram.compileShaderFiles(GL_FRAGMENT_SHADER, "fragment", "Shaders/shader.frag"));
		shader.link();

		float[] vboData = new float[] { 0, 0, 0, 1, 0, 0, 0, 1, 0 };

		VertexArray vao=new VertexArray();
		int vbo = glGenBuffers();

		vao.gen();
		vao.bind();

		glBindBuffer(GL_ARRAY_BUFFER, vbo);

		FloatBuffer buf = BufferUtils.createFloatBuffer(vboData.length);
		buf.put(vboData).flip();

		glBufferData(GL_ARRAY_BUFFER, buf, GL_STATIC_DRAW);
		glEnableVertexAttribArray(0);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 3 * Float.BYTES, 0);

		vao.unbind();


		while (!glfwWindowShouldClose(window)) {
			glClearColor(1, 1, 1, 1);
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

			shader.bind();

			vao.bind();
			// glDrawElements(GL_TRIANGLES, indices);
			glDrawArrays(GL_TRIANGLES, 0, 3);
			
			vao.unbind();

			shader.unbind();

			glfwSwapBuffers(window);
			glfwPollEvents();
		}
		shader.delete();

		glfwDestroyWindow(window);
		glfwTerminate();

	}

}