package co.megadodo.lwjgl.glframework;

public interface GLResource {
	
	public void gen();
	public void bind();
	public default void render() {
		
	}
	public void unbind();
	public void delete();

}
