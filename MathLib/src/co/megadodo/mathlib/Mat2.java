package co.megadodo.mathlib;

public class Mat2 {
	
	// _00 _10
	// _01 _11

	public float _00;
	public float _10;
	public float _01;
	public float _11;

	public Mat2(float _00, float _10, float _01, float _11) {
		this._00 = _00;
		this._10 = _10;
		this._01 = _01;
		this._11 = _11;
	}
	
	public Mat2(float[][] arr) {
		this(arr[0][0],arr[1][0],arr[0][1],arr[1][1]);
	}
	
	public float[][] toArray(){
		return new float[][] { {_00,_10}, {_01,_11} };
	}
	
	public float determinant() {
		return _00 * _11 - _10 * _01;
	}
	
	public static float determinant(float[][] arr) {
		return arr[0][0] * arr[1][1] - arr[0][1] * arr[1][0];
	}
	
	public static float determinant(float _00, float _10, float _01, float _11) {
		return _00 * _11 - _10 * _01;
	}
	
	public static void main(String[]args) {
		System.out.println("Mat2 testing.");
		Mat2 m2=new Mat2(new float[][] {{0,1},{2,3}});
		float[][]arr=m2.toArray();
		System.out.println("[ " + arr[0][0]+" "+arr[1][0] + " ]");
		System.out.println("[ " + arr[0][1]+" "+arr[1][1] + " ]");
	}
	

}
