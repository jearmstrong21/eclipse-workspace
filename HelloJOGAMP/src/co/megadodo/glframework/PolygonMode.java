package co.megadodo.glframework;

import com.jogamp.opengl.GL4;

public enum PolygonMode{
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