package co.megadodo.boids;

import java.util.ArrayList;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.post.Filter;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.ColorOverlayFilter;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;

public class Boids extends SimpleApplication {

	public static void main(String[] args) {
		Boids boids = new Boids();
		boids.start();
	}
	
	ArrayList<ColorRGBA> cols = new ArrayList<ColorRGBA>();
	ArrayList<Boid> boids = new ArrayList<Boid>();
	ArrayList<Geometry> geos = new ArrayList<Geometry>();
	
	@Override
	public void simpleInitApp() {
		System.out.println("Boids in JME3");
		for(int x=0;x<1000;x++) {
			Sphere mesh = new Sphere(10, 10, 1);
			Geometry geo = new Geometry("Boid " + x, mesh);
			Material boidMat = new Material(assetManager,"Common/MatDefs/Misc/Unshaded.j3md");
			ColorRGBA col = ColorRGBA.randomColor();
			cols.add(col);
			boidMat.setColor("Color", col);
			geo.setMaterial(boidMat);
			rootNode.attachChild(geo);
			geos.add(geo);
			boids.add(new Boid());
		}
		flyCam.setMoveSpeed(200);
		cam.setFrustumFar(10000);

		inputManager.addMapping("OnRepel", new KeyTrigger(KeyInput.KEY_R));
		inputManager.addMapping("OffRepel", new KeyTrigger(KeyInput.KEY_T));
		inputManager.addListener(listener, "OnRepel","OffRepel");
//		Filter filter = new ColorOverlayFilter(new ColorRGBA(1,1,0,0));
//		FilterPostProcessor fpp = new FilterPostProcessor();
//		fpp.addFilter(filter);
//		viewPort.addProcessor(fpp);
	}
	
	ActionListener listener = new ActionListener() {
		
		@Override
		public void onAction(String name, boolean isPressed, float tpf) {
			if(name.equals("OnRepel")) {
				Boid.doRepel = true;
			}
			if(name.equals("OffRepel")) {
				Boid.doRepel = false;
			}
		}
	};
	
	@Override
	public void simpleUpdate(float tpf) {
		if(Boid.doRepel) {
			Boid.VEC_REPEL = cam.getLocation();
		}
		for(int x=0;x<geos.size();x++) {
			geos.get(x).setLocalTranslation(boids.get(x).curPos);
			boids.get(x).update(boids);
		}
		for(int x=0;x<geos.size();x++) {
			boids.get(x).updatePos(tpf);
		}
	}

}
