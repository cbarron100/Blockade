package Server;
import java.util.*;
//import needed packages
import java.io.*;
import java.net.*;


public class GameServer{


	private ServerSocket ss;
	private int connectionNumber;
	private String[] playerNames = new String[4];
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
				writer.println(Integer.toString(playerID));
				writer.flush();
				String name = reader.readLine();
				System.out.println(playerID + " has sent their name: " + name);
				playerNames[playerID-1] = name;
				this.name = name;
				System.out.println("Added " + this.name + " to " + playerNames[playerID-1]);
				if(!Arrays.asList(playerNames).contains(null)){
					System.out.println("Original Order: " + Arrays.asList(playerNames).toString());
					setOrder(playerNames);
					System.out.println("New Order: " + Arrays.asList(playerNames).toString());
				}
				for(int i = 0; i < playerNames.length; i++){
					System.out.println(playerNames[i] + " to choose the colour");
					if(this.name.equals(playerNames[i])){
						writer.println(String.valueOf(true));
						writer.flush();
					}

				}
				while(true){

				}
			}catch (IOException ex){
				System.out.println("IOException from SSC run()");
			}
		}
	}

	public static void main(String[] args){
		GameServer gs = new GameServer();
		gs.acceptingConnections();
	}

        private void setOrder(String[] players){ // takes a list of players and their names
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
                playerNames = newPlayerOrder; // reset the order
        }
}
