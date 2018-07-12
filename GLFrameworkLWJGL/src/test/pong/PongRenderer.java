package test.pong;

import co.megadodo.lwjgl.glframework.utils.GLUtilities;

public class PongRenderer {

	
	BallRenderer ball;
	
	public void init() {
		ball=new BallRenderer();
		ball.init();
	}
	
	public void render() {
		GLUtilities.clearScreen(0, 0, 0);
		
		ball.render();
	}
	
	public void delete() {
		ball.delete();
	}

}
