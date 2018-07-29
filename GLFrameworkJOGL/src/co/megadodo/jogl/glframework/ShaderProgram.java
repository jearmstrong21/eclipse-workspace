package co.megadodo.jogl.glframework;

import java.nio.IntBuffer;

import com.jogamp.opengl.GL4;

public class ShaderProgram {
	
	public int id;
	
	public static int compileShader(GL4 gl,int type,String source) {
		int id=gl.glCreateShader(type);
		gl.glShaderSource(id, 1, new String[] {source}, IntBuffer.wrap(new int[] {0}));
		gl.glCompileShader(id);
		int success;
		gl.glGetShaderiv(arg0, arg1, arg2);
		return id;
	}
	
	

}
