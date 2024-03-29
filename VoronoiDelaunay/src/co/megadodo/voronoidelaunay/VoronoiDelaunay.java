package co.megadodo.voronoidelaunay;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class VoronoiDelaunay extends JPanel implements MouseListener {

	public static void main(String[] args) {
		new VoronoiDelaunay();
	}

	public JFrame drawFrame;

	public JFrame uiFrame;

	
	public JTabbedPane tbpAll;
		public JPanel pnlDisplaySettings;
			public JPanel pnlPointSize;
				public JLabel lblPointSize;
				public JSlider sldPointSize;
//			public JPanel pnlPointCol;
//				public JLabel lblPointCol;
//				public JColorChooser ccPointCol;
			public JPanel pnlCCenterSize;
				public JLabel lblCCenterSize;
				public JSlider sldCCenterSize;
			public JPanel pnlDrawCCirc;
				public JLabel lblDrawCCirc;
				public JCheckBox cbxDrawCCirc;
//			public JPanel pnlCCenterCol;
//				public JLabel lblCCenterCol;
//				public JSlider sldCCenterCol;

	public VoronoiDelaunay() {
		drawFrame = new JFrame("Voronoi / Delaunay");
		drawFrame.setSize(1000, 1000);
		drawFrame.add(this);
		drawFrame.setVisible(true);
		this.addMouseListener(this);

		uiFrame = new JFrame("Voronoi / Delaunay");
		uiFrame.setSize(500, 500);
		uiFrame.setVisible(true);

			tbpAll = new JTabbedPane();
				pnlDisplaySettings = new JPanel();
				pnlDisplaySettings.setLayout(new GridLayout(2,1));
					pnlPointSize = new JPanel();
						lblPointSize = new JLabel("Point size: ");
						sldPointSize = new JSlider(0, 15, 3);
					pnlPointSize.add(lblPointSize);
					pnlPointSize.add(sldPointSize);
					
//					pnlPointCol = new JPanel();
//						lblPointCol = new JLabel("Point color: ");
//						ccPointCol = new JColorChooser(Color.BLACK);
//					pnlPointCol.add(lblPointCol);
//					pnlPointCol.add(ccPointCol);
//					
					pnlCCenterSize = new JPanel();
						lblCCenterSize = new JLabel("Circumcenter size: ");
						sldCCenterSize = new JSlider(0, 15, 0);
					pnlCCenterSize.add(lblCCenterSize);
					pnlCCenterSize.add(sldCCenterSize);

				pnlDisplaySettings.add(pnlPointSize);
				pnlDisplaySettings.add(pnlCCenterSize);
			tbpAll.addTab("Display settings", pnlDisplaySettings);
			
		uiFrame.add(tbpAll);

		File file= new File("Zoe1.png");
		  try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		addUIListeners();
		
		for(int i = 0; i < 2500; i++) {
			points.add(new Vector2f(rand(0,drawFrame.getWidth()),rand(0,drawFrame.getHeight())));
		}
		triangulate();
		repaint();
	}
	
	float lerp(float a,float b,float t) {
		return a+(b-a)*t;
	}
	float norm(float a,float b,float t) {
		return (t-a)/(b-a);
	}
	float lin_remap(float t, float s1,float e1,float s2,float e2) {
		return lerp(e2,s2,norm(s1,e1,t));
	}
	
	void triangulate() {
		tris.clear();
		tris.addAll(Triangulator.triangulation(points));
	}
	
	float rand(float mi, float ma) {
		return (float)(Math.random()*(ma-mi)+mi);
	}
	
	public void addUIListeners() {
		ChangeListener changeListener = new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				repaint();
			}
		};
		sldPointSize.addChangeListener(changeListener);
//		ccPointCol.addChange
		sldCCenterSize.addChangeListener(changeListener);
	}

	ArrayList<Vector2f> points = new ArrayList<Vector2f>();
	ArrayList<Triangle> tris = new ArrayList<Triangle>();

	Color getCol(BufferedImage image, int x, int y) { 
	  int clr=  image.getRGB(x,image.getHeight()-y); 
	  int  red   = (clr & 0x00ff0000) >> 16;
	  int  green = (clr & 0x0000ff00) >> 8;
	  int  blue  =  clr & 0x000000ff;
	  return new Color(red/255.0f,green/255.0f,blue/255.0f);
	}
	
	BufferedImage image;
	
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		  // Getting pixel color by position x and y
		  
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, drawFrame.getWidth(), drawFrame.getHeight());

		for (Triangle t : tris) {
			ArrayList<Vector2f> list = new ArrayList<Vector2f>();
			list.add(t.a);
			list.add(t.b);
			list.add(t.c);
			//use zoe image for drawing triangles
			boolean cont = false;
			for (Vector2f v : list) {
				if (Vector2f.isSame(v, Triangulator.bad1) || Vector2f.isSame(v, Triangulator.bad2)
						|| Vector2f.isSame(v, Triangulator.bad3)) {
					cont = true;
				}
			}
			if (cont)
				continue;

			g2d.setColor(Color.BLACK);
			Line.draw(g2d, t.a, t.b);
			Line.draw(g2d, t.b, t.c);
			Line.draw(g2d, t.a, t.c);

			 Vector2f cc = t.circumcenter();
			 float cr = t.circumrad();
			 
			 int cx = (int) cc.x;
			 int cy = (int) cc.y;
			 int icr = (int) cr;
			 
			 int displayCR = sldCCenterSize.getValue();
			 
			 g2d.setColor(Color.GREEN);
			 g2d.fillOval(cx - displayCR/2, cy - displayCR/2, displayCR, displayCR);

			// g2d.setColor(Color.RED);
			// Line b1 = Line.bisector(t.lineAB(),4);
			// Line b2 = Line.bisector(t.lineBC(),4);
			// Line b3 = Line.bisector(t.lineAC(),4);
			// Line.draw(g2d, b1);
			// Line.draw(g2d, b2);
			// Line.draw(g2d, b3);
			//
			// g2d.setColor(Color.MAGENTA);
			// int x = (int)cc.x;
			// int y = (int)cc.y;
			// int r = (int)cr;
			// g2d.drawOval(x - r, y - r, r * 2, r * 2);
			Color colA = getCol(image, (int)lin_remap(t.a.x,0,drawFrame.getWidth(),0,image.getWidth()),(int)lin_remap(t.a.y,0,drawFrame.getHeight(),0,image.getHeight()));
//			Color colB = getCol(image, (int)t.b.x,(int)t.b.y);
//			Color colC = getCol(image, (int)t.c.x,(int)t.c.y);
			Polygon poly = new Polygon(new int[] {(int)t.a.x,(int)t.b.x,(int)t.c.x},new int[] {(int)t.a.y,(int)t.b.y,(int)t.c.y} , 3);
			g2d.setColor(colA);
			g2d.fill(poly);
			g2d.draw(poly);
		}
		for (Vector2f p : points) {
			g2d.setColor(Color.BLACK);
			int r = sldPointSize.getValue();
			g2d.fillOval((int)p.x-r, (int)p.y-r, r*2, r*2);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
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
		points.add(new Vector2f(e.getX(), e.getY()));
		triangulate();
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}