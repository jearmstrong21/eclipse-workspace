package co.megadodo.lwjgl.glframework;

import java.io.File;
import java.util.Scanner;

public class Utilities {

	public static String loadStrFromFile(String fn) {
		try {
			File f = new File(fn);
			Scanner sc = new Scanner(f);
			String str = "";
			while (sc.hasNextLine())
				str += sc.nextLine() + "\n";
			sc.close();
			return str;
		} catch (Throwable t) {
			t.printStackTrace();
			return "";
		}
	}
	
}
