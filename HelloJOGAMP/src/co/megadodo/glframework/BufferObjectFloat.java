package co.megadodo.glframework;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import com.jogamp.opengl.GL4;

public class BufferObjectFloat extends BufferObject<Float> {
	
	public BufferObjectFloat() {
		
	}

	public BufferObjectFloat(BufferTarget target, BufferType type, int stride, int attrib) {
		this.bufferTarget=target;
		this.bufferType=type;
		this.stride=stride;
		this.vertattrib=attrib;
	}
	
	public BufferObjectFloat(BufferTarget target, BufferType type, int stride, int attrib, float[]data) {
		this.bufferTarget=target;
		this.bufferType=type;
		this.stride=stride;
		this.vertattrib=attrib;
		this.data=data;
	}
	
	public int bof;
	public float[] data;
	
	public void genBuffers(GL4 gl) {
		IntBuffer ib=IntBuffer.allocate(1);
		gl.glGenBuffers(1, ib);
		bof=ib.get(0);
		
		gl.glBindBuffer(bufferTarget.glConst(), bof);
		gl.glBufferData(bufferTarget.glConst(), data.length*Float.BYTES, FloatBuffer.wrap(data), bufferType.glConst());
		gl.glEnableVertexAttribArray(vertattrib);
		gl.glVertexAttribPointer(vertattrib, stride, GL4.GL_FLOAT, false, stride*Float.BYTES, 0);
	}
	
	public void render(GL4 gl) {
		//do nothing
	}
	
	public void delete(GL4 gl) {
		gl.glDeleteBuffers(1, IntBuffer.wrap(new int[] {bof}));
	}

}
