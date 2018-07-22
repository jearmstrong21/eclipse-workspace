package test.texture;

import co.megadodo.lwjgl.glframework.buffer.AttribType;
import co.megadodo.lwjgl.glframework.buffer.BufferTarget;
import co.megadodo.lwjgl.glframework.buffer.BufferUsage;
import co.megadodo.lwjgl.glframework.buffer.PolygonMode;
import co.megadodo.lwjgl.glframework.buffer.ProvokingVertex;
import co.megadodo.lwjgl.glframework.buffer.VertexArray;
import co.megadodo.lwjgl.glframework.buffer.VertexBuffer;
import co.megadodo.lwjgl.glframework.shader.ShaderProgram;
import co.megadodo.lwjgl.glframework.shader.ShaderType;
import co.megadodo.lwjgl.glframework.texture.Texture;
import co.megadodo.lwjgl.glframework.utils.GLUtilities;
import co.megadodo.lwjgl.glframework.window.GLWindow;

public class TestTexture {

	public static void main(String[] args) {
		GLWindow.initGLFW();

		GLWindow window = new GLWindow();
		window.hints();
		window.gen();
		window.setSize(500, 500);
		window.setTitle("Test textures");
		window.bind();

		GLUtilities.printGLInfo();

		VertexArray vao = new VertexArray();
		vao.gen();
		vao.bind();

		VertexBuffer pos = new VertexBuffer();
		pos.target = BufferTarget.Array;
		pos.usage = BufferUsage.StaticDraw;
		pos.gen();
		pos.bind();
		pos.setData(new float[] { -1, -1, -1, 1, 1, 1, 1, -1 });
		pos.addVertexAttrib(0, 2, AttribType.Float, false, 2, 0);
		pos.unbind();

		VertexBuffer uv = new VertexBuffer();
		uv.target = BufferTarget.Array;
		uv.usage = BufferUsage.StaticDraw;
		uv.gen();
		uv.bind();
		uv.setData(new float[] { 0, 0, 0, 1, 1, 1, 1, 0 });
		uv.addVertexAttrib(1, 2, AttribType.Float, false, 2, 0);
		uv.unbind();

		VertexBuffer ebo = new VertexBuffer();
		ebo.target = BufferTarget.ElementArray;
		ebo.usage = BufferUsage.StaticDraw;
		ebo.gen();
		ebo.bind();
		ebo.setDataUnsigned(new byte[] { 0, 1, 2, 0, 3, 2 });
		ebo.unbind();

		vao.unbind();

		ShaderProgram sp = new ShaderProgram();
		sp.gen();
		sp.attach("Shaders/test/texture/shader.frag", ShaderType.Fragment);
		sp.attach("Shaders/test/texture/shader.vert", ShaderType.Vertex);
		sp.link();

		Texture tex1=Texture.loadTexture("Textures/test/texture/apple1.jpg");
		Texture tex2=Texture.loadTexture("Textures/test/texture/apple2.jpg");
		
		while (window.isOpen()) {
			window.bind();
			GLUtilities.clearScreen(1, 1, 1);
			
			
			sp.bind();
			
			tex1.bindToUnit(0);
			sp.setTexture("tex1", 0);
			
			tex2.bindToUnit(1);
			sp.setTexture("tex2", 1);
			
			vao.bind();
			ebo.bind();
			ebo.render(ProvokingVertex.Last,PolygonMode.Fill);
			ebo.unbind();
			vao.unbind();
			
			
			sp.unbind();

			window.unbind();
		}
		window.delete();

		GLWindow.endGLFW();
	}

}
