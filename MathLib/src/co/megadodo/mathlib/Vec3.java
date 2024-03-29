package co.megadodo.mathlib;

import java.security.InvalidParameterException;

public class Vec3 {

	public float x;
	public float y;
	public float z;

	public static final Vec3 zero = new Vec3(0, 0, 0);
	public static final Vec3 xpl = new Vec3(1, 0, 0);
	public static final Vec3 xmi = new Vec3(-1, 0, 0);
	public static final Vec3 ypl = new Vec3(0, 1, 0);
	public static final Vec3 ymi = new Vec3(0, -1, 0);
	public static final Vec3 zpl = new Vec3(0, 0, 1);
	public static final Vec3 zmi = new Vec3(0, 0, -1);

	public Vec3() {

	}

	public Vec3(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public float get(int i) {
		if (i == 0)
			return x;
		if (i == 1)
			return y;
		if (i == 2)
			return z;
		throw new InvalidParameterException("Vec3.get(" + i + ") is not 0,1 or 2");
	}

	public static Vec3 cross(Vec3 a, Vec3 b) {
		return new Vec3(Mat2.determinant(a.y, a.z, b.y, b.z), -Mat2.determinant(a.x, a.z, b.x, b.z),
				Mat2.determinant(a.x, a.y, b.x, b.y));
	}
	
	public static Vec3 mult(Vec3 a, float b) {
		return new Vec3(a.x*b,a.y*b,a.z*b);
	}

	public static float dot(Vec3 a, Vec3 b) {
		return a.x * b.x + a.y * b.y + a.z * b.z;
	}
	
	public static String toString(Vec3 v) {
		return "["+v.x+", "+v.y+", "+v.z+"]";
	}

	public static void main(String[] args) {
		System.out.println("Vec3 testing.");
		Vec3 a = new Vec3(340, -30, 20);
		System.out.println(Vec3.toString(cross(a, Vec3.mult(a,-1))));
	}

}
