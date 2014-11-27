package objects;

import java.io.IOException;

import javax.imageio.ImageIO;

public class Bullet extends FlyingObjects{

	public Bullet(int x, int y, int xSpeed, int ySpeed, int width, int height) {
		super(x, y, xSpeed, ySpeed, width, height);
		this.setBulletImg();
		}

		private void setBulletImg(){
			try {
				setImage(ImageIO.read(Bullet.class
						.getResourceAsStream("/source/bullet.png")));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
}
