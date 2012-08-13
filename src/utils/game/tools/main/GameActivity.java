package utils.game.tools.main;

import utils.game.tools.event.Event;
import android.app.Activity;

public abstract class GameActivity extends Activity
{
	public abstract boolean handleEvent(Event event);
}
