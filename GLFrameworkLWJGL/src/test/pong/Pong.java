package test.pong;

import org.joml.Matrix4f;
import org.json.JSONArray;
import org.json.JSONObject;

import co.megadodo.lwjgl.glframework.Mathf;
import co.megadodo.lwjgl.glframework.buffer.AttribType;
import co.megadodo.lwjgl.glframework.buffer.BufferTarget;
import co.megadodo.lwjgl.glframework.buffer.BufferUsage;
import co.megadodo.lwjgl.glframework.buffer.PolygonMode;
import co.megadodo.lwjgl.glframework.buffer.VertexArray;
import co.megadodo.lwjgl.glframework.buffer.VertexBuffer;
import co.megadodo.lwjgl.glframework.shader.ShaderProgram;
import co.megadodo.lwjgl.glframework.shader.ShaderType;
import co.megadodo.lwjgl.glframework.utils.GLUtilities;
import co.megadodo.lwjgl.glframework.utils.Utilities;
import co.megadodo.lwjgl.glframework.window.GLWindow;
import co.megadodo.lwjgl.glframework.window.ProfileType;

public class Pong {
	
	public static void main(String[]args) {
		
		GLWindow.initGLFW();
		
		GLWindow window=new GLWindow();
		window.hints(4,1,true,ProfileType.Core);
		window.gen();
		window.setTitle("Pong");
		window.setSize(500, 500);

		GLUtilities.printGLInfo();
		
		PongRenderer pr=new PongRenderer();
		pr.init();
		
		PongUpdater pu=new PongUpdater();
		pu.init();
		
		float ballX=0,ballY=0;
		float ballSize=0.01f;
		
		
		
		while(window.isOpen()) {
			window.bind();
			
			GLUtilities.clearScreen(0, 0, 0);
//			ballX=Mathf.map(window.getMouseX(), 0, window.getWidth(), -1, 1);
//			ballY=Mathf.map(window.getMouseY(), 0, window.getHeight(), -1, 1);
			pr.ballX=pu.bx;
			pr.ballY=pu.by;
			pr.ballSize=pu.bsize;
			pr.ballR=pu.br;
			pr.ballG=pu.bg;
			pr.ballB=pu.bb;
			pr.p1x=pu.p1x;
			pr.p1y=pu.p1y;
			pr.p1w=pu.p1w;
			pr.p1h=pu.p1h;
			pr.p1r=pu.p1r;
			pr.p1g=pu.p1g;
			pr.p1b=pu.p1b;
			pr.p2x=pu.p2x;
			pr.p2y=pu.p2y;
			pr.p2w=pu.p2w;
			pr.p2h=pu.p2h;
			pr.p2r=pu.p2r;
			pr.p2g=pu.p2g;
			pr.p2b=pu.p2b;
			pr.render();
			float mx=Mathf.map(window.getMouseX(), 0, window.getWidth(), -1, 1);
			float my=Mathf.map(window.getMouseY(), 0, window.getHeight(), 1, -1);
			pu.update(mx,my);
			
			window.unbind();
		}
		
		window.delete();
		
		GLWindow.endGLFW();
		
	}

}
