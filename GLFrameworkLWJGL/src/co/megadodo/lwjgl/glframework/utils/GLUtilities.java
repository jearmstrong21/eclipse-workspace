package co.megadodo.lwjgl.glframework.utils;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import co.megadodo.lwjgl.glframework.window.GLWindow;

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

public class GLUtilities {
	
	public static float getTime() {
		return (float)glfwGetTime();
	}
	
	public static void clearScreen() {
		clearScreen(1,1,1);
	}
	
	public static void clearScreen(float r,float g,float b) {
		glClearColor(r,g,b,1);
		glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);
	}
	
	public static void setPolygonMode(int mode) {
		glPolygonMode(GL_FRONT_AND_BACK, mode);
	}
	
	public static void setProvokingVertex(int mode) {
		glProvokingVertex(mode);
	}
	
	public static void enableDepth() {
		glEnable(GL_DEPTH_TEST);
		glDepthFunc(GL_LESS);
	}
	
	public static void disableDepth() {
		glDisable(GL_DEPTH_TEST);
	}
	
	public static void setDefaultViewport(GLWindow win) {
		setViewport(0,0,win.getFBOWidth(),win.getFBOHeight());
	}
	
	public static void enableProgramPointSize() {
		glEnable(GL_PROGRAM_POINT_SIZE);
	}
	
	public static void disableProgramPointSize() {
		glDisable(GL_PROGRAM_POINT_SIZE);
	}

	public static void setViewport(int x,int y,int w,int h) {
		glViewport(x, y, w, h);
	}
	
	public static void printGLInfo() {
		String version = glGetString(GL_VERSION);
		String vendor = glGetString(GL_VENDOR);
		String renderer = glGetString(GL_RENDERER);
		String glslVersion = glGetString(GL_SHADING_LANGUAGE_VERSION);
		int majorVersion = glGetInteger(GL_MAJOR_VERSION);
		int minorVersion = glGetInteger(GL_MINOR_VERSION);
		System.out.println("Version       : " + version);
		System.out.println("Vendor        : " + vendor);
		System.out.println("Renderer      : " + renderer);
		System.out.println("GLSL version  : " + glslVersion);
		System.out.println("Major version : " + majorVersion);
		System.out.println("Minor version : " + minorVersion);
	}

}
