package co.megadodo.fluid1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Fluid1 extends JPanel implements MouseListener, MouseMotionListener {

	public static void main(String[] args) {
		new Fluid1();
	}
	
	JFrame frame;

	public Fluid1() {
		frame = new JFrame("Fluid 1");
		frame.setSize(1000, 1000);
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
	final float H = 20.f; // kernel radius
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
	final float EPS = 50; // boundary epsilon
	final float BOUND_DAMPING = -0.5f;

	ArrayList<Particle> particles;

	public void initSim() {
		particles = new ArrayList<Particle>();
		System.out.println(POLY6);
		System.out.println(SPIKY_GRAD);
		System.out.println(VISC_LAP);
//		System.out.printf
//		for (int i = 0; i < 300; i++) {
//			Particle p = new Particle();
//			p.x = new Vec2(rand(0, 500), rand(0, 500));
//			p.v = new Vec2(0, 0);
//			p.f = new Vec2(0, 0);
//			p.rho = 0;
//			p.p = 0;
//			particles.add(p);
//		}
//		for(int x=0;x<20;x++) {
//			for(int y=0;y<20;y++) {
//				float realX=x*10;
//				float realY=500-y*10;
//				Particle p=new Particle();
//				p.x=new Vec2(realX,realY);
//				p.v=new Vec2(0,0);
//				p.f=new Vec2(0,0);
//				p.rho=0;
//				p.p=0;
//				particles.add(p);
//			}
//		}
	}

	float rand(float mi, float ma) {
		return (float) Math.random() * (ma - mi) + mi;
	}

	public void stepSim() {
		// ComputeDensityPressure()
		for (Particle pi : particles) {
			pi.rho = 0;
			for (Particle pj : particles) {
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
			for (Particle pj : particles) {
				if (pi == pj)
					continue;
				Vec2 rij = pj.x.sub(pi.x);
				float r = rij.mag();
				if (r < H) {
					fpress = fpress.sub(rij.normalize()
							.mult(MASS * (pi.p + pj.p) / (2.f * pj.rho) * SPIKY_GRAD * (float) Math.pow(H - r, 2.f)));
					fvisc = fvisc.add((pj.v.sub(pi.v)).mult(VISC * MASS / pj.rho * VISC_LAP * (H - r)));
					if(pj.rho==0) {
						System.out.printf("fpress=%f,%f, fvisc=%f,%f, NaN/NaN=%f, NaN/0=%f, 0/NaN=%f, NaN/1=%f, 1/NAN=%f\n",fpress.x,fpress.y,fvisc.x,fvisc.y,(Float.NaN/Float.NaN),(Float.NaN)/0.0f,0.0f/Float.NaN,Float.NaN/1.0f,1.0f/Float.NaN);
					}
				}
			}
	        Vec2 fgrav = G.mult(pi.rho);
	        pi.f = fpress.add(fvisc.add(fgrav));
		}
		// Integrate()
		for(Particle p:particles) {
			p.v=p.v.add(p.f.mult(DT/p.rho));
			p.x=p.x.add(p.v.mult(DT));
			if(p.x.x-EPS < 0) {
				p.v.x*=BOUND_DAMPING;
				p.x.x=EPS;
			}
			if(p.x.x+EPS>frame.getWidth()) {
				p.v.x*=BOUND_DAMPING;
				p.x.x=frame.getWidth()-EPS;
			}
			if(p.x.y-EPS<0) {
				p.v.y*=BOUND_DAMPING;
				p.x.y=EPS;
			}
			if(p.x.y+EPS>frame.getHeight()) {
				p.v.y*=BOUND_DAMPING;
				p.x.y=frame.getHeight()-EPS;
			}
		}
	}

	float pi=(float)Math.PI;
	int frames=0;
	public void displaySim(Graphics2D g2d) {
		g2d.setColor(Color.BLACK);
		int r=(int)DISPLAY_SIZE;
		g2d.fillRect(0, 0, frame.getWidth(), frame.getHeight());
		for (Particle p : particles) {
//			float ang=(float)Math.atan2(p.v.y, p.v.x);
//			float ang=(p.rho*7500f)%360;
//			float ang=(p.p*10)%360;
			float ang=p.p;
//			h=255*(float)((Math.atan2(p.v.y, p.v.x)+Math.PI)/Math.PI/2.0f);
			g2d.setColor(Color.getHSBColor((ang+pi)/pi/2,1,1));
//			g2d.setColor(Color.WHITE);
			g2d.fillOval((int) p.x.x - r/2, (int) p.x.y - r/2, r, r);
		}
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
			p.x.y=rand(frame.getHeight()-300,frame.getHeight()-50);
			p.v=new Vec2(0,0);
			p.f=new Vec2(0,0);
			p.rho=0;
			p.p=0;
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
//		if(frames%2==0) {
			Particle p=new Particle();
			p.x=new Vec2(e.getX()+rand(-30,30),e.getY()+rand(-30,30));
			p.v=new Vec2(0,0);
			p.f=new Vec2(0,0);
			p.rho=0;
			p.p=0;
			particles.add(p);
//		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
