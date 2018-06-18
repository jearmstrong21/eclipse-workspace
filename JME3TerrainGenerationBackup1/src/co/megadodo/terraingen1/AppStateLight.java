package co.megadodo.terraingen1;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.light.PointLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;

public class AppStateLight extends AbstractAppState {

	public SimpleApplication simpleApp;
	public AmbientLight ambient;
	public PointLight player;
	public DirectionalLight sun;
	@Override
	public void initialize(AppStateManager appstatemanager, Application app) {
		ambient = new AmbientLight();
		ambient.setColor(ColorRGBA.White.mult(0.1f));
		player = new PointLight();
		player.setColor(ColorRGBA.White.mult(1f));
		player.setRadius(100);
		
		sun = new DirectionalLight(new Vector3f(-0.5f,-0.5f,-0.5f).normalizeLocal(), ColorRGBA.White.mult(0.5f));
		
		simpleApp.getRootNode().addLight(sun);
		simpleApp.getRootNode().addLight(ambient);
		simpleApp.getRootNode().addLight(player);
	}
	
	@Override
	public void update(float tpf) {
		player.setPosition(simpleApp.getCamera().getLocation());
	}
	
}
