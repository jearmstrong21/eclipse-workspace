package test.pong;

public class PongUpdater {
	
	public float p1x,p1y,p1w,p1h,p1r,p1g,p1b;
	public float p2x,p2y,p2w,p2h,p2r,p2g,p2b;
	
	public float bx,by,bvx,bvy,bsize,br,bg,bb;
	
	public float minx,miny,maxx,maxy;
	
	public void init() {
		bx=-0.1f;
		by=0;
		bvx=-1*0.01f;
		bvy=1*0.01f;
		bsize=0.05f;
		br=0;
		bg=1;
		bb=1;
		p1x=-0.8f;
		p1y=0;
		p1w=0.02f;
		p1h=0.25f;
		p1r=1;
		p1g=1;
		p1b=1;
		p2x=0.8f;
		p2y=0;
		p2w=0.02f;
		p2h=0.25f;
		p2r=1;
		p2g=1;
		p2b=1;
		
		minx=-0.9f;
		miny=-0.9f;
		maxx=0.9f;
		maxy=0.9f;
	}
	
	public void update(float mouseX,float mouseY) {
		p1y=mouseY;
		if(p1y<miny+p1h)p1y=miny+p1h;
		if(p1y>maxy-p1h)p1y=maxy-p1h;
		
		p2y=by;
		if(p2y<miny+p2h)p2y=miny+p2h;
		if(p2y>maxy-p2h)p2y=maxy-p2h;
		
		bx+=bvx;
		by+=bvy;
		if(bx<minx+bsize||bx>maxx-bsize)bvx*=-1;
		if(by<miny+bsize||by>maxy-bsize)bvy*=-1;
		
		if(ballCollidesX(p1x,p1y,p1w,p1h)||ballCollidesX(p2x,p2y,p2w,p2h))bvx*=-1;
		if(ballCollidesY(p1x,p1y,p1w,p1h)||ballCollidesY(p2x,p2y,p2w,p2h))bvy*=-1;
	}
	
	public boolean ballCollidesX(float px,float py,float pw,float ph) {
		return false;
	}
	
	public boolean ballCollidesY(float px,float py,float pw,float ph) {
		return false;
	}
	
	public boolean pointInside(float x,float y,float px,float py,float pw,float ph) {
		return x>px-pw&&x<px+pw&&y>py-ph&&y<py+ph;
	}

}
