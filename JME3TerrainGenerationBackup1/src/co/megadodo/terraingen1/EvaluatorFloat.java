package co.megadodo.terraingen1;

import java.util.Map;

import org.json.JSONObject;

import com.jme3.math.Vector2f;

public class EvaluatorFloat extends Evaluator<Float> {
	
	public static float eval(JSONObject object, Map<String, Float> floatMap, Map<String, String> stringMap, Map<String,Vector2f> noiseOffsets) {
		return new EvaluatorFloat().evaluate(object, floatMap, stringMap,noiseOffsets);
	}
	
	@Override
	public Float evaluate(JSONObject o, Map<String,Float>fm,Map<String,String>sm,Map<String,Vector2f>no) {
		return evaluate(0,0,o,fm,sm,no);
	}

	public static Float evaluate(float x, float y, JSONObject object, Map<String, Float> floatMap, Map<String, String> stringMap,Map<String,Vector2f>noiseOffsets) {
		String type = object.getString("type");
		//TODO: add heightmap evaluation: if(type.equals("heightmap"))blahblahblah
		//TODO: have one instance of FastNoise
		//TODO: test in EvaluatorTest.java - print values to console
		//TODO: x,y are passed in as parameters in floatMap and are reserved as variable names
		//TODO: example in Biome1.json
		if(type.equals("literal"))return (float)object.getDouble("value");
		if(type.equals("var"))return floatMap.get(object.getString("value"));
		if(type.equals("noise2")) {
			String map = object.getString("name");
			Vector2f vecOffset = noiseOffsets.get(map);
			float zoom = (float)object.getDouble("zoom");
			float min = (float)object.getDouble("min");
			float max = (float)object.getDouble("max");
			return Utils.noisePerlin2( x*zoom+vecOffset.x,y*zoom+vecOffset.y   )*(max-min)+min;
		}
		if(type.equals("random")) {
			float mi = (float)object.getDouble("min");
			float ma = (float)object.getDouble("max");
			return (float)(Math.random() * (ma-mi)) + mi;
		}
		if(type.equals("+")) return eval(object.getJSONObject("a"),floatMap,stringMap,noiseOffsets)+eval(object.getJSONObject("b"),floatMap,stringMap,noiseOffsets);
		if(type.equals("-")) return eval(object.getJSONObject("a"),floatMap,stringMap,noiseOffsets)-eval(object.getJSONObject("b"),floatMap,stringMap,noiseOffsets);
		if(type.equals("*")) return eval(object.getJSONObject("a"),floatMap,stringMap,noiseOffsets)*eval(object.getJSONObject("b"),floatMap,stringMap,noiseOffsets);
		if(type.equals("/")) return eval(object.getJSONObject("a"),floatMap,stringMap,noiseOffsets)/eval(object.getJSONObject("b"),floatMap,stringMap,noiseOffsets);
		if(type.equals("if")) {
			boolean result = EvaluatorBool.eval(object.getJSONObject("expr"), floatMap, stringMap,noiseOffsets);
			if(result) return eval(object.getJSONObject("yes"),floatMap,stringMap,noiseOffsets);
			if(!result)return eval(object.getJSONObject("no" ),floatMap,stringMap,noiseOffsets);
		}
		return 0f;
	}

}