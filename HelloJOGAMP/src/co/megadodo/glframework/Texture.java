package co.megadodo.glframework;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import javax.imageio.ImageIO;

import com.jogamp.opengl.GL4;

public class Texture {

	public int id;
	
	public static enum TextureID {
		Tex0(GL4.GL_TEXTURE0),
		Tex1(GL4.GL_TEXTURE1),
		Tex2(GL4.GL_TEXTURE2),
		Tex3(GL4.GL_TEXTURE3),
		Tex4(GL4.GL_TEXTURE3),
		Tex5(GL4.GL_TEXTURE3),
		Tex6(GL4.GL_TEXTURE3),
		Tex7(GL4.GL_TEXTURE3),
		Tex8(GL4.GL_TEXTURE3),
		Tex9(GL4.GL_TEXTURE3),
		Tex10(GL4.GL_TEXTURE10),
		Tex11(GL4.GL_TEXTURE11),
		Tex12(GL4.GL_TEXTURE12),
		Tex13(GL4.GL_TEXTURE13),
		Tex14(GL4.GL_TEXTURE14),
		Tex15(GL4.GL_TEXTURE15),
		Tex16(GL4.GL_TEXTURE16),
		Tex17(GL4.GL_TEXTURE17),
		Tex18(GL4.GL_TEXTURE18),
		Tex19(GL4.GL_TEXTURE19),
		Tex20(GL4.GL_TEXTURE20),
		Tex21(GL4.GL_TEXTURE21),
		Tex22(GL4.GL_TEXTURE22),
		Tex23(GL4.GL_TEXTURE23),
		Tex24(GL4.GL_TEXTURE24),
		Tex25(GL4.GL_TEXTURE25),
		Tex26(GL4.GL_TEXTURE26),
		Tex27(GL4.GL_TEXTURE27),
		Tex28(GL4.GL_TEXTURE28),
		Tex29(GL4.GL_TEXTURE29),
		Tex30(GL4.GL_TEXTURE30),
		Tex31(GL4.GL_TEXTURE31);
		
		
		private int type;
		private TextureID(int i) {
			type=i;
		}
		public int glConst() {
			return type;
		}
	}
	
	public static ByteBuffer convertImageData(BufferedImage bi) 
	{
	    byte[] pixelData = ((DataBufferByte) bi.getRaster().getDataBuffer()).getData();
	    //        return ByteBuffer.wrap(pixelData);
	    ByteBuffer buf = ByteBuffer.allocateDirect(pixelData.length);
	    buf.order(ByteOrder.nativeOrder());
	    buf.put(pixelData);
	    buf.flip();
	    return buf;
	}
	
	public static ByteBuffer loadTexture(String imgName) {
		BufferedImage img;
		try {
			img = ImageIO.read(new File(imgName));
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return null;
		}
		return convertImageData(img);
	}
	
	public static BufferedImage loadTextureImg(String fn) {
		try {
			return ImageIO.read(new File(fn));
		}catch(IOException ioe) {
			ioe.printStackTrace();
			return null;
		}
	}
	
	public void genTexturesFile(GL4 gl,String filename) {
		BufferedImage img=loadTextureImg(filename);
		int w=img.getWidth();
		int h=img.getHeight();
		genTextures(gl,convertImageData(img),w,h);
	}

	public void genTextures(GL4 gl,ByteBuffer data,int width,int height) {
		IntBuffer intBuffer = IntBuffer.allocate(1);
		gl.glGenTextures(1, intBuffer);
		id = intBuffer.get(0);

		gl.glBindTexture(GL4.GL_TEXTURE_2D, id);
		gl.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_WRAP_S, GL4.GL_CLAMP_TO_EDGE);
		gl.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_WRAP_T, GL4.GL_CLAMP_TO_EDGE);
		gl.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_MIN_FILTER, GL4.GL_LINEAR);
		gl.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_MAG_FILTER, GL4.GL_LINEAR);
		gl.glTexImage2D(GL4.GL_TEXTURE_2D,0,GL4.GL_RGB,width,height,0,GL4.GL_RGB,GL4.GL_UNSIGNED_BYTE,data);
		gl.glGenerateMipmap(GL4.GL_TEXTURE_2D);
		gl.glBindTexture(GL4.GL_TEXTURE_2D, 0);
		System.out.println("Tex gen, id="+id);
	}
	
	public void bindTexture(GL4 gl, ShaderProgram shader, TextureID texNum) {
		gl.glActiveTexture(texNum.glConst());
		gl.glBindTexture(GL4.GL_TEXTURE_2D, id);
	}

}
