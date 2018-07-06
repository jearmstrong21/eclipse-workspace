package co.megadodo.lwjgl.glframework.utils;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class Utilities {

	public static String loadStrFromFile(String fn) {
		try {
			File f = new File(fn);
			Scanner sc = new Scanner(f);
			String str = "";
			while (sc.hasNextLine())
				str += sc.nextLine() + "\n";
			sc.close();
			return str;
		} catch (Throwable t) {
			t.printStackTrace();
			return "";
		}
	}
	
	public static int[] arrParseInt(String[]str) {
		int[]i=new int[str.length];
		for(int j=0;j<str.length;j++)i[j]=Integer.parseInt(str[j]);
		return i;
	}
	
	public static float[] listToArrF(List<Float>list) {
		float[]arr=new float[list.size()];
		for(int i=0;i<list.size();i++)arr[i]=list.get(i);
		return arr;
	}
	
	public static int[] listToArrI(List<Integer>list) {
		int[]arr=new int[list.size()];
		for(int i=0;i<list.size();i++)arr[i]=list.get(i);
		return arr;
	}
	
	public static byte[] listToArrB(List<Byte>list) {
		byte[]arr=new byte[list.size()];
		for(int i=0;i<list.size();i++) {
			arr[i]=list.get(i);
		}
		return arr;
	}
	
	public static float[] listToFloatArrV2(List<Vector2f>list) {
		float[]arr=new float[list.size()*2];
		for(int i=0;i<list.size();i++) {
			arr[i*2+0]=list.get(i).x;
			arr[i*2+1]=list.get(i).y;
		}
		return arr;
	}
	
	public static float[] listToFloatArrV3(List<Vector3f>list) {
		float[]arr=new float[list.size()*3];
		for(int i=0;i<list.size();i++) {
			arr[i*3+0]=list.get(i).x;
			arr[i*3+1]=list.get(i).y;
			arr[i*3+2]=list.get(i).z;
		}
		return arr;
	}
	
}
