package test.window;

import co.megadodo.lwjgl.glframework.utils.GLUtilities;
import co.megadodo.lwjgl.glframework.window.GLWindow;

public class TestWindow {
	
	public static void main(String[]args) {
		GLWindow.initGLFW();
		
		GLWindow w1=new GLWindow();
		w1.hints();
		w1.gen();
		w1.setSize(500, 500);
		w1.setTitle("Test windows, window 1");
		w1.bind();
		
		GLWindow w2=new GLWindow();
		w2.hints();
		w2.gen();
		w2.setSize(500, 500);
		w2.setTitle("Test windows, window 2");
		w2.bind();
		
		GLUtilities.printGLInfo();
		
		while(w1.isOpen()&&w2.isOpen()) {
			w1.bind();
			GLUtilities.clearScreen(1,1,1);
			
			w1.unbind();
			
			
			w2.bind();
			GLUtilities.clearScreen(0,0,0);
			
			w2.unbind();
		}
		
		w1.hide();
		w2.hide();
		
		w1.delete();
		w2.delete();
		
		GLWindow.endGLFW();
	}

}
