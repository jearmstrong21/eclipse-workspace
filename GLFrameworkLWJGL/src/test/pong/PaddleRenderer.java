package test.pong;

import org.joml.Matrix4f;

import co.megadodo.lwjgl.glframework.buffer.AttribType;
import co.megadodo.lwjgl.glframework.buffer.BufferTarget;
import co.megadodo.lwjgl.glframework.buffer.BufferUsage;
import co.megadodo.lwjgl.glframework.buffer.PolygonMode;
import co.megadodo.lwjgl.glframework.buffer.VertexArray;
import co.megadodo.lwjgl.glframework.buffer.VertexBuffer;
import co.megadodo.lwjgl.glframework.shader.ShaderProgram;
import co.megadodo.lwjgl.glframework.shader.ShaderType;

public class PaddleRenderer {
	
	ShaderProgram sp;
	VertexBuffer vbo,ebo;
	VertexArray vao;
	
	public void init() {
		sp=new ShaderProgram();
		sp.gen();
		sp.bind();
		sp.attach("Shaders/pong/paddle.vert",ShaderType.Vertex);
		sp.attach("Shaders/pong/paddle.frag",ShaderType.Fragment);
		sp.link();
		sp.unbind();
		
		vao=new VertexArray();
		vao.gen();
		vao.bind();
		
		vbo=new VertexBuffer();
		vbo.target=BufferTarget.Array;
		vbo.usage=BufferUsage.StaticDraw;
		vbo.gen();
		vbo.bind();
		vbo.setData(new float[] {-1,-1,0,  -1,1,0,  1,1,0,   1,-1,0});
		vbo.addVertexAttrib(0, 3, AttribType.Float, false, 3, 0);
		vbo.unbind();
		
		ebo=new VertexBuffer();
		ebo.target=BufferTarget.ElementArray;
		ebo.usage=BufferUsage.StaticDraw;
		ebo.gen();
		ebo.bind();
		ebo.setDataUnsigned(new int[] {0,1,2,  0,3,2});
		ebo.unbind();
		
		vao.unbind();
	}
	
	public float x,y,w,h,r,g,b;
	
	public void render(float _x,float _y,float _w,float _h,float _r,float _g,float _b) {
		x=_x;
		y=_y;
		w=_w;
		h=_h;
		r=_r;
		g=_g;
		b=_b;
		sp.bind();
		Matrix4f mat=new Matrix4f().identity().translate(x, y, 0).scale(w, h, 0);
		sp.setMat4("matrix",mat);
		sp.setFloat("r", r);
		sp.setFloat("g", g);
		sp.setFloat("b", b);
		vao.bind();
		ebo.bind();
		ebo.render(PolygonMode.Fill);
		ebo.unbind();
		vao.unbind();
		sp.unbind();
	}

}
