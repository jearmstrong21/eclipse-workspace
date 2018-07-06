package co.megadodo.lwjgl.glframework.test;

import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.assimp.Assimp;

import co.megadodo.lwjgl.glframework.Mathf;
import co.megadodo.lwjgl.glframework.buffer.PolygonMode;
import co.megadodo.lwjgl.glframework.model.Mesh;
import co.megadodo.lwjgl.glframework.model.ModelLoader;
import co.megadodo.lwjgl.glframework.shader.ShaderProgram;
import co.megadodo.lwjgl.glframework.utils.GLUtilities;
import co.megadodo.lwjgl.glframework.window.GLWindow;
import co.megadodo.lwjgl.glframework.window.Keys;

public class TestGLFramework {

	public static void main(String[] args) {

		GLWindow.initGLFW();
		GLWindow window = new GLWindow();
		window.hints();
		window.gen();
		window.setSize(500, 500);
		window.setTitle("Test Framework");
		window.show();
		window.bind();

		GLUtilities.printGLInfo();

		ShaderProgram shader = new ShaderProgram();
		shader.gen();
		shader.attach(ShaderProgram.compileShaderFiles(GL_VERTEX_SHADER, "vertex", "Shaders/model.vert"));
		shader.attach(ShaderProgram.compileShaderFiles(GL_FRAGMENT_SHADER, "fragment", "Shaders/model.frag"));
		shader.link();
		
		Mesh[] modelsKoi=ModelLoader.loadModel("models/koi.obj",Assimp.aiProcess_JoinIdenticalVertices | Assimp.aiProcess_Triangulate | Assimp.aiProcess_FixInfacingNormals);
		Mesh[] modelsDino=ModelLoader.loadModel("models/dinosaur.obj",Assimp.aiProcess_JoinIdenticalVertices | Assimp.aiProcess_Triangulate | Assimp.aiProcess_FixInfacingNormals);

		MeshRenderer[]rendKoi=new MeshRenderer[modelsKoi.length];
		for(int i=0;i<modelsKoi.length;i++) {
			rendKoi[i]=new MeshRenderer();
			rendKoi[i].mesh=modelsKoi[i];
			rendKoi[i].gen();
		}
		MeshRenderer[]rendDino=new MeshRenderer[modelsDino.length];
		for(int i=0;i<modelsDino.length;i++) {
			rendDino[i]=new MeshRenderer();
			rendDino[i].mesh=modelsDino[i];
			rendDino[i].gen();
		}
		
		float camX=0,camZ=0;
		
		boolean normals=false,uvs=false;
		
		int polyMode=PolygonMode.Fill;

		while (window.isOpen()) {
			window.bind();
			
			GLUtilities.clearScreen(1, 1, 1);
			GLUtilities.enableDepth();

			float camRotY=Mathf.map(window.getMouseX(), 0, window.getWidth(), -Mathf.PI/2,Mathf.PI/2);
			float camRotX=Mathf.map(window.getMouseY(), 0, window.getHeight(), 10,-10);
			
			float dirX=Mathf.cos(camRotY);
			float dirZ=Mathf.sin(camRotY);
			
			Vector3f camPos=new Vector3f(camX,0,camZ);
			Vector3f camLookAt=new Vector3f(camX+dirX*10,camRotX,camZ+dirZ*10);
			Vector3f camUp=new Vector3f(0,1,0);
			
			Vector3f forward=new Vector3f(dirX,0,dirZ);
			Vector3f right=forward.rotateY(Mathf.toRadians(-90));
			
			float dx=0;
			float dz=0;
			float speed=1f;
			if(window.isKeyDown(Keys.LeftShift)) {
				speed=0.25f;
			}
			if(window.isKeyDown(Keys.LeftAlt)) {
				speed=4;
			}
			if(window.isKeyDown('W')) {
				dx+=dirX*speed;
				dz+=dirZ*speed;
			}
			if(window.isKeyDown('S')) {
				dx-=dirX*speed;
				dz-=dirZ*speed;
			}
			if(window.isKeyDown('A')) {
				dx-=right.x*speed;
				dz-=right.z*speed;
			}
			if(window.isKeyDown('D')) {
				dx+=right.x*speed;
				dz+=right.z*speed;
			}
			camX+=dx;
			camZ+=dz;

			shader.bind();
			
			Matrix4f projection=new Matrix4f().identity().perspective(Mathf.toRadians(45), 1, 0.01f, 1000);
			Matrix4f view=new Matrix4f().identity().lookAt(camPos,camLookAt,camUp);
			
			Matrix4f matKoi=new Matrix4f().identity().translate(0, 0,3);
			Matrix4f matDino=new Matrix4f().identity().translate(0,0,-3);
			
			//TODO:
			//dino on random path (simplex noise)
			//koi follows dino
			
			shader.setMat4("projection", projection);
			shader.setMat4("view", view);
			shader.setBool("shadeNormals", normals);
			shader.setBool("shadeUVs", uvs);
			
			if(window.wasJustPressed('N'))normals=!normals;
			if(window.wasJustPressed('U'))uvs=!uvs;
			if(window.wasJustPressed('R'))polyMode=(polyMode==PolygonMode.Fill?PolygonMode.Lines:PolygonMode.Fill);

			shader.setMat4("model",matKoi);
			for(MeshRenderer mr:rendKoi) {
				mr.bind();
				mr.polyMode=polyMode;
				mr.render();
				mr.unbind();
			}
			
			shader.setMat4("model", matDino);
			for(MeshRenderer mr:rendDino) {
				mr.bind();
				mr.polyMode=polyMode;
				mr.render();
				mr.unbind();
			}
			
			shader.unbind();

			window.clearInputs();
			window.unbind();
		}
		shader.delete();

		for(MeshRenderer mr:rendKoi) {
			mr.delete();
		}
		for(MeshRenderer mr:rendDino) {
			mr.delete();
		}
		
		window.delete();

		GLWindow.endGLFW();

	}

}