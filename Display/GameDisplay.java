package Display;

import java.util.*;
import Board.Board;
import javax.swing.*;
import Board.Board;


public class GameDisplay{

	private static int size = 700;

	public static void main(String[] args){
		Board b = new Board("Game");
		JFrame frame = new JFrame(); // initialise the JFrame with the name as the input
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(size, size);
		/*
		JButton button1 = new JButton("Start Game");
		JButton button2 = new JButton("End Game");
		frame.getContentPane().add(button1); //adds button to the content pane
		frame.getContentPane().add(button2);
		*/
		frame.setBackground(Color.BLACK);
		frame.add(new DrawingPanel(b));
		frame.setVisible(true);


	}
}
