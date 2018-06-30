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

public class GLWindow {
	
	public long id;
	
	public static boolean glfwInitialized=false;
	
	public static void initGLFW() {
		if(!glfwInitialized) {
			glfwInit();
			glfwInitialized=true;
		}
	}
	
	public GLWindow() {
		initGLFW();
	}
	
	private boolean defaultHints=false;
	
	private int width;
	private int height;
	private String title;
	
	public void hints() {
		hints(4,1,true,ProfileType.Core);
	}
	
	public void hints(int major,int minor,boolean forwardCompat,int profile) {
		if(!defaultHints) {
			glfwDefaultWindowHints();
			defaultHints=true;
		}
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, major);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, minor);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, forwardCompat?GLFW_TRUE:GLFW_FALSE);
		glfwWindowHint(GLFW_OPENGL_PROFILE, profile);
	}
	
	public void gen() {
		width=0;
		height=0;
		title="Default title";
		if(!defaultHints) {
			hints();
		}
		id=glfwCreateWindow(width, height, title, 0, 0);
	}
	
	public void setSize(int w,int h) {
		glfwSetWindowSize(id, w, h);
		width=w;
		height=h;
	}
	
	public void setTitle(String t) {
		glfwSetWindowTitle(id,t);
		title=t;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public String getTitle() {
		return title;
	}
	
	public boolean isOpen() {
		return !glfwWindowShouldClose(id);
	}

}
