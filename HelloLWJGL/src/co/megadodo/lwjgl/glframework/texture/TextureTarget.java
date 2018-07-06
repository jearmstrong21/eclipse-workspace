package co.megadodo.lwjgl.glframework.texture;

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

public class TextureTarget {
	public static int Tex1D=GL_TEXTURE_1D;
	public static int Tex2D=GL_TEXTURE_2D;
	public static int Tex3D=GL_TEXTURE_3D;
	public static int Array1D=GL_TEXTURE_1D_ARRAY;
	public static int Array2D=GL_TEXTURE_2D_ARRAY;
	public static int Rectangle=GL_TEXTURE_RECTANGLE;
	public static int CubeMap=GL_TEXTURE_CUBE_MAP;
	public static int CubeMapArray=GL_TEXTURE_CUBE_MAP_ARRAY;
	public static int Buffer=GL_TEXTURE_BUFFER;
	public static int Multisample2D=GL_TEXTURE_2D_MULTISAMPLE;
	public static int Multisample2DArray=GL_TEXTURE_2D_MULTISAMPLE_ARRAY;
}
