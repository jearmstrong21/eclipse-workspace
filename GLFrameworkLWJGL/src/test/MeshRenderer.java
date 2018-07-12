package test;

import co.megadodo.lwjgl.glframework.GLRenderable;
import co.megadodo.lwjgl.glframework.GLResource;
import co.megadodo.lwjgl.glframework.buffer.AttribType;
import co.megadodo.lwjgl.glframework.buffer.BufferTarget;
import co.megadodo.lwjgl.glframework.buffer.BufferUsage;
import co.megadodo.lwjgl.glframework.buffer.PolygonMode;
import co.megadodo.lwjgl.glframework.buffer.VertexArray;
import co.megadodo.lwjgl.glframework.buffer.VertexBuffer;
import co.megadodo.lwjgl.glframework.model.Mesh;

public class MeshRenderer implements GLRenderable, GLResource {
	
	public Mesh mesh;
	public VertexArray vao;
	public VertexBuffer vboPos,vboNorm,vboUV,ebo;
	
	public int polyMode;
	
	public MeshRenderer() {
		
	}
	
	public void gen() {
		vao=new VertexArray();
		vao.gen();
		vao.bind();
		
		vboPos=new VertexBuffer();
		vboPos.target=BufferTarget.Array;
		vboPos.usage=BufferUsage.StaticDraw;
		vboPos.gen();
		vboPos.bind();
		vboPos.setData(mesh.pos);
		vboPos.addVertexAttrib(0, 3, AttribType.Float, false, 3, 0);
		vboPos.unbind();
		
		vboNorm=new VertexBuffer();
		vboNorm.target=BufferTarget.Array;
		vboNorm.usage=BufferUsage.StaticDraw;
		vboNorm.gen();
		vboNorm.bind();
		vboNorm.setData(mesh.normal);
		vboNorm.addVertexAttrib(1, 3, AttribType.Float, true, 3, 0);
		vboNorm.unbind();
		
		vboUV=new VertexBuffer();
		vboUV.target=BufferTarget.Array;
		vboUV.usage=BufferUsage.StaticDraw;
		vboUV.gen();
		vboUV.bind();
		vboUV.setData(mesh.uv);
		vboUV.addVertexAttrib(2, 2, AttribType.Float, false, 2, 0);
		vboUV.unbind();
		
		ebo=new VertexBuffer();
		ebo.target=BufferTarget.ElementArray;
		ebo.usage=BufferUsage.StaticDraw;
		ebo.gen();
		ebo.bind();
		ebo.setDataUnsigned(mesh.faces);
		ebo.unbind();
		
		vao.unbind();
		
	}
	
	public void bind() {
		vao.bind();
		ebo.bind();
	}
	
	public void unbind() {
		ebo.unbind();
		vao.unbind();
	}
	
	public void render() {
		render(polyMode);
	}
	
	public void render(int poly) {
		ebo.render(poly);
	}
	
	public void delete() {
		vboPos.delete();
		vboNorm.delete();
		vboUV.delete();
		ebo.delete();
		vao.delete();
	}

}
