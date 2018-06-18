package co.megadodo.terraingen1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.jme3.math.Vector3f;
import com.jme3.math.Vector2f;

public class Utils {
	
	public static FastNoise fastNoise;
	
	static {
		fastNoise = new FastNoise();
	}
	
	public static float noisePerlin2(float x, float y) {
		return fastNoise.GetPerlin(x, y)/2f + 0.5f;
	}
	
	public static JSONObject loadObject(String filename) {
		try {
			return new JSONObject(new JSONTokener(new FileInputStream(new File("JSON/"+filename))));
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static int[] listToArrayInt(ArrayList<Integer> ints) {
		int[] nums = new int[ints.size()];
		for(int x=0;x<ints.size();x++) {
			nums[x]=ints.get(x);
		}
		return nums;
	}
	
	public static float[] vec3ToFloats(ArrayList<Vector3f> vecs) {
		float[] vs = new float[vecs.size()*3];
		for(int i=0;i<vecs.size();i++) {
			vs[i*3+0]=vecs.get(i).x;
			vs[i*3+1]=vecs.get(i).y;
			vs[i*3+2]=vecs.get(i).z;
		}
		return vs;
	}
	
	public static float[] vec2ToFloats(ArrayList<Vector2f> vecs) {
		float[] vs = new float[vecs.size()*2];
		for(int i=0;i<vecs.size();i++) {
			vs[i*2+0]=vecs.get(i).x;
			vs[i*2+1]=vecs.get(i).y;
		}
		return vs;
	}
	
	public static ArrayList<Vector3f> addVecToList(ArrayList<Vector3f> vecs, Vector3f v) {
		ArrayList<Vector3f> vs = new ArrayList<Vector3f>();
		for(Vector3f item : vecs) {
			vs.add(item.add(v));
		}
		return vs;
	}
	
	public static ArrayList<Integer> addNumToList(ArrayList<Integer> ints, int i) {
		ArrayList<Integer> vals = new ArrayList<Integer>();
		for(int x : ints) {
			vals.add(x+i);
		}
		return vals;
	}
	
	public static boolean xor(boolean a, boolean b) {
		return a != b;
	}

}
