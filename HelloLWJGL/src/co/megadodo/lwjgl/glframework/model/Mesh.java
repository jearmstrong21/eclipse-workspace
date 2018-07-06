package co.megadodo.lwjgl.glframework.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import co.megadodo.lwjgl.glframework.utils.Utilities;

public class Mesh {

	public float[] pos;
	public float[] normal;
	public float[] uv;
	public int[] faces;
	
	public Mesh() {
	}
	
	public Mesh(float[]p,float[]u,float[]n,int[]b) {
		pos=p;
		uv=u;
		normal=n;
		faces=new int[b.length];
		for(int i=0;i<b.length;i++)faces[i]=b[i];
	}
	
	public static Mesh combine(Mesh a,Mesh b) {
		List<Float>pos=new ArrayList<Float>();for(float f:a.pos)pos.add(f);for(float f:b.pos)pos.add(f);
		List<Float>uv=new ArrayList<Float>();for(float f:a.uv)uv.add(f);for(float f:b.uv)uv.add(f);
		List<Float>normal=new ArrayList<Float>();for(float f:a.normal)normal.add(f);for(float f:b.normal)normal.add(f);
		List<Integer>faces=new ArrayList<Integer>();for(int byt:a.faces)faces.add(byt+0);for(int byt:b.faces)faces.add(byt+a.pos.length/3);
		return new Mesh(Utilities.listToArrF(pos),Utilities.listToArrF(uv),Utilities.listToArrF(normal),Utilities.listToArrI(faces));
	}
	
	public static Mesh combine(Mesh[]arr) {
		Mesh mesh=new Mesh(new float[] {},new float[] {},new float[] {},new int[] {});
		for(Mesh m:arr) {
			mesh=combine(mesh,m);
		}
		return mesh;
	}

}
