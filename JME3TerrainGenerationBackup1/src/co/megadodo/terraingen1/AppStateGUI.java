package co.megadodo.terraingen1;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;

public class AppStateGUI extends AbstractAppState {

	boolean showText = true;
	BitmapFont font;
	BitmapText txtMemUse;
	BitmapText txtMemFree;
	BitmapText txtMemPerc;
	BitmapText txtFPS;
	BitmapText txtNumChunks;
	BitmapText txtChunk;
	BitmapText txtPos;
	BitmapText txtDir;
	BitmapText txtTitle;
	
	BitmapText txtCrosshair;
	
	int width;
	int height;
	int rendSize;
	Node guiNode;
	Camera cam;
	
	public void setupTxt(BitmapText... txts) {
		for(BitmapText txt : txts) {
			txt.setSize(font.getCharSet().getRenderedSize());
			txt.setColor(ColorRGBA.White);
			guiNode.attachChild(txt);
		}
	}
	
	Application myapp;
	
	@Override
	public void initialize(AppStateManager appstatemanager, Application app) {
		myapp=app;
		rendSize = font.getCharSet().getRenderedSize();
		txtMemUse = new BitmapText(font, false);
		txtMemFree = new BitmapText(font, false);
		txtMemPerc = new BitmapText(font, false);
		txtFPS = new BitmapText(font, false);
		txtNumChunks = new BitmapText(font, false);
		txtChunk = new BitmapText(font, false);
		txtPos = new BitmapText(font, false);
		txtDir = new BitmapText(font, false);
		txtTitle = new BitmapText(font, false);
		setupTxt(txtMemUse,txtMemFree,txtMemPerc,txtFPS,txtNumChunks,txtChunk,txtPos,txtDir,txtTitle);
		
		txtTitle.setLocalTranslation(0, height, 0);
		txtChunk.setLocalTranslation(0, height-rendSize*2,0);
		txtPos.setLocalTranslation(0, height-rendSize*3, 0);
		txtDir.setLocalTranslation(0, height-rendSize*4, 0);
		txtNumChunks.setLocalTranslation(0,height-rendSize*5,0);
		
		txtFPS.setLocalTranslation(0, rendSize*4, 0);
		txtMemUse.setLocalTranslation(0,rendSize*3,0);
		txtMemFree.setLocalTranslation(0, rendSize*2, 0);
		txtMemPerc.setLocalTranslation(0,rendSize,0);

	    BitmapText ch = new BitmapText(font, false);
	    ch.setSize(font.getCharSet().getRenderedSize() * 2);
	    ch.setText("+");
	    ch.setLocalTranslation(width / 2 - ch.getLineWidth()/2, height / 2 + ch.getLineHeight()/2, 0);
	    guiNode.attachChild(ch);
	}
	
	float secCounter=0;
	float frameCounter=0;
	
	@Override
	public void update(float tpf) {
		txtTitle.setText("TerranGeneration 1");
		Vector3f camPos = cam.getLocation();
		Vector2f chunkPos = new Vector2f ( (int) (camPos.x/16),(int)(camPos.z/16));
		Vector3f dir = cam.getDirection();
		txtChunk.setText("Chunk: (" + chunkPos.x + ", " + chunkPos.y+")");
		txtPos.setText ("Position: (" + round(camPos.x) + ", " + round(camPos.y) + ", " + round(camPos.z) + ")");
		txtDir.setText ("Direction: ( " + round(dir.x)+", "+round(dir.y)+", "+round(dir.z)+")");
		txtNumChunks.setText("Chunks instantiated: " + AppStateWorld.numChunks);
		
		secCounter+=myapp.getTimer().getTimePerFrame();
		frameCounter++;
		
		if(secCounter>=0.5f) {
			int fps = (int)(frameCounter/secCounter);
			txtFPS.setText("FPS: " + fps);
			secCounter=0;
			frameCounter=0;
		}
		
		long memFree = Runtime.getRuntime().freeMemory()/1000000;
		long memTotal = Runtime.getRuntime().totalMemory()/1000000;
		long memUse = memTotal-memFree;
		txtMemUse.setText("Memory used: " + memUse + "mb");
		txtMemFree.setText("Memory free: " + memFree + "mb");
		txtMemPerc.setText("Memory percentage used: " + round(100f*memUse/memTotal) + "%");
	}
	
	float round(float a) {
		return (int)(a*100)/100f;
	}
	
}