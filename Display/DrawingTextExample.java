package Display;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;


public class DrawingTextExample extends JPanel{

	@OVerride
	protected void paintComponent(Graphics g){

		super.paintComponent(g);
		Graphics g2d = (Graphics2D) g;

		//set font and colour
		g2d.setFont(new Font("Arial", Font.BOLD, 24);
		g2d.setColor(Color.RED);


		//draw string
		g2d.drawString("Hello, first string", 200, 50);

	}



}
