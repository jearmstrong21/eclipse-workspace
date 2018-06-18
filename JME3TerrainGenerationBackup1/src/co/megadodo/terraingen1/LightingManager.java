package co.megadodo.terraingen1;

import java.util.ArrayList;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;

public class LightingManager {

	static class LightSource {
		Vector3f pos;
		ColorRGBA col;
		
		LightSource(Vector3f pos, ColorRGBA col) {
			this.pos=pos;
			this.col=col;
		}
	}
	static class LightPos {
		Vector3f pos;
		ColorRGBA col;
		
		LightPos(Vector3f pos, ColorRGBA col) {
			this.pos=pos;
			this.col=col;
		}
	}
	
	static ArrayList<LightSource> lights;
	static ArrayList<LightPos> posses;
	static {
		lights=new ArrayList<LightSource>();
		posses = new ArrayList<LightPos>();
//		updateLights();
	}
	public static void addLight(ColorRGBA col, Vector3f pos) {
		LightSource ls = new LightSource(pos,col);
		lights.add(ls);
	}
	public static void addCol(ColorRGBA col,Vector3f pos) {
		for(LightPos p : posses) {
			if(p.pos.equals(pos)) {
				p.col=p.col.add(col);
				return;
			}
		}
		posses.add(new LightPos(pos,col));
	}
	public static ArrayList<Vector2f> updateLights(AppStateWorld asw) {
		System.out.println("Start update lights");
		posses = new ArrayList<LightPos>();
		ArrayList<Vector2f> cUpdates = new ArrayList<Vector2f>();
		float colorLoss = 0.0025f;
		int numIters = 0;
		for(LightSource source : lights) {
			System.out.println("Light");
			ArrayList<Vector3f> open = new ArrayList<Vector3f>();
			ArrayList<Vector3f> closed = new ArrayList<Vector3f>();
			open.add(source.pos);
			ColorRGBA col = source.col.clone();
			addCol(col, open.get(0));
			while(open.size()>0) {
				Vector3f pos = open.get(0);
				open.remove(0);
				int cx=(int)(pos.x/16);
				int cz=(int)(pos.z/16);
				int rx=(int)(pos.x%16);
				int ry=(int)(pos.y);
				int rz=(int)(pos.z%16);
				while(rx<0) {
					rx+=16;
					cx--;
				}
				while(rz<0) {
					rz+=16;
					cz--;
				}
				while(rx>15) {
					rx-=16;
					cx++;
				}
				while(rz>15) {
					rz-=15;
					cz++;
				}
				if(!cUpdates.contains(new Vector2f(cx,cz)))cUpdates.add(new Vector2f(cx,cz));
				if(asw.findChunk(cx, cz).data.data[rx][ry][rz]!=null)continue;
				if(closed.contains(pos))continue;
				if(open.contains(pos))continue;
				if(col.r<=0||col.g<=0||col.b<=0)continue;
				numIters++;
				System.out.println("Light iter " + pos);
				closed.add(pos);
				addCol(col,pos);
				col=col.add(new ColorRGBA(-colorLoss,-colorLoss,-colorLoss,0));
				if(col.r>0&&col.g>0&&col.b>0) {
					open.add(new Vector3f(pos.x-1,pos.y,pos.z));
					open.add(new Vector3f(pos.x+1,pos.y,pos.z));
					open.add(new Vector3f(pos.x,pos.y-1,pos.z));
					open.add(new Vector3f(pos.x,pos.y+1,pos.z));
					open.add(new Vector3f(pos.x,pos.y,pos.z-1));
					open.add(new Vector3f(pos.x,pos.y,pos.z+1));
				}
			}
		}
		System.out.println("Light update:");
		System.out.println("Iterations: " +numIters);
		System.out.println("Num light posses: " + posses.size());
		return cUpdates;
	}
	
	public static ColorRGBA colorForPos(Vector3f pos) {
		ColorRGBA col =  new ColorRGBA(0.2f,0.2f,0.2f,1);
		for(LightPos p : posses) {
			if(p.pos.equals(pos)) {
				System.out.println("Non-default at " + p.pos + ": " + p.col);
				col=col.add(p.col);
			}
		}
		if(col.r>1)col.r=1;
		if(col.g>1)col.g=1;
		if(col.b>1)col.b=1;
		if(col.r<0)col.r=0;
		if(col.g<0)col.g=0;
		if(col.b<0)col.b=0;
		return col;
	}
	
}
