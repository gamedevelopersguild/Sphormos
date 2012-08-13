package gdg.atthack.sphormos.states.game;

import gdg.atthack.sphormos.Game;
import gdg.atthack.sphormos.SphormosMainActivity;
import gdg.atthack.sphormos.entities.PlayerEntity;
import gdg.atthack.sphormos.entities.PlayerExpBar;
import gdg.atthack.sphormos.events.game.BackOne;
import gdg.atthack.sphormos.events.game.LevelStartEvent;
import gdg.atthack.sphormos.events.game.MoveEvent;
import gdg.atthack.sphormos.events.game.PlayerLose;
import gdg.atthack.sphormos.events.game.PlayerWin;
import gdg.atthack.sphormos.main.BitmapContainer;
import gdg.atthack.sphormos.main.SoundContainer;
import gdg.atthack.sphormos.managers.EnemyManager;
import gdg.atthack.sphormos.managers.LevelManager;
import gdg.atthack.sphormos.managers.SphormosColorManager;
import utils.game.tools.event.Event;
import utils.game.tools.event.SaveEvent;
import utils.game.tools.event.ScreenResizeEvent;
import utils.game.tools.event.TouchDownEvent;
import utils.game.tools.event.activity.ActivityPauseEvent;
import utils.game.tools.event.activity.ActivityResumeEvent;
import utils.game.tools.event.activity.ActivityStopEvent;
import utils.game.tools.fsm.GameState;
import utils.game.tools.main.EventDispatcher;
import utils.game.tools.main.GameActivityNotifier;
import utils.game.tools.main.ScreenSizeManager;
import android.R;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

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

/**
 * This class contains all the logic and implementation for
 * the Splash Screen of the game. 
 * 
 *
 */
public class GameScreen extends GameState<Game> 
{

	private static final String XP_SAVE_KEY = "Pet_XP";
	private static final String XP_LEVEL_COUNTER = "Pet_Level_Counter";
	
	private Paint m_drawPaint;
	private Paint m_clearPaint;

	private float m_drawLeft;
	private float m_drawTop;
	
	private int m_stopAnimTime = 500;
	private int m_currAnimTime = 0;
	
	private double m_dSplashLeave; 
	private double m_dSplashDelay = 3000;
	
	private PlayerEntity m_pePlayer;
	private Game m_osOwner;
	
	@Override
	public void onUpdate(Game owner)  
	{
	
		long now = System.currentTimeMillis();
		EnemyManager.getInstance().update(now);
		EnemyManager.getInstance().checkPlayerCollision(m_pePlayer);
	}

	@Override
	public void onDraw(Game owner, Canvas canvas) 
	{
		EnemyManager.getInstance().draw(canvas);
		m_pePlayer.draw(canvas);
		
		LevelManager.getInstance().draw(canvas);
	}
	public Game getOwnwer()
	{
		return m_osOwner;
	}

	@Override
	public void onEnter(Game owner) 
	{
		m_osOwner = owner;
		m_drawPaint = new Paint();
		m_drawPaint.setARGB(255, 255, 255, 255);
		m_drawPaint.setAntiAlias(true);
		m_drawPaint.setTextSize(42.0f);
		m_drawPaint.setStyle(Paint.Style.FILL);
		

		SoundContainer.getInstance().playBGMusic();
		m_pePlayer = new PlayerEntity(400, 240, 30);
		
		EventDispatcher.getInstance().registerSubscriber(m_pePlayer);
		LevelManager.getInstance().setOwnerState(this);
		
		//EnemyManager.getInstance().generateEnemyBatch(100, new Rect(1000, 400, 1000, 240), m_pePlayer, 3);
		loadGame();
		
		GameActivityNotifier.getInstance().dispatcEvent(new LevelStartEvent());
		
	}

	public void loadGame()
	{
		SharedPreferences savedState = Game.getGameContext().getSharedPreferences(SphormosMainActivity.PREFS_NAME, 0);
		int savedExp = savedState.getInt(XP_SAVE_KEY, 0);
		
		if(savedExp > 0)
		{
			SoundContainer.getInstance().setPlaySound(false);
			//m_PlayerCreature.m_nLevelCounter = savedState.getInt(XP_LEVEL_COUNTER, 0);
			
			SoundContainer.getInstance().setPlaySound(true);
			
		}
		
	}
	@Override
	public void onExit(Game owner) 
	{
		EventDispatcher.getInstance().unregisterSubscriber(m_pePlayer);
		
	}

	@Override
	public void handleEvent(Game owner, Event event) 
	{ 
		if(event instanceof PlayerWin)
		{
			// Go to win State
			owner.getStateMachine().changeState(new GameWinScreen());
			
		}
		if(event instanceof PlayerLose)
		{
			// Go to lose state
			owner.getStateMachine().changeState(new GameLoseScreen());		
		}
		if(event instanceof BackOne)
		{
			owner.getStateMachine().changeState(new LevelPickScreen());
			
		}
		if(event instanceof TouchDownEvent)
		{
			if(m_pePlayer != null && m_pePlayer.isAlive())
				EnemyManager.getInstance().handleEvent(new MoveEvent(-5, -5));
		}
		else if(event instanceof SaveEvent)
		{
			SaveEvent saveEvent = (SaveEvent)event;
			//saveEvent.m_map.putInt(XP_SAVE_KEY, m_PlayerExpManager.getCurrentExperience());
			//saveEvent.m_map.putInt(XP_SAVE_KEY, 15000000);
			
			//saveEvent.m_map.putInt(XP_LEVEL_COUNTER, m_PlayerCreature.m_nLevelCounter);
			
			saveEvent.m_map.commit();
			
		}
		else if(event instanceof ActivityPauseEvent)
		{
			SoundContainer.getInstance().stopBGMusic();
		}
		
		else if(event instanceof ActivityResumeEvent)
		{
			SoundContainer.getInstance().playBGMusic();
		}
		
		else if(event instanceof ActivityStopEvent)
		{
			SoundContainer.getInstance().stopBGMusic();
			// If the user pressed the back arrow and we are on the 
			// game screen the just take him to the menu screen.
			owner.getStateMachine().changeState(new GameMenuScreen());
			
			
		}
	}

}
