package test.shadertoy;

import org.json.JSONArray;
import org.json.JSONObject;

import co.megadodo.lwjgl.glframework.buffer.AttribType;
import co.megadodo.lwjgl.glframework.buffer.BufferTarget;
import co.megadodo.lwjgl.glframework.buffer.BufferUsage;
import co.megadodo.lwjgl.glframework.buffer.PolygonMode;
import co.megadodo.lwjgl.glframework.buffer.VertexArray;
import co.megadodo.lwjgl.glframework.buffer.VertexBuffer;
import co.megadodo.lwjgl.glframework.shader.ShaderProgram;
import co.megadodo.lwjgl.glframework.shader.ShaderType;
import co.megadodo.lwjgl.glframework.utils.GLUtilities;
import co.megadodo.lwjgl.glframework.utils.Utilities;
import co.megadodo.lwjgl.glframework.window.GLWindow;

public class ShaderToy {
	
	public static void main(String[]args) {
		
		GLWindow.initGLFW();
		
		JSONObject mainObject=Utilities.loadJSONObject("ShaderToy/shadertoy.json");
		JSONObject mainShader=mainObject.getJSONObject("main-shader");
		JSONObject windowObject=mainObject.getJSONObject("window");
		//add support for this
		/*
		 * "includes": [
				{"name":"ShaderToy/includes/math.glsl","includes":["MATH"]}
			]
		 */
		GLWindow window=new GLWindow();
		window.hints();
		window.gen();
		window.setSize(windowObject.getInt("width"),windowObject.getInt("height"));
		window.setTitle("ShaderToy");
		window.show();
		window.bind();
		
		String shaderFilename=mainShader.getString("filename");
		
		String shaderCode=Utilities.loadStrFromFile(shaderFilename);
		
		JSONArray includes=mainShader.getJSONArray("includes");
		for(int i=0;i<includes.length();i++) {
			JSONObject include=includes.getJSONObject(i);
			String file=include.getString("file");
			JSONArray names=include.getJSONArray("names");
			for(int j=0;j<names.length();j++) {
				shaderCode=shaderCode.replaceAll("#include "+names.getString(j), Utilities.loadStrFromFile(file));
			}
		}
		
		String vertexShaderCode=Utilities.loadStrFromFile("ShaderToy/shadertoy/main.vert");
		
		ShaderProgram shader=new ShaderProgram();
		shader.gen();
		shader.attach(ShaderProgram.compileShader(ShaderType.Fragment, "fragment", shaderCode));
		shader.attach(ShaderProgram.compileShader(ShaderType.Vertex, "vertex", vertexShaderCode));
		shader.link();

		GLUtilities.printGLInfo();
		
		VertexArray vao=new VertexArray();
		vao.gen();
		vao.bind();
		
		float[]posData=new float[] {-1,-1,0,   -1,1,0,  1,1,0,  1,-1,0};
		float[]uvData=new float[] {0,0,  0,window.getHeight(),  window.getWidth(),window.getHeight(),  window.getWidth(),0};
		int[]triData=new int[] {0,1,2,  0,2,3};
		
		VertexBuffer vboPos=new VertexBuffer();
		vboPos.usage=BufferUsage.StaticDraw;
		vboPos.target=BufferTarget.Array;
		vboPos.gen();
		vboPos.bind();
		vboPos.setData(posData);
		vboPos.addVertexAttrib(0, 3, AttribType.Float, false, 3, 0);
		vboPos.unbind();
		
		VertexBuffer vboUV=new VertexBuffer();
		vboUV.usage=BufferUsage.StaticDraw;
		//needs to be dynamic for changing window size
		//check the c++ water sim code
		vboUV.target=BufferTarget.Array;
		vboUV.gen();
		vboUV.bind();
		vboUV.setData(uvData);
		vboUV.addVertexAttrib(1, 2, AttribType.Float, false, 2, 0);
		vboUV.unbind();
		
		VertexBuffer ebo=new VertexBuffer();
		ebo.usage=BufferUsage.StaticDraw;
		ebo.target=BufferTarget.ElementArray;
		ebo.gen();
		ebo.bind();
		ebo.setDataUnsigned(triData);
		ebo.unbind();
		
		vao.unbind();
		
		while(window.isOpen()) {
			window.bind();
			
			GLUtilities.clearScreen(1, 1, 1);
			
			shader.bind();
			shader.setFloat("iTime", GLUtilities.getTime());
			shader.setVec2("iResolution",window.getWidth(),window.getHeight());
				vao.bind();
					ebo.bind();
					ebo.render(PolygonMode.Fill);
					ebo.unbind();
				vao.unbind();
			shader.unbind();
			
			window.unbind();
		}
		
		window.delete();
		
		GLWindow.endGLFW();
		
	}

}
