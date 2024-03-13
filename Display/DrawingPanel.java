package Display;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.event.*;
import Board.Board;
import Players.*;
import java.awt.Color;

public class DrawingPanel extends JPanel implements MouseListener{
	private Board b;
	private boolean first = true; // for collecting the coordinates for moves(first and second set)
	private double gap = 30;
	private int firstX = -1;
	private int secondX = -1;
	private int firstY = -1;
	private int secondY = -1;// has to be initialised at this so the first conditon is never met before being pressed
	private int moveBlockX = -1;
	private int moveBlockY = -1;
	private int clickNumber = 0;
	public DrawingPanel(Board b){
		this.addMouseListener(this);
		this.b = b;
		this.setBackground(Color.LIGHT_GRAY);
		this.setLayout(null);
	}
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d =(Graphics2D) g;
		// drawing goes here
		//g2d.setColor(Color.RED);
		Playable[][] state = this.b.getBoard();
		int boardSize = state.length;
		for(int y = 0; y < boardSize; y++){
			for(int x = 0; x < boardSize; x++){
				if(!state[y][x].getColour().equals(" ")){
					g2d.setColor(colourType(state[y][x]));
					Ellipse2D ellipse = new Ellipse2D.Double(x*gap, y*gap, 15, 15);
        				g2d.fill(ellipse);
				}

			}
		}
	}

	private Color colourType(Playable p){
		String c = p.getColour();
		switch(c){
			case "Gold":
				return new Color(255, 204, 51);
			case "White":
				return Color.GRAY;
			case "Blue":
				return Color.BLUE;
			case "Green":
				return Color.GREEN;
			case "Yellow":
				return Color.YELLOW;
			case "Red":
				return Color.RED;
			case "Black":
				return Color.BLACK;
			case "Pink":
				return Color.PINK;
			default:
				return Color.WHITE;
		}

	}

        @Override
        public void mouseClicked(MouseEvent e){
	// invoked when it has been clicked (pressed and released) on a component
		System.out.println(this.b.printPlayerNames() + ". Turn: " + this.b.getTurn());
		if(this.first){ //checks if it is the piece about to be moved
			this.firstX = (int) ((e.getY()/gap));// have to swap these due to strucutre of the Arrays in java
			this.firstY = (int) ((e.getX()/gap));
			this.first = false;
			firstCoordinatesSelected(this.firstX, this.firstY);
		}else if(this.b.getBlockIsMoving()){
			System.out.println("Block is moving: " + this.b.getBlockIsMoving());
                        if(this.moveBlockX == -1 && this.moveBlockY == -1){
                                this.moveBlockX = (int) ((e.getY()/gap));
                                this.moveBlockY = (int) ((e.getX()/gap));
                                System.out.println("Chosen positons to move to block to: " + this.moveBlockX + ", " + this.moveBlockY);
                                Playable block = this.b.getBlockMoving();
                                moveBlock(block, this.moveBlockX, this.moveBlockY);
				this.first = true;
                        }
                }else{
			this.secondX = (int) ((e.getY()/gap));
			this.secondY = (int) ((e.getX()/gap));
			secondCoordinatesSelected(this.firstX, this.firstY, this.secondX, this.secondY);
			if(!this.b.getBlockIsMoving()){
				this.first = true;
				this.b.nextTurn();
			}
		}
	}

	private void firstCoordinatesSelected(int x, int y){
		if(withInBoard(x, y)) { // Check bounds
                        String col = this.b.getColour(x, y);
			if(!col.equals(" ")){//dont want to colour outside the allowed positions
                                System.out.println("First set of positions at: " + x + ", " + y);
                                System.out.println("Colour selected is: " + col);
                                boolean allowed = this.b.selected(x, y);
                                System.out.println("Positon for the piece allowed:" + allowed);
                                if(allowed){
                                        repaint();
                                }else{
                                        this.first = true;
                                }
                        }

		}
	}


	private void secondCoordinatesSelected(int x1, int y1, int x2, int y2){
		if(withInBoard(x2, y2)){
			String col = this.b.getColour(x2, y2);
			if(!col.equals(" ")){
				System.out.println("The moving position is " + x2 + ", " + y2);
				boolean playerMoved = this.b.movePlayer(x1, y1, x2, y2);
				if(playerMoved){
					repaint();
				}else{
					this.first = false;
				}
				resetCoordinatesFirst();
				resetCoordinatesSecond();
			}
		}
	}


	private boolean withInBoard(int x, int y){
		return (x >= 0 && x < b.getSize() && y >= 0 && y < b.getSize());
	}

	private void moveBlock(Playable block, int x, int y){
		if(withInBoard(x, y)){
			this.b.moveBlock(block, x, y);
			this.b.nextTurn();
                	resetCoordinatesThird();
			repaint();
		}
	}

	private void resetCoordinatesFirst(){
		this.firstX = -1;
		this.secondX = -1;
	}
	private void resetCoordinatesSecond(){
		this.firstY = -1;
		this.secondY = -1;
	}
	private void resetCoordinatesThird(){
		this.moveBlockX = -1;
		this.moveBlockY = -1;
	}


        @Override
        public void mousePressed(MouseEvent e){
	//invoked when a mouse is pressed on a component (not released)

	}
        @Override
        public void mouseReleased(MouseEvent e){
	// Invoked when a mouse button is released over a component

	}
	@Override
        public void mouseEntered(MouseEvent e){
	//invoked when a mpouse has entered a component
        
	}
	@Override
	public void mouseExited(MouseEvent e){
	// invoked when a mouse has exited a component
	
	}
}
