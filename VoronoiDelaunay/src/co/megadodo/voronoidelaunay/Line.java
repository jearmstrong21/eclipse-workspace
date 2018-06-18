package co.megadodo.voronoidelaunay;

import java.awt.Graphics2D;

public class Line {

	public Vector2f a;
	public Vector2f b;
	
	public Line() {
		
	}
	
	public Line(Vector2f a, Vector2f b) {
		this.a = a;
		this.b = b;
	}
	
	public static Line bisector(Line line, float f) {
		Vector2f center = Vector2f.mult(Vector2f.add(line.a, line.b), 0.5f);
		Vector2f diff = Vector2f.add(line.a, Vector2f.mult(center, -1.0f));
		Vector2f normal = Vector2f.mult(Vector2f.normalize(new Vector2f(-diff.y, diff.x)), 10.0f);
		Line l = new Line();
		l.a = Vector2f.add(center, Vector2f.mult(normal, f));
		l.b = Vector2f.add(center, Vector2f.mult(normal,-f));
		return l;
	}
	
	public static void draw(Graphics2D g, Vector2f a, Vector2f b) {
		g.drawLine((int)a.x, (int)a.y, (int)b.x, (int)b.y);
	}
	
	public static void draw(Graphics2D g, Line l) {
		draw(g, l.a, l.b);
	}
	
	public static boolean isSame(Line l1, Line l2) {
		boolean b1 = Vector2f.isSame(l1.a, l2.a) && Vector2f.isSame(l1.b, l2.b);
		boolean b2 = Vector2f.isSame(l1.a, l2.b) && Vector2f.isSame(l1.b, l2.a);
		return b1 || b2;
	}
	
	public static Vector2f intersection(Line l1, Line l2) {
		float x1 = l1.a.x;
		float y1 = l1.a.y;
		float x2 = l1.b.x;
		float y2 = l1.b.y;
		float x3 = l2.a.x;
		float y3 = l2.a.y;
		float x4 = l2.b.x;
		float y4 = l2.b.y;
		float x = ((x1 * y2 - y1 * x2) * (x3 - x4)
				- (x1 - x2) * (x3 * y4 - y3 * x4))
				/ ((x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4));
		float y = ((x1 * y2 - y1 * x2) * (y3 - y4)
				- (y1 - y2) * (x3 * y4 - y3 * x4))
				/ ((x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4));
		return new Vector2f(x, y);
	}
	
}
