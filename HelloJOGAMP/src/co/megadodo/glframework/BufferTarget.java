package co.megadodo.glframework;

import com.jogamp.opengl.GL4;

public enum BufferTarget{
	ArrayBuffer(GL4.GL_ARRAY_BUFFER),
	ElementBuffer(GL4.GL_ELEMENT_ARRAY_BUFFER);
	
	private int type;
	private BufferTarget(int i) {
		type=i;
	}
	
	public int glConst() {
		return type;
	}
}