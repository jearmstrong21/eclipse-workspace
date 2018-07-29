package co.megadodo.lwjgl.glframework.framebuffer;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import co.megadodo.lwjgl.glframework.GLResource;
import co.megadodo.lwjgl.glframework.texture.Texture;

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


public class Framebuffer implements GLResource {
	
	public int id;
	public int depthRBO;
	
	public void gen() {
		id=glGenFramebuffers();
	}
	
	public boolean complete() {
		return glCheckFramebufferStatus(GL_FRAMEBUFFER)==GL_FRAMEBUFFER_COMPLETE;
	}
	
	public void createDepthRBO(int w,int h) {
		depthRBO=glGenRenderbuffers();
		glBindRenderbuffer(GL_RENDERBUFFER,depthRBO);
		glRenderbufferStorage(GL_RENDERBUFFER,GL_DEPTH_COMPONENT,w,h);
		glFramebufferRenderbuffer(GL_FRAMEBUFFER,GL_DEPTH_ATTACHMENT,GL_RENDERBUFFER,depthRBO);
	}
	
	public void bind() {
		glBindFramebuffer(GL_FRAMEBUFFER,id);
	}
	
	public void unbind() {
		glBindFramebuffer(GL_FRAMEBUFFER,0);
	}
	
	public void attachTex(Texture tex, int attachment) {
		glFramebufferTexture2D(GL_FRAMEBUFFER, attachment, GL_TEXTURE_2D, tex.id, 0);
	}
	
	public void delete() {
		glDeleteFramebuffers(id);
	}

}
