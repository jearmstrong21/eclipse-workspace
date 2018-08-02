package co.megadodo.lwjgl.glframework.text;

public abstract class FontAtlas {
	
	public abstract AtlasPos getPos(char c);
	
	public abstract String getTextureFile();
	
	public abstract float getAspectRatio();

}
