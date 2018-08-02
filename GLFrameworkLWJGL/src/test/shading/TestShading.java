package test.shading;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import co.megadodo.lwjgl.glframework.buffer.AttribType;
import co.megadodo.lwjgl.glframework.buffer.BufferTarget;
import co.megadodo.lwjgl.glframework.buffer.BufferUsage;
import co.megadodo.lwjgl.glframework.buffer.VertexArray;
import co.megadodo.lwjgl.glframework.buffer.VertexBuffer;
import co.megadodo.lwjgl.glframework.model.Mesh;
import co.megadodo.lwjgl.glframework.model.ModelLoader;
import co.megadodo.lwjgl.glframework.shader.ShaderProgram;
import co.megadodo.lwjgl.glframework.shader.ShaderType;
import co.megadodo.lwjgl.glframework.utils.GLUtilities;
import co.megadodo.lwjgl.glframework.utils.Mathf;
import co.megadodo.lwjgl.glframework.window.GLWindow;
import co.megadodo.lwjgl.glframework.window.ProfileType;

public class TestShading {
	
	public static void main(String[]args) {
		GLWindow.initGLFW();
		
		GLWindow window=new GLWindow();
		window.hints(4, 1, true, ProfileType.Core);
		window.gen();
		window.setTitle("Test shading");
		window.setSize(500, 500);
		window.bind();
		
		GLUtilities.printGLInfo();
		
		float scale=15;
		Mesh mesh=ModelLoader.loadModel("Models/ogre.obj")[0];
		
		VertexArray vao=new VertexArray();
		vao.gen();
		vao.bind();
		
		VertexBuffer vboPos=new VertexBuffer();
		vboPos.usage=BufferUsage.StaticDraw;
		vboPos.target=BufferTarget.Array;
		vboPos.gen();
		vboPos.bind();
		vboPos.setData(mesh.pos);
		vboPos.addVertexAttrib(0, 3, AttribType.Float, false, 3, 0);
		vboPos.unbind();
		
		VertexBuffer vboNorm=new VertexBuffer();
		vboNorm.usage=BufferUsage.StaticDraw;
		vboNorm.target=BufferTarget.Array;
		vboNorm.gen();
		vboNorm.bind();
		vboNorm.setData(mesh.normal);
		vboNorm.addVertexAttrib(1, 3, AttribType.Float, true, 3, 0);
		vboNorm.unbind();
		
		VertexBuffer ebo=new VertexBuffer();
		ebo.usage=BufferUsage.StaticDraw;
		ebo.target=BufferTarget.ElementArray;
		ebo.gen();
		ebo.bind();
		ebo.setDataUnsigned(mesh.faces);
		ebo.unbind();
		
		vao.unbind();
		
		ShaderProgram shader=new ShaderProgram();
		shader.gen();
		shader.attach("Shaders/test/shading/shader.vert",ShaderType.Vertex);
		shader.attach("Shaders/test/shading/shader.frag",ShaderType.Fragment);
		shader.link();
		
		while(window.isOpen()) {
			window.bind();
			
			GLUtilities.clearScreen(1, 1, 1);
			GLUtilities.enableDepth();
			
			
			shader.bind();
//			float time=1.2f;
			float time=GLUtilities.getTime();
			
			float eyeX=30;
			float eyeY=20;
			float eyeZ=30;
			
			shader.setVec3("lights[0].diffuse", 0.8f, 0, 0);
			float lightRot=time;
			float teapotRot=0;
			shader.setVec3("lights[0].pos", 30*Mathf.cos(lightRot), 20, 30*Mathf.sin(lightRot));
			
			shader.setVec3("eyePos", eyeX, eyeY, eyeZ);
			shader.setVec3("lights[0].specular", 0, 1, 0);
			shader.setFloat("lights[0].shininess", 10);
			shader.setFloat("lights[0].Kd", 1);
			shader.setFloat("lights[0].Ks", 1);
			shader.setFloat("lights[0].Ka", 1);
			
			shader.setVec3("lights[0].ambient", 0, 0, 0.25f);
			
			shader.setInt("numLights", 2);
			
			shader.setVec3("lights[1].diffuse", 0,0.8f,0);
			shader.setVec3("lights[1].pos", 30, 20, 30);
			shader.setVec3("lights[1].specular", 0, 0, 1);
			shader.setFloat("lights[1].shininess", 10);
			shader.setVec3("lights[1].ambient", 0, 0, 0);
			shader.setFloat("lights[1].Kd", 1);
			shader.setFloat("lights[1].Ks", 0);
			shader.setFloat("lights[1].Ka", 1);

			
			Matrix4f proj=new Matrix4f().identity().perspective(Mathf.toRadians(80), 1, 0.01f, 100);
			Matrix4f view=new Matrix4f().identity().lookAt(eyeX,eyeY,eyeZ,  0,0,0,  0,1,0);
			Matrix4f model=new Matrix4f().identity().rotate(teapotRot, new Vector3f(0,1,0)).scale(scale);
			shader.setMat4("proj", proj);
			shader.setMat4("view", view);
			shader.setMat4("model", model);
			vao.bind();
			ebo.bind();
			ebo.render();
			ebo.unbind();
			vao.unbind();
			shader.unbind();
			
			window.unbind();
		}
		
		window.delete();
		
		GLWindow.endGLFW();
	}

}
