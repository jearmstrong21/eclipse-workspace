package test.pong;

public class PongRenderer {
	
	BallRenderer ball;
	
	PaddleRenderer paddle1,paddle2;
	
	public void init() {
		ball=new BallRenderer();
		ball.init();
		
		paddle1=new PaddleRenderer();
		paddle1.init();
		paddle2=new PaddleRenderer();
		paddle2.init();
	}
	
	public float ballX,ballY,ballSize,ballR,ballG,ballB;
	
	public float p1x,p1y,p1w,p1h,p1r,p1g,p1b;
	public float p2x,p2y,p2w,p2h,p2r,p2g,p2b;
	
	public void render() {
		ball.render(ballX, ballY, ballSize,ballR,ballG,ballB);
		paddle1.render(p1x,p1y,p1w,p1h,p1r,p1g,p1b);
		paddle2.render(p2x,p2y,p2w,p2h,p2r,p2g,p2b);
	}

}
