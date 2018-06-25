package co.megadodo.hellojogamp;

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
import co.megadodo.glframework.Texture;
import co.megadodo.glframework.Texture.TextureID;

public class HelloTexture implements GLEventListener {

	public static void main(String[] args) {
		GLProfile profile = GLProfile.get(GLProfile.GL4);
		GLCapabilities caps = new GLCapabilities(profile);
		GLWindow window = GLWindow.create(caps);

		HelloTexture obj = new HelloTexture();
		window.addGLEventListener(obj);

		FPSAnimator animator = new FPSAnimator(window, 60, true);
		window.setSize(800, 800);
		window.setTitle("Hello Texture");
		window.setVisible(true);
		animator.start();
	}
	
	ShaderProgram shader;
	ArrayObject vao;
	Texture tex;

	@Override
	public void init(GLAutoDrawable drawable) {
		GL4 gl = (GL4) drawable.getGL();
		shader=new ShaderProgram();
		shader.genProgramFiles(gl, "Shaders/hellotexture.vert", "Shaders/hellotexture.frag");
		
		vao=new ArrayObject();
		vao.addBuffer(new BufferObjectFloat(BufferTarget.ArrayBuffer, BufferType.Static, 3, 0, new float[] {
				-1,-1,0,
				-1, 1,0,
				 1, 1,0,
				 1,-1,0
		}));
		vao.addBuffer(new BufferObjectFloat(BufferTarget.ArrayBuffer, BufferType.Static, 2, 1, new float[] {
				0,0,
				0,1,
				1,1,
				1,0
		}));
		vao.addBuffer(new BufferObjectInt(BufferTarget.ElementBuffer, BufferType.Static, new int[] {
				0,1,2,
				0,2,3
		}));
		vao.genBuffers(gl);
		tex=new Texture();
		tex.genTexturesFile(gl, "Textures/Zoe1.png");
	}

	@Override
	public void display( GLAutoDrawable drawable) {
		GL4 gl = (GL4) drawable.getGL();
		gl.glClearColor(1, 1, 1, 1);
		gl.glClear(GL4.GL_COLOR_BUFFER_BIT|GL4.GL_DEPTH_BUFFER_BIT);
		gl.glEnable(GL4.GL_DEPTH_TEST);
		shader.bindProgram(gl);
//		tex.bindTexture(gl, shader, TextureID.Tex0);
		vao.render(gl);
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		GL4 gl = (GL4) drawable.getGL();
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int arg1, int arg2, int arg3, int arg4) {
		GL4 gl = (GL4) drawable.getGL();
	}

}
