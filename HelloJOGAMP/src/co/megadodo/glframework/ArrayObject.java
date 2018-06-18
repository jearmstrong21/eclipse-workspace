package co.megadodo.glframework;

import java.nio.IntBuffer;

import java.util.ArrayList;

import com.jogamp.opengl.GL4;


public class ArrayObject {
// Careful! Multiple BufferObjectInt in buffers with target=BufferTarget.ElementBuffer does not work
// Only one triangle buffer can be instantiated with any ArrayObject
	
	public int ao;

	public ArrayObject() {
		provokingVertex=ProvokingVertex.First;
		bufferType=BufferType.Static;
		polygonMode=PolygonMode.Fill;
		buffers=new ArrayList<BufferObject<?>>();
	}
	
	
	public ArrayList<BufferObject<?>>buffers;
	
	public ProvokingVertex provokingVertex;
	public BufferType bufferType;
	public PolygonMode polygonMode;

	public void genBuffers(GL4 gl) {
		IntBuffer intBuffer=IntBuffer.allocate(1);
		
		gl.glGenVertexArrays(1, intBuffer);
		ao=intBuffer.get(0);
		
		gl.glBindVertexArray(ao);
		
		for(BufferObject<?> bo : buffers) {
			bo.genBuffers(gl);
		}
		
		gl.glBindVertexArray(0);
        System.out.println("AO gen, ao="+ao);
	}

	public void render(GL4 gl) {
		gl.glProvokingVertex(provokingVertex.glConst());
		gl.glPolygonMode(GL4.GL_FRONT_AND_BACK, polygonMode.glConst());
		gl.glBindVertexArray(ao);
		
		for(BufferObject<?> bo : buffers) {
			if(bo.bufferTarget==BufferTarget.ElementBuffer) {
				bo.render(gl);
			}
		}
		
		gl.glBindVertexArray(0);
	}
	
	public void delete(GL4 gl) {
		gl.glDeleteVertexArrays(1, IntBuffer.wrap(new int[] {ao}));
		for(BufferObject<?>bo:buffers) {
			bo.delete(gl);
		}
	}

}
