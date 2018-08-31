package test.conwaygol;

import co.megadodo.lwjgl.glframework.framebuffer.FBOAttachment;
import co.megadodo.lwjgl.glframework.framebuffer.Framebuffer;
import co.megadodo.lwjgl.glframework.shader.ShaderType;
import co.megadodo.lwjgl.glframework.texture.Texture;
import co.megadodo.lwjgl.glframework.usable.GLMesh;
import co.megadodo.lwjgl.glframework.usable.GLShader;
import co.megadodo.lwjgl.glframework.utils.GLUtilities;
import co.megadodo.lwjgl.glframework.window.GLWindow;

public class TestConwayGOL {

	// static GLMesh quad;

	public static void main(String[] args) {
		GLWindow.initGLFW();

		GLWindow window = new GLWindow();
		window.setSize(1000, 1000);
		window.setTitle("Conway's GOL");

		GLUtilities.printGLInfo();

		GLMesh quad = new GLMesh();
		quad.addBuffer2f(0, new float[] { -1, -1, -1, 1, 1, 1, 1, -1 });
		quad.addBuffer2f(1, new float[] { 0, 0, 0, 1, 1, 1, 1, 0 });
		quad.setTriangles(new int[] { 0, 1, 2, 0, 2, 3 });

		GLShader init = new GLShader();
		init.attach("Shaders/test/conwaygol/shader.vert", ShaderType.Vertex);
		init.attach("Shaders/test/conwaygol/init.frag", ShaderType.Fragment);
		init.link();

		GLShader passThrough = new GLShader();
		passThrough.attach("Shaders/test/conwaygol/shader.vert", ShaderType.Vertex);
		passThrough.attach("Shaders/test/conwaygol/passthrough.frag", ShaderType.Fragment);
		passThrough.link();

		GLShader copyShader = new GLShader();
		copyShader.attach("Shaders/test/conwaygol/shader.vert", ShaderType.Vertex);
		copyShader.attach("Shaders/test/conwaygol/copy.frag", ShaderType.Fragment);
		copyShader.link();

		GLShader display = new GLShader();
		display.attach("Shaders/test/conwaygol/shader.vert", ShaderType.Vertex);
		display.attach("Shaders/test/conwaygol/display.frag", ShaderType.Fragment);

		int texW = window.getFBOWidth();
		int texH = window.getFBOHeight();

		Framebuffer fboPass = new Framebuffer();
		fboPass.gen();
		fboPass.bind();

		Texture texPassIn = Texture.createEmptyTexture(texW, texH);
		Texture texPassOut = Texture.createEmptyTexture(texW, texH);

		fboPass.attachTex(texPassOut, FBOAttachment.ColorAttachment0);

		fboPass.unbind();

		Framebuffer fboCopy = new Framebuffer();
		fboCopy.gen();
		fboCopy.bind();

		fboCopy.attachTex(texPassIn, FBOAttachment.ColorAttachment0);

		fboCopy.unbind();

		fboPass.bind();
		init.bind();
		quad.render();
		fboPass.unbind();

		fboCopy.bind();
		copyShader.bind();
		copyShader.set1i("fromTex", 0);
		texPassOut.bindToUnit(0);
		quad.render();
		fboCopy.unbind();

		int frames = 0;
		while (window.isOpen()) {
			window.bind();

			GLUtilities.clearScreen(1, 1, 1);

			// init.bind();
			// quad.render();

			frames++;

			// if (frames % 2 == 0) {
			{
				// for(int i=0;i<1;i++) {
				// render FROM texPassIn TO texPassOut
				GLUtilities.setViewport(0, 0, texW, texH);
				fboPass.bind();
				passThrough.bind();
				passThrough.set1i("inTex", 0);
				passThrough.set1f("dx", 1.0f / texW);
				passThrough.set1f("dy", 1.0f / texH);
				texPassIn.bindToUnit(0);
				quad.render();
				fboPass.unbind();

				// render FROM texPassOut TO texPassIn
				fboCopy.bind();
				copyShader.bind();
				copyShader.set1i("fromTex", 0);
				texPassOut.bindToUnit(0);
				quad.render();
				fboCopy.unbind();
			}

			GLUtilities.setDefaultViewport(window);

			display.bind();
			display.set1i("inTex", 0);
			display.set1f("dx", 1.0f / texW);
			display.set1f("dy", 1.0f / texH);
			texPassOut.bindToUnit(0);
			quad.render();

			window.unbind();
		}

		GLWindow.endGLFW();
	}

}
