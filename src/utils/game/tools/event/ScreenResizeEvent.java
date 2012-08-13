package utils.game.tools.event;


/**
 * This is an event internal to the game for signaling the 
 * occurrence of a change on screen size; 
 * 
 * This event should be signaled whenever there is a change
 * in the screen size. 
 * 
 * @author CodeMinion
 *
 */
public class ScreenResizeEvent extends Event
{
	private int m_newWidth;
	private int m_newHeight;
	
	public ScreenResizeEvent(int newWidth, int newHeight)
	{
		m_newHeight = newHeight;
		m_newWidth = newWidth;
	}
	
	public int getNewWdth()
	{
		return m_newWidth;
	}
	
	public int getNewHeight()
	{
		return m_newHeight;
	}
}
