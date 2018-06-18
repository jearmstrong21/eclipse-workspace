package co.megadodo.hellojogamp;

import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.IntBuffer;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

import co.megadodo.hellojogamp.VertexBuffer.PolygonMode;
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
	VertexBuffer vb;
	float[]posData=new float[] {
			0,0,0,
			1,0,0,
			0,1,0
	};
	float[]colData=new float[] {
			1,0,0,
			0,1,0,
			0,0,1
	};
	int[]triData=new int[] {0,1,2,0,2,3,
			4,5,6,4,6,7,
			8,9,10,8,10,11,
			12,13,14,12,14,15,
			16,17,18,16,18,19,
			20,21,22,20,22,23
	};
	
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
		
		//maybe name gl framework GLEZ (gl ez, gl easy)
		
		shader=new ShaderProgram();
		shader.genProgramFiles(gl, "Shaders/shader.vert", "Shaders/shader.frag");
		
		vb=new VertexBuffer();
		vb.polygonMode=PolygonMode.Fill;
		vb.genBuffers(gl,posData,colData,triData);
	}
	
	public void reshape(GLAutoDrawable drawable,int x,int y,int w,int h) {
		
	}
	
	public void update() {
		
	}
	float x=0,z=-2;
	boolean isA=false,isD=false,isW=false,isS=false;
	float time=0;
	public void render(GLAutoDrawable drawable) {
		time++;
		GL4 gl = (GL4)drawable.getGL();
		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		gl.glClear(GL4.GL_DEPTH_BUFFER_BIT|GL4.GL_COLOR_BUFFER_BIT);
		shader.bindProgram(gl);
//		Mat4 view=new Mat4();
		Mat4 transform=new Mat4();
		transform.perspective(80.0f, 1.0f, 0.01f, 10.0f);
//		transform.rotate(time*0.01f, new Vec3(1, 0, 0));
//		transform.rotate(time*0.02f, new Vec3(0, 1, 0));
//		transform.rotate(time*0.03f, new Vec3(0, 0, 1));
		transform.translate(new Vec3(x,0,z));
		frame.requestFocus();
		float speed=0.01f;
		if(isA)x-=speed;
		if(isD)x+=speed;
		if(isW)z+=speed;
		if(isS)z-=speed;
		shader.uniformMat4(gl, "Matrix", transform);
		vb.render(gl);
		System.out.println(isA+" "+isD+" "+isW+" "+isD+" "+x+" "+z);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_A)isA=true;
		if(e.getKeyCode()==KeyEvent.VK_D)isD=true;
		if(e.getKeyCode()==KeyEvent.VK_W)isW=true;
		if(e.getKeyCode()==KeyEvent.VK_S)isS=true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_A)isA=false;
		if(e.getKeyCode()==KeyEvent.VK_D)isD=false;
		if(e.getKeyCode()==KeyEvent.VK_W)isW=false;
		if(e.getKeyCode()==KeyEvent.VK_S)isS=false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
