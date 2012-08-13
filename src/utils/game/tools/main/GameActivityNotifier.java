package utils.game.tools.main;

import java.util.ArrayList;

import utils.game.tools.event.Event;

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
