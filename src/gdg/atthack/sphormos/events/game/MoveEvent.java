package gdg.atthack.sphormos.events.game;

import utils.game.tools.event.Event;

public class MoveEvent extends Event
{
	public double m_dXdelta;
	public double m_dYdelta;
	
	public MoveEvent(double xDelta, double yDelta)
	{
		m_dXdelta = xDelta;
		m_dYdelta = yDelta;
	}
}
