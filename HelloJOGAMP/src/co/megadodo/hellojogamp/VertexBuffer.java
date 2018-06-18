package co.megadodo.hellojogamp;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import com.jogamp.opengl.GL4;

public class VertexBuffer {

	public static enum BufferType{
		Static(GL4.GL_STATIC_DRAW),
		Dynamic(GL4.GL_DYNAMIC_DRAW);
		
		private int type;
		private BufferType(int i) {
			type=i;
		}
		public int glConst() {
			return type;
		}
	}
	
	public static enum ProvokingVertex{
		First(GL4.GL_FIRST_VERTEX_CONVENTION),
		Last(GL4.GL_LAST_VERTEX_CONVENTION);
		
		private int type;
		private ProvokingVertex(int i) {
			type=i;
		}
		public int glConst() {
			return type;
		}
	}
	
	public static enum PolygonMode{
		Fill(GL4.GL_FILL),
		Wireframe(GL4.GL_LINE),
		Point(GL4.GL_POINT);
		
		private int type;
		private PolygonMode(int i) {
			type=i;
		}
		public int glConst() {
			return type;
		}
	}
	
	public int vao;
	public int vbo;
	public int ebo;

	public VertexBuffer() {
		provokingVertex=ProvokingVertex.First;
		bufferType=BufferType.Static;
		polygonMode=PolygonMode.Fill;
	}
	
	public float[]floatData;
	public int[]triData;
	
	public ProvokingVertex provokingVertex;
	public BufferType bufferType;
	public PolygonMode polygonMode;

	public void genBuffers(GL4 gl,float[]floatData,int[]triData) {
		this.floatData=floatData;
		this.triData=triData;
		IntBuffer intBuffer=IntBuffer.allocate(1);
		
		gl.glGenVertexArrays(1, intBuffer);
		vao=intBuffer.get(0);
		
		gl.glGenBuffers(1, intBuffer);
		vbo=intBuffer.get(0);
		
		gl.glGenBuffers(1, intBuffer);
		ebo=intBuffer.get(0);
		
		gl.glBindVertexArray(vao);
		
		gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, vbo);
		gl.glBufferData(GL4.GL_ARRAY_BUFFER, floatData.length*Float.BYTES, FloatBuffer.wrap(floatData), bufferType.glConst());
		
		int vertSize=6;
		gl.glEnableVertexAttribArray(0);
		gl.glVertexAttribPointer(0, 3, GL4.GL_FLOAT, false, vertSize*Float.BYTES, 0);
		
		gl.glEnableVertexAttribArray(1);
		gl.glVertexAttribPointer(1, 3, GL4.GL_FLOAT, false, vertSize*Float.BYTES, 3*Float.BYTES);
		
		gl.glBindBuffer(GL4.GL_ELEMENT_ARRAY_BUFFER, ebo);
		gl.glBufferData(GL4.GL_ELEMENT_ARRAY_BUFFER, triData.length*Integer.BYTES,IntBuffer.wrap(triData),bufferType.glConst());
		
		
		gl.glBindVertexArray(0);
        System.out.println("VAO gen, vao="+vao+", vbo="+vbo+", ebo="+ebo);
	}

	public void render(GL4 gl) {
		gl.glProvokingVertex(provokingVertex.glConst());
		gl.glPolygonMode(GL4.GL_FRONT_AND_BACK, polygonMode.glConst());
		gl.glBindVertexArray(vao);
		gl.glDrawElements(GL4.GL_TRIANGLES, triData.length, GL4.GL_UNSIGNED_INT, 0);
		gl.glBindVertexArray(0);
	}

}
