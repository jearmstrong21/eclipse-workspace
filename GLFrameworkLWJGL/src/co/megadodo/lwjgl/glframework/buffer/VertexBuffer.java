package co.megadodo.lwjgl.glframework.buffer;
import co.megadodo.lwjgl.glframework.*;

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

public class VertexBuffer implements GLResource, GLRenderable {
	

	public int id;
	
	public int target;
	
	public int usage;
	
	public int polyMode;
	public int provokeMode;
	
	public void gen() {
		id=glGenBuffers();
	}
	
	public int num_data;
	
	public int data_type;
	
	public void setData(float[] data) {
		num_data=data.length;
		data_type=AttribType.Float;
		FloatBuffer buf=BufferUtils.createFloatBuffer(data.length);
		buf.put(data);
		buf.flip();
		glBufferData(target,buf,usage);
	}
	
	public void setData(byte[]data) {
		num_data=data.length;
		data_type=AttribType.Byte;
		ByteBuffer buf=BufferUtils.createByteBuffer(data.length);
		buf.put(data);
		buf.flip();
		glBufferData(target,buf,usage);
	}
	
	public void setData(int[]data) {
		num_data=data.length;
		data_type=AttribType.Integer;
		IntBuffer buf=BufferUtils.createIntBuffer(data.length);
		buf.put(data);
		buf.flip();
		glBufferData(target,buf,usage);
	}
	
	public void setDataUnsigned(byte[]data) {
		num_data=data.length;
		data_type=AttribType.UnsignedByte;
		ByteBuffer buf=BufferUtils.createByteBuffer(data.length);
		buf.put(data);
		buf.flip();
		glBufferData(target,buf,usage);
	}
	
	public void setDataUnsigned(int[]data) {
		num_data=data.length;
		data_type=AttribType.UnsignedInteger;
		IntBuffer buf=BufferUtils.createIntBuffer(data.length);
		buf.put(data);
		buf.flip();
		glBufferData(target,buf,usage);
	}
	
	public void addVertexAttrib(int attribNum, int size, int attribType, boolean normalized, int stride, int start) {

		int bytes=AttribType.getBytes(attribType);
		glEnableVertexAttribArray(attribNum);
		glVertexAttribPointer(attribNum, size, attribType, normalized, stride * bytes, start*bytes);
	}
	
	public void render() {
		render(provokeMode, polyMode);
	}
	
	public void render(int provoke, int polygonMode) {
		glPolygonMode(GL_FRONT_AND_BACK,polygonMode);
		glProvokingVertex(provoke);
		if(target==BufferTarget.ElementArray) {
			glDrawElements(GL_TRIANGLES, num_data, data_type, 0);
		}
	}
	
	public void bind() {
		glBindBuffer(target,id);
	}
	
	public void unbind() {
		glBindBuffer(target,0);
	}
	
	public void delete() {
		glDeleteBuffers(id);
	}
	
}
