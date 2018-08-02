package test.text;

import co.megadodo.lwjgl.glframework.text.TextRenderer;
import co.megadodo.lwjgl.glframework.utils.GLUtilities;
import co.megadodo.lwjgl.glframework.window.GLWindow;

public class TestText {
	
	//use VertexBuffer.modifyData for float[] and unsigned int[] for the text
	
	public static void main(String[]args) {
		GLWindow.initGLFW();
		
		GLWindow window=new GLWindow();
		window.hints();
		window.gen();
		window.setTitle("Test text");
		window.setSize(500, 500);
		window.bind();
		
		GLUtilities.printGLInfo();
		
		TextRenderer txt=new TextRenderer();
		txt.atlas=new TextAtlas();
		txt.gen();
		txt.setText("0 ABC abc\n1 DEF def\n2 GHI ghi\n3 JKL jkl\n4 MNO mno\n5 PQR pqr\n6 STU stu\n7 VWX vwx\n8 YZ  yz\n9\n~`!@#$%^&*\n()_+-={}|[\n]\\:\";'<>?,\n./");
		while(window.isOpen()) {
			window.bind();
			GLUtilities.clearScreen(0.9f, 1, 1);
			
			txt.render(window.getGLMouseX(),window.getGLMouseY(),0.1f);

			
			window.unbind();
		}
		
		window.unbind();
		
		GLWindow.endGLFW();
	}

}
