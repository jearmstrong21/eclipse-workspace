package co.megadodo.lwjgl.glframework.utils;

import org.joml.Vector3f;

public class HSB {

	public static float thetaToR(float theta) {//in range 0 to 1
		theta=Mathf.round(Mathf.fmod(theta,1)*360);
		if(Mathf.isInRange(theta, 0, 60))return 1;
		if(Mathf.isInRange(theta, 60, 120))return Mathf.map(theta, 60, 120, 1, 0);
		if(Mathf.isInRange(theta, 120, 180))return 0;
		if(Mathf.isInRange(theta, 180, 240))return 0;
		if(Mathf.isInRange(theta, 240, 300))return Mathf.map(theta, 240, 300, 0, 1);
		if(Mathf.isInRange(theta, 300, 360))return 0;
		return 0;
	}
	
	public static float thetaToG(float theta) {//in range 0 to 1
		theta=Mathf.round(Mathf.fmod(theta,1)*360);
		if(Mathf.isInRange(theta, 0, 60))return Mathf.map(theta, 0, 60, 0, 1);
		if(Mathf.isInRange(theta, 60, 120))return 1;
		if(Mathf.isInRange(theta, 120, 180))return 1;
		if(Mathf.isInRange(theta, 180, 240))return Mathf.map(theta, 180, 240, 1, 0);
		if(Mathf.isInRange(theta, 240, 300))return 0;
		if(Mathf.isInRange(theta, 300, 360))return 0;
		return 0;
	}
	
	public static float thetaToB(float theta) {//in range 0 to 1
		theta=Mathf.round(Mathf.fmod(theta,1)*360);
		if(Mathf.isInRange(theta, 0, 60))return 0;
		if(Mathf.isInRange(theta, 60, 120))return 0;
		if(Mathf.isInRange(theta, 120, 180))return Mathf.map(theta, 120, 180, 0, 1);
		if(Mathf.isInRange(theta, 180, 240))return 1;
		if(Mathf.isInRange(theta, 240, 300))return 1;
		if(Mathf.isInRange(theta, 300, 360))return Mathf.map(theta, 300, 360, 1, 0);
		return 0;
	}
	
	public static Vector3f thetaToRGB(float theta) {
		return new Vector3f(thetaToR(theta), thetaToG(theta), thetaToB(theta));
	}
	
	public static Vector3f hsbToRGB(float hue, float saturation,float brightness) {
		float r=thetaToR(hue);
		float g=thetaToG(hue);
		float b=thetaToB(hue);
		r=Mathf.lerp(saturation, 1, r);
		g=Mathf.lerp(saturation, 1, g);
		b=Mathf.lerp(saturation, 1, b);
		r*=brightness;
		g*=brightness;
		b*=brightness;
		return new Vector3f(r, g, b);
	}
	
}
