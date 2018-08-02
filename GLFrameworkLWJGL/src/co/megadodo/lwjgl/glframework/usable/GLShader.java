package co.megadodo.lwjgl.glframework.usable;

import co.megadodo.lwjgl.glframework.shader.ShaderProgram;
import co.megadodo.lwjgl.glframework.shader.ShaderType;
import co.megadodo.lwjgl.glframework.texture.Texture;

public class GLShader {
	
	public ShaderProgram sp=null;
	public boolean linked=false;
	
	
	public GLShader() {
		sp=new ShaderProgram();
		sp.gen();
	}
	
	public GLShader(String vert,String frag) {
		this();
		sp.attach(vert, ShaderType.Vertex);
		sp.attach(frag, ShaderType.Fragment);
	}
	
	private int texID=0;
	
	public void clearTextures() {
		texID=0;
	}
	
	public void setTexture(String name, Texture tex) {
		tex.bindToUnit(texID);
		set1i(name, texID);
		texID++;
	}
	
	public void link() {
		sp.link();
		linked=true;
	}
	
	public void attach(String fn,int t) {
		sp.attach(fn, t);
	}
	
	public void set1i(String name,int x) {
		sp.setInt(name, x);
	}
	
	public void set3f(String name,float x,float y,float z) {
		sp.setVec3(name, x, y, z);
	}
	
	public void set2f(String name,float x,float y) {
		sp.setVec2(name, x, y);
	}
	
	public void set1f(String name, float x) {
		sp.setFloat(name, x);
	}
	
	public void bind() {
		if(!linked) {
			linked=true;
			sp.link();
		}
		sp.bind();
	}

}
