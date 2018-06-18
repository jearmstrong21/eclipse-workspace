package co.megadodo.terraingen1;

public class Block {
	
	public String name,mesh,mat;
	public AtlasPosition xmi,xpl,ymi,ypl,zmi,zpl;
	
	public static boolean isAVisibleToB(Block a, Block b) {
		if(a==null)return true;
		if(b==null)return true;
		boolean aTransp = a.mat.equals("Transp");
		boolean bTransp = b.mat.equals("Transp");
		if(aTransp==true&&bTransp==true)return false;
		if(aTransp)return true;
		if(bTransp)return true;
		return false;
	}

}
