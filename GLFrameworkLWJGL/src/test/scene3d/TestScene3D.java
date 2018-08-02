package test.scene3d;

import org.joml.Matrix4f;

import co.megadodo.lwjgl.glframework.buffer.AttribType;
import co.megadodo.lwjgl.glframework.buffer.BufferTarget;
import co.megadodo.lwjgl.glframework.buffer.BufferUsage;
import co.megadodo.lwjgl.glframework.buffer.VertexArray;
import co.megadodo.lwjgl.glframework.buffer.VertexBuffer;
import co.megadodo.lwjgl.glframework.framebuffer.FBOAttachment;
import co.megadodo.lwjgl.glframework.framebuffer.Framebuffer;
import co.megadodo.lwjgl.glframework.model.Mesh;
import co.megadodo.lwjgl.glframework.model.ModelLoader;
import co.megadodo.lwjgl.glframework.shader.ShaderProgram;
import co.megadodo.lwjgl.glframework.shader.ShaderType;
import co.megadodo.lwjgl.glframework.texture.Texture;
import co.megadodo.lwjgl.glframework.utils.GLUtilities;
import co.megadodo.lwjgl.glframework.utils.Mathf;
import co.megadodo.lwjgl.glframework.window.GLWindow;
import co.megadodo.lwjgl.glframework.window.ProfileType;

public class TestScene3D {
	
	public static class ModelRenderer{
		VertexArray vao;
		VertexBuffer vboPos;
		VertexBuffer vboUV;
		VertexBuffer vboNorms;
		VertexBuffer ebo;
		Mesh mesh;
		public ModelRenderer(Mesh m) {
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
			ebo.render();
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
	
	public static class CubeRenderer {
		VertexArray vao;
		VertexBuffer vboPos,vboUV;
		VertexBuffer ebo;
		public CubeRenderer() {
			
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
			vboPos.setData(new float[] {-1,-1,-1, -1,-1,1,  -1,1,1,  -1,1,-1,
									1,-1,-1,  1,-1,1,  1,1,1,  1,1,-1,
									-1,-1,-1,  -1,-1,1,  1,-1,1,  1,-1,-1,
									-1,1,-1,  -1,1,1,  1,1,1,  1,1,-1,
									-1,-1,-1, -1,1,-1,  1,1,-1,  1,-1,-1,
									-1,-1,1,  -1,1,1,  1,1,1,  1,-1,1});
			vboPos.addVertexAttrib(0, 3, AttribType.Float, false, 3, 0);
			vboPos.unbind();
			
			vboUV=new VertexBuffer();
			vboUV.usage=BufferUsage.StaticDraw;
			vboUV.target=BufferTarget.Array;
			vboUV.gen();
			vboUV.bind();
			vboUV.setData(new float[] {0,0,  1,0,  1,1,  0,1,
										0,0, 1,0,  1,1,  0,1,
										0,0,  1,0,  1,1,  0,1,
										0,0,  1,0,  1,1,  0,1,
										0,0,  1,0,  1,1,  0,1,
										0,0,  1,0,  1,1,  0,1});
			vboUV.addVertexAttrib(1, 2, AttribType.Float, false, 2, 0);
			vboUV.unbind();
			
			ebo=new VertexBuffer();
			ebo.usage=BufferUsage.StaticDraw;
			ebo.target=BufferTarget.ElementArray;
			ebo.gen();
			ebo.bind();
			ebo.setDataUnsigned(new int[] {0,1,2,  0,2,3,
										   4,5,6,  4,6,7,
										   8,9,10, 8,10,11,
										   12,13,14,  12,14,15,
										   16,17,18,  16,18,19,
										   20,21,22,  20,22,23});
			ebo.unbind();
			
			vao.unbind();
		}
		
		public void render() {
			vao.bind();
			ebo.bind();
			ebo.render();
			ebo.unbind();
			vao.unbind();
		}
		
		public void delete() {
			vao.delete();
			vboPos.delete();
			vboUV.delete();
			ebo.delete();
		}
	}
	
	public static class PlaneRenderer{
		VertexArray vao;
		VertexBuffer vboPos,vboUV;
		VertexBuffer ebo;
		
		public PlaneRenderer() {
			
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
			vboPos.setData(new float[] {-1,-1,0,  -1,1,0,  1,1,0,  1,-1,0});
			vboPos.addVertexAttrib(0, 3, AttribType.Float, false, 3, 0);
			vboPos.unbind();
			
			vboUV=new VertexBuffer();
			vboUV.usage=BufferUsage.StaticDraw;
			vboUV.target=BufferTarget.Array;
			vboUV.gen();
			vboUV.bind();
			vboUV.setData(new float[] {0,0,  0,1,  1,1,  1,0});
			vboUV.addVertexAttrib(1, 2, AttribType.Float, false, 2, 0);
			vboUV.unbind();
			
			ebo=new VertexBuffer();
			ebo.usage=BufferUsage.StaticDraw;
			ebo.target=BufferTarget.ElementArray;
			ebo.gen();
			ebo.bind();
			ebo.setDataUnsigned(new int[] {0,1,2,  0,2,3});
			ebo.unbind();
			
			vao.unbind();
		}
		
		public void render() {
			vao.bind();
			ebo.bind();
			ebo.render();
			ebo.unbind();
			vao.unbind();
		}
		
		public void delete() {
			vao.delete();
			vboPos.delete();
			vboUV.delete();
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
		
		
//		String meshName="ogre";
//		float scale=30;
		String meshName="teapot";
		float scale=1;
		Mesh mesh=ModelLoader.loadModel("Models/"+meshName+".obj")[0];
		
		ModelRenderer model=new ModelRenderer(mesh);
		model.gen();
		
		ShaderProgram modelShader=new ShaderProgram();
		modelShader.gen();
		modelShader.attach("Shaders/test/scene3d/model.frag", ShaderType.Fragment);
		modelShader.attach("Shaders/test/scene3d/model.vert", ShaderType.Vertex);
		modelShader.link();
		
		ShaderProgram fxShader=new ShaderProgram();//fx=effects
		fxShader.gen();
		fxShader.attach("Shaders/test/scene3d/fx.frag", ShaderType.Fragment);
		fxShader.attach("Shaders/test/scene3d/fx.vert", ShaderType.Vertex);
		fxShader.link();
		
		int fboW=1000;
		int fboH=1000;
		
		Framebuffer fbo=new Framebuffer();
		fbo.gen();
		fbo.bind();
		fbo.createDepthRBO(fboW, fboH);
		Texture colBuffer=Texture.createEmptyTexture(fboW, fboH);
		fbo.attachTex(colBuffer, FBOAttachment.ColorAttachment0);
		
		if(!fbo.complete())System.exit(1);
		fbo.unbind();
		
		CubeRenderer cube=new CubeRenderer();
		cube.gen();
		
		PlaneRenderer plane=new PlaneRenderer();
		plane.gen();
		
		final int NORMALS=0;
		final int UV=1;
		int teapotRenderType=NORMALS;
		
		final int CUBE=0;
		final int PLANE=1;
		int fxDisplayType=PLANE;
		
		GLUtilities.printGLInfo();
		
		
		
		while(window.isOpen()) {
			window.bind();
			
			Matrix4f proj=new Matrix4f().identity().perspective(Mathf.toRadians(80), window.getWidth()/window.getHeight(), 0.01f, 100);
			
			fbo.bind();
			GLUtilities.clearScreen(0, 0, 0);
			GLUtilities.enableDepth();
			GLUtilities.setViewport(0, 0, fboW, fboH);
			
			float time=GLUtilities.getTime();
			
			if(window.isKeyDown('1'))teapotRenderType=NORMALS;
			if(window.isKeyDown('2'))teapotRenderType=UV;
			
			modelShader.bind();
			modelShader.setRoutine(ShaderType.Fragment, teapotRenderType==NORMALS?"shadeNormals":"shadeUV");
			modelShader.setMat4("proj", proj);
			modelShader.setMat4("model", new Matrix4f().identity().scale(scale));
			modelShader.setMat4("view", new Matrix4f().identity().lookAt(30*Mathf.cos(time),15,30*Mathf.sin(time),  0,0,0,  0,1,0));
			model.render();
			modelShader.unbind();
			
			fbo.unbind();
			

			GLUtilities.clearScreen(1, 1, 1);
			GLUtilities.enableDepth();
			GLUtilities.setViewport(0, 0, window.getFBOWidth(), window.getFBOHeight());
			
			fxShader.bind();
			
			colBuffer.bindToUnit(0);
			fxShader.setTexture("tex", 0);
			
			float blur=1;// Higher values on vector (non-luma) kernels create a chromatic abberation-like effect.
			fxShader.setFloat("texDX", blur/fboW);
			fxShader.setFloat("texDY", blur/fboH);
			
			fxShader.setRoutine(ShaderType.Fragment, "sobelLuma");
			
			fxShader.setMat4("model", new Matrix4f().identity());
			if(fxDisplayType==CUBE) {
				fxShader.setMat4("proj", proj);
				fxShader.setMat4("view", new Matrix4f().identity().lookAt(3*Mathf.cos(time),2,4*Mathf.sin(time),  0,0,0,  0,1,0));
				cube.render();
			}else if(fxDisplayType==PLANE) {
				fxShader.setMat4("proj", new Matrix4f().identity());
				fxShader.setMat4("view", new Matrix4f().identity());
				plane.render();
			}
			
			fxShader.unbind();
			
			
			window.unbind();
		}
		
		fbo.delete();
		
		model.delete();
		modelShader.delete();
		
		cube.delete();
		
		plane.delete();

		fxShader.delete();
		
		window.delete();
		
		GLWindow.endGLFW();
	}
	
}
