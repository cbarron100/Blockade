package Display;

import java.awt.Dimension;
import Board.Board;
import Players.*;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.JOptionPane;
public class InfoPanel extends JPanel{


	private Board b;
	private String[] colourOrder = new String[4];
	private String[] names = new String[4];
	private boolean enableButtons = false;
	private JLabel diceRoll;
	public InfoPanel(Board b){
		this.b = b;
		this.setBackground(Color.LIGHT_GRAY);
		this.setLayout(null);
		diceRoll = new JLabel();
                diceRoll.setFont(new Font("Arial", Font.BOLD, 12));
                diceRoll.setBounds(10, 250, 150, 40); // label to display dice roll
                this.add(diceRoll);
	}

	public void addAllParts(){
		names = b.getPlayerNames();
		colourOrder = b.getColourOrder();
		colourOrderJLabels();
		createButtons();
		this.repaint();
	}

	public void enableButtons(boolean en){
		this.enableButtons = en;
		System.out.println("Enabled interaction for info panel: " + enableButtons);
	}





	public void remove(){
		this.removeAll();
                this.revalidate();
                this.repaint();
	}

	public void colourOrderJLabels(){
		for(int i = 0; i < 4; i++){
			JLabel jl = new JLabel(names[i] + ": " + colourOrder[i]);
			jl.setFont(new Font("Arial", Font.BOLD, 15));
			jl.setBounds(15, 15 + (i * 20), 150, 17);
			this.add(jl);
		}
	}

	public void diceRollLabel(String roller, int roll){
		diceRoll.setText("");
		if(roll == -1){
			diceRoll.setText(roller + " Skipped");
		}else{
			diceRoll.setText(roller + " rolled: " + roll);
		}
	}



	public void createButtons(){
		int width = 70;
		int height = 40;
		int startX = 10;
		int startY = 200;
		// create buttons
		JButton dice = new JButton("Roll Dice");
		JButton select = new JButton("Back");
		JButton deSelect = new JButton("Skip");
		//Label to show the dice roll
		JLabel skipTurn = new JLabel("");
		skipTurn.setFont(new Font("Arial", Font.BOLD, 12));
		//set focus
		dice.setFocusable(false);
		select.setFocusable(false);
		deSelect.setFocusable(false);
		//set tool tip
		dice.setToolTipText("Roll Dice");
		select.setToolTipText("Return to previous state");
		deSelect.setToolTipText("Skip Turn");
		// set fonts
		dice.setFont(new Font("Arial", Font.BOLD, 10));
		select.setFont(new Font("Arial", Font.BOLD, 10));
		deSelect.setFont(new Font("Arial", Font.BOLD, 10));
		// set margins
		dice.setMargin(new Insets(10, 10, 10, 10));
		select.setMargin(new Insets(10, 10, 10, 10));
		deSelect.setMargin(new Insets(10, 10, 10, 10));
		//add action listeners
		dice.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(enableButtons){
					b.rollDice(true);
					diceRollLabel(b.getPlayerTurn(), b.getDiceRoll());
				}else{
					System.out.println("Buttons Disabled");
				}
			}

		});
		select.addActionListener(new ActionListener(){
                        @Override
                        public void actionPerformed(ActionEvent e){
                                if(enableButtons){
					System.out.println("Selected Player");
				}else{
					System.out.println("Buttons Disabled");
				}
                        }

                });

		deSelect.addActionListener(new ActionListener(){
                        @Override
                        public void actionPerformed(ActionEvent e){
				if(enableButtons){
	                                diceRollLabel(b.getPlayerTurn(), -1);
					b.sendSkipped(false);
	                        }else{
					System.out.println("Buttons Disabled");
				}
			}

                });
		// set bounds and sizes
		dice.setBounds(startX, startY, width, height);
		select.setBounds(startX+width+10, startY, width, height);
		deSelect.setBounds(startX+(width*2)+20, startY, width, height);
		skipTurn.setBounds(startX, startY + height + 10*2, width*2, height); // shows who has skipped and who hasn't
		//add to the panel
		this.add(dice);
		this.add(select);
		this.add(deSelect);
		this.add(skipTurn);
	}
}
