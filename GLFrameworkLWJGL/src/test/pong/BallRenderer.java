package test.pong;

import co.megadodo.lwjgl.glframework.buffer.AttribType;
import co.megadodo.lwjgl.glframework.buffer.PolygonMode;
import co.megadodo.lwjgl.glframework.buffer.VertexArray;
import co.megadodo.lwjgl.glframework.buffer.VertexBuffer;
import co.megadodo.lwjgl.glframework.shader.ShaderProgram;
import co.megadodo.lwjgl.glframework.shader.ShaderType;
import co.megadodo.lwjgl.glframework.utils.Utilities;

public class BallRenderer {
	
	public void init() {
		
		float[]posData=new float[] {-1,-1,0,    -1,1,0,   1,1,0,   1,-1,0};
		byte[]triData=new byte[] {0,1,2,0,2,3};
		
		vao=new VertexArray();
		vao.gen();
		vao.bind();
		
		vbo=new VertexBuffer();
		vbo.gen();
		vbo.bind();
		vbo.setData(posData);
		vbo.addVertexAttrib(0, 3, AttribType.Float, false, 3, 0);
		vbo.unbind();
		
		ebo=new VertexBuffer();
		ebo.gen();
		ebo.bind();
		ebo.setDataUnsigned(triData);
		ebo.unbind();
		
		vao.unbind();
		
		sp=new ShaderProgram();
		sp.gen();
		sp.attach(ShaderProgram.compileShader(ShaderType.Fragment, "fragment", Utilities.loadStrFromFile("Shaders/pong/ball.frag")));
		sp.attach(ShaderProgram.compileShader(ShaderType.Vertex, "vertex", Utilities.loadStrFromFile("Shaders/pong/ball.vert")));
		sp.link();
		System.out.println(sp.id+" "+vao.id+" "+vbo.id+" "+ebo.id);
	}
	
	public VertexArray vao;
	public VertexBuffer vbo,ebo;
	public ShaderProgram sp;
	
	public float x,y,rad,r,g,b;
	
	public void render() {
		sp.bind();
		
		vao.bind();
		
		ebo.bind();
		ebo.render(PolygonMode.Fill);
		ebo.unbind();
		
		vao.unbind();
		
		sp.unbind();
	}
	
	public void delete() {
		vao.delete();
		vbo.delete();
		ebo.delete();
		sp.delete();
	}

}
