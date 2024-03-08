package Display;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.event.*;
import Board.Board;
import java.awt.Color;

public class DrawingPanel extends JPanel implements MouseListener{
	private Board b;
	private int gap = 50;
	public DrawingPanel(Board b){
		this.addMouseListener(this);
		this.b = b;
	}
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d =(Graphics2D) g;
		// drawing goes here
		Rectangle2D rect = new Rectangle2D.Double(100, 100, 200, 100);
		g2d.draw(rect);
		int boardSize = b.getSize();
		for(int y = 0; y < boardSize; y++){
			for(int x = 0; x < boardSize; x++){
				Ellipse2D ellipse = new Ellipse2D.Double(x*gap, y*gap, 100, 50, 50);
                		ellipse.fill(Color.RED);
				g2d.draw(ellipse);

			}
		}

		Path2D polygon = new Path2D.Double();
		polygon.moveTo(200, 300);
		polygon.lineTo(300, 400);
		polygon.lineTo(100, 400);
		polygon.closePath();
		g2d.draw(polygon);


	}

        @Override
        public void mouseClicked(MouseEvent e){
	// invoked when it has been clicked (pressed and released) on a component
		System.out.println("This has been pressed at: " + e.getX() + ", " + e.getY());
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
