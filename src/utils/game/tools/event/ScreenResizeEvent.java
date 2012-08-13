package utils.game.tools.event;


/**
 * Code created by the Game Developers Guild. Any derivative work must contain 
 * a copy of the source code and this comments. 
 * 
 * It would be nice if you add the official GDG splash to derivative work but not 
 * required.
 * 
 * 
 * Code Licensed under CC Attribution 3.0 Unported as found on this 
 * page: http://creativecommons.org/licenses/by/3.0/legalcode  
 * 
 * 
 * @author Game Developers Guild
 * Site: www.GameDevelopersGuild.com
 * Twitter: @GameDevGuild
 * Facebook: www.facebook.com/GameDevelopersGuild
 * Facebook: www.facebook.com/GameLoopMagazine
 */

/**
 * This is an event internal to the game for signaling the 
 * occurrence of a change on screen size; 
 * 
 * This event should be signaled whenever there is a change
 * in the screen size. 
 * 
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
