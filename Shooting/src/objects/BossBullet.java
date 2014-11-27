package objects;

import java.io.IOException;

import javax.imageio.ImageIO;

public class BossBullet extends FlyingObjects{

	public BossBullet(int x, int y, int xSpeed, int ySpeed, int width, int height) {
		super(x, y, xSpeed, ySpeed, width, height);
		this.setBullet2Img();
		}

		private void setBullet2Img(){
			try {
				setImage(ImageIO.read(BossBullet.class
						.getResourceAsStream("/source/boss_bullet.png")));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
}
