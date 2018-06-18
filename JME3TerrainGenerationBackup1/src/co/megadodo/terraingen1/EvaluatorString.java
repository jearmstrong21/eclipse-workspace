package co.megadodo.terraingen1;

import java.util.Map;

import org.json.JSONObject;

import com.jme3.math.Vector2f;

public class EvaluatorString extends Evaluator<String> {

	public static String eval(JSONObject object,Map<String,Float>floatMap,Map<String,String>stringMap,Map<String,Vector2f>noiseOffsets) {
		return new EvaluatorString().evaluate(object, floatMap, stringMap,noiseOffsets);
	}
	
	@Override
	public String evaluate(JSONObject object, Map<String,Float>floatMap,Map<String,String>stringMap,Map<String,Vector2f>noiseOffsets) {
		return evaluate(0,0,object,floatMap,stringMap,noiseOffsets);
	}
	
	public static String evaluate(float x, float y, JSONObject object, Map<String, Float> floatMap, Map<String, String> stringMap,Map<String,Vector2f>noiseOffsets) {
		String type = object.getString("type");
		if(type.equals("literal"))return object.getString("value");
		if(type.equals("var"))return stringMap.get(object.getString("value"));
		if(type.equals("if")) {
			boolean bool = new EvaluatorBool().evaluate(x,y,object.getJSONObject("expr"), floatMap, stringMap,noiseOffsets);
			if(bool)return object.getString("yes");
			if(!bool)return object.getString("no");
 		}
		return "";
	}

}