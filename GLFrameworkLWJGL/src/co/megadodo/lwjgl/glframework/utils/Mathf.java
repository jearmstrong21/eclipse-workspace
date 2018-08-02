package co.megadodo.lwjgl.glframework.utils;

public class Mathf {
	
	public static final float PI=(float)Math.PI;
	
	public static float cos(float f) {
		return (float)Math.cos(f);
	}
	
	public static float round(float f) {
		return Math.round(f);
	}
	
	public static float sin(float f) {
		return (float)Math.sin(f);
	}
	
	public static float fmod(float a,float b) {//a%b
		while(a<-b)a+=b;
		while(a>b)a-=b;
		return a;
	}
	
	public static float toRadians(float f) {
		return (float)Math.toRadians(f);
	}
	
	public static float random(float mi,float ma) {
		return mi+(ma-mi)*(float)Math.random();
	}
	
	public static float toDegrees(float f) {
		return (float)Math.toDegrees(f);
	}
	
	public static float norm(float t,float a,float b) {
		return (t-a)/(b-a);
	}
	
	public static float lerp(float t,float a,float b) {
		return a+t*(b-a);
	}
	
	public static boolean isInRange(float t,float a,float b) {
		return t==clamp(t,a,b);
	}
	
	public static float clamp(float t,float a,float b) {
		if(t<a)return a;
		if(t>b)return b;
		return t;
	}
	
	public static float map(float t,float s1,float e1,float s2,float e2) {
		return lerp(norm(t,s1,e1),s2,e2);
	}
	
	public static float atan2(float y,float x) {
		return (float)Math.atan2(y, x);
	}
}
