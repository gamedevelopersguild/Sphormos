package gdg.atthack.sphormos.states.game;

import gdg.atthack.sphormos.Game;
import gdg.atthack.sphormos.main.BitmapContainer;
import gdg.atthack.sphormos.main.SoundContainer;
import gdg.atthack.sphormos.managers.LevelManager;
import android.graphics.Canvas;
import android.graphics.Rect;
import utils.game.tools.event.Event;
import utils.game.tools.event.TouchDownEvent;
import utils.game.tools.event.activity.ActivityPauseEvent;
import utils.game.tools.event.activity.ActivityResumeEvent;
import utils.game.tools.event.activity.ActivityStopEvent;
import utils.game.tools.fsm.GameState;
import utils.game.tools.main.GameActivityNotifier;
import utils.game.tools.main.ScreenSizeManager;

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
public class GameMenuScreen extends GameState<Game>
{

	Rect m_rectDest, m_rectSrc;
	// Live areas for the Start and Credits Button.
	Rect m_rectStartBtn, m_rectCreditsBtn;
	@Override
	public void onUpdate(Game owner) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDraw(Game owner, Canvas canvas) 
	{
		// TODO Auto-generated method stub
		BitmapContainer.getInstance().drawSafely(canvas, 
				BitmapContainer.getInstance().getMainMenuImage(), 
				m_rectSrc, m_rectDest, 
				BitmapContainer.getInstance().getPaint());
		
		
	}

	@Override
	public void onEnter(Game owner) 
	{
		// TODO Auto-generated method stub
		m_rectSrc = new Rect(0, 
				0, 
				BitmapContainer.getInstance().getMainMenuImage().getWidth(), 
				BitmapContainer.getInstance().getMainMenuImage().getHeight());
		
		ScreenSizeManager screenMan = ScreenSizeManager.getInstance();
		m_rectDest = new Rect(0, 0, 
				screenMan.getCorrectedX(BitmapContainer.getInstance().getMainMenuImage().getWidth()), 
				screenMan.getCorrectedY(BitmapContainer.getInstance().getMainMenuImage().getHeight()));
		
		
		m_rectStartBtn = new Rect(
				screenMan.getCorrectedX(286), 
				screenMan.getCorrectedY(220), 
				screenMan.getCorrectedX(521), 
				screenMan.getCorrectedY(288));
		
		m_rectCreditsBtn = new Rect(
				screenMan.getCorrectedX(286), 
				screenMan.getCorrectedY(332), 
				screenMan.getCorrectedX(521), 
				screenMan.getCorrectedY(402));
		
		
	}

	@Override
	public void onExit(Game owner) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleEvent(Game owner, Event event)
	{
		// TODO Auto-generated method stub
		if(event instanceof TouchDownEvent)
		{
			TouchDownEvent touchEvent = (TouchDownEvent)event;
			if(m_rectStartBtn.contains(touchEvent.getX(), touchEvent.getY()))
			{
				owner.getStateMachine().changeState(new LevelPickScreen());
			}
			else if(m_rectCreditsBtn.contains(touchEvent.getX(), touchEvent.getY()))
			{
				owner.getStateMachine().changeState(new GameCreditsScreen());
			}
			else if(event instanceof ActivityPauseEvent)
			{
				GameActivityNotifier.getInstance().dispatcEvent(event);
			}
			
			else if(event instanceof ActivityResumeEvent)
			{
				GameActivityNotifier.getInstance().dispatcEvent(event);
			}
			
			else if(event instanceof ActivityStopEvent)
			{
				GameActivityNotifier.getInstance().dispatcEvent(event);
			}
		}
		
	}

}
