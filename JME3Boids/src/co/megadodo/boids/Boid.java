package co.megadodo.boids;

import java.util.ArrayList;

import com.jme3.math.Vector3f;

public class Boid {
	
	Vector3f curPos;
	Vector3f changePos;
	Vector3f oldChange;
	
	public static int WORLD_SIZE = 200;
	public static float RULE_CENTER_MASS = 0.0025f;
	public static float RULE_AVG_VEL = 0.025f;
	public static float RULE_SEP = 0.001f;
	public static float RULE_WIND = 0;
	public static float VIEW_DIST = 100;
	public static float SEP_DIST = 15;
	public static Vector3f VEC_WIND = new Vector3f(0,0,0);
	public static float MAX_VEL = 1;
	public static float RULE_BOUNDS = 0.0001f;
	public static float RANDOMNESS = 0.01f;
	public static float RULE_REPEL = 50f;
	public static Vector3f VEC_REPEL = new Vector3f(0,0,0);
	public static boolean doRepel = false;
	
	int uid;
	static int num = 0;
	
	float random(float x) {
		return (float)(2*x*Math.random()) - x;
	}
	
	public Boid() {
		num++;
		uid = num;
		float a = WORLD_SIZE;
		float b = 1;
		curPos = new Vector3f(random(a),random(a),random(a));
		changePos = new Vector3f(random(b),random(b),random(b));
		oldChange = new Vector3f(0,0,0);
	}
	
	boolean firstFrame = true;
	
	public Vector3f ruleCenter(ArrayList<Boid> boids) {
		Vector3f center = new Vector3f(0,0,0);
		int num=0;
		for(Boid b : boids) {
			if(b.uid != uid && b.curPos.distance(curPos) <= VIEW_DIST) {
				center = center.add(b.curPos);
				num++;
			}
		}
		if(num==0)return new Vector3f(0,0,0);
		center = center.divide(num);
		return center.subtract(curPos);
	}
	
	public Vector3f ruleSep(ArrayList<Boid> boids) {
		Vector3f c = new Vector3f(0,0,0);
		for(Boid b : boids) {
			if(b.uid!=uid&&b.curPos.distance(curPos)<=SEP_DIST) {
				if(b.curPos.distance(curPos)<10f)return (new Vector3f(random(5),random(5),random(5)));
				else c = c.subtract(b.curPos.subtract(curPos));
			}
		}
		return c;
	}
	
	public Vector3f ruleAvgVel(ArrayList<Boid> boids) {
		Vector3f c = new Vector3f(0,0,0);
		int num = 0;
		for(Boid b : boids) {
			if(b.uid!=uid&&b.curPos.distance(curPos)<=VIEW_DIST) {
				c = c.add(b.oldChange);
				num++;
			}
		}
		if(num==0)return new Vector3f(0,0,0);
		return c.divide(num);
	}
	
	public Vector3f ruleBounds() {
		if(curPos.length() < WORLD_SIZE) return new Vector3f(0,0,0);
		return curPos.mult(-1);
	}
	
	public Vector3f ruleRepel() {
		float dist = curPos.distance(VEC_REPEL);
		return curPos.subtract(VEC_REPEL).divide(dist*dist);
//		if(curPos.distance(VEC_REPEL)<VIEW_DIST) {
//			return curPos.subtract(VEC_REPEL);
//		}
//		return new Vector3f(0,0,0);
	}
	
	public void update(ArrayList<Boid> boids) {
		oldChange = changePos.clone();
//		changePos = new Vector3f(0,0,0);
//		if(firstFrame) {
//			firstFrame=false;
//			changePos=new Vector3f(random(START_RANDOMNESS),random(START_RANDOMNESS),random(START_RANDOMNESS));
//		}
		changePos = changePos.add(ruleCenter(boids).mult(RULE_CENTER_MASS));
		changePos = changePos.add(ruleSep(boids).mult(RULE_SEP));
		changePos = changePos.add(ruleAvgVel(boids).mult(RULE_AVG_VEL));
		changePos = changePos.add(ruleBounds().mult(RULE_BOUNDS));
		if(doRepel) {
			changePos = changePos.add(ruleRepel().mult(RULE_REPEL));
		}
		changePos = changePos.add(new Vector3f(random(RANDOMNESS),random(RANDOMNESS),random(RANDOMNESS)).mult(0.1f));
	}
	
	public void updatePos(float tpf) {
		if(changePos.length()>MAX_VEL)changePos = changePos.normalize().mult(MAX_VEL);
		curPos = curPos.add(changePos);
	}

}
