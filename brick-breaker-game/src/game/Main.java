package game;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		JFrame obj = new JFrame();
		Gameplay gamePlay = new Gameplay();
		
		obj.setBounds(10, 10, 700, 600); // decleare window size
		obj.setTitle("Breakout Ball"); // Name of the window
		obj.setResizable(false); // not resizable
		obj.setVisible(true); // window is visible by default
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // when pressed X then window will close
		
		obj.add(gamePlay);  // add JPanel to JFrame object
	}

}
