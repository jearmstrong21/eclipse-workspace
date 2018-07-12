package co.megadodo.lwjgl.glframework.buffer;

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

public class BufferTarget {


	public static int Array=GL_ARRAY_BUFFER;
	public static int AtomicCounter=GL_ATOMIC_COUNTER_BUFFER;
	public static int CopyRead=GL_COPY_READ_BUFFER;
	public static int CopyWrite=GL_COPY_WRITE_BUFFER;
	public static int DispatchIndirect=GL_DISPATCH_INDIRECT_BUFFER;
	public static int DrawIndirect=GL_DRAW_INDIRECT_BUFFER;
	public static int ElementArray=GL_ELEMENT_ARRAY_BUFFER;
	public static int PixelPack=GL_PIXEL_PACK_BUFFER;
	public static int PixelUnpack=GL_PIXEL_UNPACK_BUFFER;
	public static int Query=GL_QUERY_BUFFER;
	public static int ShaderStorage=GL_SHADER_STORAGE_BUFFER;
	public static int Texture=GL_TEXTURE_BUFFER;
	public static int Uniform=GL_UNIFORM_BUFFER;
	
}
