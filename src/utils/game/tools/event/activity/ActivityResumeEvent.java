package utils.game.tools.event.activity;

import utils.game.tools.event.Event;
import android.content.SharedPreferences;

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
