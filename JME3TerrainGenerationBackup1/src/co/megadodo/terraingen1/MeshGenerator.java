package co.megadodo.terraingen1;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;

public class MeshGenerator {
	
	public static MeshData generateMeshBlock(BlockSideData bsdata, Block bl,Vector3f pos) {
		if(bl==null)return new MeshData();
		return generateMeshBlock(bsdata,bl.xmi,bl.xpl,bl.ymi,bl.ypl,bl.zmi,bl.zpl,pos);
	}
	
	static ColorRGBA col(float x) {
		return new ColorRGBA(x/16f,x/16f,x/16f,1);
	}
	
	public static MeshData generateMeshBlock(BlockSideData bsdata, AtlasPosition xmi,AtlasPosition xpl,AtlasPosition ymi,AtlasPosition ypl,AtlasPosition zmi,AtlasPosition zpl,Vector3f pos) {
		MeshData alldata = new MeshData();
		if(bsdata.xmi) {
			MeshData data = new MeshData();
			data.addVert(-0.5f, -0.5f, -0.5f);
			data.addVert(-0.5f, -0.5f,  0.5f);
			data.addVert(-0.5f,  0.5f, -0.5f);
			data.addVert(-0.5f,  0.5f,  0.5f);
			data.addTc(xmi._01);
			data.addTc(xmi._11);
			data.addTc(xmi._00);
			data.addTc(xmi._10);
			data.addTri(0, 1, 2);
			data.addTri(1, 3, 2);
			data.addNorm(-1f, 0f, 0f, 4);
//			data.addCol(col(ldata.xmi), 4);
			data.addCol(LightingManager.colorForPos(pos.add(new Vector3f(-1,0,0))),4);
			alldata.addData(data, new Vector3f(0,0,0));
		}
		if(bsdata.xpl) {
			MeshData data = new MeshData();
			data.addVert( 0.5f, -0.5f, -0.5f);
			data.addVert( 0.5f, -0.5f,  0.5f);
			data.addVert( 0.5f,  0.5f, -0.5f);
			data.addVert( 0.5f,  0.5f,  0.5f);
			data.addTc(xpl._01);
			data.addTc(xpl._11);
			data.addTc(xpl._00);
			data.addTc(xpl._10);
			data.addTri(2, 1, 0);
			data.addTri(2, 3, 1);
			data.addNorm(1f, 0f, 0f, 4);
//			data.addCol(col(ldata.xpl),4);
			data.addCol(LightingManager.colorForPos(pos.add(new Vector3f(1,0,0))),4);
			alldata.addData(data, new Vector3f(0,0,0));
		}
		if(bsdata.ymi) {
			MeshData data = new MeshData();
			data.addVert(-0.5f,-0.5f,-0.5f);
			data.addVert(-0.5f,-0.5f, 0.5f);
			data.addVert( 0.5f,-0.5f,-0.5f);
			data.addVert( 0.5f,-0.5f, 0.5f);
			data.addTc(ymi._01);
			data.addTc(ymi._11);
			data.addTc(ymi._00);
			data.addTc(ymi._10);
			data.addTri(2, 1, 0);
			data.addTri(2, 3, 1);
			data.addNorm(0f,-1f, 0f, 4);
//			data.addCol(col(ldata.ymi),4);
			data.addCol(LightingManager.colorForPos(pos.add(new Vector3f(0,-1,0))),4);
			alldata.addData(data, new Vector3f(0,0,0));
		}
		if(bsdata.ypl) {
			MeshData data = new MeshData();
			data.addVert(-0.5f, 0.5f,-0.5f);
			data.addVert(-0.5f, 0.5f, 0.5f);
			data.addVert( 0.5f, 0.5f,-0.5f);
			data.addVert( 0.5f, 0.5f, 0.5f);
			data.addTc(ypl._01);
			data.addTc(ypl._11);
			data.addTc(ypl._00);
			data.addTc(ypl._10);
			data.addTri(0, 1, 2);
			data.addTri(1, 3, 2);
			data.addNorm(0f, 1f, 0f, 4);
//			data.addCol(col(ldata.ypl),4);
			data.addCol(LightingManager.colorForPos(pos.add(new Vector3f(0,1,0))),4);
			alldata.addData(data, new Vector3f(0,0,0));
		}
		if(bsdata.zmi) {
			MeshData data = new MeshData();
			data.addVert(-0.5f,-0.5f,-0.5f);
			data.addVert(-0.5f, 0.5f,-0.5f);
			data.addVert( 0.5f,-0.5f,-0.5f);
			data.addVert( 0.5f, 0.5f,-0.5f);
			data.addTc(zmi._01);
			data.addTc(zmi._00);
			data.addTc(zmi._11);
			data.addTc(zmi._10);
			data.addTri(0, 1, 2);
			data.addTri(1, 3, 2);
			data.addNorm(0f,0f,-1f,4);
//			data.addCol(col(ldata.zmi),4);
			data.addCol(LightingManager.colorForPos(pos.add(new Vector3f(0,0,-1))),4);
			alldata.addData(data, new Vector3f(0,0,0));
		}
		if(bsdata.zpl) {
			MeshData data = new MeshData();
			data.addVert(-0.5f,-0.5f, 0.5f);
			data.addVert(-0.5f, 0.5f, 0.5f);
			data.addVert( 0.5f,-0.5f, 0.5f);
			data.addVert( 0.5f, 0.5f, 0.5f);
			data.addTc(zpl._01);
			data.addTc(zpl._00);
			data.addTc(zpl._11);
			data.addTc(zpl._10);
			data.addTri(2, 1, 0);
			data.addTri(2, 3, 1);
			data.addNorm(0f,0f, 1f,4);
//			data.addCol(col(ldata.zpl),4);
			data.addCol(LightingManager.colorForPos(pos.add(new Vector3f(0,0,-1))),4);
			alldata.addData(data, new Vector3f(0,0,0));
		}
		return alldata;
	}

}
