package co.megadodo.mathlib;

public class Mat3 {

	// _00

	public float _00;
	public float _10;
	public float _20;
	public float _01;
	public float _11;
	public float _21;
	public float _02;
	public float _12;
	public float _22;

	public Mat3(float _00, float _10, float _20, float _01, float _11, float _21, float _02, float _12, float _22) {
		this._00 = _00;
		this._10 = _10;
		this._20 = _20;
		this._01 = _01;
		this._11 = _11;
		this._21 = _21;
		this._02 = _02;
		this._12 = _12;
		this._22 = _22;
	}

	public Mat3(float[][] arr) {
		this(arr[0][0], arr[1][0], arr[2][0], arr[0][1], arr[1][1], arr[2][1], arr[0][2], arr[1][2], arr[2][2]);
	}

	public float[][] toArray() {
		return new float[][] { { _00, _10, _20 }, { _01, _11, _21 }, { _02, _12, _22 } };
	}

	public float determinant() {
		return _00 * Mat2.determinant(_11, _21, _12, _22) - _01 * Mat2.determinant(_10, _20, _12, _22)
				+ _02 * Mat2.determinant(_10, _20, _11, _21);
	}

	public static float determinant(float[][] arr) {
		return arr[0][0] * Mat2.determinant(arr[1][1], arr[2][1], arr[1][2], arr[2][2])
				- arr[0][1] * Mat2.determinant(arr[1][0], arr[2][0], arr[1][2], arr[2][2])
				+ arr[0][2] * Mat2.determinant(arr[1][0], arr[2][0], arr[1][1], arr[2][1]);
	}

	public static void main(String[] args) {
		Mat3 m = new Mat3(new float[][] { { 2, 4, -3 }, { 1, 7, 0 }, { -1, 5, 2 } });
		System.out.println(m.determinant());
	}

}
