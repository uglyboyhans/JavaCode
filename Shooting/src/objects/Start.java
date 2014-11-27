package objects;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JOptionPane;

public class Start extends Thread {
	private List<Boss> boss = new CopyOnWriteArrayList<Boss>();
	private List<Bullet> bullet = new CopyOnWriteArrayList<Bullet>();
	private List<BossBullet> boss_bullet = new CopyOnWriteArrayList<BossBullet>();
	private Hero hero = new Hero(200, 450, 0, 0, 31, 31);
	public int score = 0;

	public void run() {
		int time = 0;
		while (true) {
			time++;
			// 添加敌机
			addEnemy(time);
			// 敌机发射子弹
			enemiesShoot(time);
			// 敌机移动
			enemiesMove(time);
			// 子弹移动
			bulletsMove(time);
			// 我方子弹移动
			myBulletsMove(time);
			// 主角移动
			heroMove(time);
			// 检测敌机与子弹的碰撞
			collideWithMyBullets();
			// 检测主角与子弹的碰撞
			collideWithHero();
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	private void collideWithHero() {
		for (BossBullet bullet : boss_bullet) {
			if (hero.collide(bullet)) {

				JOptionPane.showMessageDialog(null, "score:          " + score);
				System.exit(0);

			}
		}
	}

	private void collideWithMyBullets() {
		List<Boss> removedBoss = new ArrayList<Boss>();
		for (Boss boss : boss) {
			for (Bullet bullet : bullet) {
				if (boss.collide(bullet)) {
					removedBoss.add(boss);
					score++;
					break;

				}
			}
		}
		boss.removeAll(removedBoss);
	}

	private void heroMove(int time) {
		if (time % 3 == 0)
			hero.move(hero.getxSpeed(), hero.getySpeed());
		if(hero.getX()<0){
			hero.setxSpeed(1);
		}
		if(hero.getY()<0){
			hero.setySpeed(-1);
		}
		if(hero.getX()>369){
			hero.setxSpeed(-1);
		}
		if(hero.getY()>519){
			hero.setySpeed(1);
		}
	}

	private void myBulletsMove(int time) {
		for (Bullet bullet : bullet) {
			if (time % 3 == 0)
				bullet.move(bullet.getxSpeed(), bullet.getySpeed());

		}
	}

	private void enemiesShoot(int time) {
		if (time % 1100 == 0) {
			for (Boss boss : boss) {
				BossBullet bbullet = new BossBullet(0, 0, 0, 0, 10, 4);
				bbullet.setX(boss.getX());
				bbullet.setY(boss.getY() + 10);
				bbullet.setySpeed(-1);
				Random r = new Random();
				int result = r.nextInt() % 3;
				bbullet.setxSpeed(result);

				boss_bullet.add(bbullet);
			}
		}
	}

	private void addEnemy(int time) {
		if (time % 1000 == 0) {
			Boss enemy = new Boss(0, 0, 0, 0, 31, 31);
			enemy.setY(0);
			enemy.setX((int) (Math.random() * 300));

			boss.add(enemy);
		}
	}

	private void bulletsMove(int time) {
		for (BossBullet bbullet : boss_bullet) {
			if (time % 5 == 0)
				bbullet.move(bbullet.getxSpeed(), bbullet.getySpeed());

		}
	}

	private void enemiesMove(int time) {
		for (Boss flo : boss) {
			if(time%100==0){
				Random r = new Random();
				int resulty = r.nextInt() % 2;
				if (resulty == 0)
					flo.setySpeed(-1);
				
				if (resulty == 1)
					flo.setySpeed(1);
				int resultx = r.nextInt() % 3;
				if (resultx == 0)
					flo.setxSpeed(-1);
				
				if (resulty == 1 || resulty == 2)
					flo.setxSpeed(1);
			}
			if (time % 5 == 0) {
				
				flo.move(flo.getxSpeed(), flo.getySpeed());
				if (flo.getX() < 0) {
					flo.setxSpeed(1);
				}
				if (flo.getY() < 0) {
					flo.setySpeed(-1);
				}
				if (flo.getX() > 400) {
					flo.setxSpeed(-1);
				}
			}
		}
	}

	public void draw(Graphics g) {
		for (Boss flo : boss) {
			flo.draw(g);
		}
		for (BossBullet bbullet : boss_bullet) {
			bbullet.draw(g);
		}
		for (Bullet bullet : bullet) {
			bullet.draw(g);
		}
		hero.draw(g);
	}

	public Hero getHero() {
		return hero;
	}

	public void shoot() {
		Bullet mybullet = new Bullet(0, 0, 0, 0, 10, 4);
		mybullet.setX(hero.getX() + 15);
		mybullet.setY(hero.getY() - 10);
		mybullet.setySpeed(1);

		bullet.add(mybullet);

	}

}
