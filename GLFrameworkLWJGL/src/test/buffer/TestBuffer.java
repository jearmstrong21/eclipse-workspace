package test.buffer;

import java.util.ArrayList;

import co.megadodo.lwjgl.glframework.Mathf;
import co.megadodo.lwjgl.glframework.buffer.AttribType;
import co.megadodo.lwjgl.glframework.buffer.BufferTarget;
import co.megadodo.lwjgl.glframework.buffer.BufferUsage;
import co.megadodo.lwjgl.glframework.buffer.PolygonMode;
import co.megadodo.lwjgl.glframework.buffer.ProvokingVertex;
import co.megadodo.lwjgl.glframework.buffer.VertexArray;
import co.megadodo.lwjgl.glframework.buffer.VertexBuffer;
import co.megadodo.lwjgl.glframework.shader.ShaderProgram;
import co.megadodo.lwjgl.glframework.shader.ShaderType;
import co.megadodo.lwjgl.glframework.utils.GLUtilities;
import co.megadodo.lwjgl.glframework.utils.Utilities;
import co.megadodo.lwjgl.glframework.window.GLWindow;

public class TestBuffer {
	
	public static void main(String[]args) {
		GLWindow.initGLFW();

		GLWindow window = new GLWindow();
		window.hints();
		window.gen();
		window.setSize(500, 500);
		window.setTitle("Test buffers");
		window.bind();

		GLUtilities.printGLInfo();

		VertexArray vao = new VertexArray();
		vao.gen();
		vao.bind();
		
		ArrayList<Float>posData=new ArrayList<Float>();
		ArrayList<Integer>triData=new ArrayList<Integer>();
		int numSides=25;
		float diffAng=Mathf.PI*2/numSides;
		for(int i=0;i<numSides;i++) {
			float ang=i*diffAng;
			triData.add(i*3+0);
			triData.add(i*3+1);
			triData.add(i*3+2);
			
			posData.add(Mathf.cos(ang));
			posData.add(Mathf.sin(ang));
			
			posData.add(Mathf.cos(ang+diffAng));
			posData.add(Mathf.sin(ang+diffAng));
			
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
		sp.attach("Shaders/test/buffer/shader.frag", ShaderType.Fragment);
		sp.attach("Shaders/test/buffer/shader.vert", ShaderType.Vertex);
		sp.link();

		while (window.isOpen()) {
			window.bind();
			GLUtilities.clearScreen(0, 0, 0);
			
			
			sp.bind();
			
			vao.bind();
			ebo.bind();
			ebo.render(ProvokingVertex.First, PolygonMode.Fill);
			ebo.unbind();
			vao.unbind();
			
			
			sp.unbind();

			window.unbind();
		}
		window.delete();

		GLWindow.endGLFW();
	}

}
