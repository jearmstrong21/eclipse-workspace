package co.megadodo.terraingen1;

import java.util.Map;

import org.json.JSONObject;

import com.jme3.math.Vector2f;

public class EvaluatorBool extends Evaluator<Boolean> {

	public static Boolean eval(JSONObject object, Map<String,Float> floatMap, Map<String,String>stringMap,Map<String,Vector2f>noiseOffsets) {
		return new EvaluatorBool().evaluate(object, floatMap, stringMap,noiseOffsets);
	}
	
	@Override
	public Boolean evaluate(JSONObject object, Map<String, Float> floatMap, Map<String, String> stringMap,Map<String,Vector2f>noiseOffsets) {
		return evaluate(0,0,object,floatMap,stringMap,noiseOffsets);
	}
	
	public static Boolean evaluate(float x,float y,JSONObject object,Map<String,Float>floatMap,Map<String,String>stringMap,Map<String,Vector2f>noiseOffsets) {
		String type = object.getString("type");
		if(type.equals("literal")) {
			return Boolean.parseBoolean(object.getString("value"));
		}
		if(type.equals("and")) {
			return eval(object.getJSONObject("a"),floatMap,stringMap,noiseOffsets)&&eval(object.getJSONObject("b"),floatMap,stringMap,noiseOffsets);
		}
		if(type.equals("or")) {
			return eval(object.getJSONObject("a"),floatMap,stringMap,noiseOffsets)||eval(object.getJSONObject("b"),floatMap,stringMap,noiseOffsets);
		}
		if(type.equals("xor")) {
			return Utils.xor(eval(object.getJSONObject("a"),floatMap,stringMap,noiseOffsets),eval(object.getJSONObject("b"),floatMap,stringMap,noiseOffsets));
		}
		if(type.equals("more")) {
			return EvaluatorFloat.evaluate(x,y,object.getJSONObject("a"), floatMap, stringMap,noiseOffsets) > EvaluatorFloat.evaluate(x,y,object.getJSONObject("b"), floatMap, stringMap,noiseOffsets);
		}
		if(type.equals("moreeq")) {
			return EvaluatorFloat.evaluate(x,y,object.getJSONObject("a"), floatMap, stringMap,noiseOffsets) >= EvaluatorFloat.evaluate(x,y,object.getJSONObject("b"), floatMap, stringMap,noiseOffsets);
		}
		if(type.equals("less")) {
			return EvaluatorFloat.evaluate(x,y,object.getJSONObject("a"), floatMap, stringMap,noiseOffsets) < EvaluatorFloat.evaluate(x,y,object.getJSONObject("b"), floatMap, stringMap,noiseOffsets);
		}
		if(type.equals("lesseq")) {
			return EvaluatorFloat.evaluate(x,y,object.getJSONObject("a"), floatMap, stringMap,noiseOffsets) <= EvaluatorFloat.evaluate(x,y,object.getJSONObject("b"), floatMap, stringMap,noiseOffsets);
		}
		return null;
	}

}