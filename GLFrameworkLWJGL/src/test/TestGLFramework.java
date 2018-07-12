package test;

//mesh-model-logic-render
//MMLR

import org.joml.Matrix4f;
import org.joml.Vector3f;

import co.megadodo.lwjgl.glframework.Mathf;
import co.megadodo.lwjgl.glframework.buffer.AttribType;
import co.megadodo.lwjgl.glframework.buffer.BufferTarget;
import co.megadodo.lwjgl.glframework.buffer.BufferUsage;
import co.megadodo.lwjgl.glframework.buffer.PolygonMode;
import co.megadodo.lwjgl.glframework.buffer.VertexArray;
import co.megadodo.lwjgl.glframework.buffer.VertexBuffer;
import co.megadodo.lwjgl.glframework.model.Mesh;
import co.megadodo.lwjgl.glframework.model.ModelLoader;
import co.megadodo.lwjgl.glframework.shader.ShaderProgram;
import co.megadodo.lwjgl.glframework.shader.ShaderType;
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

		ShaderProgram modelShader = new ShaderProgram();
		modelShader.gen();
		modelShader.attach(ShaderProgram.compileShaderFiles(ShaderType.Vertex, "vertex", "Shaders/model.vert"));
		modelShader.attach(ShaderProgram.compileShaderFiles(ShaderType.Fragment, "fragment", "Shaders/model.frag"));
		modelShader.link();

		ShaderProgram boundsShader = new ShaderProgram();
		boundsShader.gen();
		boundsShader.attach(ShaderProgram.compileShaderFiles(ShaderType.Vertex, "vertex", "Shaders/boundary.vert"));
		boundsShader.attach(ShaderProgram.compileShaderFiles(ShaderType.Fragment, "fragment", "Shaders/boundary.frag"));
		boundsShader.link();

		VertexArray vaoBounds = new VertexArray();
		vaoBounds.gen();
		vaoBounds.bind();

		float boundsSize=400;
		float boundsY=-20;
		float[] boundsPosData = new float[] {-boundsSize,boundsY,-boundsSize,   -boundsSize,boundsY,boundsSize,   boundsSize,boundsY,boundsSize,  boundsSize,boundsY,-boundsSize};
		float[] boundsColData = new float[] {0,0,0,       0,0,0.5f,   0.5f,0,0.5f,  0.5f,0,0};
		byte[] boundsTriData = new byte[] {0,1,2};

		VertexBuffer vboBoundsPos = new VertexBuffer();
		vboBoundsPos.usage = BufferUsage.StaticDraw;
		vboBoundsPos.target = BufferTarget.Array;
		vboBoundsPos.gen();
		vboBoundsPos.bind();
		vboBoundsPos.setData(boundsPosData);
		vboBoundsPos.addVertexAttrib(0, 3, AttribType.Float, false, 3, 0);
		vboBoundsPos.unbind();

		VertexBuffer vboBoundsCol = new VertexBuffer();
		vboBoundsCol.usage = BufferUsage.StaticDraw;
		vboBoundsCol.target = BufferTarget.Array;
		vboBoundsCol.gen();
		vboBoundsCol.bind();
		vboBoundsCol.setData(boundsColData);
		vboBoundsCol.addVertexAttrib(1, 3, AttribType.Float, false, 3, 0);
		vboBoundsCol.unbind();

		VertexBuffer eboBounds = new VertexBuffer();
		eboBounds.usage = BufferUsage.StaticDraw;
		eboBounds.target = BufferTarget.ElementArray;
		eboBounds.gen();
		eboBounds.bind();
		eboBounds.setDataUnsigned(boundsTriData);
		eboBounds.unbind();

		vaoBounds.unbind();

		Mesh[] modelsKoi = ModelLoader.loadModel("models/koi.obj");
		Mesh[] modelsDino = ModelLoader.loadModel("models/dinosaur.obj");

		MeshRenderer[] rendKoi = new MeshRenderer[modelsKoi.length];
		for (int i = 0; i < modelsKoi.length; i++) {
			rendKoi[i] = new MeshRenderer();
			rendKoi[i].mesh = modelsKoi[i];
			rendKoi[i].gen();
		}
		MeshRenderer[] rendDino = new MeshRenderer[modelsDino.length];
		for (int i = 0; i < modelsDino.length; i++) {
			rendDino[i] = new MeshRenderer();
			rendDino[i].mesh = modelsDino[i];
			rendDino[i].gen();
		}

		float camX = 0, camZ = 0;

		boolean normals = false, uvs = false;

		int polyMode = PolygonMode.Fill;

		float dinoX = 0, dinoZ = 0, koiX = 0, koiZ = -10, dinoVX = 0, dinoVZ = 0, koiVX = 0, koiVZ = 0;

		while (window.isOpen()) {
			window.bind();

			GLUtilities.clearScreen(1, 1, 1);
			GLUtilities.enableDepth();

			float camRotY = Mathf.map(window.getMouseX(), 0, window.getWidth(), -Mathf.PI / 2, Mathf.PI / 2);
			float camRotX = Mathf.map(window.getMouseY(), 0, window.getHeight(), 10, -10);

			float dirX = Mathf.cos(camRotY);
			float dirZ = Mathf.sin(camRotY);

			Vector3f camPos = new Vector3f(camX, 0, camZ);
			Vector3f camLookAt = new Vector3f(camX + dirX * 10, camRotX, camZ + dirZ * 10);
			Vector3f camUp = new Vector3f(0, 1, 0);

			Vector3f forward = new Vector3f(dirX, 0, dirZ);
			Vector3f right = forward.rotateY(Mathf.toRadians(-90));

			float dx = 0;
			float dz = 0;
			float speed = 1f;
			if (window.isKeyDown(Keys.LeftShift)) {
				speed = 0.25f;
			}
			if (window.isKeyDown(Keys.LeftAlt)) {
				speed = 4;
			}
			if (window.isKeyDown('W')) {
				dx += dirX * speed;
				dz += dirZ * speed;
			}
			if (window.isKeyDown('S')) {
				dx -= dirX * speed;
				dz -= dirZ * speed;
			}
			if (window.isKeyDown('A')) {
				dx -= right.x * speed;
				dz -= right.z * speed;
			}
			if (window.isKeyDown('D')) {
				dx += right.x * speed;
				dz += right.z * speed;
			}
			camX += dx;
			camZ += dz;

			if (window.wasJustPressed('N'))
				normals = !normals;
			if (window.wasJustPressed('U'))
				uvs = !uvs;
			if (window.wasJustPressed('R'))
				polyMode = (polyMode == PolygonMode.Fill ? PolygonMode.Lines : PolygonMode.Fill);

			Matrix4f projection = new Matrix4f().identity().perspective(Mathf.toRadians(45), 1, 0.01f, 1000);
			Matrix4f view = new Matrix4f().identity().lookAt(camPos, camLookAt, camUp);

			Matrix4f matKoi = new Matrix4f().identity().translate(koiX, 0, koiZ);
			Matrix4f matDino = new Matrix4f().identity().translate(dinoX, 0, dinoZ);

			float t = GLUtilities.getTime();
			dinoVX = SimplexNoise.noisef(t * 0.25f, 0) * 1f;
			dinoVZ = SimplexNoise.noisef(t * 0.25f, 100) * 1f;
			dinoX += dinoVX;
			dinoZ += dinoVZ;

			koiVX += (dinoX - koiX) * 0.0025f;
			koiVZ += (dinoZ - koiZ) * 0.0025f;
			koiX += koiVX;
			koiZ += koiVZ;

			koiX=Mathf.clamp(koiX, -boundsSize, boundsSize);
			koiZ=Mathf.clamp(koiZ, -boundsSize, boundsSize);
			dinoX=Mathf.clamp(dinoX, -boundsSize, boundsSize);
			dinoZ=Mathf.clamp(dinoZ, -boundsSize, boundsSize);

			modelShader.bind();
				modelShader.setMat4("projection", projection);
				modelShader.setMat4("view", view);
				modelShader.setBool("shadeNormals", normals);
				modelShader.setBool("shadeUVs", uvs);
				modelShader.setMat4("model", matKoi);
					for (MeshRenderer mr : rendKoi) {
						mr.bind();
						mr.polyMode = polyMode;
						mr.render();
						mr.unbind();
					}
				modelShader.setMat4("model", matDino);
					for (MeshRenderer mr : rendDino) {
						mr.bind();
						mr.polyMode = polyMode;
						mr.render();
						mr.unbind();
					}
			modelShader.unbind();

			boundsShader.bind();
				boundsShader.setMat4("projection", projection);
				boundsShader.setMat4("view", view);
				boundsShader.setMat4("model", new Matrix4f().identity());
					vaoBounds.bind();
						eboBounds.bind();
							eboBounds.render(PolygonMode.Fill);
						eboBounds.unbind();
					vaoBounds.unbind();
			boundsShader.unbind();

			window.clearInputs();
			window.unbind();
		}
		boundsShader.delete();
		vaoBounds.delete();
		eboBounds.delete();
		vboBoundsPos.delete();
		vboBoundsCol.delete();
		modelShader.delete();

		for (MeshRenderer mr : rendKoi) {
			mr.delete();
		}
		for (MeshRenderer mr : rendDino) {
			mr.delete();
		}

		window.delete();

		GLWindow.endGLFW();

	}

}