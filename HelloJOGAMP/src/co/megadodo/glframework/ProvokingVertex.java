package co.megadodo.glframework;

import com.jogamp.opengl.GL4;

public enum ProvokingVertex{
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
