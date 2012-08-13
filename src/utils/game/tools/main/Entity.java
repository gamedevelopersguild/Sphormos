package utils.game.tools.main;

import utils.game.tools.event.Event;
import utils.game.tools.fsm.GameStateMachine;
import android.graphics.Canvas;

/**
 * Base class that defines the entities for the game.
 * Every entity has a set of functionality they must 
 * implement. 
 * 
 * @author Frank Hernandez
 * @date 03/07/2011
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
