package utils.game.tools.fsm;

import utils.game.tools.event.Event;
import android.graphics.Canvas;

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

public abstract class GameState <T> 
{

	
	/**
	 * The implementation of this method should contain
	 * the update logic for the given entity. This is 
	 * where all the work the entity should perform in a 
	 * single step should be place.
	 * @param owner Owner of the state.
	 */
	public abstract void onUpdate(T owner);
	
	/**
	 * The implementation of this method should contain
	 * the logic needed for drawing the given entity.
	 * @param canvas
	 * @param owner Owner of the state.
	 */
	public abstract void onDraw(T owner, Canvas canvas);
	
	/**
	 * The implementation of this method should contain
	 * the logic needed for setting up any logic that
	 * must be in place by the times this state updates.
	 * @param owner Owner of the state.
	 */
	public abstract void onEnter(T owner);
	
	/**
	 * The implementation of this method should contain
	 * the logic needed for performing any clean up 
	 * needed to occur before this state finishes executing.
	 * @param owner Owner of the state.
	 */
	public abstract void onExit(T owner);

	/**
	 * The implementation of this should contain the 
	 * logic to handle the given event if the event is 
	 * handled at all to begin with.
	 * @param event
	 */
	public abstract void handleEvent(T owner, Event event);
}
