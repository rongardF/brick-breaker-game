package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

import javax.swing.JPanel;

public class Gameplay extends JPanel implements KeyListener, ActionListener{
	
	// declear variables 
	private boolean play = false;
	private int score = 0;
	private int totalBricks = 21;
	
	private Timer timer;
	private int delay = 8;
	
	private int playerX = 310;
	
	private int ballPosX = 120;
	private int ballPosY = 350;
	private int ballXDir = -1;
	private int ballYDir = -2;
	
	private MapGenerator map;
	
	// define constructor method
	public Gameplay()
	{
		map = new MapGenerator(3,7); // draw map
		addKeyListener(this); // add keylistener for this class
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this); // create timer
		timer.start(); // start the timer (and register ActionListener)
	}
	
	public void paint(Graphics g)
	{
		// background
		g.setColor(Color.black);
		g.fillRect(1, 1, 692, 592);
		
		// drawing map
		map.draw((Graphics2D) g);
		
		// scores
		g.setColor(Color.white);
		g.setFont(new Font("serif",Font.BOLD,25));
		g.drawString(""+score, 590, 30);
		
		// borders
		g.setColor(Color.yellow);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(681, 0, 3, 692);
		
		// the paddle
		g.setColor(Color.green);
		g.fillRect(playerX, 550, 100, 8);
		
		// the ball
		g.setColor(Color.yellow);
		g.fillOval(ballPosX, ballPosY, 20, 20);
		
		if (totalBricks <= 0) {
			play = false;
			ballXDir = 0;
			ballYDir = 0;
			g.setColor(Color.RED);
			g.setFont(new Font("serif",Font.BOLD,40));
			g.drawString("You won! Score: "+score, 200, 300);
			
			g.setFont(new Font("serif",Font.BOLD,20));
			g.drawString("Press ENTER to restart", 230, 350);
		}
		
		if (ballPosY > 570) {
			play = false;
			ballXDir = 0;
			ballYDir = 0;
			g.setColor(Color.RED);
			g.setFont(new Font("serif",Font.BOLD,40));
			g.drawString("Game over, score: "+score, 190, 300);
			
			g.setFont(new Font("serif",Font.BOLD,20));
			g.drawString("Press ENTER to restart", 230, 350);
		}
		
		g.dispose();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) { // this method gets called after timer times out
		timer.start(); // restart the timer
		if (play) { // if game is ongoing
			if (new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) { // find out if ball intersects with the player bar
				ballYDir = - ballYDir; // if it did then ball starts to move in opposite direction
			}
			
			A : for (int i = 0; i < map.map.length; i++) {
				for (int j = 0; j < map.map[0].length; j++) {
					if(map.map[i][j] > 0) { // if brick is not destroyed then draw it
						int brickX = j * map.brickWidth + 80;
						int brickY = i * map.brickHeight + 50;
						int brickWidth = map.brickWidth;
						int brickHeight = map.brickHeight;
						
						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
						Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20, 20);
						Rectangle brickRect = rect;
						
						if(ballRect.intersects(brickRect)) {
							map.setBrickValue(0, i, j); // destroy this brick
							totalBricks--; 
							score += 5;
							
							// change the direction of the ball when bounces off a brick
							if (ballPosX + 19 <= brickRect.x || ballPosX + 1 >= brickRect.x + brickRect.width) {
								ballXDir = -ballXDir;
							} else {
								ballYDir = -ballYDir;
							}
							
							break A; // break both loops because ball can touch only one brick at a time
						}
					}
				}
			}
			
			ballPosX += ballXDir;
			ballPosY += ballYDir;
			// left wall will bounce ball in right direction
			if (ballPosX < 0) {   
				ballXDir = -ballXDir;
			}
			// upper wall will bounce the ball in a downwards direction
			if (ballPosY < 0) {
				ballYDir = -ballYDir;
			}
			// right wall will bounce the ball in left direction
			if (ballPosX > 670) {
				ballXDir = -ballXDir;
			}
		}
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		// register legal key events and define what happens when does events happen
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			if (playerX >= 600) // limit the right movement with the player bar
			{
				playerX = 600;
			} else { // just move right with the bar
				moveRight();
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) 
		{
			if (playerX < 10) // limit the left movement with the player bar
			{
				playerX = 10;
			} else { // move left with the player bar
				moveLeft();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_ENTER) { // if game over and gae has stopped, then enter restarts the game
			if (!play) {
				// reset all the variables for the new game
				play = true;
				ballPosX = 120;
				ballPosY = 350;
				ballXDir = -1;
				ballYDir = -2;
				playerX = 310;
				score = 0;
				totalBricks = 21;
				map = new MapGenerator(3,7);
				
				repaint(); // repaint must be called to draw the new game
			}
		}
	}
	
	// methods for moving the player bar to right and left
	public void moveRight() {
		play = true;
		playerX += 20;
	}
	
	public void moveLeft() {
		play = true;
		playerX -= 20;
	}
	
	@Override
	public void keyReleased(KeyEvent e) {}
	
}
