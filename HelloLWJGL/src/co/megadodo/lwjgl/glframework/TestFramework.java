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

		GLWindow.initGLFW();
		GLWindow window=new GLWindow();
		window.hints();
		window.gen();
		window.setSize(500,500);
		window.setTitle("Test Framework");
		window.show();
		window.bind();

		GLUtilities.printGLInfo();

		ShaderProgram shader = new ShaderProgram();
		shader.gen();
		shader.attach(ShaderProgram.compileShaderFiles(GL_VERTEX_SHADER, "vertex", "Shaders/shader.vert"));
		shader.attach(ShaderProgram.compileShaderFiles(GL_FRAGMENT_SHADER, "fragment", "Shaders/shader.frag"));
		shader.link();

		float[] vboPosData = new float[] { -1,-1,0,  -1,1,0,   1,1,0,   1,-1,0};
		float[] vboUVData=new float[] {    0,0,      0,1,      1,1,     1,0};
		float[] vboColData=new float[] {   1,0.75f,0.75f,    0.75f,1,0.75f,    0.75f,0.75f,1,   1,1,1};
		
		byte[]triData=new byte[] {0,1,2,0,2,3};
			
		VertexArray vao=new VertexArray();
		VertexBuffer vboPos=new VertexBuffer();
		vboPos.target=BufferTarget.Array;
		vboPos.usage=BufferUsage.StaticDraw;
		VertexBuffer vboUV=new VertexBuffer();
		vboUV.target=BufferTarget.Array;
		vboUV.usage=BufferUsage.StaticDraw;
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
		
		vboUV.gen();
		vboUV.bind();
		vboUV.setData(vboUVData);
		vboUV.addVertexAttrib(1, 2, AttribType.Float, false, 2, 0);
		vboUV.unbind();
		
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
		
		Texture tex1=Texture.loadTexture("Images/Zoe1.png");
		Texture tex2=Texture.loadTexture("Images/smiley.png");

		while (window.isOpen()) {
			window.bind();
			glClearColor(1, 1, 1, 1);
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			
			
			shader.bind();
			
			tex1.bindToUnit(0);
			tex2.bindToUnit(1);
			
			
			Mat4 model=Mat4.MAT4_IDENTITY;
			Mat4 view=Mat4.MAT4_IDENTITY;
			Mat4 projection=Mat4.MAT4_IDENTITY;
			shader.setMat4("model", model);
			shader.setMat4("view", view);
			shader.setMat4("projection", projection);
			shader.setInt("tex1", 0);
			shader.setInt("tex2", 1);
			shader.setFloat("blendTex", Mathf.cos(GLUtilities.getTime())*0.5f+0.5f);
			shader.setFloat("blendCol", Mathf.cos(GLUtilities.getTime()*5+10)*0.5f+0.5f);

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
		vboUV .delete();
		vboCol.delete();
		eboBuf.delete();

		tex1.delete();
		tex2.delete();

		window.delete();
		
		GLWindow.endGLFW();

	}

}