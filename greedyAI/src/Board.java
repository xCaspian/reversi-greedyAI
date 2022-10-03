import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Board implements ActionListener
{
	private JFrame boardFrame = new JFrame();
	private JLabel label;
	private ColorButton[][] buttonArray = new ColorButton[8][8];
	private Game game;
	private String myLabel;
	private JButton greedyAI;
	private boolean player;
	private static JWindow window = new JWindow();

	public Board(Game m, boolean player)
	{
		game = m;
		game.storeBoard(this);
		this.player = player;
		myLabel = player ? "white" : "black";
	}
		 
	public void createBoard()
	{
		// Close window terminates program
		boardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		boardFrame.setTitle(String.format("Reversi - %s Player", myLabel));
		label = new JLabel(getLabel());
		greedyAI = new JButton(String.format("Greedy AI (play %s)", myLabel));

		label.setFont(new Font("Ariel", Font.BOLD, 16));
		boardFrame.setLayout(new BorderLayout());
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(8,8));
		
		for(int y=0; y<buttonArray.length; y++)
		{
			for(int x=0; x<buttonArray[y].length; x++)
			{
				int pos_x, pos_y;
				pos_x = player ? x : 7-x;
				pos_y = player ? y : 7-y;
				
				buttonArray[pos_y][pos_x] = new ColorButton(40, 40, new Color(204,255,153), 1, Color.BLACK, pos_x, pos_y);
				panel.add(buttonArray[pos_y][pos_x]);
				buttonArray[pos_y][pos_x].addActionListener((i) -> buttonPress(i));
			}
		}
		update();	// ensures interfaces are equivalent		
		
		greedyAI.setFont(new Font("Ariel", Font.BOLD, 20));
		greedyAI.addActionListener(this);
		
		boardFrame.add(label, BorderLayout.NORTH);
		boardFrame.add(panel, BorderLayout.CENTER);
		boardFrame.add(greedyAI, BorderLayout.SOUTH);

		// find dimensions of screen and frame to center boards
		Dimension screenSize = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
		Dimension windowSize = new Dimension(boardFrame.getPreferredSize());
		int horizontalOffset = screenSize.width / 2 - windowSize.width / 2 + (player ? -200 : 200);
		int verticalOffset = screenSize.height / 2 - windowSize.height / 2;
		
		boardFrame.pack();  // Resize frame to fit content
		boardFrame.setLocation(horizontalOffset, verticalOffset);
		boardFrame.setVisible(true);  // Display it - until you close out
	}
	
	private String getLabel()
	{
		return String.format("%s player - ", myLabel) + (game.getTurn() == player ? "your turn to place a piece" : "not your turn");
	}
	
	public void buttonPress(ActionEvent e)
	{
		ColorButton button = (ColorButton) e.getSource();
		int x = button.getx();
		int y = button.gety();
		updateBoard(x, y);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		int bestMove = 0, pos_x = 0, pos_y = 0;
		
		// greedy AI
		for(int y=0; y<8; y++)
		{
			for(int x=0; x<8; x++)
			{
				int move = game.checkValidMove(x, y, player, false);
				if(move > bestMove)
				{
					bestMove = move;
					pos_x = x;
					pos_y = y;
				}
			}
		}
		
		updateBoard(pos_x, pos_y);		
	}
	
	public void updateBoard(int x, int y)
	{
		int whiteScore, blackScore;
		String scoreMsg;
		
		if(game.checkValidMove(x, y, player, true) > 0)
		{
			if(game.nextTurn())
			{
				game.updateAllBoards();
			}
			else
			{
				game.updateAllBoards();	
				if(!game.hasValidMove(player))
				{
					whiteScore = game.getScore(true);
					blackScore = game.getScore(false);
					scoreMsg = (whiteScore == blackScore ? "Draw " : (whiteScore > blackScore ? "White" : "Black") + " wins: ") + whiteScore + ":" + blackScore;
					JOptionPane.showMessageDialog(null, scoreMsg);
				}	
			}
			
		}
		
	}
	
	public void update()
	{
		boolean turn = game.getTurn();
		
		for(int y=0; y<buttonArray.length; y++)
		{
			for(int x=0; x<buttonArray[y].length; x++)
			{
				buttonArray[y][x].setState(game.getState(x, y));
				
				if(turn == player)
				{
					buttonArray[y][x].setEnabled(true);
					greedyAI.setEnabled(true);
				}
				else
				{
					buttonArray[y][x].setEnabled(false);
					greedyAI.setEnabled(false);
				}
			}
		}
		label.setText(getLabel());
		boardFrame.repaint();
	}
}
