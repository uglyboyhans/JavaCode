package objects;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class FlyingObjects {
	protected int x;
	protected int y;
	protected int xSpeed;
	protected int ySpeed;
	protected int width;
	protected int height;
	protected BufferedImage image;;

	//constructor:
	public FlyingObjects(int x, int y, int xSpeed, int ySpeed, int width,
			int height) {
		super();
		this.x = x;
		this.y = y;
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.width = width;
		this.height = height;
	}
	//gets and sets:
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public double getxSpeed() {
		return xSpeed;
	}
	public void setxSpeed(int d) {
		this.xSpeed = d;
	}
	public double getySpeed() {
		return ySpeed;
	}
	public void setySpeed(int d) {
		this.ySpeed = d;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public BufferedImage getImage() {
		return image;
	}
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	//draw:
	public void draw(Graphics g) {
		g.drawImage(image, x, y, height, width, null);
	}
	//move:
	public void move(double d,double e){
		x=(int) (x+d);
		y=(int) (y-e);
	}

	//collide:
	public boolean collide(FlyingObjects another){
		int x=this.x;
		int y=this.y;
		int x1=another.x;
		int y1=another.y;
		int a=this.x+this.getWidth();
		int b=this.y+this.getHeight();
		int a1=another.x+another.getWidth();
		int b1=another.y+another.getHeight();
		if(a<x1||a1<x)
		{
			//肯定不相交
			return false;
		}
		if(b<y1||b1<y)
		{
			//肯定不相交
			return false;
		}
		
		return true;
	}	

}//end class
