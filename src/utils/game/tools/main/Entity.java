package utils.game.tools.main;

import utils.game.tools.event.Event;
import utils.game.tools.fsm.GameStateMachine;
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

/**
 * Base class that defines the entities for the game.
 * Every entity has a set of functionality they must 
 * implement. 
 * 
 */
public abstract class Entity 
{
	
	
	/**
	 * This method should implement the draw logic for 
	 * the current entity. 
	 * @param spriteBatch Canvas object to draw on.
	 */
	public abstract void draw(Canvas spriteBatch);
	
	/**
	 * This method should implement the update logic for
	 * the current entity.
	 * @param gameTime elapsed time in milliseconds.
	 */
	public abstract void update(double gameTime);

	/**
	 * This method should implement any initialization
	 * that must be performed for this entity. 
	 * Since some resources may not be available during 
	 * the creation of the entity object, you could still
	 * initialize the entity at some later time when the 
	 * resources are available. 
	 */
	public abstract void initializeEntity();
	
	/**
	 * This method delegates the handling of the 
	 * event to the state machine for this entity,
	 * if any.
	 * @param event event to handle
	 */
	public abstract void handleEvent(Event event);
}
