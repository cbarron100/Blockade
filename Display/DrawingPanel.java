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
	private Playable[][] b;
	private double gap = 30;
	public DrawingPanel(Board b){
		this.addMouseListener(this);
		this.b = b.getBoard();
	}
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d =(Graphics2D) g;
		// drawing goes here
		//g2d.setColor(Color.RED);
		
		int boardSize = b.length;
		System.out.println(boardSize);
		for(int y = 1; y < boardSize+1; y++){
			for(int x = 1; x < boardSize+1; x++){
				g2d.setColor(colourType(this.b[y-1][x-1]));
				if(!this.b[y-1][x-1].getColour().equals(" ")){
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
			default:
				return Color.WHITE;
		}

	}

        @Override
        public void mouseClicked(MouseEvent e){
	// invoked when it has been clicked (pressed and released) on a component
		System.out.println("This has been pressed at: " + e.getX() + ", " + e.getY());
		int x = (int) ((e.getX()/gap)-1.0);
		int y = (int) ((e.getY()/gap)-1.0);
		this.b.
		System.out.println("This has been pressed at: " + x + ", " + y);

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
