package Display;

import java.util.*;
import Board.Board;
import javax.swing.*;
import java.awt.Color;
import javax.swing.JOptionPane;
public class GameDisplay{

	private static int size = 700;

	public static void main(String[] args){
		Board b = new Board("Game");
		JFrame frame = new JFrame(); // initialise the JFrame with the name as the input
		int id = b.getPlayerID();
		frame.setTitle("Barricade Game #" + id);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(250 + 17*30, 18*30);
		frame.setLayout(null);
		frame.setResizable(false);
		DrawingPanel drawing = new DrawingPanel(b);
		InfoPanel info = new InfoPanel(b);
		drawing.setBounds(250, 0, 17*30, 17*30);
		info.setBounds(0,0, 250, 17*30);
		drawing.repaint();
		/*
		JButton button1 = new JButton("Start Game");
		JButton button2 = new JButton("End Game");
		frame.getContentPane().add(button1); //adds button to the content pane
		frame.getContentPane().add(button2);
		*/
		frame.add(info);
		frame.add(drawing);
		frame.setVisible(true);
		String name = JOptionPane.showInputDialog(frame, "What is your name?", null);
		b.connectToServer();
		if(!name.equals("") && b.canChooseColours()){
			b.setBoardOwner(name);
			String colourChoice = JOptionPane.showInputDialog(frame, "What colour do you want?", null);
			System.out.println(name + " chose the colour " + colourChoice);
			if(!colourChoice.equals(null)){
				drawing.repaint();
			}
		}

	}
}
