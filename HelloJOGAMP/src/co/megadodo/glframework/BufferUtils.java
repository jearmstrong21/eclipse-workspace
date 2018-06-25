package co.megadodo.glframework;

import glm.vec._3.Vec3;

import java.util.ArrayList;

public class BufferUtils {
	
	public static float[] v3ToFloatArr(Vec3...vec3s) {
		float[]f=new float[vec3s.length*3];
		for(int i=0;i<vec3s.length;i++) {
			f[i*3+0]=vec3s[i].x;
			f[i*3+1]=vec3s[i].y;
			f[i*3+2]=vec3s[i].z;
		}
		return f;
	}
	
	public static Vec3[] listToVec3Arr(ArrayList<Vec3>list) {
		Vec3[]arr=new Vec3[list.size()];
		for(int i=0;i<list.size();i++)arr[i]=list.get(i);
		return arr;
	}
	
	public static int[] listToIntArr(ArrayList<Integer>list) {
		int[]arr=new int[list.size()];
		for(int i=0;i<list.size();i++)arr[i]=list.get(i);
		return arr;
	}
	
	public static float[] listToFloatArr(ArrayList<Float>list) {
		float[]arr=new float[list.size()];
		for(int i=0;i<list.size();i++)arr[i]=list.get(i);
		return arr;
	}

}
