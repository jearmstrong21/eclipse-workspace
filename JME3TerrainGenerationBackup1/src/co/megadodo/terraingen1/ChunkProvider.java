package co.megadodo.terraingen1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.jme3.math.Vector2f;

public class ChunkProvider {
	
	static JSONObject prefsObject;
	static JSONObject worldObject;
	static ArrayList<Biome> biomes;
	static JSONArray biomesArray;
	static JSONArray paramsArray;
	static Map<String, String> stringMap;
	static Map<String, Float> floatMap;
	static Map<String, Vector2f> noiseOffsets;
	static int numSamples;
	static int sealvl;
	static String waterblock;
	static {
		prefsObject = Utils.loadObject("Preferences.json");
		worldObject = Utils.loadObject(prefsObject.getString("WorldGen"));
		biomesArray = worldObject.getJSONArray("Biomes");
		paramsArray = worldObject.getJSONArray("params");
		numSamples = worldObject.getInt("heightSamples");
		sealvl=worldObject.getInt("sealvl");
		waterblock=worldObject.getString("waterblock");
		biomes = new ArrayList<Biome>();
		for(int i=0;i<biomesArray.length();i++) {
			JSONObject obj = Utils.loadObject(biomesArray.getString(i));
			Biome b = new Biome();
			b.top = obj.getJSONObject("top");
			b.mid = obj.getJSONObject("mid");
			b.bot = obj.getJSONObject("bot");
			b.canExist = obj.getJSONObject("canExist");
			b.heightMap = obj.getJSONObject("heightMap");
			biomes.add(b);
		}
		stringMap = new HashMap<String,String>();
		floatMap = new HashMap<String,Float>();
		noiseOffsets = new HashMap<String, Vector2f>();
		for(int i=0;i<paramsArray.length();i++) {
			JSONObject obj = paramsArray.getJSONObject(i);
			String name = obj.getString("name");
			String type = obj.getString("type");
			if(type.equals("float")) {
				float val = (float)obj.getDouble("value");
				floatMap.put(name, val);
			}
			if(type.equals("string")) {
				stringMap.put(name, obj.getString("value"));
			}
			if(type.equals("noise")) {
				noiseOffsets.put(name, new Vector2f((float)Math.random()*10000f,(float)Math.random()*10000f));
			}
		}
	}
	
	static float height(int x,int z) {
		return Utils.noisePerlin2(x*2, z*2) *60+20;
//				return
//				Utils.noisePerlin2(x*10+10000, z*10+10000)*20;
	}
	
	static float blerp(float a00,float a10,float a01,float a11,float x,float y) {
		return a00*(1-x)*(1-y)+a10*x*(1-y)+a01*(1-x)*y+a11*x*y;
	}
	
	static Block findBlock(String name, ArrayList<Block> bls) {
		for(Block b : bls) {
			if(b.name.equals(name))return b;
		}
		return null;
	}

	public static Chunk makeChunk(int cx, int cz,ArrayList<Block> bls) {
		//TODO: biomes are not evaluating correctly
		Chunk c = new Chunk();
		Biome[][] bmap = new Biome[16+1][16+1];
		for(int x=0;x<=16;x++) {
			for(int z=0;z<=16;z++) {
				for(Biome biome : biomes) {
					if(EvaluatorBool.evaluate(x+cx*16,z+cz*16,biome.canExist, floatMap, stringMap, noiseOffsets))bmap[x][z]=biome;
				}
			}
		}
		float[][] samples = new float[numSamples+1][numSamples+1];
		for(int x=0;x<=numSamples;x++) {
			for(int z=0;z<=numSamples;z++) {
				int rx = x*16/(numSamples);
				int rz = z*16/(numSamples);
				samples[x][z]=EvaluatorFloat.evaluate(rx+cx*16,rz+cz*16,bmap[rx][rz].heightMap,floatMap,stringMap,noiseOffsets);
			}
		}
		float[][] heightMap = new float[16][16];
		for(int x=0;x<16;x++) {
			for(int z=0;z<16;z++) {
				int sx = (int)(x*numSamples/16f);
				int sz = (int)(z*numSamples/16f);
				float a00 = samples[sx][sz];
				float a10 = samples[sx+1][sz];
				float a01 = samples[sx][sz+1];
				float a11 = samples[sx+1][sz+1];
//				System.out.println(x/16f+" "+z/16f);
				heightMap[x][z]=blerp(a00,a10,a01,a11,x/16f,z/16f);
			}
		}
		for(int x=0;x<16;x++) {
			for(int z=0;z<16;z++) {
				Biome b = bmap[x][z];
//				int noise = EvaluatorFloat.evaluate(x+cx*16,z+cz*16,b.heightMap, floatMap, stringMap, noiseOffsets).intValue();
				int noise = (int)heightMap[x][z];
				
//				int noise = (int)height(cx*16+x,cz*16+z);
				//TODO: StringEvaluator takes in float x,float y
				c.data[x][noise][z] = findBlock(EvaluatorString.evaluate(x+cx*16,z+cz*16,b.top, floatMap, stringMap, noiseOffsets),bls);
				for(int y=0;y<noise-10;y++) {
					c.data[x][y][z]=findBlock(EvaluatorString.evaluate(x+cx*16,z+cz*16,b.bot, floatMap, stringMap, noiseOffsets),bls);;
				}
				for(int y=noise-10;y<noise;y++) {
					if(y>0)
					c.data[x][y][z]=findBlock(EvaluatorString.evaluate(x+cx*16,z+cz*16,b.mid, floatMap, stringMap, noiseOffsets),bls);;
				}
				if(noise<sealvl) {
					for(int y=noise+1;y<=sealvl;y++) {
						c.data[x][y][z]=findBlock(waterblock,bls);
					}
				}
			}
		}
		return c;
	}
	
}
