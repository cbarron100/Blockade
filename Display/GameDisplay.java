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
		b.connectToServer();

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

		frame.add(info);
		frame.add(drawing);
		frame.setVisible(true);


		String name = JOptionPane.showInputDialog(frame, "What is your name?", null);
		b.setBoardOwner(name);
		int waiting = 1;
		while(!b.canChooseColours()){
			if(waiting == 1){
				System.out.println("Waiting to choose colour");
				waiting = 0;
			}
		}
		String colourChoice = JOptionPane.showInputDialog(frame, "What colour do you want?", null);
		System.out.println(name + " chose the colour " + colourChoice);
		b.colourChosen(colourChoice);
		waiting = 1;
		while(Arrays.asList(b.getColourOrder()).contains(null)){
			b.recieveNamesColours();
			if(waiting == 1){
				System.out.println("Wating for the rest of the colours");
				waiting = 0;
			}
		}
		System.out.println("-------------------------------");
		System.out.println("Recieved all the names and colours! In order!");
		info.addAllParts();
		boolean mouseEn = b.mouseEnabled();
		while(true){
			System.out.println("Allowed to move: " + !mouseEn);
			drawing.enableMouse(mouseEn);
			if(mouseEn){
				System.out.println("Find away to disable everyone else's container");
			}else{
				System.out.println("Cointainer Disabled, wating for others data");
				b.recieveingSelectedFromOther();
	                        b.receiveMoveCoordinates();
			}
			mouseEn = b.mouseEnabled();
		}
	}
}
