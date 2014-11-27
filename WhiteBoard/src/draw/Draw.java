package draw;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Vector;

import javax.swing.JPanel;

import object.Client;


public class Draw extends JPanel{
	private  int mark = 0;//1=line 2=rect 3=circle 4=random
	private  int X1 = 0;
	private  int Y1 = 0;
	private  int X2 = 0;
	private  int Y2 = 0;//position
	private boolean isDrag = true;
	private boolean isMyselfSend = false;
	public static Color color = new Color(200,0,0);
	/*vector saves points*/
	private Vector<Point> lineStart = new  Vector<Point>();
	private Vector<Point> lineEnd = new  Vector<Point>();
	private Vector<Point> rectStart = new  Vector<Point>();
	private Vector<Point> rectEnd = new  Vector<Point>();
	private Vector<Point> circleStart = new  Vector<Point>();
	private Vector<Point> circleEnd = new  Vector<Point>();
	private Vector<Point> points = new  Vector<Point>();
	private Client talk = new Client();
	
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public Draw() {
		super();
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {	
				X1 = e.getX();
				Y1 = e.getY();
				talk.sendP("mark="+mark+" 1 "+X1+" "+Y1);//mark=_  1/2  x  y
				isMyselfSend = true;
			}
			public void mouseReleased(MouseEvent e){
				X2 = e.getX();
				Y2 = e.getY();
				if(mark == 1){
					lineStart.add(new Point(X1,Y1));
					lineEnd.add(new Point(X2,Y2));
					talk.sendP("mark="+mark+" 2 "+X2+" "+Y2);//mark=_  1/2  x  y
					isMyselfSend = true;
				}
				if(mark ==2 ){
					rectStart.add(new Point(X1,Y1));
					rectEnd.add(new Point(X2,Y2));
					talk.sendP("mark="+mark+" 2 "+X2+" "+Y2);//mark=_  1/2  x  y
					isMyselfSend = true;
				}else if(mark ==3){
					circleStart.add(new Point(X1,Y1));
					circleEnd.add(new Point(X2,Y2));
					talk.sendP("mark="+mark+" 2 "+X2+" "+Y2);//mark=_  1/2  x  y
					isMyselfSend = true;
				}
				if(mark == 4){
					talk.sendP("mark="+mark+" 3 "+X2+" "+Y2);
					isMyselfSend = true;
					points.add(new Point(X2,Y2));
					points.add(new Point(X2,Y2));
				}
				isDrag = true;
				repaint();
			}
			
		});
		addMouseMotionListener(new MouseMotionAdapter(){
			public void mouseDragged(MouseEvent e) {
				isDrag = false;
				X2 = e.getX();
				Y2 = e.getY();
				//time = (time+1)%5;
				//talk.sendP("mark="+5+" 2 "+X2+" "+Y2);//mark=_  1/2  x  y
				if(mark == 4 ){
					points.add(new Point(X2,Y2));
					talk.sendP("mark="+mark+" 2 "+X2+" "+Y2);//mark=_  1/2  x  y
					isMyselfSend = true;
				}
				repaint();
			}
		});
		Thread thread = new Thread(){
			public void run() {
				repaint();
			}
		};
		thread.start();
		
	}
	
	public void paint (Graphics g){
		Font f1 = new Font("TimesRoman",Font.BOLD,40);
		g.setColor(color);
		g.setFont(f1);
		for (int i = 0; i < lineStart.size(); i++) {
			g.drawLine(lineStart.get(i).x,lineStart.get(i).y, lineEnd.get(i).x,lineEnd.get(i).y);
		}
		for (int i = 0; i < rectStart.size(); i++) {
			g.drawRect(rectStart.get(i).x,rectStart.get(i).y, rectEnd.get(i).x-rectStart.get(i).x,rectEnd.get(i).y-rectStart.get(i).y);
		}
		for (int i = 0; i < circleStart.size(); i++) {
			g.drawOval(circleStart.get(i).x,circleStart.get(i).y, circleEnd.get(i).x-circleStart.get(i).x,circleEnd.get(i).y-circleStart.get(i).y);
		}
		for (int i = 0; i < points.size()-1; i++) {
			if(points.get(i).x == points.get(i+1).x && points.get(i).y == points.get(i+1).y){
				i+=2;
				continue;
			}
			g.drawLine(points.get(i).x, points.get(i).y, points.get(i+1).x, points.get(i+1).y);
		}
		if (!isDrag) 
		{
			if (mark==1) {
				g.drawLine(X1, Y1, X2, Y2);
			}else if (mark==2) {
				g.drawRect(X1, Y1, X2-X1, Y2-Y1);
			}else if (mark==3) {
				g.drawOval(X1, Y1, X2-X1, Y2-Y1);
			}
		}
		
	}
	public void clear() {
		lineStart.removeAllElements();
		lineEnd.removeAllElements();
		rectStart.removeAllElements();
		rectEnd.removeAllElements();
		circleStart.removeAllElements();
		circleEnd.removeAllElements();
		points.removeAllElements();
		talk.sendP("clear");
		isMyselfSend = true;
		repaint();
	}
	
	
}