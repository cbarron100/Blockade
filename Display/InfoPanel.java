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
public class InfoPanel extends JPanel{


	private Board b;
	private String[] colourOrder = new String[4];
	private String[] names = new String[4];
	public InfoPanel(Board b){
		this.b = b;
		this.setBackground(Color.CYAN);
		this.setLayout(null);
		createTextFieldsNames();
	}





	public void createTextFieldsNames(){
		for(int i = 0; i < 4; i++){
			JTextField jt = new JTextField(10);
			JLabel label = new JLabel("");
			jt.setFont(new Font("Arial", Font.BOLD, 10));
			jt.setForeground(Color.WHITE);
			jt.setBackground(Color.BLACK);
			jt.setBounds(20, 26 + (i*50), 100, 24);
			label.setBounds(130, 26 + (i*50), 100, 24);
			jt.setToolTipText("Enter your name");
			jt.setMargin(new Insets(5,5,5,5)); // space between the text and the edge of textfeild
			final int index = i;
			jt.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e){
					label.setText(jt.getText());
					names[index] = jt.getText();
					System.out.println(Arrays.toString(names));
					if(!Arrays.asList(names).contains(null)){
			                        ArrayList<String> messages = b.setOrder(names);
                 				names = b.getPlayerNames();
						remove();
						createTextFieldsColours(names);
						rollingOrderInformation(messages);
					}

				}

			});
			this.add(label);
			this.add(jt);
		}
        }

	 public void createTextFieldsColours(String[] nameOrder){
                for(int i = 0; i < 4; i++){
			String name = nameOrder[i];
                        JTextField jt = new JTextField(10);
                        JLabel label = new JLabel(name + " type colour");
                        jt.setFont(new Font("Arial", Font.BOLD, 10));
                        jt.setForeground(Color.WHITE);
                        jt.setBackground(Color.BLACK);
                        jt.setBounds(20, 26 + (i*50), 100, 24);
			label.setFont(new Font("Arial", Font.BOLD, 10));
                        label.setBounds(130, 26 + (i * 50), 100, 24);
                        jt.setToolTipText(name + " enter colour");
                        jt.setMargin(new Insets(5,5,5,5)); // space between the text and the edge of textfeild
                        final int index = i;
                        jt.addActionListener(new ActionListener(){
                                @Override
                                public void actionPerformed(ActionEvent e){
					if(index == 0){
						label.setText(jt.getText() + ": " + nameOrder[index]);
	                                        colourOrder[index] = jt.getText();
					}else if(colourOrder[index - 1] != null){
						label.setText(jt.getText() + ": " + nameOrder[index]);
						colourOrder[index] = jt.getText();
					}else{
						label.setText("Wait");
					}

					if(!Arrays.asList(colourOrder).contains(null)){
						b.setColourOrder(colourOrder);
						remove();
						createButtons();
						colourOrderJLabels();
						System.out.println(Arrays.toString(b.getColourOrder()));
						System.out.println(b.printPlayerNames());
                                	}
				}
                        });
                        this.add(label);
                        this.add(jt);
                }
		this.repaint();
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


	public void rollingOrderInformation(ArrayList<String> messages){
		int i = 1;
		for(String message : messages){
			JLabel jl = new JLabel(message);
			jl.setFont(new Font("Arial", Font.BOLD, 10));
                        jl.setBounds(15, 250 + (i * 15), 200, 24);
			this.add(jl);
			i++;
		}
		this.repaint();
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
		JLabel diceRoll = new JLabel("");
		diceRoll.setFont(new Font("Arial", Font.BOLD, 12));
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
				diceRoll.setText(b.getPlayerTurn() + " rolled: " + b.rollDice());
				skipTurn.setText("");
			}

		});
		select.addActionListener(new ActionListener(){
                        @Override
                        public void actionPerformed(ActionEvent e){
                                System.out.println("Selected Player");
                        }

                });

		deSelect.addActionListener(new ActionListener(){
                        @Override
                        public void actionPerformed(ActionEvent e){
                                skipTurn.setText(b.getPlayerTurn() + " Skipped");
				b.nextTurn();
                        }

                });
		// set bounds and sizes
		dice.setBounds(startX, startY, width, height);
		select.setBounds(startX+width+10, startY, width, height);
		deSelect.setBounds(startX+(width*2)+20, startY, width, height);
		diceRoll.setBounds(startX, startY + height + 10, width*2, height); // label to display dice roll
		skipTurn.setBounds(startX, startY + height + 10*2, width*2, height); // shows who has skipped and who hasn't
		//add to the panel
		this.add(dice);
		this.add(select);
		this.add(deSelect);
		this.add(diceRoll);
		this.add(skipTurn);
	}
}
