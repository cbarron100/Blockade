package Board;

import java.util.*;
import Players.*;

public class Board{
	private String name;
	private boolean winner = false;
	private final int size = 17;
	private String[] gamePlayers = new String[4];
	private String[] colourOrder = new String[4];
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
		System.out.println("This game called " + this.name + " has started!");
		for(int x = 0; x < size; x++) {
			for(int y = 0; y < size; y++) {
				if(x%2 == 0) {
					if(x == 0 && y == 8) {
						board[x][y] = new Playable(x, y, "Gold");
					}else if(x == 2 && (y == 0 || y == 16)) {
						board[x][y] = new Playable(x, y, "White");
					}else if(x == 4 && y == 8) {
						board[x][y] = new Playable(x, y, "Black");
					}else if(x == 6 && (y == 6 || y == 10)) {
						board[x][y] = new Playable(x, y, "White");
					}else if(x == 8 && (y == 4 || y == 12)) {
						board[x][y] = new Playable(x, y, "White");
					}else if(x == 10 && (y == 2 || y == 14)) {
						board[x][y] = new Playable(x, y, "White");
					}else if(x == 12 && (y == 0 || y == 4 || y == 8 || y == 12 || y ==16)) {
						board[x][y] = new Playable(x, y, "white");
					}else if((x == 14 || x == 16) && (y == 2 || y == 6 || y == 10 || y == 14)) {
						switch(y) {
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
					if(x == 1 || x == 3) {
						board[x][y] = (y == 8) ? new Playable(x, y, "Black") : new Playable(x, y, "White");
					}else if(x == 5 && (y >= 6 && y <= 10)) {
						board[x][y] = (y == 8) ? new Playable(x, y, "Black") : new Playable(x, y, "White");
					}else if(x == 7 && (y >= 4 && y <= 12)) {
						board[x][y] = (y == 6 || y == 10) ? new Playable(x, y, "Black") : new Playable(x, y, "White");
					}else if(x == 9 && (y >= 2 && y <= 14)) {
						board[x][y] = new Playable(x, y, "White");
					}else if(x == 11) {
						board[x][y] = (y%4 == 0) ? new Playable(x, y, "Black") : new Playable(x, y, "White");
					}else if(x == 13) {
						board[x][y] = new Playable(x, y, "White");
					}else if(x == 15) {
						if(y >= 1 && y <= 3) {
							board[x][y] = new Playable(x, y, "Blue");
						}else if(y >= 5 && y <= 7) {
							board[x][y] = new Playable(x, y, "Green");
						}else if(y >= 9 && y <= 11) {
							board[x][y] = new Playable(x, y, "Yellow");
						}else if(y >= 13 && y <= 15) {
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
		if(current.getColour().equals(colour) && !current.getColour().equals(destination.getColour())){
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
					System.out.println("Where Would you like to move the block?");
					int blockX = scanner.nextInt();
					int blockY = scanner.nextInt();
					moveBlock(block, blockX, blockY);
					break;
				default:
					int destinationHomeX = destination.getOriginalX();
					int destinationHomeY = destination.getOriginalY();
					board[destinationHomeX][destinationHomeY] = destination;
					destination = current;
					board[x][y] = new Playable(x, y, "White");


			}

		}else{
			if(current.getColour().equals(destination.getColour())){
				System.out.println("You already have a colour there");
			}else{
				System.out.println("You may have chosen the wrong positon.");
			}
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



	public String printTeamOrder(){
		return Arrays.toString(this.colourOrder);
	}

	public int rollDice(){
		return random.nextInt(6-1+1)+1;
	}


	public void chooseColours(){
		int person = 0;
		Scanner input = new Scanner(System.in);
		System.out.println("To choose the colour you have to either type a letter or the number corresponding to the colour.");
		System.out.println("Blue: B or 1");
		System.out.println("Green: G or 2");
		System.out.println("Yellow: Y or 3");
		System.out.println("Red: R or 4");
		String perviousPlayer;
		for(int i = 0; i < this.gamePlayers.length; i++){
			boolean chosen = false;
			System.out.println(this.gamePlayers[i] + " please choose a team.");
			String choice = input.nextLine();
			if(choice.length() == 1){
				if(choice.equals("B") || choice.equals("1")){
					colourOrder[i] = "Blue";
				}else if(choice.equals("G") || choice.equals("2")){
					colourOrder[i] = "Green";
				}else if(choice.equals("Y") || choice.equals("3")){
					colourOrder[i] = "Yellow";
				}else if(choice.equals("R") || choice.equals("4")){
					colourOrder[i] = "Red";
				}else{
					System.out.println("Invalid input");
					i--;
				}
			}else{
				System.out.println("Invalid input");
				i--;
			}
		}

	}

	public void startGame(){
		this.printBoard();
		boolean finished = false; //winner qualifier
		int roller = 0;
		while(!finished){
			Scanner keyboard = new Scanner(System.in);
			if(roller == 5){
				roller = 0;
			}
			int roll = rollDice();
                        System.out.println(this.gamePlayers[roller] + " has to move " + roll + " spaces");
                        System.out.println("Which piece do you want to move? X axis first!");
                        int toMoveX = keyboard.nextInt();
			System.out.println("Which piece do you want to move? Y axis second!");
                        String toMoveYStr = keyboard.nextLine();
                        int toMoveY = alphabet.indexOf(toMoveYStr);
			if((toMoveX - 1 < 0 || toMoveX - 1 > size) && (toMoveY - 1 < 0 || toMoveY - 1 > size)){
				if(board[toMoveX-1][toMoveY-1].getColour().equals(colourOrder[roller])){
				 	System.out.println("Where would you like to move to?");
					int moveToX = keyboard.nextInt();
					String moveToYStr = keyboard.nextLine();
					int moveToY = alphabet.indexOf(moveToYStr);
					movePlayer(toMoveX-1, toMoveY-1, moveToX-1, moveToY-1, colourOrder[roller]);
				}else{
					System.out.println("This is not your team. You are team: " + colourOrder[roller]);
					roller--;
				}
				roller++;
			}else{
				System.out.println("Values are invalid");
			}
		}



	}


	public void printBoard(){
		String row = ConsoleColours.WHITE_UNDERLINED + " # | ";
		for(int num = 1; num <= board.length; num++){
			int mod = num%10;
			row += " " + mod + " ";
		}
		row += ConsoleColours.RESET;
		System.out.println(row);
		row = "";
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[0].length; j++){
				Playable item = board[i][j];
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
			System.out.println(" " + alphabet.substring(i, i+1).toUpperCase() + " | " + row);
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
		System.out.println(ConsoleColours.WHITE_UNDERLINED + "                                                            " + ConsoleColours.RESET);
		Scanner keyboard = new Scanner(System.in); // asking for player names
		System.out.println("Who is going to play?");
		String names1 = keyboard.nextLine();
		String names2 = keyboard.nextLine();
		String names3 = keyboard.nextLine();
		String names4 = keyboard.nextLine();
		System.out.println(ConsoleColours.WHITE_UNDERLINED + "                                                            " + ConsoleColours.RESET);
		System.out.println("Now, let's check the order!"); // setting order
		b.setOrder(new String[]{names1, names2, names3, names4});
		System.out.println(ConsoleColours.WHITE_UNDERLINED + "                                                            " + ConsoleColours.RESET);
		b.chooseColours(); //choose colours
		System.out.println("The players and their teams in the game are : " + b.printPlayerNames() + " " + b.printTeamOrder());
		System.out.println(ConsoleColours.WHITE_UNDERLINED + "                                                            " + ConsoleColours.RESET);
		System.out.println("Now we are able to start the game! Have fun!"); //wishing luck
		while(!b.hasWinner()){
			b.startGame();
		}
	}

}
