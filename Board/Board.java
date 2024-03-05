package Board;

import java.util.*;
import Players.*;

public class Board{
	private String name;
	private boolean winner = false;
	private final int size = 17;
	private String[] gamePlayers = new String[4];
	private Playable[][] board = new Playable[size][size];
	private Random random = new Random();
	public String alphabet = "abcdefghijklmnopqrstuvwxyz";
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
		System.out.println("This game is called " + this.name + " has started!");
		for(int x = 0; x < size; x++) {
			for(int y = 0; y < size; y++) {
				if(y%2 == 0) {
					if(y == 0 && x == 8) {
						board[x][y] = new Playable(x, y, "Gold");
					}else if(y == 2 && (x == 0 || x == 16)) {
						board[x][y] = new Playable(x, y, "White");
					}else if(y == 4 && x == 8) {
						board[x][y] = new Playable(x, y, "Black");
					}else if(y == 6 && (x == 6 || x == 10)) {
						board[x][y] = new Playable(x, y, "White");
					}else if(y == 8 && (x == 4 || x == 12)) {
						board[x][y] = new Playable(x, y, "White");
					}else if(y == 10 && (x == 2 || x == 14)) {
						board[x][y] = new Playable(x, y, "White");
					}else if(y == 12 && (x%4 == 0)) {
						board[x][y] = new Playable(x, y, "white");
					}else if((y == 14 || y == 16) && (x == 2 || x == 6 || x == 10 || x == 14)) {
						switch(x) {
						case 2:
							board[x][y] = new Playable(x, y, "Blue");
							break;
						case 6:
							board[x][y] = new Playable(x, y, "Green");
							break;
						case 10:
							board[x][y] = new Playable(x, y, "Yellow");
							break;
						case 14:
							board[x][y] = new Playable(x, y, "Red");
							break;
						}
					}else{
						board[x][y] = new Playable(x, y, " ");
					}
				}else {
					if(y == 1 || y == 3) {
						board[x][y] = (x == 8) ? new Playable(x, y, "Black") : new Playable(x, y, "White");
					}else if(y == 5 && (x >= 6 && x <= 10)) {
						board[x][y] = (x == 8) ? new Playable(x, y, "Black") : new Playable(x, y, "White");
					}else if(y == 7 && (x >= 4 && x <= 12)) {
						board[x][y] = (x == 6 || x == 10) ? new Playable(x, y, "Black") : new Playable(x, y, "White");
					}else if(y == 9 && (x >= 2 && x <= 14)) {
						board[x][y] = new Playable(x, y, "White");
					}else if(y == 11) {
						board[x][y] = (x%4 == 0) ? new Playable(x, y, "Black") : new Playable(x, y, "White");
					}else if(y == 13) {
						board[x][y] = new Playable(x, y, "White");
					}else if(y == 15) {
						if(x >= 1 && x <= 3) {
							board[x][y] = new Playable(x, y, "Blue");
						}else if(x >= 5 && x <= 7) {
							board[x][y] = new Playable(x, y, "Green");
						}else if( x >= 9 && x <= 11) {
							board[x][y] = new Playable(x, y, "Yellow");
						}else if(x >= 13 && x <= 15) {
							board[x][y] = new Playable(x, y, "Red");
						}else{
							board[x][y] = new Playable(x, y, " ");
						}
					}else {
						board[x][y] = new Playable(x, y, " ");
					}
				}
			}
		}

	}


	public boolean movePlayer(int x, int y, int newX, int newY, String colour){
		if(x == newX && y == newY){
			return false;
		}
         // check if it is a legal move connot move to a nothing positon
         // check if the colour is the same, cannot move team piece on top of team piece
         // check if winning move
         // check if other colour is there
         // check if block is there
         // check if empty

		Playable current = board[x][y];
		Playable destination = board[newX][newY];
		if(current.getColour().equals(colour)){
			switch(destination.getColour()){
				case " ":
					System.out.println("Cannot move here, not part of the board!");
					break;
				case "White":
					//update the borad 
					current.setCurrentCoordinates(new int[]{newX, newY});
					board[newX][newY] = current;
					//clear old position
					board[x][y] = new Playable(x, y, "White");
					break;
				case current:
					System.out.println("You already have a player here!");
					break;
				case "Gold":
					// Win situation
					System.out.println("WOOOOHOOOOO!!!!!!!!");
					System.out.println("YOU WIN!!");
					//win positon set to the player
					destination = current;
					current.setCurrentCoordinates(new int[]{newX, newY});
					board[newX][newY] = current;
					//old position empty
					board[x][y] = new Playable(x, y, "White");
					winner = true;
					break;
				case "Black":
					Playable block = board[newX][newY];
					current.setCurrentCoordinates(new int[]{newX, newY});
					board[newX][newY] = current;
					board[x][y] = new Playable(x, y, "White");
					Scanner scanner = new Scanner(System.in);
					System.out.println("Where Would you like to move the block?")
					int blockX = scanner.nextInt();
					int blockY = scanner.nextInt();
					moveBlock(block, blockX, blockY);
					break;
				default:
					int desintationHomeX = destination.getOriginalX();
					int destinationHomeY = destination.getOrignialY();
					board[destinationHomeX][destinationHomeY] = destination;
					destination = current;
					board[x][y] = new Playable(x, y, "White");


			}

		}else{
			System.out.println("You may have chosen the wrong positon.");
		}
		return true; // move success

	}

	private void moveBlock(Playable block, int x, int y){
		int prevX = block.getCurrentX();
		int prevY = block.getCurrentY();
		if(board[x][y].getColour().equals("White")){
			board[x][y] = block;
			board[x][y].setCurrentCoordinates(new int[]{x, y});
		}else{
			System.out.println("Cannot place the block there!");
		}

	}

	public void setPlayerNames(String[] names){

		this.gamePlayers = names;

	}


	public void setOrder(String[] players){
		String[] newPlayerOrder = new String[4];
		ArrayList<Integer> preOrder = new ArrayList<Integer>();
		int index = 0;
		while(index < players.length){
			int roll = rollDice();
			System.out.println(players[index] + " Rolled: " + roll);
			if(preOrder.contains(roll)){
				System.out.println(players[index] + " has to roll again! The number was already rolled");
				index--;

			}else{
				preOrder.add(roll);

			}
			index++;
		}
		ArrayList<Integer> order = new ArrayList<Integer>(preOrder);
		Collections.sort(order, Collections.reverseOrder());
		int newIndex = 0;
		for(int i : order){
			int preIndex = preOrder.indexOf(i);
			newPlayerOrder[newIndex] = players[preIndex];
			newIndex++;
		}
		setPlayerNames(newPlayerOrder);
	}


	public boolean hasWinner(){
		return winner;
	}
	public String[] getPlayerNames(){
		return this.gamePlayers;
	}


	public String printPlayerNames(){

		return Arrays.toString(this.gamePlayers);

	}


	public int rollDice(){
		return random.nextInt(6-1+1)+1;
	}

	public void messagesToMovePiece(){
		boolean finished = false;
		while(!finished){
			int roll = b.rollDice();
                        System.out.println(players[personRoll] + " has to move " + roll +>
                        System.out.println("Which piece do you want to move?");
                        int toMoveX = keyboard.nextInt();
                        String toMoveY = keyboard.nextLine();
                        System.out.print
		}


	}

	public void printBoard(){
		String total = "";
		String row = "";
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[0].length; j++){
				Playable item = board[j][i];
				switch(item.getColour()){
					case "White":
						row += " O ";
						break;
					case "Black":
						row += " # ";
						break;
					case "Blue":
						row += ConsoleColours.BLUE_BOLD + " B ";
						break;
					case "Green":
						row += ConsoleColours.GREEN_BOLD + " G ";
						break;
					case "Yellow":
						row += ConsoleColours.YELLOW_BOLD + " Y ";
						break;
					case "Red":
						row += ConsoleColours.RED_BOLD + " R ";
						break;
					case "Gold":
						row += ConsoleColours.WHITE_BOLD_BRIGHT + " $ ";
						break;
					default:
						row += "   ";
						break;

				}
			row += ConsoleColours.RESET;
			}
			System.out.println(row);
			row = "";
		}


	}
/*

	public boolean isEmpty(int x, int y){
		continue;
	}
*/
	public static void main(String[] args){
		Board b = new Board(args[0]); // initialising the game with a name
		b.printBoard(); // printing board so we know what it looks like
		System.out.println("------------------------------------------------------------");
		Scanner keyboard = new Scanner(System.in); // asking for player names
		System.out.println("Who is going to play?");
		String names1 = keyboard.nextLine();
		String names2 = keyboard.nextLine();
		String names3 = keyboard.nextLine();
		String names4 = keyboard.nextLine();
		System.out.println("Now, let's check the order!"); // setting order
		b.setOrder(new String[]{names1, names2, names3, names4});
		System.out.println("The players in the game are : " + b.printPlayerNames());
		System.out.println("Now we are able to start the game! Have fun!"); //wishing luck
		int personRoll = 0;
		String[] players = b.getPlayerNames();
		while(!b.hasWinner()){
			System.out.println("Person to roll is: " + players[personRoll]);
			System.out.println("Press enter to roll");
			String enter = keyboard.nextLine();
			if(!enter.isEmpty() || enter.equals(" ")){
				
			}
		}
	}

}
