package co.megadodo.terraingen1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.jme3.math.Vector2f;

public class EvaluatorTest {
	
	public static void main(String[] args) throws JSONException, FileNotFoundException {
		JSONObject object = new JSONObject(new JSONTokener(new FileInputStream(new File("EvaluatorTest.json"))));
		Map<String, Float> floatMap = new HashMap<String,Float>();
		Map<String, String> stringMap = new HashMap<String,String>();
		Map<String, Vector2f> noiseOffsets = new HashMap<String,Vector2f>();
		floatMap.put("x", 30f);
		floatMap.put("y", 1f);
		float result = EvaluatorFloat.eval(object, floatMap, stringMap,noiseOffsets);
		System.out.println("Result: " + result);
	}

}
