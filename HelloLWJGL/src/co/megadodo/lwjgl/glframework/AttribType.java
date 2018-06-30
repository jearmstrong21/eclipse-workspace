package co.megadodo.lwjgl.glframework;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.io.File;
import java.nio.*;
import java.util.Scanner;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL21.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL31.*;
import static org.lwjgl.opengl.GL32.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.opengl.GL40.*;
import static org.lwjgl.opengl.GL41.*;
import static org.lwjgl.opengl.GL42.*;
import static org.lwjgl.opengl.GL43.*;
import static org.lwjgl.opengl.GL44.*;
import static org.lwjgl.opengl.GL45.*;
import static org.lwjgl.opengl.GL46.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class AttribType {
	
	public static int Float=GL_FLOAT;
	public static int Integer=GL_INT;
	public static int Byte=GL_BYTE;
	public static int UnsignedByte=GL_UNSIGNED_BYTE;
	public static int UnsignedInteger=GL_UNSIGNED_INT;
	
	public static int getBytes(int type) {
		if(type==AttribType.Float)return java.lang.Float.BYTES;
		if(type==AttribType.Integer)return java.lang.Integer.BYTES;
		if(type==AttribType.Byte)return java.lang.Byte.BYTES;
		return 0;
	}

}
