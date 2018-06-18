package co.megadodo.terraingen1;

import java.util.Map;

import org.json.JSONObject;

import com.jme3.math.Vector2f;

public abstract class Evaluator<E> {
	
	//Example:
	//object: {
	//	type: "range",
	//	param: "heightMap",
	//	min: 30,
	//  max: 50
	//}
	//floatMap: <"heightMap", 20>
	//stringMap: <empty>
	public abstract E evaluate(JSONObject object, Map<String, Float> floatMap, Map<String, String> stringMap,Map<String,Vector2f>noiseOffsets);

}
