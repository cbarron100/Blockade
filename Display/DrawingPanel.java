package Display;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.event.*;



public class DrawingPanel extends JPanel implements MouseListener{

	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d =(Graphics2D) g;
		// drawing goes here
		Rectangle2D rect = new Rectangle2D.Double(100, 100, 200, 100);
		rect.addMouseListener(this);
		g2d.draw(rect);

		
		Ellipse2D ellipse = new Ellipse2D.Double(400, 100, 200, 100);
		g2d.draw(ellipse);

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
		System.out.println("This has been pressed");
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
