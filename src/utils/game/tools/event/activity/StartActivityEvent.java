package utils.game.tools.event.activity;

import utils.game.tools.event.Event;
import android.content.Intent;

public class StartActivityEvent extends Event
{
	public Intent activityIntent;
	
	public StartActivityEvent(Intent intent)
	{
		activityIntent = intent;
	}
	
}
