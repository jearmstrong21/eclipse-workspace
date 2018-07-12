package test.pong;

import org.joml.Vector2f;

import co.megadodo.lwjgl.glframework.window.GLWindow;

public class PongUpdater {
	
	public Vector2f ballPos,ballVel;
	public float ballRad;
	
	public void init() {
		ballPos=new Vector2f(0,0);
		ballVel=new Vector2f(0,0);
		ballRad=0.01f;
	}
	
	public void update(GLWindow window) {
		
	}
	
	public void delete() {
		
	}

}
