package co.megadodo.lwjgl.glframework.usable;

import java.util.HashMap;
import java.util.Map;

import co.megadodo.lwjgl.glframework.buffer.AttribType;
import co.megadodo.lwjgl.glframework.buffer.BufferTarget;
import co.megadodo.lwjgl.glframework.buffer.BufferUsage;
import co.megadodo.lwjgl.glframework.buffer.PolygonMode;
import co.megadodo.lwjgl.glframework.buffer.ProvokingVertex;
import co.megadodo.lwjgl.glframework.buffer.VertexArray;
import co.megadodo.lwjgl.glframework.buffer.VertexBuffer;
import co.megadodo.lwjgl.glframework.utils.GLUtilities;

public class GLMesh {

	public VertexArray vao;
	public Map<Integer, VertexBuffer> buffers;
	public VertexBuffer ebo;
	
	public int numVertices;
	
	public GLMesh() {
		vao=new VertexArray();
		vao.gen();
		
		buffers=new HashMap<Integer, VertexBuffer>();
		
		ebo=null;
	}
	
	public VertexBuffer getBuffer(int attrib) {
		return buffers.get(attrib);
	}
	
	public void addBuffer3f(int attrib,float[]data) {
		addBuffer(attrib,data,3);
	}
	
	public void addBuffer2f(int attrib,float[]data) {
		addBuffer(attrib,data,2);
	}
	
	public void addBuffer(int attrib,float[]data,int size) {
		numVertices=data.length/size;
		vao.bind();
		VertexBuffer vbo=new VertexBuffer();
		vbo.usage=BufferUsage.StaticDraw;
		vbo.target=BufferTarget.Array;
		vbo.gen();
		vbo.bind();
		vbo.setData(data);
		vbo.addVertexAttrib(attrib, size, AttribType.Float, false, size, 0);
		buffers.put(attrib, vbo);
		vbo.unbind();
		vao.unbind();
	}
	
	public void setTriangles(int[]data) {
		ebo=new VertexBuffer();
		ebo.usage=BufferUsage.StaticDraw;
		ebo.target=BufferTarget.ElementArray;
		ebo.gen();
		ebo.bind();
		ebo.setDataUnsigned(data);
		ebo.unbind();
	}
	
	public void render() {
		GLUtilities.setPolygonMode(PolygonMode.Fill);
		GLUtilities.setProvokingVertex(ProvokingVertex.First);
		if(ebo==null) {
			vao.bind();
			vao.renderArrays(numVertices);
			vao.unbind();
		}else {
			vao.bind();
			ebo.bind();
			ebo.render();
			ebo.unbind();
			vao.unbind();
		}
	}
	
}
