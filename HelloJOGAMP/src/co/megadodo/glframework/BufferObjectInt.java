package co.megadodo.glframework;

import java.nio.IntBuffer;

import com.jogamp.opengl.GL4;

public class BufferObjectInt extends BufferObject<Integer> {
	
	public BufferObjectInt() {
		
	}

	public BufferObjectInt(BufferTarget target, BufferType type) {
		this.bufferTarget=target;
		this.bufferType=type;
	}
	
	public BufferObjectInt(BufferTarget target, BufferType type, int[]data) {
		this.bufferTarget=target;
		this.bufferType=type;
		this.data=data;
	}
	
	public int boi;
	public int[] data;
	
	public void genBuffers(GL4 gl) {
		IntBuffer ib=IntBuffer.allocate(1);
		gl.glGenBuffers(1, ib);
		boi=ib.get(0);
		
		gl.glBindBuffer(bufferTarget.glConst(), boi);
		gl.glBufferData(bufferTarget.glConst(), data.length*Integer.BYTES, IntBuffer.wrap(data), bufferType.glConst());
//		gl.glEnableVertexAttribArray(vertattrib);
//		gl.glVertexAttribPointer(vertattrib, stride, GL4.GL_FLOAT, false, stride*Float.BYTES, 0);
	}
	
	public void render(GL4 gl) {
		gl.glDrawElements(GL4.GL_TRIANGLES, data.length, GL4.GL_UNSIGNED_INT, 0);
	}
	
	public void delete(GL4 gl) {
		gl.glDeleteBuffers(1, IntBuffer.wrap(new int[] {boi}));
	}

}
