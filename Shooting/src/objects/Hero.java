package objects;

import java.io.IOException;

import javax.imageio.ImageIO;

public class Hero extends FlyingObjects{

	public Hero(int x, int y, int xSpeed, int ySpeed, int width, int height) {
		super(x, y, xSpeed, ySpeed, width, height);
		this.setHeroImg();
	}

	private void setHeroImg(){
		try {
			setImage(ImageIO.read(Hero.class
					.getResourceAsStream("/source/hero.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
}
