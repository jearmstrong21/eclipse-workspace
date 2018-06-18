package co.megadodo.terraingen1;

import java.util.ArrayList;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.util.BufferUtils;

public class MeshData {
	
	public ArrayList<Vector3f> verts;
	public ArrayList<Vector3f> norms;
	public ArrayList<Vector2f> tcs;
	public ArrayList<ColorRGBA> cols;
	public ArrayList<Integer> tris;
	
	public MeshData() {
		verts = new ArrayList<>();
		tcs   = new ArrayList<>();
		tris  = new ArrayList<>();
		norms = new ArrayList<>();
		cols  = new ArrayList<>();
	}
	
	public void addData(MeshData other, Vector3f pos) {
		int numVerts = verts.size();
		verts.addAll(Utils.addVecToList(other.verts, pos));
		tcs.addAll(other.tcs);
		norms.addAll(other.norms);
		tris.addAll(Utils.addNumToList(other.tris, numVerts));
		cols.addAll(other.cols);
	}
	
	public Mesh makeMesh() {
		Mesh mesh = new Mesh();
		mesh.setBuffer(Type.Position, 3, Utils.vec3ToFloats(verts));
		mesh.setBuffer(Type.TexCoord, 2, Utils.vec2ToFloats(tcs));
		mesh.setBuffer(Type.Normal,3,Utils.vec3ToFloats(norms));
		mesh.setBuffer(Type.Index,3,Utils.listToArrayInt(tris));
		ColorRGBA[]colsar=new ColorRGBA[cols.size()];
		for(int i=0;i<colsar.length;i++) {
			colsar[i]=cols.get(i);
		}
		mesh.setBuffer(Type.Color,4,BufferUtils.createFloatBuffer(colsar));
		return mesh;
	}
	
	public void addNorm(float x, float y, float z) {
		norms.add(new Vector3f(x,y,z));
	}
	
	public void addNorm(float x, float y, float z, int num) {
		for(int a=0;a<num;a++) {
			addNorm(x,y,z);
		}
	}
	
	public void addCol(ColorRGBA c) {
		cols.add(c);
	}
	
	public void addCol(ColorRGBA c, int num) {
		for(int a=0;a<num;a++) {
			addCol(c);
		}
	}
	
	public void addVert(float x, float y, float z) {
		verts.add(new Vector3f(x,y,z));
	}
	
	public void addTc(float x, float y) {
		addTc(new Vector2f(x,y));
	}
	
	public void addTc(Vector2f tc) {
		tcs.add(tc);
	}
	
	public void addTri(int a, int b, int c) {
		tris.add(a);
		tris.add(b);
		tris.add(c);
	}

}
