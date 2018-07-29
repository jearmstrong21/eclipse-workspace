package co.megadodo.fluid2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Fluid2 extends JPanel implements MouseListener, MouseMotionListener {

	public static void main(String[] args) {
		new Fluid2();
	}
	
	JFrame frame;

	public Fluid2() {
		frame = new JFrame("Fluid 2");
		frame.setSize(900, 900);
		frame.add(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		frame.setVisible(true);
		initSim();
	}

	class Vec2 {
		float x, y;

		Vec2(float x, float y) {
			this.x = x;
			this.y = y;
		}

		Vec2 normalize() {
			float m = mag();
			return new Vec2(x / m, y / m);
		}

		float mag() {
			return (float) Math.sqrt(x * x + y * y);
		}

		Vec2 sub(Vec2 other) {
			return new Vec2(x - other.x, y - other.y);
		}

		Vec2 add(Vec2 o) {
			return new Vec2(x + o.x, y + o.y);
		}

		float squaredMag() {
			return x * x + y * y;
		}

		Vec2 mult(float f) {
			return new Vec2(f * x, f * y);
		}
	}

	class Particle {
		Vec2 x, v, f;
		float rho, p;
		int gridX,gridY;
		void computeGridPos() {
			gridX=(int)(x.x/cellW);
			gridY=(int)(x.y/cellH);
		}
	}

	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		stepSim();
		displaySim(g2d);
		repaint();
	}

	final Vec2 G = new Vec2(0.f, 12000 * 9.8f); // external (gravitational) forces
	final float REST_DENS = 1000.f; // rest density
	final float GAS_CONST = 2000.f; // const for equation of state
	final float H = 25.f; // kernel radius
	final float DISPLAY_SIZE=H;
	final float HSQ = H * H; // radius^2 for optimization
	final float MASS = 65.f; // assume all particles have the same mass
	final float VISC = 1500.f; // viscosity constant
	final float DT = 0.0002f; // integration timestep

	// smoothing kernels defined in Müller and their gradients
	final float POLY6 = 315.0f / (65.f * (float) Math.PI * (float) Math.pow(H, 9.f));
	final float SPIKY_GRAD = -45.f / ((float) Math.PI * (float) Math.pow(H, 6.f));
	final float VISC_LAP = 45.f / ((float) Math.PI * (float) Math.pow(H, 6.f));
	
	// simulation parameters
	final float EPS = 40; // boundary epsilon
	final float BOUND_DAMPING = -0.5f;

	ArrayList<Particle> particles;

	public void initSim() {
		gridW=(int)(frame.getWidth()/H);
		gridH=(int)(frame.getHeight()/H);
		cellW=frame.getWidth()/gridW;
		cellH=frame.getHeight()/gridH;
		grid=new Box[gridW][gridH];
		particles = new ArrayList<Particle>();
		for(int i=0;i<1000;i++)addParticle(frame.getWidth()/2+rand(-100,100),frame.getHeight()/2+rand(-100,100));
//		for(int x=0;x<100;x++) {
//			for(int y=0;y<15;y++) {
//				addParticle(map(x,0,100,100,frame.getWidth()-100),map(y,0,15,100,frame.getHeight()-100));
//			}
//		}
		initializing=false;
	}//add gridding
	boolean initializing=true;
	
	float lerp(float t,float a,float b) {
		return a+(b-a)*t;
	}
	
	float norm(float t,float a,float b) {
		return (t-a)/(b-a);
	}
	
	float map(float t,float s1,float e1,float s2,float e2) {
		return lerp(norm(t,s1,e1),s2,e2);
	}

	float rand(float mi, float ma) {
		return (float) Math.random() * (ma - mi) + mi;
	}
	
	class Box{
		ArrayList<Particle>particles=new ArrayList<Particle>();
		void add(Particle p) {
			particles.add(p);
		}
	}
	
	Box[][]grid;
	int gridW;
	int gridH;
	int cellW;
	int cellH;
	
	public void stepSim() {
		if(initializing)return;
		for(int i=0;i<gridW;i++) {
			for(int j=0;j<gridH;j++) {
				grid[i][j]=new Box();
			}
		}
		for(Particle p:particles) {
			p.computeGridPos();
			int i=p.gridX;
			int j=p.gridY;
			int r=1;
			for(int x=i-r;x<=i+r;x++) {
				for(int y=j-r;y<=j+r;y++) {
					if(x<0||y<0||x>=gridW||y>=gridH)continue;
					grid[x][y].add(p);
				}
			}
		}
		//add gridding computation
		// ComputeDensityPressure()
		for (Particle pi : particles) {
			pi.rho = 0;
			for (Particle pj : grid[pi.gridX][pi.gridY].particles) {
				Vec2 rij = pi.x.sub(pj.x);
				float r2 = rij.squaredMag();
				if (r2 < HSQ) {
					pi.rho += MASS * POLY6 * (float) Math.pow(HSQ - r2, 3);
				}
			}
			pi.p = GAS_CONST * (pi.rho - REST_DENS);
		}

		// ComputeForces()
		for (Particle pi : particles) {
			Vec2 fpress = new Vec2(0, 0);
			Vec2 fvisc = new Vec2(0, 0);
			for (Particle pj : grid[pi.gridX][pi.gridY].particles) {
				if (pi == pj)
					continue;
				Vec2 rij = pj.x.sub(pi.x);
				float r = rij.mag();
				if (r < H) {
					fpress = fpress.sub(rij.normalize()
							.mult(MASS * (pi.p + pj.p) / (2.f * pj.rho) * SPIKY_GRAD * (float) Math.pow(H - r, 2.f)));
					fvisc = fvisc.add((pj.v.sub(pi.v)).mult(VISC * MASS / pj.rho * VISC_LAP * (H - r)));
				}
			}
	        Vec2 fgrav = G.mult(pi.rho);
	        pi.f = fpress.add(fvisc.add(fgrav));
		}
		// Integrate()
		for(Particle p:particles) {
			p.v=p.v.add(p.f.mult(DT/p.rho));
			p.x=p.x.add(p.v.mult(DT));
			if(p.x.x < EPS) {
				p.v.x*=BOUND_DAMPING;
				p.x.x=EPS;
			}
			if(p.x.x>frame.getWidth()-EPS) {
				p.v.x*=BOUND_DAMPING;
				p.x.x=frame.getWidth()-EPS;
			}
			if(p.x.y<EPS) {
				p.v.y*=BOUND_DAMPING;
				p.x.y=EPS;
			}
			if(p.x.y>frame.getHeight()-EPS) {
				p.v.y*=BOUND_DAMPING;
				p.x.y=frame.getHeight()-EPS;
			}
		}
	}

	float pi=(float)Math.PI;
	int frames=0;
	public void displaySim(Graphics2D g2d) {
		if(initializing)return;
		g2d.setColor(Color.WHITE);
		int r=(int)DISPLAY_SIZE;
		g2d.fillRect(0, 0, frame.getWidth(), frame.getHeight());
		for (Particle p : particles) {
//			float ang=(float)Math.atan2(p.v.y, p.v.x);
//			float ang=(p.rho*7500f)%360;
//			float ang=(p.p*10)%360;
//			float ang=p.p;
			float ang=(float)Math.toRadians(45);
//			h=255*(float)((Math.atan2(p.v.y, p.v.x)+Math.PI)/Math.PI/2.0f);
			g2d.setColor(Color.getHSBColor((ang+pi)/pi/2,1,1));
//			g2d.setColor(Color.WHITE);
			g2d.fillOval((int) p.x.x - r/2, (int) p.x.y - r/2, r, r);
		}
//		for(int x=0;x<gridW;x++) {
//			for(int y=0;y<gridH;y++) {
//				g2d.setColor(Color.BLACK);
//				g2d.drawRect(x*cellW, y*cellH, cellW, cellH);
//			}
//		}
		frames++;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		for(Particle p:particles) {
			p.x.x=rand(0,100);
			p.x.y=rand(0,frame.getHeight());
			p.v=new Vec2(0,0);
			p.f=new Vec2(0,0);
			p.rho=0;
			p.p=0;
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
//		if(frames%2==0) {
		addParticle(e.getX(),e.getY());
//		}
	}
	void addParticle(float x,float y) {
		Particle p=new Particle();
		p.x=new Vec2(x+rand(-30,30),y+rand(-30,30));
		p.v=new Vec2(0,0);
		p.f=new Vec2(0,0);
		p.rho=0;
		p.p=0;
		particles.add(p);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
