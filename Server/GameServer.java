package Server;
import java.util.*;
//import needed packages
import java.io.*;
import java.net.*;


public class GameServer{


	private ServerSocket ss;
	private int connectionNumber;
	private String[] newOrder = new String[4];
	private String[] playerNames = new String[4];
	private String[] colourOrder = new String[4];
	private ServerSideConnection player1;
	private ServerSideConnection player2;
	private ServerSideConnection player3;
	private ServerSideConnection player4;

	public GameServer(){
		System.out.println("-------Starting Game Server-----------");
		connectionNumber = 0;
		try{
			ss = new ServerSocket(40000); //port number
		}catch (IOException ex){
			System.out.println("IOException from GameSever constructor");
		}
	}


	public void acceptingConnections(){
		try{
			System.out.println("Accepting Connections...");
			while(connectionNumber < 4){
				Socket s = ss.accept();
				connectionNumber++;
				System.out.println("Number of connected players: " + connectionNumber);
				ServerSideConnection ssc = new ServerSideConnection(s, connectionNumber);
				switch(connectionNumber){
					case 1:
						player1 = ssc;
						break;
					case 2:
						player2 = ssc;
						break;
					case 3:
						player3 = ssc;
						break;
					case 4:
						player4 = ssc;
						break;
					default:
						break;
				}
				Thread t = new Thread(ssc);
				t.start();
			}
			System.out.println("We now have " + connectionNumber + " players connected");
		} catch (IOException ex){
			System.out.println("IOException from acceptingConnections()");
		}
	}

	private class ServerSideConnection implements Runnable{
		private Socket socket;
		private BufferedReader reader;
		private PrintWriter writer;
		private int playerID; //internally see who and what player this belongs too
		private String name;
		private int turn;
		public ServerSideConnection(Socket s, int id){
			socket = s;
			playerID = id;
			try{
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				writer = new PrintWriter(socket.getOutputStream(), true);
			}catch (IOException ex){
				System.out.println("IOException from SSC constructor");
			}
		}


		@Override
		public void run(){
			try{
				writer.println(Integer.toString(playerID)); // send each player their ID number
				writer.flush(); // amkes sure it gets there
				String name = reader.readLine(); // reads the input the player gives
				System.out.println(playerID + " has sent their name: " + name);
				playerNames[playerID-1] = name;// adds this the names list
				this.name = name;
				System.out.println("Added " + this.name + " to " + Arrays.asList(playerNames).toString());
				if(!Arrays.asList(playerNames).contains(null)){ // checks so that everyone has written their names
					System.out.println("Original Order: " + Arrays.asList(playerNames).toString());
					newOrder = setOrder(playerNames); // resets order
					System.out.println("New Order: " + Arrays.asList(newOrder).toString());
					for(String order : newOrder){
                                        	System.out.println(order + " to choose the colour");
						int id = Arrays.asList(playerNames).indexOf(order); // check to see the id of the original list - needed because of playerID value
						int newID = Arrays.asList(newOrder).indexOf(order); // corresponds to the where to place the colour chosen
	                                        switch(id){
							case 0:
								colourOrder[newID] = player1.writeReadColourChoice();
								break;
							case 1:
								colourOrder[newID] = player2.writeReadColourChoice();
								break;
							case 2:
								colourOrder[newID] = player3.writeReadColourChoice();
								break;
							case 3:
								colourOrder[newID] = player4.writeReadColourChoice();
								break;
							default:
								break;
						}
					}
					System.out.println("------------------------------------------------");
					System.out.println("The colour order: " + Arrays.asList(colourOrder).toString());
					System.out.println("The Player order: " + Arrays.asList(newOrder).toString());
					System.out.println("------------------------------------------------");
					sendFinalOrder(newOrder, colourOrder);
					System.out.println("Time To start the game");
					startGame();
				}
			}catch (IOException ex){
				System.out.println("IOException from SSC run()");
			}
		}

		public String writeReadColourChoice(){
			try{
				writer.println(Boolean.toString(true)); // sending allowed to choose colour
				writer.flush();
				System.out.println("Player " + this.name + " allowed to choose colour");
				String c = reader.readLine(); // reading response
				return c;
			}catch (IOException ex){
				System.out.println("IOException at writeReadColour()");
			}
			return null;
		}


		public void enableMouseListener(){
			try{
				writer.println(Boolean.toString(true)); // allows for player to use mouse
				writer.flush();
				System.out.println(this.name + " has enabled screen rights");
			} catch (RuntimeException ex){
				System.out.println("IOExpetion at enableMouseListener()");
			}
		}

		public String recieveIntCoords(){ // change name to something more generic used to revieve all values
			try{
				System.out.println("Waiting for the value");
				String spot = reader.readLine();
				System.out.println("Received: " + spot);
				return spot;
			} catch (IOException ex){
				System.out.println("IOException at recieveIntCoords()");
			}
			return null;
		}


		public void startGame(){
			try{
				int turn = 0;
				while(true){
		                        int indexNameNum = Arrays.asList(playerNames).indexOf(newOrder[turn]);
		                        String xSelected = "";
	        	                String ySelected = "";
					String xDestination = "";
					String yDestination = "";
					String rollValue = "";
	                                // gets the index of the player in the original list to get the right ssc
	                                switch(indexNameNum){
	                                        case 0:
	                                                // enable mouse listener
	                                                enableMouseListener(indexNameNum);
							//recieve roll value
							rollValue = player1.recieveIntCoords();
							sendRollValue(indexNameNum, rollValue);
							System.out.println("Server recieved Roll Value: " + rollValue);
	                                                if(rollValue.equals("Skip")){
								break;
							}else{
								// get the selelected values for the first move
		                                                xSelected = player1.recieveIntCoords();
		                                                ySelected = player1.recieveIntCoords();
		                                                // send the selceted to the other players
								System.out.println("Server Recieved: " + xSelected + " and " + ySelected);
								sendSelectedIntCoordsToOthers(indexNameNum, xSelected, ySelected);
								// have to revalue them for the structure, there is a better way but for now it works
								xSelected = "";
								ySelected = "";
		                                                // get the destination and send the destination
								xSelected = player1.recieveIntCoords();
	                                                        ySelected = player1.recieveIntCoords();
								xDestination = player1.recieveIntCoords();
								yDestination = player1.recieveIntCoords();
								System.out.println("Server has revieved destination coordinates: " + xDestination + ", " + yDestination);
								sendMovingIntCoordsToOthers(indexNameNum, xSelected, ySelected, xDestination, yDestination);
		                                                // if the player can move the block then get that as well
		                                                break;
							}
	                                        case 1:
	                                                // enable mouse listener
	                                                enableMouseListener(indexNameNum);
							//recieve roll value
                                                        rollValue = player2.recieveIntCoords();
							sendRollValue(indexNameNum, rollValue);
                                                        System.out.println("Server recieved Roll Value: " + rollValue);
                                                        if(rollValue.equals("Skip")){
								break;
							}else{
								// get the selelected values for the first move
	                                                        xSelected = player2.recieveIntCoords();
	                                                        ySelected = player2.recieveIntCoords();
	                                                        // send the selceted to the other players
	                                                        System.out.println("Server Recieved: " + xSelected + " and " + ySelected);
	                                                        sendSelectedIntCoordsToOthers(indexNameNum, xSelected, ySelected);
		                                                // send the selceted to the other players
								// have to revalue them for the structure, there is a better way but for now it works

								xSelected = "";
	                                                        ySelected = "";
								// get the destination and send the destination

								xSelected = player2.recieveIntCoords();
	                                                        ySelected = player2.recieveIntCoords();
		                                                xDestination = player2.recieveIntCoords();
	                                                        yDestination = player2.recieveIntCoords();
	                                                        System.out.println("Server has revieved destination coordinates: " + xDestination + ", " + yDestination);
	                                                        sendMovingIntCoordsToOthers(indexNameNum, xSelected, ySelected, xDestination, yDestination);
								break;
							}
	                                        case 2:
	                                                // enable mouse listener
	                                                enableMouseListener(indexNameNum);
							//recieve roll value
                                                        rollValue = player3.recieveIntCoords();
                                                        sendRollValue(indexNameNum, rollValue);
                                                        System.out.println("Server recieved Roll Value: " + rollValue);
							if(rollValue.equals("Skip")){
								break;
							}else{
								// get the selelected values for the first move
	                                                        xSelected = player3.recieveIntCoords();
	                                                        ySelected = player3.recieveIntCoords();
	                                                        // send the selceted to the other players
	                                                        System.out.println("Server Recieved: " + xSelected + " and " + ySelected);
	                                                        sendSelectedIntCoordsToOthers(indexNameNum, xSelected, ySelected);
								// have to revalue them for the structure, there is a better way but for now it works
								xSelected = "";
								ySelected = "";
		                                                // get the destination and send the destination
								xSelected = player3.recieveIntCoords();
	                                                        ySelected = player3.recieveIntCoords();
								xDestination = player3.recieveIntCoords();
	                                                        yDestination = player3.recieveIntCoords();
	                                                        System.out.println("Server has revieved destination coordinates: " + xDestination + ", " + yDestination);
	                                                        sendMovingIntCoordsToOthers(indexNameNum, xSelected, ySelected, xDestination, yDestination);

	                                                	// if the player can move the block then get that as well
	                                                	break;
							}

	                                        case 3:
	                                                // enable mouse listener
	                                                enableMouseListener(indexNameNum);
	                                                //recieve roll value
                                                        rollValue = player4.recieveIntCoords();
                                                        sendRollValue(indexNameNum, rollValue);
                                                        System.out.println("Server recieved Roll Value: " + rollValue);
							if(rollValue.equals("Skip")){
								break;
							}else{
								// get the selelected values for the first move
	                                                        xSelected = player4.recieveIntCoords();
	                                                        ySelected = player4.recieveIntCoords();
	                                                        // send the selceted to the other players
	                                                        System.out.println("Server Recieved: " + xSelected + " and " + ySelected);
								sendSelectedIntCoordsToOthers(indexNameNum, xSelected, ySelected);
		                                                // have to revalue them for the structure, there is a better way but for now it works
								xSelected = "";
	                                                        ySelected = "";
								// get the destination and send the destination
								xSelected = player4.recieveIntCoords();
	                                                        ySelected = player4.recieveIntCoords();
								xDestination = player4.recieveIntCoords();
	                                                        yDestination = player4.recieveIntCoords();
	                                                        System.out.println("Server has revieved destination coordinates: " + xDestination + ", " + yDestination);
	                                                        sendMovingIntCoordsToOthers(indexNameNum, xSelected, ySelected, xDestination, yDestination);

		                                                // if the player can move the block then get that as well
		                                                break;
							}
	                                        default:
	                                                break;
	        			}
					if(rollValue.equals("6")){
						turn--;
					}
					if(turn == 3){
						turn = -1;
					}
					turn++;
		                }
			}catch (RuntimeException ex){
				System.out.println("IOException at startGame()");
			}
		}


		public void sendRollValue(int i, String dr){
			try{
				switch(i){
					case 0: // 1 should be missing
						player2.writer.println(dr);
						player2.writer.flush();

						player3.writer.println(dr);
						player3.writer.flush();

						player4.writer.println(dr);
						player4.writer.flush();
						System.out.println("Roll dice value from Player 1: " + dr + " sent from server to other players");
						break;
					case 1: // 2 should be missing
						player1.writer.println(dr);
                                                player1.writer.flush();

                                                player3.writer.println(dr);
                                                player3.writer.flush();

                                                player4.writer.println(dr);
                                                player4.writer.flush();
                                                System.out.println("Roll dice value from Player 2: " + dr + " sent from server to other players");
                                                break;


					case 2: // 3 should be missing
						player1.writer.println(dr);
                                                player1.writer.flush();

                                                player2.writer.println(dr);
                                                player2.writer.flush();

                                                player4.writer.println(dr);
                                                player4.writer.flush();
                                                System.out.println("Roll dice value from Player 3: " + dr + " sent from server to other players");
                                                break;



					case 3: // 4 should be missing
						player1.writer.println(dr);
                                                player1.writer.flush();

                                                player3.writer.println(dr);
                                                player3.writer.flush();

                                                player2.writer.println(dr);
                                                player2.writer.flush();
                                                System.out.println("Roll dice value from Player 4: " + dr + " sent from server to other players");
                                                break;


					default:
						break;
				}

			}catch(RuntimeException ex){
				System.out.println("IOException from sendRollValue()");
			}
		}

		public void sendMovingIntCoordsToOthers(int i, String oldX, String oldY, String newX, String newY){
                        try{
                                switch(i){
                                        case 0:// 1 should be missing
                                                player2.writer.println(oldX);
                                                player2.writer.flush();

                                                player2.writer.println(oldY);
                                                player2.writer.flush();

						player2.writer.println(newX);
                                                player2.writer.flush();

                                                player2.writer.println(newY);
                                                player2.writer.flush();
						//-----------------------------//
                                                player3.writer.println(oldX);
                                                player3.writer.flush();

                                                player3.writer.println(oldY);
                                                player3.writer.flush();

                                                player3.writer.println(newX);
                                                player3.writer.flush();

                                                player3.writer.println(newY);
                                                player3.writer.flush();
						//-----------------------------//
						player4.writer.println(oldX);
                                                player4.writer.flush();

                                                player4.writer.println(oldY);
                                                player4.writer.flush();

						player4.writer.println(newX);
                                                player4.writer.flush();

                                                player4.writer.println(newY);
                                                player4.writer.flush();

                                                System.out.println("Server has sent moving coordinates from player 1 to other players");
                                                break;
                                        case 1://2 should be missing
						player1.writer.println(oldX);
                                                player1.writer.flush();

                                                player1.writer.println(oldY);
                                                player1.writer.flush();

                                                player1.writer.println(newX);
                                                player1.writer.flush();

                                                player1.writer.println(newY);
                                                player1.writer.flush();
                                                //-----------------------------//
                                                player3.writer.println(oldX);
                                                player3.writer.flush();

                                                player3.writer.println(oldY);
                                                player3.writer.flush();

                                                player3.writer.println(newX);
                                                player3.writer.flush();

                                                player3.writer.println(newY);
                                                player3.writer.flush();
                                                //-----------------------------//
                                                player4.writer.println(oldX);
                                                player4.writer.flush();

                                                player4.writer.println(oldY);
                                                player4.writer.flush();

                                                player4.writer.println(newX);
                                                player4.writer.flush();

                                                player4.writer.println(newY);
                                                player4.writer.flush();

                                                System.out.println("Server has sent moving coordinates from player 2 to other players");
                                                break;

                                        case 2:// three should be missing
						player2.writer.println(oldX);
                                                player2.writer.flush();

                                                player2.writer.println(oldY);
                                                player2.writer.flush();

                                                player2.writer.println(newX);
                                                player2.writer.flush();

                                                player2.writer.println(newY);
                                                player2.writer.flush();
                                                //-----------------------------//
                                                player1.writer.println(oldX);
                                                player1.writer.flush();

                                                player1.writer.println(oldY);
                                                player1.writer.flush();

                                                player1.writer.println(newX);
                                                player1.writer.flush();

                                                player1.writer.println(newY);
                                                player1.writer.flush();
                                                //-----------------------------//
                                                player4.writer.println(oldX);
                                                player4.writer.flush();

                                                player4.writer.println(oldY);
                                                player4.writer.flush();

                                                player4.writer.println(newX);
                                                player4.writer.flush();

                                                player4.writer.println(newY);
                                                player4.writer.flush();

                                                System.out.println("Server has sent moving coordinates from player 3 to other players");
                                                break;
                                        case 3:// 4 should be missing
						player2.writer.println(oldX);
                                                player2.writer.flush();

                                                player2.writer.println(oldY);
                                                player2.writer.flush();

                                                player2.writer.println(newX);
                                                player2.writer.flush();

                                                player2.writer.println(newY);
                                                player2.writer.flush();
                                                //-----------------------------//
                                                player3.writer.println(oldX);
                                                player3.writer.flush();

                                                player3.writer.println(oldY);
                                                player3.writer.flush();

                                                player3.writer.println(newX);
                                                player3.writer.flush();

                                                player3.writer.println(newY);
                                                player3.writer.flush();
                                                //-----------------------------//
                                                player1.writer.println(oldX);
                                                player1.writer.flush();

                                                player1.writer.println(oldY);
                                                player1.writer.flush();

                                                player1.writer.println(newX);
                                                player1.writer.flush();

                                                player1.writer.println(newY);
                                                player1.writer.flush();

                                                System.out.println("Server has sent moving coordinates from player 4 to other players");
                                                break;
                                        default:
                                                break;

                                }
                        } catch (RuntimeException ex){
                                System.out.println("IOException at sendSelectedIntCoordsToRest()");
                        }
                }


		public void sendSelectedIntCoordsToOthers(int i, String x, String y){
			try{
				switch(i){
					case 0:
						player2.writer.println(x);
						player2.writer.flush();

						player2.writer.println(y);
						player2.writer.flush();

						player3.writer.println(x);
						player3.writer.flush();

						player3.writer.println(y);
						player3.writer.flush();

						player4.writer.println(x);
						player4.writer.flush();
						player4.writer.println(y);
						player4.writer.flush();
						System.out.println("Server has sent from player 1 to other players");
						break;
					case 1:
						player1.writer.println(x);
                                                player1.writer.flush();
                                                player1.writer.println(y);
                                                player1.writer.flush();

                                                player3.writer.println(x);
                                                player3.writer.flush();
                                                player3.writer.println(y);
                                                player3.writer.flush();

                                                player4.writer.println(x);
                                                player4.writer.flush();
                                                player4.writer.println(y);
                                                player4.writer.flush();
						System.out.println("Server has sent from player 2 to other players");
                                                break;

					case 2:
						player1.writer.println(x);
                                                player1.writer.flush();
                                                player1.writer.println(y);
                                                player1.writer.flush();

                                                player2.writer.println(x);
                                                player2.writer.flush();
                                                player2.writer.println(y);
                                                player2.writer.flush();

                                                player4.writer.println(x);
                                                player4.writer.flush();
                                                player4.writer.println(y);
                                                player4.writer.flush();
						System.out.println("Server has sent from player 3 to other players");
                                                break;
					case 3:
						player1.writer.println(x);
                                                player1.writer.flush();
                                                player1.writer.println(y);
                                                player1.writer.flush();

                                                player2.writer.println(x);
                                                player2.writer.flush();
                                                player2.writer.println(y);
                                                player2.writer.flush();

                                                player3.writer.println(x);
                                                player3.writer.flush();
                                                player3.writer.println(y);
                                                player3.writer.flush();
						System.out.println("Server has sent from player 4 to other players");
                                                break;
					default:
						break;

				}
			} catch (RuntimeException ex){
				System.out.println("IOException at sendSelectedIntCoordsToRest()");
			}
		}

		public void enableMouseListener(int p){
			try{
				String t = Boolean.toString(true);
				String f = Boolean.toString(false);
				switch(p){
					case 0:
						player1.writer.println(t);
						player1.writer.flush();
						System.out.println("Sending player " + (p+1) + " mouse enabled message");
						player2.writer.println(f);
						player2.writer.flush();

						player3.writer.println(f);
						player3.writer.flush();

						player4.writer.println(f);
						player4.writer.flush();
						break;
					case 1:
						player2.writer.println(t);
                                                player2.writer.flush();
						System.out.println("Sending player " + (p+1) + " mouse enabled message");
                                                player1.writer.println(f);
                                                player1.writer.flush();

                                                player3.writer.println(f);
                                                player3.writer.flush();

                                                player4.writer.println(f);
                                                player4.writer.flush();
                                                break;
					case 2:
						player3.writer.println(t);
                                                player3.writer.flush();
						System.out.println("Sending player " + (p+1) + " mouse enabled message");
                                                player1.writer.println(f);
                                                player1.writer.flush();

                                                player2.writer.println(f);
                                                player2.writer.flush();

                                                player4.writer.println(f);
                                                player4.writer.flush();
                                                break;
					case 3:
						player4.writer.println(t);
                                                player4.writer.flush();
						System.out.println("Sending player " + (p+1) + " mouse enabled message");
                                                player1.writer.println(f);
                                                player1.writer.flush();

                                                player2.writer.println(f);
                                                player2.writer.flush();

                                                player3.writer.println(f);
                                                player3.writer.flush();
                                                break;
					default:
						break;
				}
			}catch (RuntimeException ex){
				System.out.println("Sending mouse Enabled messaged Failed");
			}
		}
		public void sendFinalOrder(String[] nameOrder, String[] colourOrder){
			try{
				// serialise the array
				System.out.println("-----------------------");
				System.out.println("Sending everyone the final order");
				String serialName = String.join(",", nameOrder);
				String serialColours = String.join(",", colourOrder);
				// send arrays to everyone
				player1.writer.println(serialName);
				player2.writer.println(serialName);
				player3.writer.println(serialName);
				player4.writer.println(serialName);
				// Flush the PrintWriter objects
        			player1.writer.flush();
        			player2.writer.flush();
        			player3.writer.flush();
        			player4.writer.flush();

				//colour order
				player1.writer.println(serialColours);
				player2.writer.println(serialColours);
				player3.writer.println(serialColours);
				player4.writer.println(serialColours);
				// Flush the PrintWriter objects
			        player1.writer.flush();
			        player2.writer.flush();
			        player3.writer.flush();
			        player4.writer.flush();
				System.out.println("Sent final orders");
				System.out.println("-----------------------");
			} catch(RuntimeException ex){
				System.out.println("IOException in sendFinalOrder()");
			}
		}
	}
	public static void main(String[] args){
		GameServer gs = new GameServer();
		gs.acceptingConnections();
	}

        private String[] setOrder(String[] players){ // takes a list of players and their names
                String[] newPlayerOrder = new String[4];
                ArrayList<Integer> preOrder = new ArrayList<Integer>(); // keeps track of what values have been rolled before so players afterwards can roll again
                int index = 0;
		Random random = new Random();
                ArrayList<String> rollMessages = new ArrayList<>(); // we will use this for the messages in the gui for transparency purposes
                while(index < players.length){
                        int roll = random.nextInt(6-1+1)+1;; // get the roll for this turn
                        if(preOrder.contains(roll)){
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
                return newPlayerOrder; // reset the order
        }
}
