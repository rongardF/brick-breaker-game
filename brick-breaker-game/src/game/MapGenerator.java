package game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class MapGenerator {
	public int map[][];
	public int brickWidth;
	public int brickHeight;
	
	// method generates a map for the bricks
	public MapGenerator(int row, int col) {
		map = new int[row][col]; // two dimensional array
		for(int i=0; i< map.length; i++) {
			for(int j=0; j< map[0].length; j++) {
				map[i][j] = 1;  // 1 indicates that brick has to be shown, 0 indicates that it is not shown
			}
		}
		brickWidth = 540/col;
		brickHeight = 150/row;
	}

	public void draw(Graphics2D g) {
		for(int i=0; i< map.length; i++) {
			for(int j=0; j< map[0].length; j++) {
				if (map[i][j] == 1) {
					// draw the bricks
					g.setColor(Color.white);
					g.fillRect(j*brickWidth + 80, i*brickHeight + 50, brickWidth, brickHeight);
					
					g.setStroke(new BasicStroke(3));
					g.setColor(Color.black);
					g.drawRect(j*brickWidth + 80, i*brickHeight + 50, brickWidth, brickHeight);
				}
			}
		}
	}
	
	// method that provides a way to detsroy a brick
	public void setBrickValue(int value, int row, int col) {
		map[row][col] = value;
	}
}
