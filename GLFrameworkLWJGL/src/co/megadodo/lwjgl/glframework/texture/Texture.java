package co.megadodo.lwjgl.glframework.texture;

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

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.system.MemoryStack;

import co.megadodo.lwjgl.glframework.GLResource;

public class Texture implements GLResource {
	
	public static int WRAP_U=GL_TEXTURE_WRAP_S;
	public static int WRAP_V=GL_TEXTURE_WRAP_T;
	
	public static int FILTER_NEAREST=GL_NEAREST;
	public static int FILTER_LINEAR=GL_LINEAR;
	
	public static int MIN_FILTER=GL_TEXTURE_MIN_FILTER;
	public static int MAG_FILTER=GL_TEXTURE_MAG_FILTER;
	public static int CLAMP_TO_BORDER=GL_CLAMP_TO_BORDER;
	
	public int id;
	
	public int target;
	
	public int width;
	public int height;
	public ByteBuffer data;
	
	public void gen() {
		id=glGenTextures();
	}
	
	public void bind() {
		glBindTexture(target,id);
	}
	
	public void setParam(int name, int value) {
		glTexParameteri(target,name,value);
	}
	
	public void setData(int w, int h, ByteBuffer data) {
		this.data=data;
		width=w;
		height=h;
		glTexImage2D(target,0,GL_RGB,w,h,0,GL_RGB,GL_UNSIGNED_BYTE,data);
	}
	
	public static void activateUnit(int i) {
		glActiveTexture(GL_TEXTURE0+i);
	}
	
	public void bindToUnit(int i) {
		activateUnit(i);
		bind();
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
		t.target=TextureTarget.Tex2D;
		t.gen();
		t.bind();
		t.setParam(WRAP_U, CLAMP_TO_BORDER);
		t.setParam(WRAP_V, CLAMP_TO_BORDER);
		t.setParam(MIN_FILTER, FILTER_NEAREST);
		t.setParam(MAG_FILTER, FILTER_NEAREST);
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