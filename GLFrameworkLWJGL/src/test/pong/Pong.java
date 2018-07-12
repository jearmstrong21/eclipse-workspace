package test.pong;

import co.megadodo.lwjgl.glframework.utils.GLUtilities;
import co.megadodo.lwjgl.glframework.window.GLWindow;

public class Pong {
	
	public static void main(String[]args) {
		GLWindow.initGLFW();
		
		GLWindow window = new GLWindow();
		window.hints();
		window.gen();
		window.setSize(500, 500);
		window.setTitle("Pong");
		window.show();
		window.bind();
		
		GLUtilities.printGLInfo();
		
		PongRenderer pr=new PongRenderer();
		
		pr.init();
		
		while(window.isOpen()) {
			window.bind();
			
			pr.render();
			
			window.unbind();
		}

		pr.delete();
		
		window.delete();
		
		GLWindow.endGLFW();
	}

}
