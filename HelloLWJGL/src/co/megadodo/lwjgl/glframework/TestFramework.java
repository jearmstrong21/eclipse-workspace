package co.megadodo.lwjgl.glframework;

import org.joml.*;
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

		GLWindow.initGLFW();
		GLWindow window = new GLWindow();
		window.hints();
		window.gen();
		window.setSize(500, 500);
		window.setTitle("Test Framework");
		window.show();
		window.bind();

		GLUtilities.printGLInfo();

		ShaderProgram shader = new ShaderProgram();
		shader.gen();
		shader.attach(ShaderProgram.compileShaderFiles(GL_VERTEX_SHADER, "vertex", "Shaders/shader.vert"));
		shader.attach(ShaderProgram.compileShaderFiles(GL_FRAGMENT_SHADER, "fragment", "Shaders/shader.frag"));
		shader.link();

		float[] vboPosData = new float[] { -1, -1, -1, -1, -1, 1, -1, 1, 1, -1, 1, -1,

				1, -1, -1, 1, -1, 1, 1, 1, 1, 1, 1, -1,

				-1, -1, -1, -1, -1, 1, 1, -1, 1, 1, -1, -1,

				-1, 1, -1, -1, 1, 1, 1, 1, 1, 1, 1, -1,

				-1, -1, -1, -1, 1, -1, 1, 1, -1, 1, -1, -1,

				-1, -1, 1, -1, 1, 1, 1, 1, 1, 1, -1, 1 };
		float[] vboColData = new float[] { 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 0,

				1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 0,

				0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 0,

				0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0,

				0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 0, 0,

				0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1 };

		byte[] triData = new byte[] { 0, 1, 2, 0, 2, 3,

				4, 5, 6, 4, 6, 7,

				8, 9, 10, 8, 10, 11,

				12, 13, 14, 12, 14, 15,

				16, 17, 18, 16, 18, 19,

				20, 21, 22, 20, 22, 23 };

		VertexArray vao = new VertexArray();
		VertexBuffer vboPos = new VertexBuffer();
		vboPos.target = BufferTarget.Array;
		vboPos.usage = BufferUsage.StaticDraw;
		// VertexBuffer vboUV=new VertexBuffer();
		// vboUV.target=BufferTarget.Array;
		// vboUV.usage=BufferUsage.StaticDraw;
		VertexBuffer vboCol = new VertexBuffer();
		vboCol.target = BufferTarget.Array;
		vboCol.usage = BufferUsage.StaticDraw;
		VertexBuffer eboBuf = new VertexBuffer();
		eboBuf.target = BufferTarget.ElementArray;
		eboBuf.usage = BufferUsage.StaticDraw;

		vao.gen();
		vao.bind();

		vboPos.gen();
		vboPos.bind();
		vboPos.setData(vboPosData);
		vboPos.addVertexAttrib(0, 3, AttribType.Float, false, 3, 0);
		vboPos.unbind();

		// vboUV.gen();
		// vboUV.bind();
		// vboUV.setData(vboUVData);
		// vboUV.addVertexAttrib(1, 2, AttribType.Float, false, 2, 0);
		// vboUV.unbind();

		vboCol.gen();
		vboCol.bind();
		vboCol.setData(vboColData);
		vboCol.addVertexAttrib(2, 3, AttribType.Float, false, 3, 0);
		vboCol.unbind();

		eboBuf.gen();
		eboBuf.bind();
		eboBuf.setDataUnsigned(triData);
		eboBuf.unbind();

		vao.unbind();

		// Texture tex1=Texture.loadTexture("Images/Zoe1.png");
		// Texture tex2=Texture.loadTexture("Images/smiley.png");

		Vector3f camPos=new Vector3f(0,0,0);
		Vector3f camDir=new Vector3f(1,0,0);
		
		float camRotX=0,camRotY=0;
		
		while (window.isOpen()) {
			window.bind();
			glClearColor(1, 1, 1, 1);
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			glEnable(GL_DEPTH_TEST);
			glDepthFunc(GL_LESS);
			//B(Ax)=(BA)x;
			
			Matrix4f projection=new Matrix4f().identity().perspective(Mathf.toRadians(80.0f), 1.0f, 0.01f, 10.0f);

			
			float ncamRotY=Mathf.map(window.getMouseX(), 0, window.getWidth(), -Mathf.PI, Mathf.PI);
			float ncamRotX=Mathf.map(window.getMouseY(), 0, window.getHeight(), -Mathf.PI/3, Mathf.PI/3);
			
			camRotY=ncamRotY;
			camRotX=ncamRotX;
			
			camDir=new Vector3f(1,0,0);
			camDir.rotateY(ncamRotY);
			camDir.rotateX(ncamRotX);
			
			Matrix4f trans=new Matrix4f().identity().translate(camPos);

			Quaternionf quatX=new Quaternionf().identity().rotate(camRotX, 0, 0);
			Quaternionf quatY=new Quaternionf().identity().rotate(0, camRotY, 0);
			Matrix4f matRotX=quatX.get(new Matrix4f());
			Matrix4f matRotY=quatY.get(new Matrix4f());
			
			float speed=0.01f;
			if(window.isKeyDown('W')) {
				camPos.add(camDir.mul(-speed,new Vector3f()));
			}
			if(window.isKeyDown('S')) {
				camPos.add(camDir.mul(speed,new Vector3f()));
			}
			System.out.println(camDir+" "+ncamRotY+" "+ncamRotX);
			
			Matrix4f viewRot=new Matrix4f().identity().mul(matRotX).mul(matRotY);
			
			Matrix4f view=new Matrix4f().mul(viewRot).mul(trans);

			

			
			shader.bind();
			shader.setMat4("projection", projection);
			shader.setMat4("view", view);
			shader.setMat4("model", new Matrix4f().identity());

			vao.bind();

			eboBuf.bind();
			eboBuf.render();
			eboBuf.unbind();

			vao.unbind();

			shader.unbind();

			window.unbind();
		}
		shader.delete();

		vao.delete();

		vboPos.delete();
		// vboUV .delete();
		vboCol.delete();
		eboBuf.delete();

		// tex1.delete();
		// tex2.delete();

		window.delete();

		GLWindow.endGLFW();

	}
}