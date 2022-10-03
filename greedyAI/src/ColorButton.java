
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JButton;

public class ColorButton extends JButton
{
	private Color drawColor;
	private Color borderColor;
	private int borderSize, x, y;
	private Boolean state;
	
	public ColorButton(int width, int height, Color color, int borderWidth, Color borderCol, int x_coord, int y_coord)
	{
		// Member variable/attribute = parameter
		borderSize = borderWidth;
		drawColor = color;
		borderColor = borderCol;
		x = x_coord;
		y = y_coord;
		
		// Call some inherited methods from JLabel
		setMinimumSize(new Dimension(width, height));
		setPreferredSize(new Dimension(width, height));
	}
	
	protected void paintComponent(Graphics g)
	{
		if(borderColor != null)
		{
			g.setColor(borderColor);
			g.fillRect(0,  0,  getWidth(), getHeight());
		}
		
		if(drawColor != null)
		{
			g.setColor(drawColor);
			g.fillRect(borderSize, borderSize, getWidth() - borderSize*2, getHeight() - borderSize*2);
		}
		
		if(state != null)
		{
			g.setColor(state==false?Color.white:Color.black);
			g.fillOval(1, 1, getWidth()-2, getHeight()-2);
			
			g.setColor(state==false?Color.black:Color.white);
			g.fillOval(2, 2, getWidth()-4, getHeight()-4);
		}
	}
	
	public int getx()
	{
		return x;
	}
	
	public int gety()
	{
		return y;
	}
	
	public void setState(Boolean counter)
	{
		if(state != counter)
		{
			state = counter;
		}
	}

	
}
