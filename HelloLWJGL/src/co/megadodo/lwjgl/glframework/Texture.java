package co.megadodo.lwjgl.glframework;

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

import static org.lwjgl.stb.STBImage.*;

public class Texture implements GLResource {
	
	public int id;
	
	public int width;
	public int height;
	
	public void gen() {
		id=glGenTextures();
	}
	
	public void bind() {
		glBindTexture(GL_TEXTURE_2D,id);
	}
	
	public void setParam(int name, int value) {
		glTexParameteri(GL_TEXTURE_2D,name,value);
	}
	
	public void setData(int w, int h, ByteBuffer data) {
		width=w;
		height=h;
		glTexImage2D(GL_TEXTURE_2D,0,GL_RGBA8,w,h,0,GL_RGBA,GL_UNSIGNED_BYTE,data);
	}
	
	public static void activateUnit(int i) {
		glActiveTexture(GL_TEXTURE0+i);
	}
	
	public static Texture loadTexture(String filename) {
		ByteBuffer img;
		int w,h;
		try(MemoryStack stack=MemoryStack.stackPush()){
			IntBuffer ibw=stack.mallocInt(1);
			IntBuffer ibh=stack.mallocInt(1);
			IntBuffer comp=stack.mallocInt(1);
			stbi_set_flip_vertically_on_load(true);
			img=stbi_load(filename,ibw,ibh,comp,4);
			if(img==null) {
				throw new RuntimeException("Failed to load image "+filename);
			}
			w=ibw.get();
			h=ibh.get();
		}
		return createTexture(w,h,img);
	}
	
	public static Texture createTexture(int w,int h,ByteBuffer img) {
		Texture t=new Texture();
		t.gen();
		t.bind();
		t.setParam(GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
		t.setParam(GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);
		t.setParam(GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		t.setParam(GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		t.setData(w, h, img);
		t.unbind();
		return t;
	}
	
	public void unbind() {
		glBindTexture(GL_TEXTURE_2D,0);
	}
	
	public void delete() {
		glDeleteTextures(id);
	}

}
