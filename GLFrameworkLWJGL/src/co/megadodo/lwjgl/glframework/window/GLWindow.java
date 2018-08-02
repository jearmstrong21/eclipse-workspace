package co.megadodo.lwjgl.glframework.window;

import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_FORWARD_COMPAT;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwGetFramebufferSize;
import static org.lwjgl.glfw.GLFW.glfwHideWindow;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetCharCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwSetWindowTitle;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

import java.util.ArrayList;

import org.lwjgl.glfw.GLFWCharCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.opengl.GL;

import co.megadodo.lwjgl.glframework.GLResource;
import co.megadodo.lwjgl.glframework.utils.Mathf;

public class GLWindow implements GLResource {
	
	public long id;
	
	public static boolean glfwInitialized=false;
	public static boolean glfwTerminated=false;
	
	public static void initGLFW() {
		if(!glfwInitialized) {
			glfwInit();
			glfwInitialized=true;
		}
	}
	
	public static void endGLFW() {
		if(!glfwTerminated) {
			glfwTerminate();
			glfwTerminated=true;
		}
	}
	
	public GLWindow() {
		initGLFW();
		hints();
		gen();
	}
	
	
	private int width;
	private int height;
	private String title;
	
	public void hints() {
		hints(4,1,true,ProfileType.Core);
	}
	
	public void hints(int major,int minor,boolean forwardCompat,int profile) {
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, major);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, minor);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, forwardCompat?GLFW_TRUE:GLFW_FALSE);
		glfwWindowHint(GLFW_OPENGL_PROFILE, profile);
	}
	
	public float getGLMouseX() {
		return Mathf.map(getMouseX(), 0, getWidth(), -1, 1);
	}
	
	public float getGLMouseY() {
		return Mathf.map(getMouseY(), 0, getHeight(), 1, -1);
	}
	
	public int getFBOWidth() {
		int[]w=new int[1];
		int[]h=new int[1];
		glfwGetFramebufferSize(id, w, h);
		return w[0];
	}
	
	public int getFBOHeight() {
		int[]w=new int[1];
		int[]h=new int[1];
		glfwGetFramebufferSize(id, w, h);
		return h[0];
	}
	
	public void requestFocus() {
		hide();
		show();
	}
	
	public ArrayList<Character>keys=new ArrayList<Character>();
	
	public boolean isKeyDown(char c) {
		return keys.contains(c);
	}
	
	public boolean wasJustPressed(char c) {
		return justPressed.contains(c);
	}
	
	public boolean wasJustReleased(char c) {
		return justReleased.contains(c);
	}
	
	public ArrayList<Character>justPressed=new ArrayList<Character>();
	public ArrayList<Character>justReleased=new ArrayList<Character>();
	
	public void clearInputs() {
		justPressed.clear();
		justReleased.clear();
	}
	
	public void gen() {
		if(!glfwInitialized){
			glfwInit();
			glfwInitialized=true;
		}
		width=100;
		height=100;
		title="Default title";
		id=glfwCreateWindow(width, height, title, 0, 0);
		glfwMakeContextCurrent(id);
		glfwSwapInterval(1);
		glfwSetKeyCallback(id, new GLFWKeyCallback() {
			
			@Override
			public void invoke(long window, int key, int scancode, int action, int mods) {
				if(action==GLFW_PRESS) {
					keys.add((char)key);
					justPressed.add((char)key);
//					System.out.println("Added key "+((char)key));
				}
				if(action==GLFW_RELEASE) {
					keys.remove(Character.valueOf((char)key));
					justReleased.add((char)key);
//					System.out.println("Removed key "+((char)key));
				}
			}
		});
		glfwSetMouseButtonCallback(id, new GLFWMouseButtonCallback() {
			
			@Override
			public void invoke(long window, int button, int action, int mods) {
				
			}
		});
		glfwSetCharCallback(id, new GLFWCharCallback() {
			
			@Override
			public void invoke(long window, int codepoint) {
				
			}
		});
		bind();
	}
	
	public float getMouseX() {
		double[]xpos=new double[1];
		double[]ypos=new double[1];
		glfwGetCursorPos(id, xpos, ypos);
		return (float)xpos[0];
	}
	
	public float getMouseY() {
		double[]xpos=new double[1];
		double[]ypos=new double[1];
		glfwGetCursorPos(id, xpos, ypos);
		return (float)ypos[0];
	}
	
	public void bind() {
		glfwMakeContextCurrent(id);
		GL.createCapabilities();
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
	
	public void unbind() {
		glfwSwapBuffers(id);
		glfwPollEvents();
	}
	
	public void delete() {
		glfwDestroyWindow(id);
	}
	
	public void hide() {
		glfwHideWindow(id);
	}
	
	public void show() {
		glfwShowWindow(id);
	}

}
