package utils.game.tools.event.activity;

import utils.game.tools.event.Event;
import android.content.SharedPreferences;


public class ActivityResumeEvent extends Event {

	private SharedPreferences m_settings;
	
	public ActivityResumeEvent(SharedPreferences settings)
	{
		m_settings = settings;
	}
	public SharedPreferences getSettings()
	{
		return m_settings;
	}
}
