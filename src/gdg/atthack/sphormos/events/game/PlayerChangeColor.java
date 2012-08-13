package gdg.atthack.sphormos.events.game;

import utils.game.tools.event.Event;

public class PlayerChangeColor extends Event{

	public int colorIndex;
	
	public PlayerChangeColor(int newIndex)
	{
		colorIndex = newIndex;
	}
}
