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
		if(this.first == true){ //checks if it is the piece about to be moved
			this.firstX = (int) ((e.getY()/gap));// have to swap these due to strucutre of the Arrays in java
			this.firstY = (int) ((e.getX()/gap));
			this.first = false;
			firstCoordinatesSelected(this.firstX, this.firstY);
		}else{
			this.secondX = (int) ((e.getY()/gap));
			this.secondY = (int) ((e.getX()/gap));
			this.first = true;
			secondCoordinatesSelected(this.secondX, this.secondY);
		}
	}

	private void firstCoordinatesSelected(int x, int y){
		if(this.firstX >= 0 && this.firstX < b.getSize() && this.firstY >= 0 && this.firstY < b.getSize()) { // Check bounds
                        String col = this.b.getColour(this.firstX, this.firstY); // Swap y and x indices
                        if(!col.equals(" ")){//dont want to colour outside the allowed positions
                                System.out.println("First set of positions at: " + this.firstX + ", " + this.firstY);
                                System.out.println("Colour selected is: " + col);
                                boolean allowed = this.b.selected(this.firstX, this.firstY);
                                System.out.println(allowed);
                                if(allowed){
                                        repaint();
                                }else{
                                        this.first = true;
                                        resetCoordinates();
                                }
                        }

		}
	}


	private void secondCoordinatesSelected(int x, int y){
		if(this.secondX >= 0 && this.secondX < b.getSize() && this.secondY >= 0 && this.secondY < b.getSize()){
			String col = this.b.getColour(this.secondX, this.secondY);
			if(!col.equals(" ")){
				System.out.println("The moving position is " + this.secondX + ", " + this.secondY);
				boolean playerMoved = this.b.movePlayer(this.firstX, this.firstY, this.secondX, this.secondY);
				if(playerMoved){
					resetCoordinates();
					repaint();
				}
			}
		}
	}



	private void resetCoordinates(){
		this.firstX = -1;
		this.secondX = -1;
		this.firstY = -1;
		this.secondY = -1;
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
