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

/**
 * 
 * This class broadcasts the desired event out 
 * to all the registered participants. 
 * 
 *
 */
public class EventDispatcher 
{
	ArrayList<Entity> m_registeredSubscribers;
	
	private EventDispatcher()
	{
		m_registeredSubscribers = new ArrayList<Entity>();
	}
	
	private static EventDispatcher m_Instance = null;
	
	public static EventDispatcher getInstance()
	{
		if(m_Instance == null)
			m_Instance = new EventDispatcher();
		
		return m_Instance;
	}
	
	/**
	 * Register the given entity as a listener 
	 * to this dispatcher. 
	 * 
	 * @param ent entity object to remove from the list.
	 */
	public void registerSubscriber(Entity ent)
	{
		if(m_registeredSubscribers == null)
			return;
		
		if(m_registeredSubscribers.contains(ent))
			return;
		
		m_registeredSubscribers.add(ent);
	}
	
	/**
	 * Removes the given entity as a listener 
	 * from this dispatcher. 
	 * 
	 * @param ent entity object to remove from the list.
	 */
	public void unregisterSubscriber(Entity ent)
	{
		if(m_registeredSubscribers == null)
			return;
		
		if(!m_registeredSubscribers.contains(ent))
			return;
		
		m_registeredSubscribers.remove(ent);
	}
	
	/**
	 * This method will dispatch the given event 
	 * to all registered subscribers. 
	 * 
	 * @param thisEvent event to dispatch.
	 */
	public void dispatchEvent(Event thisEvent)
	{
		for(int i = 0; i < m_registeredSubscribers.size(); i++)
		{
			m_registeredSubscribers.get(i).handleEvent(thisEvent);
		}
	}
	
	/**
	 * This method removes every subscriber from the list
	 * and resets the instance held by the singleton instance 
	 * to null.
	 */
	public void restDispatcher()
	{
		while(m_registeredSubscribers.size()>0)
		{
			m_registeredSubscribers.remove(0);
		}
		
		m_Instance = null;
		
	}
}
