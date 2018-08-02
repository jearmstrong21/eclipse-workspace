package co.megadodo.lwjgl.glframework.text;

import org.joml.Matrix4f;

import co.megadodo.lwjgl.glframework.buffer.AttribType;
import co.megadodo.lwjgl.glframework.buffer.BufferTarget;
import co.megadodo.lwjgl.glframework.buffer.BufferUsage;
import co.megadodo.lwjgl.glframework.buffer.PolygonMode;
import co.megadodo.lwjgl.glframework.buffer.ProvokingVertex;
import co.megadodo.lwjgl.glframework.buffer.VertexArray;
import co.megadodo.lwjgl.glframework.buffer.VertexBuffer;
import co.megadodo.lwjgl.glframework.shader.ShaderProgram;
import co.megadodo.lwjgl.glframework.shader.ShaderType;
import co.megadodo.lwjgl.glframework.texture.Texture;

public class TextRenderer {//2d

	public ShaderProgram shader=new ShaderProgram();
	public VertexArray vao=new VertexArray();
	public VertexBuffer vboPos=new VertexBuffer(),vboUV=new VertexBuffer(),ebo=new VertexBuffer();
	
//	public static int MAX_CHARS=1000;
	
	public TextRenderer() {
		
	}
	
	public Texture tex;
	
	public FontAtlas atlas;
	
	public void gen() {
		tex=Texture.loadTexture(atlas.getTextureFile(),false);
		shader.gen();
		shader.attach("Shaders/text/text.vert", ShaderType.Vertex);
		shader.attach("Shaders/text/text.frag", ShaderType.Fragment);
		shader.link();
		
		vao.gen();
		vao.bind();
		
		vboPos.usage=BufferUsage.StaticDraw;
		vboPos.target=BufferTarget.Array;
		vboPos.gen();
		vboPos.bind();
		vboPos.setData(new float[] {});
		vboPos.addVertexAttrib(0, 2, AttribType.Float, false, 2, 0);
		vboPos.unbind();

		vboUV.usage=BufferUsage.StaticDraw;
		vboUV.target=BufferTarget.Array;
		vboUV.gen();
		vboUV.bind();
		vboUV.setData(new float[] {});
		vboUV.addVertexAttrib(1, 2, AttribType.Float, false, 2, 0);
		vboUV.unbind();
		
		ebo.usage=BufferUsage.StaticDraw;
		ebo.target=BufferTarget.ElementArray;
		ebo.gen();
		ebo.bind();
		ebo.setDataUnsigned(new int[] {});
		ebo.unbind();
	}
	
	public void setText(String txt) {
		vao.delete();
		vboPos.delete();
		vboUV.delete();
		ebo.delete();
		int numChars=txt.length();
		float[]posData=new float[8*numChars];
		float[]uvData=new float[8*numChars];
		int[]triData=new int[6*numChars];
		int i_p=0;
		int i_u=0;
		int i_t=0;
		int x=0;
		int y=-1;//not y=0 so that the top-left of first character is top-left corner
		int numCharsSoFar=0;
		float aspect=atlas.getAspectRatio();
		float w=aspect;
		float h=1;
		for(int i=0;i<numChars;i++) {
			AtlasPos pos=atlas.getPos(txt.charAt(i));
			if(txt.charAt(i)=='\n') {
				x=0;
				y--;
				continue;
			}
			posData[i_p]=x*w;i_p++;
			posData[i_p]=y*h;i_p++;
			
			posData[i_p]=(x+1)*w;i_p++;
			posData[i_p]=y*h;i_p++;
			
			posData[i_p]=(x+1)*w;i_p++;
			posData[i_p]=(y+1)*h;i_p++;
			
			posData[i_p]=x*w;i_p++;
			posData[i_p]=(y+1)*h;i_p++;
			

			uvData[i_u]=pos.x;i_u++;
			uvData[i_u]=pos.y+pos.h;i_u++;

			uvData[i_u]=pos.x+pos.w;i_u++;
			uvData[i_u]=pos.y+pos.h;i_u++;

			uvData[i_u]=pos.x+pos.w;i_u++;
			uvData[i_u]=pos.y;i_u++;

			uvData[i_u]=pos.x;i_u++;
			uvData[i_u]=pos.y;i_u++;
			

			triData[i_t]=numCharsSoFar*4+0;i_t++;
			triData[i_t]=numCharsSoFar*4+1;i_t++;
			triData[i_t]=numCharsSoFar*4+2;i_t++;
			triData[i_t]=numCharsSoFar*4+0;i_t++;//fix the triangulation, probably use ArrayList
			triData[i_t]=numCharsSoFar*4+3;i_t++;
			triData[i_t]=numCharsSoFar*4+2;i_t++;
			x++;
			numCharsSoFar++;
		}
		//x,y,  w,h per character
		//0,0,  1,1
		//1,0,  1,1
		//2,0,  1,1
		
		vao.gen();
		vao.bind();
		
		vboPos.gen();
		vboPos.bind();
		vboPos.setData(posData);
		vboPos.addVertexAttrib(0, 2, AttribType.Float, false, 2, 0);
		vboPos.unbind();
		
		vboUV.gen();
		vboUV.bind();
		vboUV.setData(uvData);
		vboUV.addVertexAttrib(1, 2, AttribType.Float, false, 2, 0);
		vboUV.unbind();
		
		ebo.gen();
		ebo.bind();
		ebo.setDataUnsigned(triData);
		ebo.unbind();
		
		vao.unbind();
	}
	
	public void render(float x,float y,float size) {
		shader.bind();
		tex.bindToUnit(0);
		shader.setTexture("tex", 0);
		shader.setMat4("matrix", new Matrix4f().identity().translate(x, y, 0).scale(size));
		vao.bind();
		ebo.bind();
		ebo.render();		
		ebo.unbind();
		vao.unbind();
		shader.unbind();
	}
	
	public void delete() {
		
	}
	
}
