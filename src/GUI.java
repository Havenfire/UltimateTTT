import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

/*
 *Description: This is the Graphical User interface. It shows and creates all game elements
 */
public class GUI {

	
	private static final int SQUARE_SIZE = 100;
	
	

	//thematic elements of the game
	private String squareType = "text";

	private String[] winnerName = {"X","O","X-Wing","TIE fighter", "Fairport", "Spencerport","Mr. Klus","Mr. Laley"};
	private String[] themeNames = {"text", "ship", "robotics", "faces"};	
	private JButton[] themes = new JButton[themeNames.length];
	private JButton themeMenu;
	boolean themeMenuIsOpen = false;

	//Objects for AI
	private JButton aiMenu;
	private String[] aiNames = {"Easy", "Medium", "Hard"};	
	private JButton switchMulti;
	private JButton[] aiList = new JButton[aiNames.length];
	boolean aiMenuIsOpen = false;

	
	private JButton restartButton;
	private JButton howToPlayButton;
	private String rules = ""
			+ "Each small 3-by-3 tic-tac-toe board is referred to as a local board, and the larger 3-by-3 board is referred to as the global board."
			+ "\n\nThe game starts with X playing wherever they want in any of the 81 empty spots. This move 'sends' their opponent to its relative location. "
			+ "\nFor example, if X played in the top right square of their local board, then O needs to play next in the local board at the top right of the global board. "
			+ "\nO can then play in any one of the nine available spots in that local board, each move sending X to a different local board."
			+ "\n\nIf a move is played so that it is to win a local board by the rules of normal tic-tac-toe, then the entire local board is marked as a victory for the player in the global board."
			+ "\n\nOnce the outcome of a local board is decided (win or draw), no more moves may be played in that board. If a player is sent to such a board, then that player may play in any other board."
			+ "\n\nGame play ends when either a player wins the global board or there are no legal moves remaining, in which case the game is a draw.";

	
	private BigBoard board;
	private JFrame frame;
	private JPanel panel;
	private ArrayList<JLabel> imgList; 
	private JButton[][] buttons = new JButton[9][9];

	//formats button locations
	private int xAddOn = 0;
	private int yAddOn = 0;
	
	private boolean [] winners = new boolean [9];
	
	private AI bot;
	
	//coordinate locations of each square, which are determined later
	final private int[] xCoordinate = {0, 105, 210, 0, 105, 210, 0, 105, 210};
	final private int[] yCoordinate = {0, 0, 0, 105, 105, 105, 210, 210, 210};

	//Constructor that creates a Board, and makes all the visible things visible and formatted
	public GUI() {
		board = new BigBoard();
		imgList = new ArrayList<JLabel>();
		initDisplay();
		repaint();
	}

	
	
	//updates the Board to its new state, and checks for a winner
	private void repaint() {
		
		//clears big X's and O's
		for(int i = 0; i < imgList.size(); i++)
			panel.remove(imgList.get(i));
		imgList.clear();
		

		for(int i = 0; i < 9; i++)
		{
			String picture = "";
			ImageIcon icon;

			for(int k = 0; k < 9; k++)
			{
				//sets blank squares to be blank, else sets Square to it's small board value
				if(board.boardAt(i).squareAt(k).getValue() == "")
				{
					icon = new ImageIcon("square.gif");
					picture = "square.gif";
				}
				else
				{
					icon = new ImageIcon(squareType + board.boardAt(i).squareAt(k).getValue() + ".gif");
					picture = squareType + board.boardAt(i).squareAt(k).getValue() + ".gif";
				}
				buttons[i][k].setIcon(icon);
				
				//makes unplayable Squares grey
				if(board.getBoardToPlay() != 10 && board.getBoardToPlay() != i)
				{
					icon = new ImageIcon("gray" + picture);
					buttons[i][k].setIcon(icon);
				}
			
			
	        
				//covers up won small boards with big images
				if(board.boardAt(i).gameIsWon() && !winners[i])
				{
	
					ImageIcon wonBoard = new ImageIcon(squareType + "Big" + board.boardAt(i).getWinner() + ".gif");
					JLabel label = new JLabel(wonBoard);
					imgList.add(label);
					for(int j = 0; j < 9; j++)
						panel.remove(buttons[i][j]);
					panel.add(imgList.get(imgList.size() - 1));
					label.setBounds(buttons[i][0].getX(), buttons[i][0].getY(), 310, 310);
					label.setVisible(true);
					winners[i] = true;
				}
			
				
			}
						
		}
		//checks if the game is won, and shows a message if true
		if(board.gameIsWon())
		{
	           int wonIndex = 0;
	           for(int i = 0; i < themeNames.length; i++)
	        	   if(themeNames[i].equals(squareType))
	        		   wonIndex = i;  
	           wonIndex *= 2;
	           if(board.OHasWon())
	        	   wonIndex += 1;
	           
	          
	           JOptionPane.showMessageDialog(frame, "Congratulations, " + winnerName[wonIndex] +" won the game!");
	          
	           board = new BigBoard();
	           
				for(int i = 0; i < 9; i++)
				{
					for( int j = 0; j < 9; j++)
						panel.add(buttons[i][j]);
				}
				for(int i = imgList.size()-1; i > 0 ; i--)
				{
					panel.remove(imgList.get(i));
					imgList.remove(i);	
				}
				for(int i = 0; i < 9; i ++)
				{
					winners[i] = false;
				}
				if(bot != null)
					bot = new AI(board, bot.getDifficulty());
				
				panel.updateUI();

				repaint();
		}
	}

	//initializes the JFrame, JPanel, and all the Buttons. 
	private void initDisplay() {
		
		//sets JFrame properties and JPanel properties
		frame = new JFrame("Ultimate Tic Tac Toe");	
		frame.setSize(1500, 1400);
		frame.setVisible(true);
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(1800, 1300));
		panel.setVisible(true);
		panel.setBackground(Color.BLACK);		
		panel.setLayout(null);
		
		frame.add(panel);
			
		//switch to multiplayer from AI button and it's properties
		switchMulti = new JButton();
		switchMulti = new JButton();
		switchMulti.setBounds(1500, 200, 100, 100);
		panel.add(switchMulti);
		switchMulti.setPreferredSize(new Dimension(100, 100));
		switchMulti.setText("Multiplayer");
		switchMulti.setVisible(false);
		switchMulti.putClientProperty(bot, bot);
		switchMulti.setFocusPainted(false);
		switchMulti.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				bot = null;
				switchMulti.setVisible(false);
				aiMenu.setText("Multiplayer");
				for(int k = 0; k < aiList.length; k++)					
					aiList[k].setVisible(false);
			}
			
		}
		
		);
		
		//restart button and it's properties
		restartButton = new JButton();
		restartButton.setBounds(1100, 500, 100, 50);
		panel.add(restartButton);
		restartButton.setPreferredSize(new Dimension(100, 50));
		restartButton.setText("Restart");
		restartButton.setFocusPainted(false);
		restartButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) 
       	    {
				board = new BigBoard();
				
				for(int i = 0; i < 9; i++)
				{
					for( int j = 0; j < 9; j++)
						panel.add(buttons[i][j]);
				
				
				}
				for(int i = imgList.size()-1; i > 0 ; i--)
				{
					panel.remove(imgList.get(i));
					imgList.remove(i);
					
				}
				for(int i = 0; i < 9; i ++)
				{
					winners[i] = false;
				}
				if(bot != null)
					bot = new AI(board, bot.getDifficulty());
				
				panel.updateUI();

				repaint();
       	    }
		});
		
		//how to play button and it's properties
		howToPlayButton = new JButton();
		howToPlayButton.setBounds(1100, 400, 100, 50);
		panel.add(howToPlayButton);
		howToPlayButton.setPreferredSize(new Dimension(100, 50));
		howToPlayButton.setText("How to Play");
		howToPlayButton.setFocusPainted(false);

		howToPlayButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				JOptionPane pane = new JOptionPane(rules);
				JDialog d = pane.createDialog(null, "How to Play");
				d.setLocation(300, 300);
				d.setVisible(true);
			}
		});

		//theme menu and it's properties
		themeMenu = new JButton();
		themeMenu.setBounds(1100, 300, 100, 50);
		panel.add(themeMenu);
		themeMenu.setPreferredSize(new Dimension(100,50));
		themeMenu.setText("Theme");	
		themeMenu.setFocusPainted(false);
		themeMenu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				themeMenuIsOpen = !themeMenuIsOpen;
				for(int i = 0; i < themes.length; i++)
				{
					if(themes[i]!=null)
					themes[i].setVisible(themeMenuIsOpen);
				}
				for(int i = 0; i < 9; i ++)
				{
					winners[i] = false;
				}
				repaint();

			}
		});

		//individual themes and they're properties
		int xAddThemes = 0;
		for(int i = 0; i < themes.length; i++)
		{
			themes[i] = new JButton();
			themes[i].setBounds(1200 + xAddThemes, 300, 100, 100);
			panel.add(themes[i]);
			themes[i].setPreferredSize(new Dimension(100,100));
			ImageIcon icon = new ImageIcon(themeNames[i] + "X.gif");
			themes[i].setIcon(icon);
			themes[i].setVisible(false);
			themes[i].putClientProperty("index", i);
			themes[i].setRolloverEnabled(false);
			themes[i].setFocusPainted(false);

			themes[i].addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e)
				{
					int index = (Integer) ((JButton) e.getSource()).getClientProperty("index");
					squareType = themeNames[index];
					themeMenuIsOpen = !themeMenuIsOpen;
					for(int k = 0; k < themeNames.length; k++)					
						themes[k].setVisible(false);
					
					for(int i = 0; i < 9; i ++)
					{
						winners[i] = false;
					}
					repaint();
				}
			});
			xAddThemes += 100;
		}

		//ai menu and it's properties
		aiMenu = new JButton();
		aiMenu.setBounds(1100, 200, 100, 50);
		panel.add(aiMenu);
		aiMenu.setPreferredSize(new Dimension(100,50));
		aiMenu.setText("Multiplayer");
		aiMenu.setFocusPainted(false);

		aiMenu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				aiMenuIsOpen = !aiMenuIsOpen;
				
				for(int i = 0; i < aiList.length; i++)				
					if(aiList[i]!=null)
						aiList[i].setVisible(aiMenuIsOpen);					
					
				
				switchMulti.setVisible(aiMenuIsOpen);
				
				
				if(bot != null)
					aiMenu.setText(aiNames[bot.getDifficulty()] + " AI");				 
				else 		
					aiMenu.setText("Multiplayer");
				
				for(int i = 0; i < 9; i ++)
				{
					winners[i] = false;
				}
				repaint();

			}
		});
		
		//different AIs and they're properties
		int xAddAi = 0;
		for(int i = 0; i < aiList.length; i++)
		{
			aiList[i] = new JButton();
			aiList[i].setBounds(1200 + xAddAi, 200, 100, 100);
			panel.add(aiList[i]);
			aiList[i].setPreferredSize(new Dimension(100,100));		
			aiList[i].setText(aiNames[i]);
			aiList[i].setVisible(false);
			aiList[i].putClientProperty("index", i);

			aiList[i].addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e)
				{
					int index = (Integer) ((JButton) e.getSource()).getClientProperty("index");
					
					
					
					bot = new AI(board, index);

					aiMenuIsOpen = !aiMenuIsOpen;
					if(bot != null)						
						aiMenu.setText(aiNames[bot.getDifficulty()] + " AI");				 
					
					else 		
						aiMenu.setText("Multiplayer");
					
					
					
					for(int k = 0; k < aiList.length; k++)					
						aiList[k].setVisible(false);
					
					
					for(int i = 0; i < 9; i ++)					
						winners[i] = false;
					switchMulti.setVisible(false);
					
					repaint();
				}
			});
			xAddAi += 100;
		}
		
		for(int k = 0; k < 9; k++)
		{
				
			//Adds new buttons to the array
			for(int i = 0; i < 9; i++)
				buttons[k][i] = new JButton();
			
			
	
			
			//gives the buttons values, qualities, coordinates, and actionListeners, 
			for(int i = 0; i < 9; i++)
		    {
				//Coordinates of buttons
				buttons[k][i].setBounds(xCoordinate[i] + xAddOn, yCoordinate[i] + yAddOn, SQUARE_SIZE, SQUARE_SIZE);	    	
		        
				buttons[k][i].setRolloverEnabled(false);
		        
		        
				panel.add(buttons[k][i]);
				buttons[k][i].setPreferredSize(new Dimension(100, 100));
		        panel.setComponentZOrder(buttons[k][i], 1);
		        //sets them to blank images
		        ImageIcon icon = new ImageIcon("square.gif");
		        buttons[k][i].setIcon(icon);
				
				//buttons[i] now contain a property telling it what button it is
				//also gives each button an actionListeners
		        buttons[k][i].putClientProperty("buttonNum", i);
		        buttons[k][i].putClientProperty("panelNum", k);
		        buttons[k][i].addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) 
		       	    {
						int index = (Integer) ((JButton) e.getSource()).getClientProperty("buttonNum");
						int boardNum = (Integer) ((JButton) e.getSource()).getClientProperty("panelNum");
		       	    	board.takeTurn(boardNum, index);
		                repaint();
		                
		                
		                if(board.boardAt(boardNum).gameIsWon())
		                	for(int i = 0; i < 9; i ++)
		                		winners[i] = false;
							
		                if(bot != null)
		                	bot.play();
		                
		               
		                repaint();
		       	    }
				});
		        Boolean test = new Boolean(true);


		    }
			xAddOn += 340;
	        if(xAddOn > 680)
	        {
	        	xAddOn = 0;
	        	yAddOn += 340;
	        }
	      
	        
			frame.pack();
			
		}

		
	}
}
