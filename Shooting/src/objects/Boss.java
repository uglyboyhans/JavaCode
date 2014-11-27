package objects;

import java.io.IOException;
import javax.imageio.ImageIO;

public class Boss extends FlyingObjects {
    //constructor
	public Boss(int x, int y, int xSpeed, int ySpeed, int width, int height) {
		super(x, y, xSpeed, ySpeed, width, height);
		this.setBossImg();
	}
	private void setBossImg(){
	try {
		setImage(ImageIO.read(Boss.class
				.getResourceAsStream("/source/boss.png")));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
}
