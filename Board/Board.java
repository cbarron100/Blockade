package Board;

import java.util.*;
import Players.*;
//server and network connection packages
import java.io.*;
import java.net.*;




public class Board{
	private String name;
	private boolean winner = false;
	private final int size = 17;
	private String[] gamePlayers = new String[4];
	private String[] colourOrder = new String[4];
	private Playable[][] board = new Playable[size][size];
	private Playable[][] previousState = new Playable[size][size];
	private Random random = new Random();
	//private Scanner keyboard = new Scanner(System.in);
	private int[][] gates = new int[][]{{13, 2}, {13, 6}, {13, 10}, {13, 14}};
	private int turn = 0;
	private String colourToMove; // this is to keep track of the colour that is about to move, we change the colour on the board to see which has been selected
	private boolean blockToMove = false;
	private Playable blockMoving;
	private int rolledNumber; // keeping track of rolled number just in case a 6 is rolled
	private ClientSideConnection csc; //this is for connecting across netwroks
	private int playerID;
	private String boardOwner = "";

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
		if(colourToMove.equals(this.colourOrder[this.turn]) && !current.getColour().equals(destination.getColour())){
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
			String x1Str = Integer.toString(x);
                	String y1Str = Integer.toString(y);
                	String x2Str = Integer.toString(newX);
                	String y2Str = Integer.toString(newY);
                	csc.sendMovePlayerCoordinates(x1Str, y1Str, x2Str, y2Str);
                	System.out.print("Sending Moving Coordinates to the other players: " + x1Str + ", " + y1Str + " and " + x2Str + ", " + y2Str);
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
		return winner;
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
                	String xStr = Integer.toString(x); // convert to string
                	String yStr = Integer.toString(y);
        	        System.out.println("Sending Selected Coordinates to the other players: " + xStr + ", " + yStr);
			csc.sendSelectedCoordinates(xStr , yStr); //send to server
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

	public int getPlayerID(){
		return this.playerID;
	}

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

	public void setBoardOwner(String owner){
                this.boardOwner = owner;
                csc.sendName(owner);
        }

	public void sendBlockMoveCoordinates(int bx, int by){
		String bxStr = Integer.toString(bx);
		String byStr = Integer.toString(by);
		csc.blockCoordinates(bxStr, byStr);
		System.out.println("Sending block coordinates: " + bxStr + ", " + byStr);
	}


	public void recieveingSelectedFromOther(){
		csc.receiveSelectedCoordinated();
	}
	public void receiveMoveCoordinates(){
		csc.recieveMoveCoordinates();
	}


	public boolean canChooseColours(){
                //System.out.println("Can this choose Colour: " + csc.canChooseColours());
                return csc.canChooseColours();
        }

	public void colourChosen(String colour){
		csc.sendColourChoice(colour);
	}

	public boolean mouseEnabled(){
		String mouse = csc.readForMouse();
		return Boolean.parseBoolean(mouse);
	}

	public void recieveNamesColours(){
		csc.recieveOrders();
		System.out.println("-------------------------------------");
		System.out.println("Current team order list: " + printTeamOrder());
                System.out.println("Current Name list: " + printPlayerNames());
	}

	public void connectToServer(){
		csc = new ClientSideConnection();
	}

	//client connection Inner class
	// has everything for the player to connect and comunicate with the server
	private class ClientSideConnection{
		private Socket socket;
		//send and recieve data
		private BufferedReader readerInput; // accepting data
		private PrintWriter writerOutput; // sending data
		private boolean chooseColours = false;
		public ClientSideConnection(){
			System.out.println("----Client Created-----");
			try{
				socket = new Socket("localhost", 40000); // initiates the socket and connection to the server to localhost and port number 40000
				readerInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				writerOutput = new PrintWriter(socket.getOutputStream(), true);
				playerID = Integer.parseInt(readerInput.readLine()); // it gets sent as a String
				System.out.println("Connected to server as player: " + playerID + ".");
				//readerInput.close();
				//writerOutput.close();
				//socket.close();
			}catch (IOException ex){
				System.out.println("IOException from ClientSideConnection Constructor");
			}
		}

		public boolean canChooseColours(){
			try{
				chooseColours = Boolean.parseBoolean(readerInput.readLine());
				return chooseColours;
			}catch(IOException ex){
				System.out.println("IOException in canChooseColours()");
			}
			return false;
		}


		public void recieveOrders(){
			try{
				//recieving final orders for names and colours
				String serialName = readerInput.readLine();
				String serialColour = readerInput.readLine();
				// convert back to arrays
				String[] tempName = serialName.split(",");
				String[] tempColour = serialColour.split(",");
				setColourOrder(tempColour);
				setPlayerNames(tempName);
				System.out.println("Received both orders");
			}catch (IOException ex){
				System.out.println("IOException at revievingOrders()");
			}
		}


		public void sendSelectedCoordinates(String x, String y){
			try{
				writerOutput.println(x);
				writerOutput.flush();

				writerOutput.println(y);
				writerOutput.flush();
				System.out.println("Sent the coordinates");
			}catch(RuntimeException ex){
				System.out.println("IOException at sendSelectedCoordinates()");
			}
		}

		public void blockCoordinates(String bx, String by){
			try{
				writerOutput.println(bx);
				writerOutput.flush();

				writerOutput.println(by);
				writerOutput.flush();

				System.out.println("Send the block coordinates");
			} catch(RuntimeException ex){
				System.out.println("Error in blockCoordinates()");
			}
		}

		public void sendMovePlayerCoordinates(String x1, String y1, String x2, String y2){
                        try{
                                writerOutput.println(x1);
				writerOutput.flush();

                                writerOutput.println(y1);
				writerOutput.flush();

				writerOutput.println(x2);
				writerOutput.flush();


                                writerOutput.println(y2);
				writerOutput.flush();
                                System.out.println("Sent the coordinates for moving player");
                        }catch(RuntimeException ex){
                                System.out.println("IOException at sendMovePlayerCoordinates()");
                        }
                }


		public void sendName(String name){
			try{
                        	writerOutput.println(name);
                	        System.out.println("Sending " + name + " name to server");
        	                writerOutput.flush();
			}catch (RuntimeException ex){
				System.out.println("IOException in sendName()");
			}
		}

		public void sendColourChoice(String colour){
			try{
				writerOutput.println(colour);
				System.out.println("Sending " + colour + " to server");
				writerOutput.flush();
			} catch (RuntimeException ex){
				System.out.println("IOException in sendColour()");
			}
		}

		public void receiveSelectedCoordinated(){
			try{
				String otherPlayerX = readerInput.readLine();
				String otherPlayerY = readerInput.readLine();
				System.out.println("Recieved: " + otherPlayerX + ", " + otherPlayerY + " from someone else");
			} catch(IOException ex){
				System.out.println("IOException from receiveSelectedCoordinated()");
			}
		}
		public void recieveMoveCoordinates(){
                        try{
                                String otherPlayerOldX = readerInput.readLine();
                                String otherPlayerOldY = readerInput.readLine();
				String otherPlayerNewX = readerInput.readLine();
				String otherPlayerNewY = readerInput.readLine();
				System.out.println("-----------------------------------");
                                System.out.println("Recieved Moving from: " + otherPlayerOldX + ", " + otherPlayerOldY + " from someone else");
				System.out.println("Recieved Moving to: " + otherPlayerNewX + ", " + otherPlayerNewY + " from someone else");
                        	System.out.println("-----------------------------------");
			} catch(IOException ex){
                                System.out.println("IOException from receiveSelectedCoordinated()");
                        }
                }

		public String readForMouse(){
			try{
				return readerInput.readLine();
			} catch(IOException ex){
				System.out.println("IO Exception at readForMouse()");
			}
			return Boolean.toString(false);
		}
	}

}
