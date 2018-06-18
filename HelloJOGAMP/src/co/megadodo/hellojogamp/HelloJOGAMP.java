package co.megadodo.hellojogamp;

import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.IntBuffer;
import java.util.ArrayList;

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
		shader.delProgram(gl);
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

		int w=100;
		int h=100;
		
		ArrayList<Vec3>posList=new ArrayList<Vec3>();
		ArrayList<Vec3>colList=new ArrayList<Vec3>();
		ArrayList<Integer>triList=new ArrayList<Integer>();
		
		float dx=1.0f/w;
		float dy=1.0f/h;
		OpenSimplexNoise osn=new OpenSimplexNoise((long)(Math.random()*Long.MAX_VALUE));
		for(int ix=0;ix<w;ix++) {
			for(int iy=0;iy<h;iy++) {
				float x=ix*dx*2.0f-1.0f;
				float y=iy*dy*2.0f-1.0f;
				int i=posList.size();
				float f00=((float)osn.eval(x,y));
				float f10=((float)osn.eval(x+dx,y));
				float f01=((float)osn.eval(x,y+dy));
				float f11=((float)osn.eval(x+dy,y+dx));
				Vec3 col00=new Vec3(f00,0,0);
				Vec3 col10=new Vec3(f10,0,0);
				Vec3 col01=new Vec3(f01,0,0);
				Vec3 col11=new Vec3(f11,0,0);
				posList.add(new Vec3(x,y,0));colList.add(col00);
				posList.add(new Vec3(x+dx*2,y,0));colList.add(col10);
				posList.add(new Vec3(x+dx*2,y+dy*2,0));colList.add(col11);
				posList.add(new Vec3(x,y+dy*2,0));colList.add(col01);
				triList.add(i+0);
				triList.add(i+1);
				triList.add(i+2);
				triList.add(i+0);
				triList.add(i+2);
				triList.add(i+3);
			}
		}
		
		float[]posData=BufferUtils.v3ToFloatArr(BufferUtils.listToVec3Arr(posList));
		float[]colData=BufferUtils.v3ToFloatArr(BufferUtils.listToVec3Arr(colList));
		int[]triData=BufferUtils.listToIntArr(triList);
		
		BufferObjectFloat posbf=new BufferObjectFloat(BufferTarget.ArrayBuffer, BufferType.Static, 3, 0, posData);
		posbf.genBuffers(gl);
		
		BufferObjectFloat colbf=new BufferObjectFloat(BufferTarget.ArrayBuffer, BufferType.Static, 3, 1, colData);
		colbf.genBuffers(gl);
		
		BufferObjectInt trisbf=new BufferObjectInt(BufferTarget.ElementBuffer, BufferType.Static, triData);
		trisbf.genBuffers(gl);
		
		vao.buffers.add(posbf);
		vao.buffers.add(colbf);
		vao.buffers.add(trisbf);
		vao.genBuffers(gl);
	}
	
	public void reshape(GLAutoDrawable drawable,int x,int y,int w,int h) {
		
	}
	
	public void update() {
		
	}
	public void render(GLAutoDrawable drawable) {
		GL4 gl = (GL4)drawable.getGL();
		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		gl.glClear(GL4.GL_DEPTH_BUFFER_BIT|GL4.GL_COLOR_BUFFER_BIT);
		shader.bindProgram(gl);
		shader.uniformMat4(gl, "Matrix", new Mat4());
		vao.render(gl);
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

}
