
public class Main
{	
	public static void main(String[] args)
	{
		Game game = new Game();
		new Board(game, true).createBoard();
		new Board(game, false).createBoard();
	}
}
