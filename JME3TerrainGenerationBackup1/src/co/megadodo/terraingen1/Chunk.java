package co.megadodo.terraingen1;

import com.jme3.math.Vector3f;

public class Chunk {
	
	public final static int length=16,height=256;
	Block[][][] data;
	
	public Chunk() {
		data=new Block[16][256][16];
		for(int x=0;x<16;x++) {
			for(int y=0;y<256;y++) {
				for(int z=0;z<16;z++) {
					data[x][y][z]=null;
				}
			}
		}
	}
	
	public MeshData generateMesh(int cx,int cz,String mat, Vector3f offset, Chunk xmi,Chunk xpl,Chunk zmi,Chunk zpl) {
		MeshData alldata = new MeshData();
		
		for(int x=0;x<16;x++) {
			for(int y=0;y<256;y++) {
				for(int z=0;z<16;z++) {
					Block b = data[x][y][z];
					if(b==null)continue;
					if(!b.mat.equals(mat))continue;
					BlockSideData bsdata = sideData(x,y,z,xmi,xpl,zmi,zpl);
					MeshData mdata = MeshGenerator.generateMeshBlock(bsdata, b,new Vector3f(cx*16+x,y,cz*16+z));
					alldata.addData(mdata, new Vector3f(x,y,z));
				}
			}
		}
		
		MeshData offsetdata = new MeshData();
		offsetdata.addData(alldata, offset);
		return offsetdata;
	}
	
	public BlockSideData sideData(int x,int y,int z,Chunk xmi,Chunk xpl,Chunk zmi,Chunk zpl) {
		Block here = data[x][y][z];
		BlockSideData bsdata = new BlockSideData();
		bsdata.xmi = Block.isAVisibleToB(here, get(x-1,y,z,xmi,xpl,zmi,zpl));
		bsdata.xpl = Block.isAVisibleToB(here, get(x+1,y,z,xmi,xpl,zmi,zpl));
		bsdata.ymi = Block.isAVisibleToB(here, get(x,y-1,z,xmi,xpl,zmi,zpl));
		bsdata.ypl = Block.isAVisibleToB(here, get(x,y+1,z,xmi,xpl,zmi,zpl));
		bsdata.zmi = Block.isAVisibleToB(here, get(x,y,z-1,xmi,xpl,zmi,zpl));
		bsdata.zpl = Block.isAVisibleToB(here, get(x,y,z+1,xmi,xpl,zmi,zpl));
		return bsdata;
	}
	
	public Block get(int x, int y, int z, Chunk xmi,Chunk xpl,Chunk zmi,Chunk zpl) {
		if(y<0)return null;
		if(y>255)return null;
		if(x<0)return xmi.data[16+x][y][z];
		if(z<0)return zmi.data[x][y][16+z];
		if(x>15)return xpl.data[16-x][y][z];
		if(z>15)return zpl.data[x][y][16-z];
		return data[x][y][z];
	}

}
