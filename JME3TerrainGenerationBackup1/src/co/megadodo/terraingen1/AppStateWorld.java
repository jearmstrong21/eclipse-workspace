package co.megadodo.terraingen1;

import java.util.ArrayList;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.bounding.BoundingSphere;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

public class AppStateWorld extends AbstractAppState {
	
	static int roundPos(float a) {
		return Math.round(a);
	}
	
	public RayTraceResult rayTrace(Vector3f pos, Vector3f dir) {
		Vector3f cur = pos.clone();
		RayTraceResult rtr = new RayTraceResult();
		Vector3f inc = dir.clone().mult(0.5f);
		boolean keepGoing = true;
		int lx=0;
		int ly=0;
		int lz=0;
		int lcx=0;
		int lcz=0;
		int x=0;
		int y=0;
		int z=0;
		int cx=0;
		int cz=0;
		int num=0;
		while(keepGoing) {
			num++;
			if(num>50)keepGoing=false;
			cur=cur.add(inc);
			lx=x;
			ly=y;
			lz=z;
			lcx=cx;
			lcz=cz;
			cx=roundPos(cur.x)/16;
			cz=roundPos(cur.z)/16;
			x=roundPos(cur.x)%16;
			y=roundPos(cur.y);
			z=roundPos(cur.z)%16;
			while(x<0) {
				x+=16;
				cx--;
			}
			while(z<0) {
				z+=16;
				cz--;
			}
			while(x>15) {
				x-=16;
				cx++;
			}
			while(z>15) {
				z-=16;
				cz++;
			}
			if(y>255||y<0)keepGoing=false;
			else {
				ChunkPair p = findChunk(cx,cz);
				if(p==null) {
					keepGoing=false;
				}else {
					if(p.data.data[x][y][z]!=null) {
						keepGoing=false;
					}
				}
			}
		}
		rtr.hitX=x;
		rtr.hitY=y;
		rtr.hitZ=z;
		rtr.hitCX=cx;
		rtr.hitCZ=cz;
		rtr.lX=lx;
		rtr.lY=ly;
		rtr.lZ=lz;
		rtr.lCX=lcx;
		rtr.lCZ=lcz;
		return rtr;
	}
	
	public TerrainGeneration tg;
	
	public static int numChunks = 0;
	
	class ChunkPair{
		int x;
		int z;
		Chunk data;
		boolean inst = false;
		
		ChunkPair(int x,int z,Chunk c,boolean i) {
			this.x=x;
			this.z=z;
			this.data=c;
			this.inst=i;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ChunkPair other = (ChunkPair) obj;
			if (x != other.x)
				return false;
			if (z != other.z)
				return false;
			return true;
		}
	}
	ArrayList<Node> chunkInstNodes;
	ArrayList<Vector2f> chunkInstPosses;
	ArrayList<ChunkPair> chunks;
	
	ChunkPair findChunk(int x, int z) {
		for(ChunkPair p : chunks) {
			if(p.x==x&&p.z==z)return p;
		}
		return new ChunkPair(0,0,null,false);
	}
	
	@Override
	public void initialize(AppStateManager appstatemanager, Application app) {
		self=this;
		chunkInstNodes = new ArrayList<Node>();
		chunkInstPosses = new ArrayList<Vector2f>();
		chunks = new ArrayList<ChunkPair>();
		
		tg.getInputManager().addMapping("Break", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
		tg.getInputManager().addMapping("Place", new KeyTrigger(KeyInput.KEY_R));
		tg.getInputManager().addMapping("Explosion", new KeyTrigger(KeyInput.KEY_P));
		tg.getInputManager().addMapping("Light", new KeyTrigger(KeyInput.KEY_L));
		tg.getInputManager().addMapping("Bl0", new KeyTrigger(KeyInput.KEY_1));
		tg.getInputManager().addMapping("Bl1", new KeyTrigger(KeyInput.KEY_2));
		tg.getInputManager().addMapping("Bl2", new KeyTrigger(KeyInput.KEY_3));
		tg.getInputManager().addMapping("Bl3", new KeyTrigger(KeyInput.KEY_4));
		tg.getInputManager().addMapping("Bl4", new KeyTrigger(KeyInput.KEY_5));
		tg.getInputManager().addMapping("Bl5", new KeyTrigger(KeyInput.KEY_6));
		tg.getInputManager().addMapping("Bl6", new KeyTrigger(KeyInput.KEY_7));
		tg.getInputManager().addMapping("Bl7", new KeyTrigger(KeyInput.KEY_8));
		tg.getInputManager().addMapping("Bl8", new KeyTrigger(KeyInput.KEY_9));
		tg.getInputManager().addMapping("Bl9", new KeyTrigger(KeyInput.KEY_0));
		tg.getInputManager().addListener(inputListener, "Break","Place","Explosion","Light","Bl0","Bl1","Bl2","Bl3","Bl4","Bl5","Bl6","Bl7","Bl8","Bl9");
	}
	
	int instRange = 7;
	int remDist = 10;
	int chunksAddPF = 2;
	int chunksRemPF = 2;
	AppStateWorld self;
	
	public void addLight(ColorRGBA col, Vector3f pos) {
		LightingManager.addLight(col,new Vector3f((int)pos.x,(int)pos.y,(int)pos.z));
		ArrayList<Vector2f> lupdates=LightingManager.updateLights(self);
		for(Vector2f v : lupdates) {
			redoChunk((int)v.x,(int)v.y);
		}
	}
	int placeBlockInd=2;
	ActionListener inputListener = new ActionListener() {
		
		@Override
		public void onAction(String name, boolean isPressed, float tpf) {
			if(!isPressed)return;
			if(name.equals("Light")) {
				addLight(new ColorRGBA(0.75f,0.75f,1,1),tg.getCamera().getLocation());
			}
			if(name.startsWith("Bl")) {
				placeBlockInd=Integer.parseInt(name.replace("Bl",""));
			}
			if(name.equals("Break")) {
				RayTraceResult rtr = rayTrace(tg.getCamera().getLocation(), tg.getCamera().getDirection());
				ChunkPair cp = findChunk(rtr.hitCX,rtr.hitCZ);
				if(cp!=null) {
					cp.data.data[rtr.hitX][rtr.hitY][rtr.hitZ]=null;
					ArrayList<Vector2f> lupdates=LightingManager.updateLights(self);
					if(lupdates.contains(new Vector2f(rtr.hitCX,rtr.hitCZ)))lupdates.remove(new Vector2f(rtr.hitCX,rtr.hitCZ));
					redoChunk(rtr.hitCX,rtr.hitCZ);
					for(Vector2f v : lupdates) {
						redoChunk((int)v.x,(int)v.y);
					}
					System.out.println("Chunk pos: " + rtr.hitCX+", "+rtr.hitCZ);
					System.out.println("Position: " + rtr.hitX+", "+rtr.hitY+", "+rtr.hitZ);
				}
			}
			if(name.equals("Explosion")) {
				ArrayList<Vector2f> chunksToUpdate = new ArrayList<Vector2f>();
				Vector3f camPos = tg.getCamera().getLocation();
				int destroyDist = 10;
				int range = (int)(1.732*destroyDist);
				for(int x=-range;x<=range;x++) {
					for(int y=-range;y<=range;y++) {
						for(int z=-range;z<=range;z++) {
							if(x*x+y*y+z*z<destroyDist*destroyDist) {
								int rx = (int)camPos.x+x;
								int ry = (int)camPos.y+y;
								int rz = (int)camPos.z+z;
								int hcx=roundPos(rx)/16;
								int hcz=roundPos(rz)/16;
								int hx=roundPos(rx)%16;
								int hy=roundPos(ry);
								int hz=roundPos(rz)%16;
								while(hx<0) {
									hx+=16;
									hcx--;
								}
								while(hz<0) {
									hz+=16;
									hcz--;
								}
								while(hx>15) {
									hx-=16;
									hcx++;
								}
								while(hz>15) {
									hz-=16;
									hcz++;
								}
								if(hy<0)continue;
								if(hy>255)continue;
								findChunk(hcx,hcz).data.data[hx][hy][hz]=null;
								Vector2f cpos = new Vector2f(hcx,hcz);
								if(!chunksToUpdate.contains(cpos)) {
									chunksToUpdate.add(cpos);
								}
							}
						}
					}
				}
				LightingManager.updateLights(self);
				for(Vector2f v : chunksToUpdate) {
					redoChunk((int)v.x, (int)v.y);
				}
			}
			if(name.equals("Place")) {
				RayTraceResult rtr = rayTrace(tg.getCamera().getLocation(),tg.getCamera().getDirection());
				ChunkPair cp = findChunk(rtr.lCX,rtr.lCZ);
				if(cp!=null) {
					cp.data.data[rtr.lX][rtr.lY][rtr.lZ]=tg.blocks.get(placeBlockInd);
					LightingManager.updateLights(self);
//					addLight(new Vector3f(rtr.lCX*16+rtr.lX,rtr.lY,rtr.lCZ*16+rtr.lZ));
					redoChunk(rtr.lCX,rtr.lCZ);
				}
			}
		}
	};
	
	public void redoChunk(int cx, int cz) {
		ChunkPair cp = findChunk(cx,cz);
		removeChunk(cp);
		addChunk(cx, cz, cp.data,findChunk(cx-1,cz).data, findChunk(cx+1,cz).data, findChunk(cx,cz-1).data, findChunk(cx,cz+1).data);
	}
	
	@Override
	public void update(float tpf) {
		Vector3f camPos = tg.getCamera().getLocation();
		int camChunkPosX = (int)(camPos.x/16f)+1;
		int camChunkPosZ = (int)(camPos.z/16f);
		int num=0;
		for(int x=-instRange;x<=instRange;x++) {
			for(int z=-instRange;z<=instRange;z++) {
				if(num>chunksAddPF)continue;
				int rx = x+camChunkPosX;
				int rz = z+camChunkPosZ;
				ChunkPair p = findChunk(rx,rz);
				if(p==null) {
					addChunk(rx,rz);
					num++;
				}else {
					if(!p.inst) {
						addChunk(rx,rz);
						num++;
					}
				}
			}
		}
		num=0;
		for(int i=0;i<chunkInstPosses.size();i++) {
			if(num>chunksRemPF)continue;
			Vector2f chunkPos = chunkInstPosses.get(i);
			if(new Vector2f(camChunkPosX,camChunkPosZ).distance(chunkPos)>remDist) {
				removeChunk(i);
				num++;
			}
		}
	}
	
	public void removeChunk(ChunkPair cp) {
		removeChunk(chunkInstPosses.indexOf(new Vector2f(cp.x,cp.z)));
	}
	
	public void removeChunk(int index) {
		Vector2f pos = chunkInstPosses.get(index);
		findChunk((int)pos.x, (int)pos.y).inst = false;
		chunkInstPosses.remove(index);
		tg.getRootNode().detachChild(chunkInstNodes.get(index));
		chunkInstNodes.remove(index);
		numChunks--;
	}
	
	public void addChunk(int cx, int cz) {
		Chunk xmi=findChunk(cx-1,cz).data;
		Chunk xpl=findChunk(cx+1,cz).data;
		Chunk zmi=findChunk(cx,cz-1).data;
		Chunk zpl=findChunk(cx,cz+1).data;
		if(xmi==null) {
			xmi=ChunkProvider.makeChunk(cx-1, cz, tg.blocks);
			chunks.add(new ChunkPair(cx-1,cz,xmi,false));
		}
		if(xpl==null) {
			xpl=ChunkProvider.makeChunk(cx+1, cz, tg.blocks);
			chunks.add(new ChunkPair(cx+1,cz,xpl,false));
		}
		if(zmi==null) {
			zmi=ChunkProvider.makeChunk(cx, cz-1, tg.blocks);
			chunks.add(new ChunkPair(cx,cz-1,zmi,false));
		}
		if(zpl==null) {
			zpl=ChunkProvider.makeChunk(cx, cz+1, tg.blocks);
			chunks.add(new ChunkPair(cx,cz+1,zpl,false));
		}
		Chunk cur = findChunk(cx,cz).data;
		if(cur==null) {
			cur=ChunkProvider.makeChunk(cx, cz, tg.blocks);
			chunks.add(new ChunkPair(cx,cz,cur,true));
		}
		addChunk(cx,cz,cur,xmi,xpl,zmi,zpl);
	}
	
	public void addChunk(int cx, int cz, Chunk chunk,Chunk xmi,Chunk xpl,Chunk zmi,Chunk zpl) {
		findChunk(cx,cz).inst=true;
		numChunks++;
		chunkInstPosses.add(new Vector2f(cx,cz));
		MeshData mdataOpaque = chunk.generateMesh(cx,cz,"Opaque", new Vector3f(cx*16,0,cz*16), xmi, xpl, zmi, zpl);
		Geometry geoOpaque = new Geometry("Opaque",mdataOpaque.makeMesh());
		geoOpaque.setMaterial(tg.matOpaque);
		
		MeshData mdataTransp = chunk.generateMesh(cx,cz,"Transp", new Vector3f(cx*16,0,cz*16), xmi, xpl, zmi, zpl);
		Geometry geoTransp = new Geometry("Transp",mdataTransp.makeMesh());
		geoTransp.setMaterial(tg.matTransp);
		geoTransp.setQueueBucket(Bucket.Transparent);
		
		Node node = new Node();
		node.attachChild(geoOpaque);
		node.attachChild(geoTransp);
		node.setModelBound(new BoundingSphere(Float.POSITIVE_INFINITY,Vector3f.ZERO));
		chunkInstNodes.add(node);
		
		tg.getRootNode().attachChild(node);
	}

}
