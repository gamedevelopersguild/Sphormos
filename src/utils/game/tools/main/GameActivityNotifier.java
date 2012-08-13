package utils.game.tools.main;

import java.util.ArrayList;

import utils.game.tools.event.Event;

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

public class GameActivityNotifier 
{
	public ArrayList<GameActivity> m_arrSubscribers = null;
	
	private static GameActivityNotifier m_Instance = null;
	
	private GameActivityNotifier()
	{
		m_arrSubscribers = new ArrayList<GameActivity>();
	}
	
	public static GameActivityNotifier getInstance()
	{
		if(m_Instance == null)
			m_Instance = new GameActivityNotifier();
		
		return m_Instance;
	}
	
	public void registerGameActivity(GameActivity act)
	{
		if(m_arrSubscribers.contains(act))
			return;
		
		m_arrSubscribers.add(act);
	}
	
	public void unregisterGameActivity(GameActivity act)
	{
		if(!m_arrSubscribers.contains(act))
			return;
		
		m_arrSubscribers.remove(act);
	}
	
	public void releaseResources()
	{
		m_arrSubscribers = null;
		m_Instance = null;
	}
	
	public void dispatcEvent(Event event)
	{
		if(m_arrSubscribers == null)
			return;
		
		for(int i = 0; i < m_arrSubscribers.size(); i++)
		{
			m_arrSubscribers.get(i).handleEvent(event);
		}
	}
}
