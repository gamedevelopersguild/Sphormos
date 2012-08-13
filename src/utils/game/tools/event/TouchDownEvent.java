package utils.game.tools.event;


public class TouchDownEvent extends Event 
{
	private int m_xPos;
	private int m_yPos;
	
	public TouchDownEvent(int x, int y)
	{
		m_xPos = x;
		m_yPos = y;
	}
	
	public int getX()
	{
		return m_xPos;
	}
	
	public int getY()
	{
		return m_yPos;
	}
	
}
