package co.megadodo.voronoidelaunay;

public class Vector2f {
	
	public float x, y;
	
	public Vector2f() {
		this(0, 0);
	}
	
	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public static Vector2f add(Vector2f a, Vector2f b) {
		return new Vector2f(a.x + b.x, a.y + b.y);
	}
	
	public static Vector2f mult(Vector2f a, float f) {
		return new Vector2f(a.x * f, a.y * f);
	}
	
	public static Vector2f normalize(Vector2f a) {
		return Vector2f.mult(a, 1.0f / Vector2f.magnitude(a));
	}
	
	public static float magnitude(Vector2f a) {
		return (float) Math.sqrt(a.x * a.x + a.y * a.y);
	}
	
	public static Vector2f sub(Vector2f a, Vector2f b) {
		return new Vector2f(a.x - b.x, a.y - b.y);
	}
	
	public static float dist(Vector2f a, Vector2f b) {
		return Vector2f.magnitude(Vector2f.sub(a, b));
	}
	
	public static boolean isSame(Vector2f a, Vector2f b) {
		return a.x == b.x && a.y == b.y;
	}
	
}
