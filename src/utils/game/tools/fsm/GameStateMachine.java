package utils.game.tools.fsm;

import utils.game.tools.event.Event;
import android.graphics.Canvas;

public class GameStateMachine <T> 
{
	private GameState<T> m_prevState;
	private GameState<T> m_currState;
	private T m_machineOwner;
	
	public GameStateMachine(T owner)
	{
		this.m_machineOwner = owner;
		m_currState = null;
		m_prevState = null;
	}
	
	/**
	 * This method performs the logic needed to switch 
	 * to the new state. It takes care of doing any 
	 * clean up needed by the state by calling the state's
	 * onExit method. It also performs any set up that is 
	 * required by the new state by calling the new state's 
	 * onEnter method. 
	 * 
	 * Also, before any switching is performed, it stores
	 * the current state as a previous state so that we can
	 * easily revert one step back if needed. 
	 * 
	 * @param newState New state to transfer to.
	 */
	public void changeState(GameState<T> newState)
	{
		if(newState == null)
			return;
		
		if(m_currState != null)
		{
			m_currState.onExit(m_machineOwner);
			m_prevState = m_currState;
		}
		
		m_currState = newState;
		m_currState.onEnter(m_machineOwner);
	}
	
	/**
	 * This method causes the machine to move back
	 * to the previous state if any. This only moves
	 * back one step because we don't keep any memory 
	 * further than that. 
	 */
	public void revertState()
	{
		changeState(m_prevState);
	}
	
	/**
	 * This method returns the current state the machine is
	 * on. 
	 * @return The current state the machine is on.
	 */
	public GameState<T> getCurrentState()
	{
		return m_currState;
	}
	
	/**
	 * This method delegates the drawing its current state.
	 * @param canvas Canvas to draw on.
	 */
	public void onDraw(Canvas canvas)
	{
		if(this.getCurrentState() == null)
			return ; 
		
		this.getCurrentState().onDraw(m_machineOwner, canvas);
	}
	
	/**
	 * This method delegates the update to its current state.
	 */
	public void onUpdate()
	{
		if(this.getCurrentState() == null)
			return; 
		
		this.getCurrentState().onUpdate(m_machineOwner);
	}
	
	/**
	 * This method delegates the handling of the event
	 * to the current state.
	 * @param event event to handle
	 */
	public void handleEvent(Event event)
	{
		if(this.getCurrentState() == null)
			return;
		
		// If the app is closing then just call the 
		// on exit for the current state. That should 
		// take care of any clean up needed. 
		//if(event instanceof AppCloasedEvent)
		//{
		//	this.getCurrentState().onExit(m_machineOwner);
		//	return;
		//}
		// Otherwise send the events to the current state to be 
		// handled there.
		this.getCurrentState().handleEvent(m_machineOwner, event);
	}
	
}
