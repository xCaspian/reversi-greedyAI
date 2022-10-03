
import java.util.ArrayList;

public class Game
{		
	private ArrayList<Board> listOfBoards = new ArrayList<Board>();
	private Boolean[][] state = new Boolean[8][8]; // null = blank, true = white, false = black
	private boolean turn;
	
	public Game()
	{
		state[3][3] = true;
		state[4][4] = true;
		state[3][4] = false;
		state[4][3] = false;
		turn = true; // white turn first
	}
	
	public void storeBoard(Board board)
	{
		listOfBoards.add(board);
	}
	
	public void updateAllBoards()
	{
		for(int i=0; i<listOfBoards.size(); i++)
			listOfBoards.get(i).update();
	}
	
	public Boolean getState(int x, int y)
	{
		return state[y][x];
	}
	
	public int getScore(boolean player)
	{
		int score = 0;
		
		for(Boolean[] rows : state)
		{
			for(Boolean cell : rows)
			{
				if (cell != null && cell == player)
					score++;
			}
		}
		return score;
	}
	
	public boolean getTurn()
	{
		return turn;
	}
	
	public boolean nextTurn()
	{
		if(hasValidMove(!turn))
		{
			turn = !turn;
			return true;
		}
		return false;
	}
	
	public void setState(int x, int y, boolean player)
	{
		state[y][x] = player;
	}
	
	public boolean hasValidMove(boolean player)
	{
		for(int y=0; y<8; y++)
		{
			for(int x=0; x<8; x++)
			{
				if(checkValidMove(x, y, player, false) > 0)
					return true;
			}
		}
		return false;
	}
	
	public int checkValidMove(int x, int y, boolean player, boolean update)
	{
		int totalCount = 0;
		
		if(getState(x, y) != null)
		{
			return 0;
		}
		
		for(int xoffset=-1; xoffset<=1; xoffset++)
		{
			for(int yoffset=-1; yoffset<=1; yoffset++)
			{
				if(xoffset == 0 && yoffset == 0)
					continue;
				
				int pos_x = x, pos_y = y, count = 0;
				
				while(true)
				{
					pos_x += xoffset;
					pos_y += yoffset;
					
					// edge of board
					if(pos_x < 0 || pos_x > 7 || pos_y < 0 || pos_y > 7)
					{
						count = 0;
						break;
					}
					
					Boolean currState = getState(pos_x, pos_y);
					
					if(currState == null)
					{
						count = 0;
						break;
					}
					
					if(currState == player)
						break;
					
					count++;
				}
				
				totalCount += count;
				
				if(update && count>0)
				{
					pos_x = x;
					pos_y = y;
					
					for(int i=0; i<=count; i++)
					{
						state[pos_y][pos_x] = player;
						pos_x += xoffset;
						pos_y += yoffset;
					}
				}
			}
		}
	
		return totalCount;
	}

}

