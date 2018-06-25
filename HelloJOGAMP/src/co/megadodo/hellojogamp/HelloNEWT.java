package co.megadodo.hellojogamp;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.FPSAnimator;

import co.megadodo.glframework.ArrayObject;
import co.megadodo.glframework.BufferObjectFloat;
import co.megadodo.glframework.BufferObjectInt;
import co.megadodo.glframework.BufferTarget;
import co.megadodo.glframework.BufferType;
import co.megadodo.glframework.ShaderProgram;
import glm.mat._4.Mat4;
import glm.vec._3.Vec3;
import glm.vec._4.Vec4;

public class HelloNEWT implements GLEventListener, KeyListener {

	public static void main(String[] args) {
		new HelloNEWT();
	}

	public static final int WINDOW_WIDTH=500;
	public static final int WINDOW_HEIGHT=500;
	public static final String WINDOW_TITLE="Hello NEWT!";
	
	public GLWindow window;
	
	public HelloNEWT() {
		GLProfile profile = GLProfile.get(GLProfile.GL4);
		GLCapabilities caps = new GLCapabilities(profile);
		window = GLWindow.create(caps);
		window.addKeyListener(this);
		
		FPSAnimator animator = new FPSAnimator(window, 60, true);
		
		window.addWindowListener(new WindowAdapter() {
			@Override
            public void windowDestroyNotify(WindowEvent arg0) {
                // Use a dedicate thread to run the stop() to ensure that the
                // animator stops before program exits.
                new Thread() {
                    @Override
                    public void run() {
                        if (animator.isStarted())
                            animator.stop();    // stop the animator loop
                        System.exit(0);
                    }
                }.start();
            }
		});
		window.addGLEventListener(this);
		window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		window.setTitle(WINDOW_TITLE);
		window.setVisible(true);
		animator.start();
	}
	
	ArrayObject ao;
	ShaderProgram shader;

	@Override
	public void init(GLAutoDrawable drawable) {
		GL4 gl = (GL4)drawable.getGL();
		BufferObjectFloat pos=new BufferObjectFloat(BufferTarget.ArrayBuffer, BufferType.Static, 3, 0, new float[] {
				-1,-1,-1,
				-1,-1, 1,
				-1, 1, 1,
				-1, 1,-1,
				
				 1,-1,-1,
				 1,-1, 1,
				 1, 1, 1,
				 1, 1,-1,
				 
				-1,-1,-1,
				-1, 1,-1,
				 1, 1,-1,
				 1,-1,-1,
				 
				-1,-1, 1,
				-1, 1, 1,
				 1, 1, 1,
				 1,-1, 1,
				 
				-1,-1,-1,
				-1,-1, 1,
				 1,-1, 1,
				 1,-1,-1,
				 
				-1, 1,-1,
				-1, 1, 1,
				 1, 1, 1,
				 1, 1,-1
		});
		BufferObjectFloat col=new BufferObjectFloat(BufferTarget.ArrayBuffer, BufferType.Static, 3, 1, new float[] {
				0,0,0,
				0,0,1,
				0,1,1,
				0,1,0,
				
				1,0,0,
				1,0,1,
				1,1,1,
				1,1,0,
				
				0,0,0,
				0,1,0,
				1,1,0,
				1,0,0,
				
				0,0,1,
				0,1,1,
				1,1,1,
				1,0,1,
				
				0,0,0,
				0,0,1,
				1,0,1,
				1,0,0,
				
				0,1,0,
				0,1,1,
				1,1,1,
				1,1,0
		});
		BufferObjectInt tri=new BufferObjectInt(BufferTarget.ElementBuffer, BufferType.Static, new int[] {
				0,1,2,
				0,2,3,
				
				4,5,6,
				4,6,7,
				
				8,9,10,
				8,10,11,
				
				12,13,14,
				12,14,15,
				
				16,17,18,
				16,18,19,
				
				20,21,22,
				20,22,23
		});
		ao=new ArrayObject();
		ao.addBuffer(pos);
		ao.addBuffer(col);
		ao.addBuffer(tri);
		ao.genBuffers(gl);
		shader=new ShaderProgram();
		shader.genProgramFiles(gl, "Shaders/shader.vert", "Shaders/shader.frag");
	}
	
	float time=0;

	Vec3 pos=new Vec3(0,0,-2.5f);
	float angle=0;
	
	@Override
	public void display(GLAutoDrawable drawable) {
		time++;
		GL4 gl = (GL4)drawable.getGL();
		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		gl.glClear(GL4.GL_COLOR_BUFFER_BIT|GL4.GL_DEPTH_BUFFER_BIT);
		gl.glEnable(GL4.GL_DEPTH_TEST);
		shader.bindProgram(gl);
		Mat4 mat=new Mat4();
		mat.perspective(80, 1, 0.001f, 10);
//		mat.rotate(time*0.01f, new Vec3(0,1,0));
		mat.translate(pos);
//		mat.rotate(angle, new Vec3(0,1,0));
//		mat.translate
		mat.rotate(time*0.01f,new Vec3(0,1,0));
		shader.uniformMat4(gl, "Matrix", mat);
		ao.render(gl);
		float speed=0.01f;
		Vec3 forward=new Mat4().rotate(angle, new Vec3(0,1,0)).mul(new Vec4(pos.x,pos.y,pos.z,1.0)).toVec3_();
		if(isA)pos.x-=speed;
		if(isD)pos.x+=speed;
		if(isW)pos.z-=speed;
		if(isS)pos.z+=speed;
		if(isRight)angle-=0.01;
		if(isLeft)angle+=0.01;
		System.out.println(pos.x+" "+pos.z);
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		GL4 gl = (GL4)drawable.getGL();
		ao.delete(gl);
		shader.delete(gl);
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int arg0, int arg1, int arg2, int arg3) {
		GL4 gl = (GL4)drawable.getGL();
	}
	
	boolean isA=false,isD=false,isW=false,isS=false,isLeft=false,isRight=false;

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyChar()=='a')isA=!isA;
		if(e.getKeyChar()=='d')isD=!isD;
		if(e.getKeyChar()=='w')isW=!isW;
		if(e.getKeyChar()=='s')isS=!isS;
		if(e.getKeyChar()=='q')isLeft=!isLeft;
		if(e.getKeyChar()=='e')isRight=!isRight;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
