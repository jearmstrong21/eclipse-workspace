package co.megadodo.terraingen1;

import com.jme3.math.Vector2f;

public class AtlasPosition {

	public Vector2f _00;
	public Vector2f _01;
	public Vector2f _10;
	public Vector2f _11;
	public String name;
	
	public AtlasPosition(String n, float x, float y, float w, float h) {
		name = n;
		float xfrac = 1f/w;
		float yfrac = 1f/h;
		
		_00 = new Vector2f(x*xfrac,1-y*yfrac);
		_01 = new Vector2f(x*xfrac,1-y*yfrac-yfrac);
		_10 = new Vector2f(x*xfrac+xfrac,1-y*yfrac);
		_11 = new Vector2f(x*xfrac+xfrac,1-y*yfrac-yfrac);
		
		_00 = new Vector2f(0,1);
		_01 = new Vector2f(0,1-yfrac);
		_10 = new Vector2f(xfrac,1);
		_11 = new Vector2f(xfrac,1-yfrac);
		
		Vector2f offset = new Vector2f(x*xfrac,y*yfrac);
		_00=_00.add(offset);
		_10=_10.add(offset);
		_01=_01.add(offset);
		_11=_11.add(offset);
		
		System.out.println(_00);
		System.out.println(_01);
		System.out.println(_10);
		System.out.println(_11);
	}
	
}
