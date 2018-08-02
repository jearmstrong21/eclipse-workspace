package test.slidepuzzle;

import co.megadodo.lwjgl.glframework.framebuffer.FBOAttachment;
import co.megadodo.lwjgl.glframework.framebuffer.Framebuffer;
import co.megadodo.lwjgl.glframework.text.TextRenderer;
import co.megadodo.lwjgl.glframework.texture.Texture;
import co.megadodo.lwjgl.glframework.usable.GLMesh;
import co.megadodo.lwjgl.glframework.usable.GLShader;
import co.megadodo.lwjgl.glframework.utils.GLUtilities;
import co.megadodo.lwjgl.glframework.utils.Mathf;
import test.text.TextAtlas;

public class SlidingRectangle {
	
	public GLMesh mesh;
	public GLShader shader;
	public TextRenderer txt;
	
	public int number;
	public float colR;
	public float colG;
	public float colB;
	
	public int x;
	public int y;
	
	public int gridSize;
	
	public TextAtlas atlas;
	
	public SlidingRectangle(int x, int y, int gridSize, int number, float colR, float colG, float colB) {
		atlas=new TextAtlas();
		this.x = x;
		this.y = y;
		this.gridSize = gridSize;
		this.number = number;
		this.colR = colR;
		this.colG = colG;
		this.colB = colB;
		
		mesh=new GLMesh();
		mesh.addBuffer2f(0,new float[] {0,0,  0,1,  1,1, 1,0});//pos
		mesh.setTriangles(new int[] {0,1,2,0,2,3});
		
		shader=new GLShader("Shaders/test/slidepuzzle/rect.vert", "Shaders/test/slidepuzzle/rect.frag");
		shader.link();
		
		txt=new TextRenderer();
		txt.atlas=atlas;
		txt.gen();
		txt.setText(""+number);
	}
	
	public void render() {
		shader.bind();
		shader.set3f("color", colR, colG, colB);
		shader.set2f("offset", x, y);
		shader.set1f("gridSize", gridSize);

		mesh.render();
		txt.render(Mathf.map(x+0.5f, 0, gridSize, -1, 1), Mathf.map(y+0.75f, 0, gridSize, -1, 1), 0.1f);
	}

}
