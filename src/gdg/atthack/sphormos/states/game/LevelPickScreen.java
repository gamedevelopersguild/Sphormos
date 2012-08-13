package gdg.atthack.sphormos.states.game;

import gdg.atthack.sphormos.Game;
import gdg.atthack.sphormos.entities.PlayerEntity;
import gdg.atthack.sphormos.events.game.BackOne;
import gdg.atthack.sphormos.events.game.LevelStartEvent;
import gdg.atthack.sphormos.main.BitmapContainer;
import gdg.atthack.sphormos.main.SoundContainer;
import gdg.atthack.sphormos.managers.EnemyManager;
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
public class LevelPickScreen extends GameState<Game>
{

	Rect m_rectDest, m_rectSrc;
	// Live areas for the Start and Credits Button.
	Rect m_lvl1Btn, m_lvl2Btn, m_lvl3Btn;
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
				BitmapContainer.getInstance().getLevelPick(), 
				m_rectSrc, m_rectDest, 
				BitmapContainer.getInstance().getPaint());
		
		//canvas.drawRect(m_rectCreditsBtn, BitmapContainer.getInstance().getPaint());
		//canvas.drawRect(m_rectStartBtn, BitmapContainer.getInstance().getPaint());

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
		
		
		m_lvl1Btn = new Rect(
				screenMan.getCorrectedX(85), 
				screenMan.getCorrectedY(191), 
				screenMan.getCorrectedX(239), 
				screenMan.getCorrectedY(339));
		
		m_lvl2Btn = new Rect(
				screenMan.getCorrectedX(330), 
				screenMan.getCorrectedY(191), 
				screenMan.getCorrectedX(430), 
				screenMan.getCorrectedY(339));
		
		m_lvl3Btn = new Rect(
				screenMan.getCorrectedX(482), 
				screenMan.getCorrectedY(191), 
				screenMan.getCorrectedX(726), 
				screenMan.getCorrectedY(339)); 
		
	}

	@Override
	public void onExit(Game owner) 
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void handleEvent(Game owner, Event event)
	{
		if(event instanceof BackOne)
		{
			owner.getStateMachine().changeState(new GameMenuScreen());		
		}
		
		// TODO Auto-generated method stub
		if(event instanceof TouchDownEvent)
		{
			TouchDownEvent touchEvent = (TouchDownEvent)event;
			if(m_lvl1Btn.contains(touchEvent.getX(), touchEvent.getY()))
			{
				int amounOfEnemies = 20;
				
				int startHealth = 5;
				int endHealth = 50;
				int enemyMaxSize = 40;
				
				/*
				int startHealth = 78;
				int endHealth = 7853;
				int enemyMaxSize = 5026;
				*/
				PlayerEntity m_pePlayer = new PlayerEntity(400, 240, startHealth);
				EnemyManager.getInstance().generateEnemyBatch(amounOfEnemies, new Rect(1000, 400, 1000, 240), m_pePlayer, 1, enemyMaxSize);
				LevelManager.getInstance().setLevelLimits(startHealth, endHealth);
				owner.getStateMachine().changeState(new GameScreen());
					
			}
			else if(m_lvl2Btn.contains(touchEvent.getX(), touchEvent.getY()))
			{
				int amounOfEnemies = 50;
				
				int startHealth = 5;
				int endHealth = 100;
				int enemyMaxSize = 90;
				
				
				/*
				int startHealth = 78;
				int endHealth = 31415;
				int enemyMaxSize = 25446;
				*/
				PlayerEntity m_pePlayer = new PlayerEntity(400, 240, startHealth);
				EnemyManager.getInstance().generateEnemyBatch(amounOfEnemies, new Rect(1000, 400, 1000, 240), m_pePlayer, 2, enemyMaxSize);
				LevelManager.getInstance().setLevelLimits(startHealth, endHealth);
				owner.getStateMachine().changeState(new GameScreen());
				
			}
			else if(m_lvl3Btn.contains(touchEvent.getX(), touchEvent.getY()))
			{
				int amounOfEnemies = 100;
				int startHealth = 5;
				int endHealth = 200;
				int enemyMaxSize = 140;
				
				PlayerEntity m_pePlayer = new PlayerEntity(400, 240, startHealth);
				EnemyManager.getInstance().generateEnemyBatch(amounOfEnemies, new Rect(1000, 400, 1000, 240), m_pePlayer, 3, enemyMaxSize);
				LevelManager.getInstance().setLevelLimits(startHealth, endHealth);
				owner.getStateMachine().changeState(new GameScreen());
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
