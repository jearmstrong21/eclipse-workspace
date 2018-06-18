package co.megadodo.glframework;

import com.jogamp.opengl.GL4;

public enum BufferType {
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