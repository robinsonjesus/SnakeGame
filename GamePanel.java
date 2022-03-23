
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener{

    // Declare everythng that we are going to need for this program
	
	// Declare the screen
	static final int SCREEN_WIDTH = 600;	
	static final int SCREEN_HEIGHT = 600;		

	// How big do we want the objects in this game?
	static final int UNIT_SIZE = 25;
	
	// How objects we can actually fit on the screen
	static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / (UNIT_SIZE*UNIT_SIZE);
	
	// Create a delay for our timer	
	static final int DELAY = 75;
	
	
	// Create two arrays for the position of the parts of the body of the snake, including the head	
	final int x[] = new int[GAME_UNITS];
	final int y[] = new int[GAME_UNITS];
	
	int bodyParts = 6; // there is no because
	int applesEaten; // Apples that the snake has eaten
	
	int appleX; // The x coordinate of the apple when appear
	int appleY; //The y coordinate of the apple when appear
	
	/* The direction of the snake can be:
	 * R: RIGHT
	 * L: LEFT
	 * U: UP
	 * D: DOWN 
	 */
	
	char direction = 'R';
	boolean running = false;
	Timer timer;
	Random random;
	
	
	
	// Create a constructor		
	GamePanel() {
		
		// The first thing to do is create an instance of the random class
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}
	
	// Create a method named startGame	
	public void startGame() {
		
		newApple();
		running = true;
		// we pass "this" into the method because we want to use the action listener interface
		timer = new Timer(DELAY, this);
		timer.start();
		
	}
	
	// Create a method named paintComponents
	// Parameter Grpahics g, I do not know what this does for now
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		draw(g);
		
	}

	// Create a method named draw
	// Parameter Grpahics g, I do not know what this does for now
	
	public void draw(Graphics g) {
	
		if(running) {
			
			/*
			for(int i=0; i < SCREEN_HEIGHT/UNIT_SIZE; i++) {
				g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
				g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
			}
			*/
			// paint the apple
			g.setColor(Color.red);
			// shape the apple 
			// How high and wide the apple is?? >>> unit size!!
			g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
			
			for(int i = 0 ; i < bodyParts; i++) {
				if(i == 0) {
					g.setColor(Color.green);
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);				
				}				
				else {
					g.setColor(new Color(45, 180, 0));
					g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
			}
			g.setColor(Color.red);
			g.setFont(new Font("Ink Free", Font.BOLD, 40));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten))/2, g.getFont().getSize());
		}
		else {
			gameOver(g); // g is our graphics that we are receiving with this parameter
		}
		
	}
	
	public void newApple() {
		// I make sure to cast the division to avoid problems
		appleX = random.nextInt((int)(SCREEN_WIDTH / UNIT_SIZE))*UNIT_SIZE;
		appleY = random.nextInt((int)(SCREEN_HEIGHT / UNIT_SIZE))*UNIT_SIZE;
		// I divide by UNIT_SIZE and then multiply by UNIT_SIZE because I want apples to be distribuited evently
		
	}
	
	public void move() {
		
		// For loop to iterate over all the body parts of the snake
		for(int i=bodyParts; i>0; i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
			
		}
		
		switch(direction) {
		case 'U':
			y[0] = y[0] - UNIT_SIZE;
			break;
		}
		
		switch(direction) {
		case 'D':
			y[0] = y[0] + UNIT_SIZE;
			break;
		}
		
		switch(direction) {
		case 'L':
			x[0] = x[0] - UNIT_SIZE;
			break;
		}
		
		switch(direction) {
		case 'R':
			x[0] = x[0] + UNIT_SIZE;
			break;
		}
		
	}
	
	// Create a method for the action of eating apples, named checkApplr
	
	public void checkApple() {
		
		if((x[0] == appleX) && (y[0] == appleY)) {
			bodyParts++;
			applesEaten++;
			newApple();
		}
		
	}
	
	// Create a method for check the collisions
	
	public void checkCollisions() {
		// this checks if head collides with body
		for(int i = bodyParts; i>0; i--) {
			if((x[0] == x[i]) && (y[0] == y[i])) {
				running = false;
			}
			// check if head touches left border
			if(x[0] < 0) {
				running = false;
			}
			// check if head touches right border
			if(x[0] > SCREEN_WIDTH) {
				running = false;
			}
			// check if head touches top border
			if(y[0] < 0) {
				running = false;
			}
			// check if head touches bottom border border
			if(y[0] > SCREEN_HEIGHT) {
				running = false;
			}
			if(!running) {
				timer.stop();
			}
		}
		
	}	
	
	// create a game over method
	
	public void gameOver(Graphics g) {
		// Game Over text
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 75));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
		
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 40));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: " + applesEaten))/2, g.getFont().getSize());

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(running) {
			move();
			checkApple();
			checkCollisions();
				
		}
		
		repaint();
		
	}
	
	public class MyKeyAdapter extends KeyAdapter{
		
		// Indicate that we are overriding a method 
		@Override
		/*
		 * By this code below, I will be abble to control the snake. I use:
		 * + keyPressed, a method I imported
		 * + case KeyEvent.VK_XXX also a method imported, this is used to detect when I press a key
		 * + in every case I switch the variable direction used in move 
		*/
		
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction != 'R') {
					direction = 'L';
				}
				break; // don't forget to break after the case
				
			case KeyEvent.VK_RIGHT:
				if(direction != 'L') {
					direction = 'R';
				}
				break;
			
			case KeyEvent.VK_UP:
				if(direction != 'D') {
					direction = 'U';
				}
				break;
				
			case KeyEvent.VK_DOWN:
				if(direction != 'U') {
					direction = 'D';
				}
				break;
			
			}
			
		}
		
	}
	
}
