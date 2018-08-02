package test.slidepuzzle;

import java.awt.Window;

import org.joml.Vector3f;

import co.megadodo.lwjgl.glframework.framebuffer.FBOAttachment;
import co.megadodo.lwjgl.glframework.framebuffer.Framebuffer;
import co.megadodo.lwjgl.glframework.text.TextRenderer;
import co.megadodo.lwjgl.glframework.texture.Texture;
import co.megadodo.lwjgl.glframework.usable.GLMesh;
import co.megadodo.lwjgl.glframework.usable.GLShader;
import co.megadodo.lwjgl.glframework.utils.GLUtilities;
import co.megadodo.lwjgl.glframework.utils.HSB;
import co.megadodo.lwjgl.glframework.utils.Mathf;
import co.megadodo.lwjgl.glframework.window.GLWindow;
import test.text.TextAtlas;

public class SlidePuzzleController {


	
	TextAtlas atlas=new TextAtlas();
	
	int gridSize=4;
	SlidingRectangle[][] grid;
	int spaceX=0;
	int spaceY=0;
	
	Framebuffer fboSolved;
	Texture texSolved;
	
	GLMesh solvedMesh;
	GLShader solvedShader;
	
	TextRenderer txtHelp;
	
	String helpText="Slide puzzle\nWADS - slide the puzzle\nSPACE - scramble the puzzle\nF - show solved grid";
	
	public void renderHelp() {
		txtHelp.render(-0.9f, 0.9f, 0.1f);
	}
	
	public void initHelp() {
		txtHelp=new TextRenderer();
		txtHelp.atlas=new TextAtlas();
		txtHelp.gen();
		txtHelp.setText(helpText);
	}
	
	public void init(int windowW,int windowH) {
		
		
		grid=new SlidingRectangle[gridSize][gridSize];
		
		int i=0;
		for(int x=0;x<gridSize;x++) {
			for(int y=0;y<gridSize;y++) {
				i++;
				float theta=Mathf.map(i, 0, gridSize*gridSize, 0, 1);
				Vector3f color=HSB.hsbToRGB(theta, 0.5f, 1);
				grid[x][y]=new SlidingRectangle(x,y, gridSize, i,  color.x,color.y,color.z);
			}
		}
		grid[0][0]=null;
		

		
		fboSolved=new Framebuffer();
		fboSolved.gen();
		fboSolved.bind();
		texSolved=Texture.createEmptyTexture(windowW, windowH);
		fboSolved.attachTex(texSolved, FBOAttachment.ColorAttachment0);
		fboSolved.unbind();
		
		fboSolved.bind();
//		GLUtilities.setViewport(0, 0, windowW, windowH);
		GLUtilities.clearScreen(1, 1, 1);
		renderGrid();
		fboSolved.unbind();
		
		solvedMesh=new GLMesh();
		solvedMesh.addBuffer2f(0, new float[] {-1,-1,  -1,1,  1,1,  1,-1});
		solvedMesh.addBuffer2f(1, new float[] {0,0,  0,1,  1,1,  1,0});
		solvedMesh.setTriangles(new int[] {0,1,2,  0,2,3});
		
		solvedShader=new GLShader("Shaders/test/slidepuzzle/solved.vert","Shaders/test/slidepuzzle/solved.frag");

	}
	
	public void setSpace(int x,int y) {
		grid[spaceX][spaceY]=grid[x][y];
		grid[x][y]=null;
//		SlidingRectangle temp=grid[x1][y1];
//		grid[x1][y1]=grid[x2][y2];
//		grid[x2][y2]=temp;
	}
	
	public void moveLeft() {
		if(spaceX<gridSize-1) {
			setSpace(spaceX+1,spaceY);
			spaceX++;
		}
	}
	
	public void moveRight() {
		if(spaceX>0) {
			setSpace(spaceX-1,spaceY);
			spaceX--;
		}
	}
	
	public void moveUp() {
		if(spaceY>0) {
			setSpace(spaceX,spaceY-1);
			spaceY--;
		}
	}
	
	public void moveDown() {
		if(spaceY<gridSize-1) {
			setSpace(spaceX,spaceY+1);
			spaceY++;
		}
	}
	
	public void assignPositions() {
		for(int x=0;x<gridSize;x++) {
			for(int y=0;y<gridSize;y++) {
				if(grid[x][y]==null)continue;
				grid[x][y].x=x;
				grid[x][y].y=y;
			}
		}
	}
	
	public void scramble() {
		for(int i=0;i<10000;i++) {
			int r=(int)Mathf.round(Mathf.random(0, 4));
			if(r==0)moveLeft();
			if(r==1)moveRight();
			if(r==2)moveUp();
			if(r==3)moveDown();
			assignPositions();
		}
	}
	
	public void update(GLWindow window) {
		if(window.wasJustPressed('A')) {
			moveLeft();
		}
		if(window.wasJustPressed('D')) {
			moveRight();
		}
		if(window.wasJustPressed('W')) {
			moveUp();
		}
		if(window.wasJustPressed('S')) {
			moveDown();
		}
		if(window.wasJustPressed(' ')) {
			scramble();
		}
		assignPositions();
	}
	
	public void renderSolved() {
		solvedShader.bind();
		solvedShader.clearTextures();
		solvedShader.setTexture("tex", texSolved);
		solvedMesh.render();
	}
	
	public void render(GLWindow window) {
		if(window.isKeyDown('F')) {
			renderSolved();
		}else {
			renderGrid();
		}
	}
	
	public void renderGrid() {
		for(int x=0;x<gridSize;x++) {
			for(int y=0;y<gridSize;y++) {
				if(grid[x][y]!=null)grid[x][y].render();
			}
		}
	}

}
