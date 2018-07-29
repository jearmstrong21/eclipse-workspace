package test.scene3d;

import org.joml.Matrix4f;

import co.megadodo.lwjgl.glframework.Mathf;
import co.megadodo.lwjgl.glframework.buffer.AttribType;
import co.megadodo.lwjgl.glframework.buffer.BufferTarget;
import co.megadodo.lwjgl.glframework.buffer.BufferUsage;
import co.megadodo.lwjgl.glframework.buffer.PolygonMode;
import co.megadodo.lwjgl.glframework.buffer.ProvokingVertex;
import co.megadodo.lwjgl.glframework.buffer.VertexArray;
import co.megadodo.lwjgl.glframework.buffer.VertexBuffer;
import co.megadodo.lwjgl.glframework.model.Mesh;
import co.megadodo.lwjgl.glframework.model.ModelLoader;
import co.megadodo.lwjgl.glframework.shader.ShaderProgram;
import co.megadodo.lwjgl.glframework.shader.ShaderType;
import co.megadodo.lwjgl.glframework.utils.GLUtilities;
import co.megadodo.lwjgl.glframework.window.GLWindow;
import co.megadodo.lwjgl.glframework.window.ProfileType;

public class TestScene3D {
	
	public static class Teapot{
		VertexArray vao;
		VertexBuffer vboPos;
		VertexBuffer vboUV;
		VertexBuffer vboNorms;
		VertexBuffer ebo;
		Mesh mesh;
		public Teapot(Mesh m) {
			mesh=m;
		}
		
		public void gen() {
			vao=new VertexArray();
			vao.gen();
			vao.bind();
			
			vboPos=new VertexBuffer();
			vboPos.usage=BufferUsage.StaticDraw;
			vboPos.target=BufferTarget.Array;
			vboPos.gen();
			vboPos.bind();
			vboPos.setData(mesh.pos);
			vboPos.addVertexAttrib(0, 3, AttribType.Float, false, 3, 0);
			vboPos.unbind();
			
			vboUV=new VertexBuffer();
			vboUV.usage=BufferUsage.StaticDraw;
			vboUV.target=BufferTarget.Array;
			vboUV.gen();
			vboUV.bind();
			vboUV.setData(mesh.uv);
			vboUV.addVertexAttrib(1, 2, AttribType.Float, false, 2, 0);
			vboUV.unbind();
			
			vboNorms=new VertexBuffer();
			vboNorms.usage=BufferUsage.StaticDraw;
			vboNorms.target=BufferTarget.Array;
			vboNorms.gen();
			vboNorms.bind();
			vboNorms.setData(mesh.normal);
			vboNorms.addVertexAttrib(2, 3, AttribType.Float, true, 3, 0);
			vboNorms.unbind();
			
			ebo=new VertexBuffer();
			ebo.usage=BufferUsage.StaticDraw;
			ebo.target=BufferTarget.ElementArray;
			ebo.gen();
			ebo.bind();
			ebo.setDataUnsigned(mesh.faces);
			ebo.unbind();
			
			vao.unbind();
		}
		
		public void render() {
			vao.bind();
			ebo.bind();
			ebo.render(ProvokingVertex.First, PolygonMode.Fill);
			ebo.unbind();
			vao.unbind();
		}
		
		public void delete() {
			vao.delete();
			vboPos.delete();
			vboUV.delete();
			vboNorms.delete();
			ebo.delete();
		}
	}
	
	public static void main(String[]args) {
		GLWindow.initGLFW();
		
		GLWindow window=new GLWindow();
		window.hints(4, 1, true, ProfileType.Core);
		window.gen();
		window.setSize(500, 500);
		window.setTitle("Test 3D scene");
		window.bind();
		
		Mesh mesh=ModelLoader.loadModel("Models/teapot.obj")[0];
		
		
		Teapot tp=new Teapot(mesh);
		tp.gen();
		
		ShaderProgram shader=new ShaderProgram();
		shader.gen();
		shader.attach("Shaders/test/scene3d/teapot.frag", ShaderType.Fragment);
		shader.attach("Shaders/test/scene3d/teapot.vert", ShaderType.Vertex);
		shader.link();
		
		int NORMALS=0;
		int UV=1;
		int type=NORMALS;
		
		GLUtilities.printGLInfo();
		
		while(window.isOpen()) {
			window.bind();
//			window.clearInputs();
			GLUtilities.clearScreen(1, 1, 1);
			GLUtilities.enableDepth();
			
			float time=GLUtilities.getTime();
			
			if(window.isKeyDown('1'))type=NORMALS;
			if(window.isKeyDown('2'))type=UV;
			
			shader.bind();
			shader.setRoutine(ShaderType.Fragment, type==NORMALS?"shadeNormals":"shadeUV");
			shader.setMat4("proj", new Matrix4f().identity().perspective(Mathf.toRadians(80), 1, 0.01f, 100));
			shader.setMat4("model", new Matrix4f().identity());
			shader.setMat4("view", new Matrix4f().identity().lookAt(30*Mathf.cos(time),15,30*Mathf.sin(time),  0,0,0,  0,1,0));
			tp.render();
			shader.unbind();
			
			window.unbind();
		}
		
		tp.delete();
		
		GLWindow.endGLFW();
	}
	
}
