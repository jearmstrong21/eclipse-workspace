package co.megadodo.glframework;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Scanner;

import com.jogamp.opengl.GL4;

import glm.mat._4.Mat4;

public class ShaderProgram {
	
	public ShaderProgram() {
		
	}
	
	public static String loadStrFromFile(String fn) {
		try {
			File f=new File(fn);
			Scanner sc=new Scanner(f);
			String str="";
			while(sc.hasNextLine())str+=sc.nextLine()+"\n";
			sc.close();
			return str;
		}catch(Throwable t) {
			t.printStackTrace();
			return "";
		}
	}
	
	public static int compileShader(GL4 gl, int type, String typeStr, String source) {
		int shaderID=gl.glCreateShader(type);
		gl.glShaderSource(shaderID, 1, new String[] {source}, null);
		gl.glCompileShader(shaderID);
		IntBuffer buffer=IntBuffer.allocate(1);
		gl.glGetShaderiv(shaderID, GL4.GL_COMPILE_STATUS, buffer);
		if(buffer.get(0)==GL4.GL_FALSE) {
			ByteBuffer infoBuffer=ByteBuffer.allocate(512);
			gl.glGetShaderInfoLog(shaderID, 512, null, infoBuffer);
			System.out.println("Shader compilation error, " + typeStr + ":");
			for(int i=0;i<infoBuffer.capacity();i++) {
				System.out.print((char)infoBuffer.get(i));
			}
			System.out.println();
			return -1;
		}
		return shaderID;
	}
	
	public static int compileProgram(GL4 gl, int vert, int frag) {
		int program=gl.glCreateProgram();
		gl.glAttachShader(program, vert);
		gl.glAttachShader(program, frag);
		gl.glLinkProgram(program);
		gl.glDeleteShader(vert);
		gl.glDeleteShader(frag);
		return program;
	}
	
	public int program;
	
	public void genProgramFiles(GL4 gl,String vertFile,String fragFile) {
		genProgram(gl,ShaderProgram.loadStrFromFile(vertFile),ShaderProgram.loadStrFromFile(fragFile));
	}
	
	public void genProgram(GL4 gl,String vert, String frag) {
		int vertID=ShaderProgram.compileShader(gl,GL4.GL_VERTEX_SHADER,"vertex",vert);
		int fragID=ShaderProgram.compileShader(gl, GL4.GL_FRAGMENT_SHADER, "fragment", frag);
		program=compileProgram(gl,vertID,fragID);
		System.out.println("Shader gen, program="+program+", vert="+vertID+", frag="+fragID);
	}
	public void bindProgram(GL4 gl) {
		gl.glUseProgram(program);
	}
	public void delete(GL4 gl) {
		gl.glDeleteProgram(program);
	}
	public void uniformMat4(GL4 gl,String param,Mat4 mat) {
		gl.glUniformMatrix4fv(gl.glGetUniformLocation(program, param), 1, false, mat.toDfb_());
	}
	public void uniformTex(GL4 gl,String param,Texture tex) {
		gl.glUniform1i(gl.glGetUniformLocation(program, param), tex.id);
	}

}
