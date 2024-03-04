package Board;

import java.util.*;
import Players.*;

public class Board{
	private String name;
	private final int size = 17;

	private String[][] board = new String[size][size];
/*
	public enum contains{RED, BLUE, BLOCK, GREEN, YELLOW, EMPTY, NOTHING, GOAL};
	private String[][] board = new String[size][size];

	private final ArrayList<redCircle> reds = new ArrayList<>();
	private final ArrayList<blueCircle> blues = new ArrayList<>();
	private final ArrayList<yellowCircle> yellows = new ArrayList<>();
	private final ArrayList<greenCircle> greens = new ArrayList<>();
	private final ArrayList<blockadeCircles> blockades = new ArrayList<>();
	private final ArrayList<emptyCircles> empties = new ArrayList<>();
	private ArrayList<Graphics> redGraph, yellowGraph, blueGraph, greenGraph, blockGraph;
*/
	public Board(String name) {
		this.name = name;
		System.out.println("This game is called " + this.name + "!");
		for(int y = 0; y < size; y++) {
			for(int x = 0; x < size; x++) {
				if(y%2 == 0) {
					if(y == 0 && x == 8) {
						board[x][y] = "Goal";
					}else if(y == 2 && (x == 0 || x == 16)) {
						board[x][y] = "Empty";
					}else if(y == 4 && x == 8) {
						board[x][y] = "Block";
					}else if(y == 6 && (x == 6 || x == 10)) {
						board[x][y] = "Empty";
					}else if(y == 8 && (x == 4 || x == 12)) {
						board[x][y] = "Empty";
					}else if(y == 10 && (x == 2 || x == 14)) {
						board[x][y] = "Empty";
					}else if(y == 12 && (x%4 == 0)) {
						board[x][y] = "Empty";
					}else if((y == 14 || y == 16) && (x == 2 || x == 6 || x == 10 || x == 14)) {
						switch(x) {
						case 2:
							board[x][y] = "Blue";
							break;
						case 6:
							board[x][y] = "Green";
							break;
						case 10:
							board[x][y] = "Yellow";
							break;
						case 14:
							board[x][y] = "Red";
							break;
						}
					}else{
						board[x][y] = "";
					}
				}else {
					if(y == 1 || y == 3) {
						board[x][y] = (x == 8) ? "Block" : "Empty";
					}else if(y == 5 && (x >= 6 && x <= 10)) {
						board[x][y] = (x == 8) ? "Block" : "Empty";
					}else if(y == 7 && (x >= 4 && x <= 12)) {
						board[x][y] = (x == 6 || x == 10) ? "Block" : "Empty";
					}else if(y == 9 && (x >= 2 && x <= 14)) {
						board[x][y] = "Empty";
					}else if(y == 11) {
						board[x][y] = (x%4 == 0) ? "Block" : "Empty";
					}else if(y == 13) {
						board[x][y] = "Empty";
					}else if(y == 15) {
						if(x >= 1 && x <= 3) {
							board[x][y] = "Blue";
						}else if(x >= 5 && x <= 7) {
							board[x][y] = "Green";
						}else if( x >= 9 && x <= 11) {
							board[x][y] = "Yellow";
						}else if(x >= 13 && x <= 15) {
							board[x][y] = "Red";
						}else{
							board[x][y] = "";
						}
					}else {
						board[x][y] = "";
					}
				}
			}
		}

	}


	public void printBoard(){
		String total = "";
		String row = "";
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[0].length; j++){
				String item = board[i][j];
				switch(item){
					case "Empty":
						row += "O";
						break;
					case "Block":
						row += "#";
						break;
					case "Blue":
						row += "B";
						break;
					case "Green":
						row += "G";
						break;
					case "Yellow":
						row += "Y";
						break;
					case "Red":
						row += "R";
						break;
					default:
						row += " ";
						break;

				}
			}
			System.out.println(row);
			row = "";
		}


	}



	public static void main(String[] args){
		Board b = new Board(args[0]);
		b.printBoard();
	}
}


