package test.framebuffer;

import java.util.ArrayList;

import co.megadodo.lwjgl.glframework.Mathf;
import co.megadodo.lwjgl.glframework.buffer.AttribType;
import co.megadodo.lwjgl.glframework.buffer.BufferTarget;
import co.megadodo.lwjgl.glframework.buffer.BufferUsage;
import co.megadodo.lwjgl.glframework.buffer.PolygonMode;
import co.megadodo.lwjgl.glframework.buffer.ProvokingVertex;
import co.megadodo.lwjgl.glframework.buffer.VertexArray;
import co.megadodo.lwjgl.glframework.buffer.VertexBuffer;
import co.megadodo.lwjgl.glframework.framebuffer.FBOAttachment;
import co.megadodo.lwjgl.glframework.framebuffer.Framebuffer;
import co.megadodo.lwjgl.glframework.shader.ShaderProgram;
import co.megadodo.lwjgl.glframework.shader.ShaderType;
import co.megadodo.lwjgl.glframework.texture.Texture;
import co.megadodo.lwjgl.glframework.utils.GLUtilities;
import co.megadodo.lwjgl.glframework.utils.Utilities;
import co.megadodo.lwjgl.glframework.window.GLWindow;

public class TestFramebuffer {

	public static void main(String[] args) {
		GLWindow.initGLFW();

		GLWindow window = new GLWindow();
		window.hints();
		window.gen();
		window.setSize(500, 500);
		window.setTitle("Test framebuffer");
		window.bind();

		GLUtilities.printGLInfo();

		VertexArray vao = new VertexArray();
		vao.gen();
		vao.bind();

		ArrayList<Float> posData = new ArrayList<Float>();
		ArrayList<Integer> triData = new ArrayList<Integer>();
		int numSides = 25;
		float diffAng = Mathf.PI * 2 / numSides;
		for (int i = 0; i < numSides; i++) {
			float ang = i * diffAng;
			triData.add(i * 3 + 0);
			triData.add(i * 3 + 1);
			triData.add(i * 3 + 2);

			posData.add(Mathf.cos(ang));
			posData.add(Mathf.sin(ang));

			posData.add(Mathf.cos(ang + diffAng));
			posData.add(Mathf.sin(ang + diffAng));

			posData.add(0.0f);
			posData.add(0.0f);
		}

		VertexBuffer pos = new VertexBuffer();
		pos.target = BufferTarget.Array;
		pos.usage = BufferUsage.StaticDraw;
		pos.gen();
		pos.bind();
		pos.setData(Utilities.listToArrF(posData));
		pos.addVertexAttrib(0, 2, AttribType.Float, false, 2, 0);
		pos.unbind();

		VertexBuffer ebo = new VertexBuffer();
		ebo.target = BufferTarget.ElementArray;
		ebo.usage = BufferUsage.StaticDraw;
		ebo.gen();
		ebo.bind();
		ebo.setDataUnsigned(Utilities.listToArrI(triData));
		ebo.unbind();

		vao.unbind();

		ShaderProgram sp = new ShaderProgram();
		sp.gen();
		sp.attach("Shaders/test/framebuffer/circle.frag", ShaderType.Fragment);
		sp.attach("Shaders/test/framebuffer/circle.vert", ShaderType.Vertex);
		sp.link();
		
		Framebuffer fbo=new Framebuffer();
		fbo.gen();
		fbo.bind();
		
		Texture colBuffer=Texture.createTexture(window.getFBOWidth(), window.getFBOHeight(), null);
		fbo.attachTex(colBuffer, FBOAttachment.ColorAttachment0);
		
		if(!fbo.complete())System.exit(1);
		fbo.unbind();
		
		
		
		VertexArray vaoQuad=new VertexArray();
		vaoQuad.gen();
		vaoQuad.bind();
		VertexBuffer vboQuad=new VertexBuffer();
		vboQuad.target=BufferTarget.Array;
		vboQuad.usage=BufferUsage.StaticDraw;
		vboQuad.gen();
		vboQuad.bind();
		vboQuad.setData(new float[] {-1,-1,   -1,1,   1,1,  1,-1});
		vboQuad.addVertexAttrib(0, 2, AttribType.Float, false, 2, 0);
		vboQuad.unbind();
		VertexBuffer vboUV=new VertexBuffer();
		vboUV.target=BufferTarget.Array;
		vboUV.usage=BufferUsage.StaticDraw;
		vboUV.gen();
		vboUV.bind();
		vboUV.setData(new float[] {0,0,  0,1,  1,1,  1,0});
		vboUV.addVertexAttrib(1, 2, AttribType.Float, false, 2, 0);
		vboUV.unbind();
		VertexBuffer eboQuad=new VertexBuffer();
		eboQuad.target=BufferTarget.ElementArray;
		eboQuad.usage=BufferUsage.StaticDraw;
		eboQuad.gen();
		eboQuad.bind();
		eboQuad.setDataUnsigned(new int[] {0,1,2,  0,2,3});
		eboQuad.unbind();
		vaoQuad.unbind();
		
		ShaderProgram spQuad=new ShaderProgram();
		spQuad.gen();
		spQuad.attach("Shaders/test/framebuffer/fbo.frag", ShaderType.Fragment);
		spQuad.attach("Shaders/test/framebuffer/fbo.vert", ShaderType.Vertex);
		spQuad.link();
		

		while (window.isOpen()) {
			window.bind();
			fbo.bind();
			GLUtilities.clearScreen(0, 0, 0);
			GLUtilities.enableDepth();
			
			sp.bind();

			vao.bind();
			ebo.bind();
			ebo.render(ProvokingVertex.First, PolygonMode.Fill);
			ebo.unbind();
			vao.unbind();

			sp.unbind();
			fbo.unbind();
			
			
			GLUtilities.clearScreen(0, 0, 0);
			GLUtilities.disableDepth();
			
			spQuad.bind();
			
			colBuffer.bindToUnit(0);
			spQuad.setTexture("tex", 0);
			spQuad.setFloat("texW", window.getWidth());
			spQuad.setFloat("texH", window.getHeight());
			spQuad.setFloat("blurRad", (int)(window.getMouseX()/20.0f));
			vaoQuad.bind();
			eboQuad.bind();
			eboQuad.render(ProvokingVertex.First, PolygonMode.Fill);
			eboQuad.unbind();
			vaoQuad.unbind();
			spQuad.unbind();
			

			window.unbind();
		}
		window.delete();

		GLWindow.endGLFW();
	}

}
