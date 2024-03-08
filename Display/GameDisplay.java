package Display;

import java.util.*;
import Board.Board;
import javax.swing.*;



public class GameDisplay{

	private static int size = 700;

	public static void main(String[] args){

		JFrame frame = new JFrame(args[0]); // initialise the JFrame with the name as the input
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(size, size);
		/*
		JButton button1 = new JButton("Start Game");
		JButton button2 = new JButton("End Game");
		frame.getContentPane().add(button1); //adds button to the content pane
		frame.getContentPane().add(button2);
		*/
		frame.add(new DrawingPanel());
		frame.setVisible(true);


	}
}
