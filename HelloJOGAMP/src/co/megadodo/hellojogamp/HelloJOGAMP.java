
package co.megadodo.hellojogamp;

import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Scanner;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

import co.megadodo.glframework.ArrayObject;
import co.megadodo.glframework.BufferObjectFloat;
import co.megadodo.glframework.BufferObjectInt;
import co.megadodo.glframework.BufferTarget;
import co.megadodo.glframework.BufferType;
import co.megadodo.glframework.BufferUtils;
import co.megadodo.glframework.PolygonMode;
import co.megadodo.glframework.ShaderProgram;
import glm.Glm;
import glm.mat._4.Mat4;
import glm.vec._3.Vec3;

public class HelloJOGAMP implements GLEventListener, KeyListener {
	
	Frame frame;

	public static void main(String[]args) {
		new HelloJOGAMP();
	}
	public HelloJOGAMP() {
		GLProfile glp=GLProfile.get(GLProfile.GL4);
		GLCapabilities caps=new GLCapabilities(glp);
		GLCanvas canvas=new GLCanvas(caps);
		frame=new Frame("Hello JOGAMP");
		frame.setSize(400,400);
		frame.add(canvas);
		frame.setVisible(true);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		canvas.addGLEventListener(this);
		frame.addKeyListener(this);
		FPSAnimator animator=new FPSAnimator(canvas, 60);
//		animator.add(canvas);
		animator.start();
	}
	
	public void display(GLAutoDrawable drawable) {
		update();
		render(drawable);
	}
	
	public void dispose(GLAutoDrawable drawable) {
		GL4 gl=(GL4)drawable.getGL();
		shader.delete(gl);
	}
	
	ShaderProgram shader;
	ArrayObject vao;
	
	public void init(GLAutoDrawable drawable) {
		GL4 gl = (GL4)drawable.getGL();
		
		String version=gl.glGetString(GL4.GL_VERSION);
		String vendor=gl.glGetString(GL4.GL_VENDOR);
		String glslVersion=gl.glGetString(GL4.GL_SHADING_LANGUAGE_VERSION);
		String renderer=gl.glGetString(GL4.GL_RENDERER);
		IntBuffer bufferMajor=IntBuffer.allocate(1);
		IntBuffer bufferMinor=IntBuffer.allocate(1);
		gl.glGetIntegerv(GL4.GL_MAJOR_VERSION, bufferMajor);
		gl.glGetIntegerv(GL4.GL_MINOR_VERSION, bufferMinor);
		int major=bufferMajor.get(0);
		int minor=bufferMinor.get(0);
		
		System.out.println("Version: "+version);
		System.out.println("Vendor: "+vendor);
		System.out.println("GLSL Version: "+glslVersion);
		System.out.println("Renderer: "+renderer);
		System.out.println("Major version: "+major);
		System.out.println("Minor version: "+minor);
		
		
		shader=new ShaderProgram();
		shader.genProgramFiles(gl, "Shaders/shader.vert", "Shaders/shader.frag");
		
		vao=new ArrayObject();
		vao.polygonMode=PolygonMode.Fill;
		
		float[]posData=new float[] {0,0,0,1,0,0,0,0,1,0};
		int[]triData=new int[] {0,1,2};
		
		BufferObjectFloat posbf=new BufferObjectFloat(BufferTarget.ArrayBuffer, BufferType.Static, 3, 0, posData);
		posbf.genBuffers(gl);
		
		BufferObjectInt trisbf=new BufferObjectInt(BufferTarget.ElementBuffer, BufferType.Static, triData);
		trisbf.genBuffers(gl);
		
		vao.buffers.add(posbf);
		vao.buffers.add(trisbf);
		vao.genBuffers(gl);
//		camPos=new Vec3(0,0,-2.5);
//		camDir=new Vec3(0,0,1.0);
//		camUp=new Vec3(0,1,0);
	}
	
	public void reshape(GLAutoDrawable drawable,int x,int y,int w,int h) {
		
	}
	
	public void update() {
		
	}
	float ang=0;
	Vec3 camPos;
	Vec3 camDir;
	Vec3 camUp;
	public void render(GLAutoDrawable drawable) {
		GL4 gl = (GL4)drawable.getGL();
		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		gl.glClear(GL4.GL_DEPTH_BUFFER_BIT|GL4.GL_COLOR_BUFFER_BIT);
		gl.glEnable(GL4.GL_DEPTH_TEST);
		shader.bindProgram(gl);
		
		Mat4 view=new Mat4();
//		view.translate(camPos);
//		view.lookAt(camPos, camDir, camUp);
//		view.rotate(ang,new Vec3(0,1,0));
		Mat4 model=new Mat4();
//		model.rotate(ang, new Vec3(0.0,1.0,0.0));
		Mat4 proj=new Mat4();
//		proj.perspective(80.0f, 1.0f, 0.01f, 10.0f);
		
		float angSpeed=0.01f;
		float camSpeed=0.01f;
		if(keys.contains(KeyEvent.VK_LEFT))ang+=angSpeed;
		if(keys.contains(KeyEvent.VK_RIGHT))ang-=angSpeed;
		if(charKeys.contains('w'))camPos.add(Glm.mul_(camDir, camSpeed));
		if(charKeys.contains('s'))camPos.add(Glm.mul_(camDir, -camSpeed));
		shader.uniformMat4(gl, "Matrix", proj.mul(view.mul(model)));
		vao.render(gl);
		frame.requestFocus();
	}
	ArrayList<Integer>keys=new ArrayList<Integer>();
	ArrayList<Character>charKeys=new ArrayList<Character>();
	int n=0;
	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("Key pressed "+n);
		n++;
		if(!keys.contains(e.getKeyCode()))keys.add((Integer)e.getKeyCode());
		if(!charKeys.contains(e.getKeyChar()))charKeys.add((Character)e.getKeyChar());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(keys.contains(e.getKeyCode()))keys.remove((Integer)e.getKeyCode());
		if(charKeys.contains(e.getKeyChar()))charKeys.remove((Character)e.getKeyChar());
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if(!keys.contains(e.getKeyCode()))keys.add((Integer)e.getKeyCode());
		if(!charKeys.contains(e.getKeyChar()))charKeys.add((Character)e.getKeyChar());
	}

}
