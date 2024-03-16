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
		b.recieveTurn();
		boolean interaction = b.mouseEnabled();
		String[] turnOrder = b.getColourOrder();
		String[] players = b.getPlayerNames();
		int othersRoll;
		while(true){
			int turn = b.getTurn();
			b.setTurnCompleteToFalse();
			System.out.println("---------------------------------");
			System.out.println("This board belongs to player #" + b.getPlayerID());
			System.out.println("Allowed to move: " + interaction);
			drawing.enableMouse(interaction);
			info.enableButtons(interaction);
			System.out.println("Player to move is: " + players[turn] + " with colour " + turnOrder[turn]);
			if(interaction){
				System.out.println("Roll dice and choose who to move");
				int rotation = 0;
				while(interaction){
					if(rotation == 0){
						System.out.println("Waiting to finish turn");
						rotation = 1;
					}
					b.recieveTurn();
					interaction = b.mouseEnabled();
				}
				b.setDiceRollToFalse();
				System.out.println("Turn complete");
				System.out.println("Next person to play: " + players[b.getTurn()]);
			}else{
				System.out.println("Cointainer Disabled, wating for others data");
				b.recieveDiceRoll();
				System.out.println("Others rolled dice");
				info.diceRollLabel(b.getPlayerTurn(), b.getDiceRoll());
				if(b.getDiceRoll() == -1){
					System.out.println("Player: " + b.getPlayerTurn() + " Skipped");
				}else{
					b.recieveingSelectedFromOther();
		                        drawing.repaint();
					b.recieveMoveCoordinates();
					drawing.repaint();
					// receive value for moving block
					if(b.getBlockIsMoving()){
						b.recieveBlockMovingCoordinatesFromOthers();
						System.out.println("Block Should be moved");
						drawing.repaint();
					}
					System.out.println("Next person to play: " + players[b.getTurn()]);
				}
				b.setDiceRollToFalse();
				b.recieveTurn();
				interaction = b.mouseEnabled();
			}
		}
	}
}
