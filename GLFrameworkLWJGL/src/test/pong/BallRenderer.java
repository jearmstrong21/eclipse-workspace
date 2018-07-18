package test.pong;

import org.joml.Matrix4f;

import co.megadodo.lwjgl.glframework.buffer.AttribType;
import co.megadodo.lwjgl.glframework.buffer.BufferTarget;
import co.megadodo.lwjgl.glframework.buffer.BufferUsage;
import co.megadodo.lwjgl.glframework.buffer.PolygonMode;
import co.megadodo.lwjgl.glframework.buffer.VertexArray;
import co.megadodo.lwjgl.glframework.buffer.VertexBuffer;
import co.megadodo.lwjgl.glframework.shader.ShaderProgram;
import co.megadodo.lwjgl.glframework.shader.ShaderType;
import co.megadodo.lwjgl.glframework.utils.Utilities;

public class BallRenderer {

	ShaderProgram ballShader;
	VertexBuffer vboPos;
	VertexBuffer ebo;
	VertexArray vao;

	public void init() {

		ballShader = new ShaderProgram();
		ballShader.gen();
		ballShader.attach(ShaderProgram.compileShader(ShaderType.Fragment, "fragment",
				Utilities.loadStrFromFile("Shaders/pong/ball.frag")));
		ballShader.attach(ShaderProgram.compileShader(ShaderType.Vertex, "vertex",
				Utilities.loadStrFromFile("Shaders/pong/ball.vert")));
		ballShader.link();

		vao = new VertexArray();
		vao.gen();
		vao.bind();

		float[] posData = new float[] { -1, -1, 0, -1, 1, 0, 1, 1, 0, 1, -1, 0 };
		int[] triData = new int[] { 0, 1, 2, 0, 2, 3 };

		vboPos = new VertexBuffer();
		vboPos.usage = BufferUsage.StaticDraw;
		vboPos.target = BufferTarget.Array;
		vboPos.gen();
		vboPos.bind();
		vboPos.setData(posData);
		vboPos.addVertexAttrib(0, 3, AttribType.Float, false, 3, 0);
		vboPos.unbind();

		ebo = new VertexBuffer();
		ebo.usage = BufferUsage.StaticDraw;
		ebo.target = BufferTarget.ElementArray;
		ebo.gen();
		ebo.bind();
		ebo.setDataUnsigned(triData);
		ebo.unbind();

		vao.unbind();
	}

	public void render(float ballX,float ballY,float ballSize,float r,float g,float b) {
		ballShader.bind();
		Matrix4f ballMat = new Matrix4f().identity().translate(ballX, ballY, 0).scale(ballSize);
		ballShader.setMat4("matrix", ballMat);
		ballShader.setFloat("r", r);
		ballShader.setFloat("g", g);
		ballShader.setFloat("b", b);
		vao.bind();
		ebo.bind();
		ebo.render(PolygonMode.Fill);
		ebo.unbind();
		vao.unbind();
		ballShader.unbind();
	}

}
