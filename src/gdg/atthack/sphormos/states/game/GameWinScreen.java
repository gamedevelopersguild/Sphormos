package gdg.atthack.sphormos.states.game;

import gdg.atthack.sphormos.Game;
import gdg.atthack.sphormos.main.BitmapContainer;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.net.Uri;
import utils.game.tools.event.Event;
import utils.game.tools.event.TouchDownEvent;
import utils.game.tools.event.activity.StartActivityEvent;
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

public class GameWinScreen extends GameState<Game> 
{

	private Rect m_rectDest, m_rectSrc;
	
	// Used to allow the user to click on the guild address and
	// be taken to the guild page.
	private Rect m_guildTouchRect; 
	@Override
	public void onUpdate(Game owner) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDraw(Game owner, Canvas canvas) 
	{
		BitmapContainer.getInstance().drawSafely(canvas, 
				BitmapContainer.getInstance().getYouWinImage(), 
				m_rectSrc, m_rectDest,
				BitmapContainer.getInstance().getPaint());
		
	}

	@Override
	public void onEnter(Game owner) 
	{
		m_rectSrc = new Rect(0, 0, 
				BitmapContainer.getInstance().getYouWinImage().getWidth(), 
				BitmapContainer.getInstance().getYouWinImage().getHeight());
		
		ScreenSizeManager screenMan = ScreenSizeManager.getInstance();
		m_rectDest = new Rect(0, 0, 
				screenMan.getCorrectedX(m_rectSrc.right), 
				screenMan.getCorrectedY(m_rectSrc.bottom));
		
		m_guildTouchRect = new Rect(
				screenMan.getCorrectedX(68), 
				screenMan.getCorrectedY(420), 
				screenMan.getCorrectedX(698), 
				screenMan.getCorrectedY(474));
		
	}

	@Override
	public void onExit(Game owner) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleEvent(Game owner, Event event) 
	{
		if(event instanceof TouchDownEvent)
		{
			/*
			TouchDownEvent touch = (TouchDownEvent)event;
			if(m_guildTouchRect.contains(touch.getX(), touch.getY()))
			{
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, 
						Uri.parse("http://www.GameDevelopersGuild.com"));
				
				GameActivityNotifier.getInstance().dispatcEvent(new StartActivityEvent(browserIntent));
			}*/
			
			owner.getStateMachine().changeState(new LevelPickScreen());
		}
		
	}

	
}
