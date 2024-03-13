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
	private Playable[][] previousState = new Playable[size][size];
	private Random random = new Random();
	public String alphabet = "abcdefghijklmnopqrstuvwxyz";
	private Scanner keyboard = new Scanner(System.in);
	private int[][] gates = new int[][]{{13, 2}, {13, 6}, {13, 10}, {13, 14}};
	private int turn = 0;
	private String colourToMove; // this is to keep track of the colour that is about to move, we change the colour on the board to see which has been selected
	private boolean blockToMove = false;
	private Playable blockMoving;
	private int rolledNumber; // keeping track of rolled number just in case a 6 is rolled


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
						this.board[x][y] = new Playable(x, y, "Gold");
					}else if(x == 2 && (y == 0 || y == 16)) {
						this.board[x][y] = new Playable(x, y, "White");
					}else if(x == 4 && y == 8) {
						this.board[x][y] = new Playable(x, y, "Black");
					}else if(x == 6 && (y == 6 || y == 10)) {
						this.board[x][y] = new Playable(x, y, "White");
					}else if(x == 8 && (y == 4 || y == 12)) {
						this.board[x][y] = new Playable(x, y, "White");
					}else if(x == 10 && (y == 2 || y == 14)) {
						this.board[x][y] = new Playable(x, y, "White");
					}else if(x == 12 && (y == 0 || y == 4 || y == 8 || y == 12 || y == 16)) {
						this.board[x][y] = new Playable(x, y, "White");
					}else if((x == 14 || x == 16) && (y == 2 || y == 6 || y == 10 || y == 14)) {
						switch(y) {
						case 2:
							this.board[x][y] = new Playable(x, y, "Blue");
							break;
						case 6:
							this.board[x][y] = new Playable(x, y, "Green");
							break;
						case 10:
							this.board[x][y] = new Playable(x, y, "Yellow");
							break;
						case 14:
							this.board[x][y] = new Playable(x, y, "Red");
							break;
						}
					}else{
						this.board[x][y] = new Playable(x, y, " ");
					}
				}else {
					if(x == 1 || x == 3) {
						this.board[x][y] = (y == 8) ? new Playable(x, y, "Black") : new Playable(x, y, "White");
					}else if(x == 5 && (y >= 6 && y <= 10)) {
						this.board[x][y] = (y == 8) ? new Playable(x, y, "Black") : new Playable(x, y, "White");
					}else if(x == 7 && (y >= 4 && y <= 12)) {
						this.board[x][y] = (y == 6 || y == 10) ? new Playable(x, y, "Black") : new Playable(x, y, "White");
					}else if(x == 9 && (y >= 2 && y <= 14)) {
						this.board[x][y] = new Playable(x, y, "White");
					}else if(x == 11) {
						this.board[x][y] = (y%4 == 0) ? new Playable(x, y, "Black") : new Playable(x, y, "White");
					}else if(x == 13) {
						this.board[x][y] = new Playable(x, y, "White");
					}else if(x == 15) {
						if(y >= 1 && y <= 3) {
							this.board[x][y] = new Playable(x, y, "Blue");
						}else if(y >= 5 && y <= 7) {
							this.board[x][y] = new Playable(x, y, "Green");
						}else if(y >= 9 && y <= 11) {
							this.board[x][y] = new Playable(x, y, "Yellow");
						}else if(y >= 13 && y <= 15) {
							this.board[x][y] = new Playable(x, y, "Red");
						}else{
							this.board[x][y] = new Playable(x, y, " ");
						}
					}else {
						this.board[x][y] = new Playable(x, y, " ");
					}
				}
			}
		}
		setPreviousGameStateToCurrent();
	}

	public int getSize(){
		return this.size;
	}
	public Playable[][] getBoard(){
		return this.board;
	}
	public boolean movePlayer(int x, int y, int newX, int newY){
		if(x == newX && y == newY){
			return false;
		}
         // check if it is a legal move connot move to a nothing positon
         // check if the colour is the same, cannot move team piece on top of team piece
         // check if winning move
         // check if other colour is there
         // check if block is there
         // check if empty
		System.out.println("The person who is moving is " + this.gamePlayers[this.turn]);
		System.out.println("The colour this person has is " + this.colourOrder[this.turn]);
		Playable current = this.board[x][y];
		Playable destination = this.board[newX][newY];
		if(this.colourToMove.equals(this.colourOrder[this.turn]) && !current.getColour().equals(destination.getColour())){
			setPreviousGameStateToCurrent();
			switch(destination.getColour()){
				case " ":
					System.out.println("Cannot move here, not part of the board!");
					break;
				case "White":
					//update the borad 
					current.setCurrentCoordinates(new int[]{newX, newY});
					board[newX][newY] = current;
					board[newX][newY].setColour(this.colourToMove);
					//clear old position
					board[x][y] = new Playable(x, y, "White");
					break;
				case "Gold":
					// Win situation
					//win positon set to the player
					current.setCurrentCoordinates(new int[]{newX, newY});
					board[newX][newY] = current;
					//old position empty
					board[x][y] = new Playable(x, y, "White");
					this.winner = true;
					System.out.println("WOOOOHOOOOO!!!!!!!!");
                                        System.out.println("YOU WIN!!");
					break;
				case "Black":
					this.blockMoving = board[newX][newY];
					current.setCurrentCoordinates(new int[]{newX, newY});
					board[newX][newY] = current;
					board[newX][newY].setColour(this.colourToMove);
					board[x][y] = new Playable(x, y, "White");
					this.blockToMove = true;


					//System.out.println("Where Would you like to move the block?");
					//int[] blockXY = messagesForInput(true);
					//moveBlock(block, blockXY[0], blockXY[1]); // sub one for y due to structure of the display
					break;
				default:
					int destinationHomeX = destination.getOriginalX();
					int destinationHomeY = destination.getOriginalY();
					board[destinationHomeX][destinationHomeY] = destination;
					board[newX][newY] = current;
					board[newX][newY].setColour(this.colourToMove);
					board[x][y] = new Playable(x, y, "White");

			}
                        return true; // move success
		}else{
			if(current.getColour().equals(destination.getColour())){ // player and destination are the same colour - you can't land on a team player
				System.out.println("You already have a colour there");
				return false;
			}else{
				System.out.println("You may have chosen the wrong positon.");
				return false;
			}
		}
	}

	private void setPreviousGameStateToCurrent(){
		 this.previousState = this.board;
	}

	public void returnToPrevious(){
		this.board = this.previousState;
	}

	private String colour(String str){

		switch(str){
			case "Blue":
				return ConsoleColours.BLUE;
			case "Green":
				return ConsoleColours.GREEN;
			case "Red":
				return ConsoleColours.RED;
			case "Yellow":
				return ConsoleColours.YELLOW;
			default:
				break;
		}
		return ConsoleColours.WHITE;
	}

	public void moveBlock(Playable block, int x, int y){
		int prevX = block.getCurrentX();
		int prevY = block.getCurrentY();
		System.out.println("Previous coordinates: " + prevX + ", " + prevY);
		System.out.println("Colour block to move to is " + board[x][y].getColour());
		System.out.println("Block is moving to: " + x + ", " + y);
		if(board[x][y].getColour().equals("White")){
			block.setCurrentCoordinates(new int[]{x, y});
			board[x][y] = block;
			this.blockToMove = false;
			this.blockMoving = null;
		}else{
			System.out.println("Cannot place the block there!");
		}

	}

	public void setPlayerNames(String[] names){
		this.gamePlayers = names;
	}

	public String[] getColourOrder(){
		return this.colourOrder;
	}

	public boolean getBlockIsMoving(){
		return this.blockToMove;
	}

	public Playable getBlockMoving(){
		return this.blockMoving;
	}


	public void nextTurn(){
		if(this.rolledNumber == 6){
                        this.turn--;
                }
		if(this.turn == 3){
			this.turn = -1;
		}
		this.turn++;
	}

	public ArrayList<String> setOrder(String[] players){ // takes a list of players and their names
		String[] newPlayerOrder = new String[4];
		ArrayList<Integer> preOrder = new ArrayList<Integer>(); // keeps track of what values have been rolled before so players afterwards can roll again
		int index = 0;
		ArrayList<String> rollMessages = new ArrayList<>(); // we will use this for the messages in the gui for transparency purposes
		while(index < players.length){
			int roll = rollDice(); // get the roll for this turn
			rollMessages.add(players[index] + " Rolled: " + roll); // we will keep on adding messages each spot will be a new line
			if(preOrder.contains(roll)){
				rollMessages.add(players[index] + " to roll again! Number taken!");
				index--; // this player has to roll again

			}else{
				preOrder.add(roll); // no one has that value so we can move on to the next player

			}
			index++;
		}
		ArrayList<Integer> order = new ArrayList<Integer>(preOrder); // copy the order so we keep track of where it is
		Collections.sort(order, Collections.reverseOrder());// place it in descending order
		int newIndex = 0;
		for(int i : order){
			int preIndex = preOrder.indexOf(i); // now we find the person who has the corresponding number and positon in the old list
			newPlayerOrder[newIndex] = players[preIndex]; //they take place in a new list in the rolling order
			newIndex++;
		}
		setPlayerNames(newPlayerOrder); // reset the order
		return rollMessages;
	}


	public boolean hasWinner(){
		return this.winner;
	}
	public String[] getPlayerNames(){
		return this.gamePlayers;
	}

	public int getTurn(){
		return this.turn;
	}

	public String printPlayerNames(){
		return Arrays.toString(this.gamePlayers);
	}

	public boolean selected(int x, int y){
		boolean allows = this.isTurn(x, y);
		if(allows){
			this.colourToMove = this.board[x][y].getColour();
			this.board[x][y].setColour("Pink");
			return true;
		}else{
			return false;
		}
	}

	public boolean isTurn(int x, int y){
		return this.board[x][y].getColour().equals(colourOrder[this.turn]);
	}

	public String getColour(int x, int y){
		return this.board[x][y].getColour();
	}

	public String printTeamOrder(){
		return Arrays.toString(this.colourOrder);
	}

	public int rollDice(){
		this.rolledNumber = random.nextInt(6-1+1)+1;
		return this.rolledNumber;
	}
	public String getPlayerTurn(){
		return this.gamePlayers[this.turn];
	}

	public void setColourOrder(String[] colourOrder){
		this.colourOrder = colourOrder;
		this.turn = 0;
	}

/*
	public void chooseColours(){
		int person = 0;
		System.out.println("To choose the colour you have to either type a letter or the number corresponding to the colour.");
		System.out.println("Blue: B or 1");
		System.out.println("Green: G or 2");
		System.out.println("Yellow: Y or 3");
		System.out.println("Red: R or 4");
		String perviousPlayer;
		for(int i = 0; i < this.gamePlayers.length; i++){
			boolean chosen = false;
			System.out.println(this.gamePlayers[i] + " please choose a team.");
			String choice = this.keyboard.next().toUpperCase();
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
*/

	private ArrayList<Integer[]> checkFeasibleMoves(int x, int y, String colour, int roll, int[] previousPos, ArrayList<Integer[]> feasibleMoves){
		if(roll == 0){ // have reached all of what we could do this turn
			if(board[x][y].getColour().equals(colour)){
				return feasibleMoves;
			}else{
				System.out.println("Current {x, y} " + x + ", " + y);
				feasibleMoves.add(new Integer[]{x, y});
				return feasibleMoves;
			}
		}
		if(x == 0 || x == 16 || y == 0 || y == 16){ // off the board
			return feasibleMoves;
		}else if(x > 13){//check if the player is in the village line
			//check if there is a blockade in the way
			int[] gatePos = findGatePosition(colour); //get gate positon using the colour of the player from help funtion
			if(board[gatePos[0]][gatePos[1]].getColour().equals("Black") && roll > 1){ //using the return of the line above
				return feasibleMoves; // means that they cannot move from the village
			}else if(board[gatePos[0]][gatePos[1]].getColour().equals("Black") && roll == 1){
				checkFeasibleMoves(gatePos[0], gatePos[1], colour, roll - 1, new int[]{x ,y}, feasibleMoves);// gate has black but the roll is one so we can land on the block
			}else{
				return checkFeasibleMoves(gatePos[0], gatePos[1], colour, roll - 1, new int[]{x, y}, feasibleMoves); // this means that it is empty and we can move from this position
			}										      // all moves from the village start from here
		}else{
		////check that roll isn't 0, has to be one because that would mean that this iteration is the last move

		// we can pretty much move anywhere as long as it is on the board and it isn't our own team
		// check 3 directions, we cannot go back on ourselves
			if(x == previousPos[0] && y == previousPos[1]){
				return feasibleMoves;
			// check if the current coordinate is a block ( we can not move further in that direction if it is)
			}else if(board[x][y].getColour().equals("Black")){
				return feasibleMoves;
			}else{// check in all directions but because of the condition about the results of including previous steps is ignored
				if(!board[x+1][y].getColour().equals(" ")){
					checkFeasibleMoves(x+1, y, colour, roll - 1, new int[]{x, y}, feasibleMoves);
				}else if(!board[x-1][y].getColour().equals(" ")){
					checkFeasibleMoves(x-1, y, colour, roll - 1, new int[]{x, y}, feasibleMoves);
				}else if(!board[x][y+1].getColour().equals(" ")){
					checkFeasibleMoves(x, y+1, colour, roll - 1, new int[]{x, y}, feasibleMoves);
				}else if(!board[x][y-1].getColour().equals(" ")){
					checkFeasibleMoves(x, y-1, colour, roll - 1, new int[]{x, y}, feasibleMoves);
				}
			}
		}
		return feasibleMoves;
	}


	private int[] findGatePosition(String colour){ // should maybe throw an error here instead
		switch(colour){
			case "Blue":
				return this.gates[0];
			case "Green":
				return this.gates[1];
			case "Yellow":
				return this.gates[2];
			default:
				return this.gates[3];

		}
	}






/*	public void startGame(){
		this.printBoard();
		boolean finished = false; //winner qualifier
		int roller = 0;
		int roll = rollDice();
		boolean change = false;
		while(!finished){
			if(roller == 4){
				roller = 0;
			}
			if(change){
				roll = rollDice();
				change = false;
			}
			String col = colour(colourOrder[roller]);
                        System.out.println(col + this.gamePlayers[roller] + " has to move " + roll + " spaces");
			int[] xY = messagesForInput(false); // repeated code become a method returns the input X and Y coordinates False = choosing moving player 
							    // true = move to position
			ArrayList<Integer[]> possibleMoves;
			possibleMoves = checkFeasibleMoves(xY[0], xY[1], colourOrder[roller], roll, new int[2], new ArrayList<Integer[]>());
			for (Integer[] move : possibleMoves) {
    				for (Integer coordinate : move) {
        				System.out.print(coordinate + " ");
    				}
    				System.out.println(); // Move to the next line after printing each move
			}
			if((xY[0] > 0 || xY[0] < size) && (xY[1] > 0 || xY[1] < size)){
				if(board[xY[0]][xY[1]].getColour().equals(colourOrder[roller])){
					int[] newXY = messagesForInput(true);
					boolean turn = movePlayer(xY[0], xY[1], newXY[0], newXY[1]); // X coordinate stays the same as it is because it comes from a string of letters, sub 1 from y because of the strucutre of the display
					if(turn){
						this.printBoard();
						if(this.hasWinner()){
							finished = true;
							System.out.println(col + "WELL DONE " + this.gamePlayers[roller] + "!" + ConsoleColours.RESET);
							break;
						}
						change = true;
						if(roll == 6){
							roller--;
						}
					}else{
						continue;
					}
				}else{
					System.out.println(board[xY[0]][xY[1]].getColour() + " is not your team. You are team: " + colourOrder[roller]);
					roller--;
				}
				roller++;
			}else{
				System.out.println("Values are invalid");
			}
		}
	}

	private int[] messagesForInput(boolean destination){
			if(destination){
				System.out.println("Where do you want to move the piece? Y axis first!");
			}else{
				System.out.println("Which piece do you want to move? Y axis first!");
			}
			System.out.flush(); // Flush the output buffer to ensure the message is immediately displayed
	                String toMoveYStr = this.keyboard.next(); // this will become the vertical position
			int toMoveX = alphabet.indexOf(toMoveYStr); // since they input a letter findind the position of the character in the alphabet will convert to an int
			if(destination){
				System.out.println("Where do you want to move the piece? X axis!");
                        }else{
                                System.out.println("Which piece do you want to move? X axis!");
                        }
	                System.out.flush(); // Flush the output buffer to ensure the message is immediately displayed
			String toMoveXStr = this.keyboard.next(); // this will become the horizontal position
	                int toMoveY = Integer.parseInt(toMoveXStr);
		return new int[]{toMoveX, toMoveY-1};
	}

	public void closeScanner(){
		this.keyboard.close();
	}

	public Scanner getScanner(){
		return this.keyboard;
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

	public static void main(String[] args){
		String gameName = "";
		for(String arg : args){
			gameName += arg + " ";
		}
		Board b = new Board(gameName); // initialising the game with a name
		b.printBoard(); // printing board so we know what it looks like
		System.out.println(ConsoleColours.WHITE_UNDERLINED + "                                                            " + ConsoleColours.RESET); // asking for player names
		System.out.println("Who is going to play?");
		//String names1 = b.getScanner().nextLine();
		//String names2 = b.getScanner().nextLine();
		//String names3 = b.getScanner().nextLine();
		//String names4 = b.getScanner().nextLine();
		System.out.println(ConsoleColours.WHITE_UNDERLINED + "                                                            " + ConsoleColours.RESET);
		System.out.println("Now, let's check the order!"); // setting order
		//b.setOrder(new String[]{names1, names2, names3, names4});
		System.out.println(ConsoleColours.WHITE_UNDERLINED + "                                                            " + ConsoleColours.RESET);
		b.chooseColours(); //choose colours
		System.out.println("The players and their teams in the game are : " + b.printPlayerNames() + " " + b.printTeamOrder());
		System.out.println(ConsoleColours.WHITE_UNDERLINED + "                                                            " + ConsoleColours.RESET);
		System.out.println("Now we are able to start the game! Have fun!"); //wishing luck
		while(!b.hasWinner()){
			b.startGame();
		}
		b.closeScanner();
	}
*/
}
