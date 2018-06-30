package co.megadodo.lwjgl.glframework;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Matrices;
import com.hackoeur.jglm.Vec3;

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

		float[] vboPosData = new float[] { 0, 0, 0, 1, 0, 0, 0, 1, 0 };
		float[] vboColData = new float[] { 1,0,0,0,1,0,0,0,1,0};
		byte[]triData=new byte[] {0,1,2};
		
		VertexArray vao=new VertexArray();
		VertexBuffer vboPos=new VertexBuffer();
		vboPos.target=BufferTarget.Array;
		vboPos.usage=BufferUsage.StaticDraw;
		VertexBuffer vboCol=new VertexBuffer();
		vboCol.target=BufferTarget.Array;
		vboCol.usage=BufferUsage.StaticDraw;
		VertexBuffer eboBuf=new VertexBuffer();
		eboBuf.target=BufferTarget.ElementArray;
		eboBuf.usage=BufferUsage.StaticDraw;

		vao.gen();
		vao.bind();

		vboPos.gen();
		vboPos.bind();
		vboPos.setData(vboPosData);
		vboPos.addVertexAttrib(0, 3, AttribType.Float, false, 3, 0);
		vboPos.unbind();
		
		vboCol.gen();
		vboCol.bind();
		vboCol.setData(vboColData);
		vboCol.addVertexAttrib(1, 3, AttribType.Float, false, 3, 0);
		vboCol.unbind();

		eboBuf.gen();
		eboBuf.bind();
		eboBuf.setDataUnsigned(triData);
		eboBuf.unbind();
		
		vao.unbind();
		System.out.printf("vao=%d, vboPos=%d, vboCol=%d, ebo=%d, shader=%d\n",vao.id,vboPos.id,vboCol.id,eboBuf.id,shader.id);

		while (!glfwWindowShouldClose(window)) {
			glClearColor(1, 1, 1, 1);
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

			shader.bind();
			
			Mat4 model=Mat4.MAT4_IDENTITY;
			Mat4 view=Matrices.translate(new Vec3(0,0.2f,0));
			Mat4 projection=Mat4.MAT4_IDENTITY;
			shader.setMat4("model", model);
			shader.setMat4("view", view);
			shader.setMat4("projection", projection);

			vao.bind();
			
			eboBuf.bind();
			eboBuf.render();
			eboBuf.unbind();

			vao.unbind();

			shader.unbind();

			glfwSwapBuffers(window);
			glfwPollEvents();
		}
		shader.delete();
		
		vao.delete();
		
		vboPos.delete();
		vboCol.delete();
//		eboBuf.delete();

		glfwDestroyWindow(window);
		glfwTerminate();

	}

}