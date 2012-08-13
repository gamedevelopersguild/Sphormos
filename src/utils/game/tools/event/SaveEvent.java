package utils.game.tools.event;

import android.content.SharedPreferences;

public class SaveEvent extends Event
{

	public SharedPreferences.Editor m_map;
	public SaveEvent(SharedPreferences.Editor map)
	{
		m_map = map;
	}
}
