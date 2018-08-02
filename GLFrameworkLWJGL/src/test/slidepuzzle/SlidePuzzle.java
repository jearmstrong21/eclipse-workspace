package test.slidepuzzle;

import co.megadodo.lwjgl.glframework.utils.GLUtilities;
import co.megadodo.lwjgl.glframework.window.GLWindow;

public class SlidePuzzle {
	
	public static void main(String[]args) {
		GLWindow.initGLFW();
		
		GLWindow window=new GLWindow();
		window.setSize(500, 500);
		window.setTitle("Slide puzzle");

		GLWindow windowHelp=new GLWindow();
		windowHelp.setSize(500, 500);
		windowHelp.setTitle("Slide puzzle instructions");
		
		
		window.bind();
		
		
		GLUtilities.printGLInfo();
		
		SlidePuzzleController spc=new SlidePuzzleController();
		spc.init(window.getFBOWidth(), window.getFBOHeight());
		window.unbind();
		
		//render solved to texture here
		//fix HSB order
		//add key to swap between solved and current (hold 's' to see solved)

		windowHelp.bind();
		GLUtilities.clearScreen(1, 1, 1);
		spc.initHelp();
		windowHelp.unbind();
		
		while(window.isOpen()&&windowHelp.isOpen()) {
			window.bind();
			GLUtilities.clearScreen(1, 1, 1);
			spc.render(window);
			spc.update(window);
//			for(Character c:window.justPressed) {
//				System.out.print(c+" ");
//			}
//			System.out.println();
			window.justPressed.clear();
			window.justReleased.clear();
			window.unbind();
			
			windowHelp.bind();
			GLUtilities.clearScreen(1, 1, 1);
			spc.renderHelp();
			windowHelp.unbind();
		}
		
		GLWindow.endGLFW();
	}

}
