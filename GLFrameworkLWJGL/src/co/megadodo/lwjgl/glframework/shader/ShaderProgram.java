package co.megadodo.lwjgl.glframework.shader;
import co.megadodo.lwjgl.glframework.*;
import co.megadodo.lwjgl.glframework.utils.*;
import co.megadodo.lwjgl.glframework.texture.*;

import org.joml.Matrix4f;
import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.io.File;
import java.nio.*;
import java.util.Scanner;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL21.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL31.*;
import static org.lwjgl.opengl.GL32.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.opengl.GL40.*;
import static org.lwjgl.opengl.GL41.*;
import static org.lwjgl.opengl.GL42.*;
import static org.lwjgl.opengl.GL43.*;
import static org.lwjgl.opengl.GL44.*;
import static org.lwjgl.opengl.GL45.*;
import static org.lwjgl.opengl.GL46.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class ShaderProgram implements GLResource  {
	
	public static int compileShader(int type, String strType,String source) {
		int id=glCreateShader(type);
		glShaderSource(id,source);
		glCompileShader(id);
		if(glGetShaderi(id, GL_COMPILE_STATUS)==GL_FALSE) {
			System.out.println("Shader compile error for type "+strType+":\n"+glGetShaderInfoLog(id));
		}
		return id;
	}
	
	public static int compileShaderFiles(int type, String strType, String filename) {
		return compileShader(type,strType,Utilities.loadStrFromFile(filename));
	}
	
	public static ShaderProgram createShader(String vfn,String ffn) {
		ShaderProgram sp=new ShaderProgram();
		sp.gen();
		sp.attach(vfn, GL_VERTEX_SHADER);
		sp.attach(ffn,GL_FRAGMENT_SHADER);
		sp.link();
		return sp;
	}

	public int id;
	
	public ShaderProgram() {
		
	}
	
	public void gen() {
		id=glCreateProgram();
	}
	
	public void attach(int shader) {
		glAttachShader(id, shader);
	}
	
	public void attach(String filename, int type) {
		attach(compileShaderFiles(type,(type==GL_VERTEX_SHADER?"vertex":"fragment"),filename));
	}
	
	public void link() {
		glLinkProgram(id);
	}
	
	public void bind() {
		glUseProgram(id);
	}
	
	public void unbind() {
		glUseProgram(0);
	}
	
	public void setInt(String paramName,int i) {
		glUniform1i(glGetUniformLocation(id,paramName),i);
	}
	
	public void setTexture(String paramName,Texture t) {
		setInt(paramName,t.id);
	}
	
	public void setTexture(String paramName,int i) {
		setInt(paramName,i);
	}
	
	public void setFloat(String paramName,float f) {
		glUniform1f(glGetUniformLocation(id,paramName),f);
	}
	
	public void setBool(String paramName,boolean b) {
		glUniform1i(glGetUniformLocation(id,paramName),b?1:0);
	}
	
	public void setMat4(String paramName,Matrix4f m) {
		int loc=glGetUniformLocation(id,paramName);
		FloatBuffer buf=BufferUtils.createFloatBuffer(16);
		buf.put(m.get(new float[16]));
		buf.flip();
		glUniformMatrix4fv(loc,false,buf);
	}
	
	public void delete() {
		glDeleteProgram(id);
	}
	
}
