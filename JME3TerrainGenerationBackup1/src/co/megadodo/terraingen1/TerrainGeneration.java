package co.megadodo.terraingen1;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.ColorRGBA;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.post.filters.BloomFilter.GlowMode;
import com.jme3.system.AppSettings;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.MagFilter;
import com.jme3.texture.Texture.MinFilter;
import com.jme3.util.SkyFactory;

public class TerrainGeneration extends SimpleApplication {
	
	public static TerrainGeneration tg;

	public static void main(String[] args) {
		AppSettings settings = new AppSettings(true);
		settings.setMinWidth(640);
		settings.setMinHeight(480);
		settings.setWidth(1024);
		settings.setHeight(768);
		settings.setTitle("Terrain Generation");
		TerrainGeneration app = new TerrainGeneration();
		app.setSettings(settings);
		tg = app;
		app.start();
	}

	//Preferences
	public JSONObject prefObject;
	public String atlasFileName;
	public boolean renderLit;
	public boolean renderShowNormals;
	public boolean renderWireframe;
	
	//Atlas
	public JSONObject atlasObject;
	public String atlasTexName;
	public Texture atlasTex;
	public int atlasW;
	public int atlasH;
	public JSONArray atlasTilesArray;
	public ArrayList<AtlasPosition> atlasPosses;

	//Blocks
	public ArrayList<Block> blocks;
	
	//Materials
	public Material matOpaque;
	public Material matTransp;
	
	public void loadPreferences() {
		prefObject = Utils.loadObject("Preferences.json");
		atlasFileName = prefObject.getString("Atlas");
		JSONObject renderPrefs = prefObject.getJSONObject("Render");
		renderLit = renderPrefs.getBoolean("Lit");
		renderShowNormals = renderPrefs.getBoolean("ShowNormals");
		renderWireframe = renderPrefs.getBoolean("Wireframe");
	}
	public void loadAtlas() {
		atlasObject = Utils.loadObject(atlasFileName);
		atlasTexName = atlasObject.getString("texture");
		atlasW = atlasObject.getInt("atlasW");
		atlasH = atlasObject.getInt("atlasH");
		atlasTilesArray = atlasObject.getJSONArray("tiles");
		atlasTex = assetManager.loadTexture(atlasTexName);
		atlasTex.setMinFilter(MinFilter.NearestNoMipMaps);
		atlasTex.setMagFilter(MagFilter.Nearest);
		atlasPosses = new ArrayList<AtlasPosition>();
		for(int i=0;i<atlasTilesArray.length();i++) {
			JSONObject obj = atlasTilesArray.getJSONObject(i);
			String name = obj.getString("name");
			int x = obj.getInt("x");
			int y = obj.getInt("y");
			atlasPosses.add(new AtlasPosition(name, x, y, atlasW, atlasH));
			System.out.println("Loaded atlas tile " + obj.toString());
		}
		flyCam.setMoveSpeed(5);
	}
	public void loadBlocks() {
		blocks = new ArrayList<Block>();
		JSONArray blocksArray = prefObject.getJSONArray("Blocks");
		for(int i=0;i<blocksArray.length();i++) {
			String name = blocksArray.getString(i);
			loadBlock(name);
		}
	}
	public void loadBlock(String blockName) {
		JSONObject blockObj = Utils.loadObject(blockName);
		System.out.println("Loaded block " + blockObj.toString());
		Block block = new Block();
		block.name = blockObj.getString("name");
		block.mesh = blockObj.getString("mesh");
		block.mat = blockObj.getString("mat");
		if(block.mesh.equals("Block")) {
			block.xmi = findAtlasPosition(blockObj.getString("xmi"));
			block.xpl = findAtlasPosition(blockObj.getString("xpl"));
			block.ymi = findAtlasPosition(blockObj.getString("ymi"));
			block.ypl = findAtlasPosition(blockObj.getString("ypl"));
			block.zmi = findAtlasPosition(blockObj.getString("zmi"));
			block.zpl = findAtlasPosition(blockObj.getString("zpl"));
		}
		blocks.add(block);
	}
	public void loadMaterials() {
		if(renderShowNormals) {
			matTransp = new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");
			matOpaque = new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");
		} else if(renderLit) {
			matTransp = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
			matTransp.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
			matTransp.setTexture("DiffuseMap", atlasTex);
			matOpaque = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
			matOpaque.setTexture("DiffuseMap", atlasTex);
		} else {
			matTransp = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
			matTransp.setTexture("ColorMap", atlasTex);
			matTransp.setBoolean("VertexColor", true);
			matOpaque = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
			matOpaque.setTexture("ColorMap", atlasTex);
			matOpaque.setBoolean("VertexColor", true);
		}
		if(renderWireframe) {
			matTransp.getAdditionalRenderState().setWireframe(true);
			matTransp.getAdditionalRenderState().setLineWidth(5f);
			matOpaque.getAdditionalRenderState().setWireframe(true);
			matOpaque.getAdditionalRenderState().setLineWidth(5f);
		}
	}
	
	public AtlasPosition findAtlasPosition(String name) {
		for(AtlasPosition p : atlasPosses) {
			if(p.name.equals(name))return p;
		}
		return new AtlasPosition(name,0,0,1,1);
	}
	
	@Override
	public void simpleInitApp() {
		setDisplayFps(false);
		setDisplayStatView(false);
		
		AppStateLight asl = new AppStateLight();
		asl.simpleApp = this;
		stateManager.attach(asl);
		
		AppStateWorld asw = new AppStateWorld();
		asw.tg = this;
		stateManager.attach(asw);
		
		AppStateGUI asg = new AppStateGUI();
		asg.font = guiFont;
		asg.width = settings.getWidth();
		asg.height = settings.getHeight();
		asg.guiNode = guiNode;
		asg.cam = cam;
		stateManager.attach(asg);
		
		FastNoise inst = new FastNoise();
		assetManager.registerLocator("Images/", FileLocator.class);
		loadPreferences();
		loadAtlas();
		loadBlocks();
		loadMaterials();
		
		rootNode.attachChild(SkyFactory.createSky(assetManager, "Sky/BrightSky.dds", SkyFactory.EnvMapType.CubeMap));
		flyCam.setMoveSpeed(20);
//		matOpaque.getAdditionalRenderState().setWireframe(true);
	}

}
