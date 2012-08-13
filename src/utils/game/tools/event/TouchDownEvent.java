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
