package test.sims;

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

public class ReactionDiffusion {

	public static void main(String[] args) {
		GLWindow.initGLFW();

		GLWindow window = new GLWindow();
		window.hints();
		window.gen();
		window.setSize(500, 500);
		window.setTitle("Reaction diffusion");
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
		ebo.setDataUnsigned(new int[] { 0, 1, 2, 0, 2, 3 });
		ebo.unbind();

		vao.unbind();

		ShaderProgram spCompute = new ShaderProgram();
		spCompute.gen();
		spCompute.attach("Shaders/test/sims/reactiondiffusion/compute.frag", ShaderType.Fragment);
		spCompute.attach("Shaders/test/sims/reactiondiffusion/compute.vert", ShaderType.Vertex);
		spCompute.link();

		ShaderProgram spDisplay = new ShaderProgram();
		spDisplay.gen();
		spDisplay.attach("Shaders/test/sims/reactiondiffusion/display.frag", ShaderType.Fragment);
		spDisplay.attach("Shaders/test/sims/reactiondiffusion/display.vert", ShaderType.Vertex);
		spDisplay.link();
		
		Texture tex1=Texture.loadTexture("Textures/test/texture/apple1.jpg");
		Texture tex2=Texture.loadTexture("Textures/test/texture/apple1.jpg");


		int frames = 0;
		while (window.isOpen()) {
			frames++;
			window.bind();
			
			GLUtilities.clearScreen(1, 1, 1);
			spCompute.bind();
			tex1.bindToUnit(0);
			spCompute.setTexture("tex1", 0);
			tex2.bindToUnit(1);
			spCompute.setTexture("tex2", 1);
			spCompute.setInt("frames", frames);
			vao.bind();
			ebo.bind();
			
			ebo.render(ProvokingVertex.First,PolygonMode.Fill);
			ebo.unbind();
			vao.unbind();
			spCompute.unbind();

//			spDisplay.bind();
//			tex1.bindToUnit(0);
//			spDisplay.setTexture("tex1", 0);
//			vao.bind();
//			ebo.bind();
//			ebo.render(ProvokingVertex.First, PolygonMode.Fill);
//			ebo.unbind();
//			vao.unbind();
//			spDisplay.unbind();

			window.unbind();
		}
		window.delete();

		GLWindow.endGLFW();
	}

}
