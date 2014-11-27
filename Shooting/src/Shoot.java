import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import objects.Boss;
import objects.Start;


public class Shoot extends JPanel {

	Start shoot=new Start();
	public static void main(String[] args) {
		JFrame frame = new JFrame("                 hh's shoot");
		frame.setSize(400,550);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().add(new Shoot());
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	public Shoot() {
		super();
		initGUI();
		this.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent evt) {
				if(evt.getKeyCode()==KeyEvent.VK_LEFT)
					shoot.getHero().setxSpeed(-1);
				if(evt.getKeyCode()==KeyEvent.VK_RIGHT)
					shoot.getHero().setxSpeed(1);
				if(evt.getKeyCode()==KeyEvent.VK_UP)
					shoot.getHero().setySpeed(1);
				if(evt.getKeyCode()==KeyEvent.VK_DOWN)
					shoot.getHero().setySpeed(-1);
				if(evt.getKeyCode()==KeyEvent.VK_SPACE)
					shoot.shoot();
			}

			@Override
			public void keyReleased(KeyEvent evt) {
				if(evt.getKeyCode()==KeyEvent.VK_LEFT)
					shoot.getHero().setxSpeed(0);
				if(evt.getKeyCode()==KeyEvent.VK_RIGHT)
					shoot.getHero().setxSpeed(0);
				if(evt.getKeyCode()==KeyEvent.VK_UP)
					shoot.getHero().setySpeed(0);
				if(evt.getKeyCode()==KeyEvent.VK_DOWN)
					shoot.getHero().setySpeed(0);				
			}

			@Override
			public void keyTyped(KeyEvent evt) {
				// TODO Auto-generated method stub
				
			}
			
		});
		this.setFocusable(true);
		this.requestFocus();
		shoot.start();
	}
	
	private void initGUI() {
		try {
			setPreferredSize(new Dimension(400, 550));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void paint(Graphics g)
	{
		try {
			Image back=ImageIO.read(Shoot.class
					.getResourceAsStream("/source/back.png"));
			g.drawImage(back, 0, 0,getSize().width,getSize().height,this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//super.paint(g);
		shoot.draw(g);
		repaint(10);
	}

}

