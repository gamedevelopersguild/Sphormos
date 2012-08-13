package gdg.atthack.sphormos.managers;

import gdg.atthack.sphormos.Game;
import gdg.atthack.sphormos.entities.PlayerExpBar;
import gdg.atthack.sphormos.events.game.PlayerLose;
import gdg.atthack.sphormos.events.game.PlayerWin;
import gdg.atthack.sphormos.states.game.GameMenuScreen;
import gdg.atthack.sphormos.states.game.GameScreen;
import android.graphics.Canvas;
import utils.game.tools.event.Event;
import utils.game.tools.fsm.GameState;
import utils.game.tools.main.Entity;

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
public class LevelManager extends Entity
{
	private double m_nStartSize, m_dEndSize, m_dCurrentSize;
	
	private static LevelManager m_Instance = null;
	
	private PlayerExpBar m_pxExpBar;
	private GameState m_osOwner;
	
	private LevelManager()
	{
		m_pxExpBar = new PlayerExpBar();
		m_pxExpBar.initializeEntity();
		m_pxExpBar.resizeExpBar(0);
	}
	
	public void dispose()
	{
		m_Instance = null;
	}
	
	public static LevelManager getInstance()
	{
		if(m_Instance == null)
			m_Instance = new LevelManager();
		
		return m_Instance;
	}
	
	public void setLevelLimits(double start, double end)
	{
		m_nStartSize = start;
		m_dCurrentSize = start;
		m_dEndSize = end;
		updateExpBar();
	}
	
	private void updateExpBar()
	{
		if(m_nStartSize>=m_dEndSize)
		{
			m_nStartSize = m_dEndSize;
			// Signal Level End;
		}
		else if(m_nStartSize<=0)
		{
			m_nStartSize = 0;
			// Signal Level Loss
		}
		
		m_pxExpBar.resizeExpBar((m_dEndSize-m_dCurrentSize)/m_dEndSize);
			
	}
	public void addLife(double lifeDelta)
	{
		m_dCurrentSize +=lifeDelta;
		
		if(m_dCurrentSize > m_dEndSize)
		{
			// Signal Level Won
			GameScreen owner;
			if(m_osOwner instanceof GameScreen)
			{
				owner = (GameScreen)m_osOwner;
				owner.getOwnwer().getStateMachine().handleEvent(new PlayerWin());
			}
		}
		else if(m_dCurrentSize<=0)
		{
			// Signal Level Loss
			GameScreen owner;
			if(m_osOwner instanceof GameScreen)
			{
				owner = (GameScreen)m_osOwner;
				owner.getOwnwer().getStateMachine().handleEvent(new PlayerLose());
			}
		}
		
		updateExpBar();
	}
	
	public void setOwnerState(GameState owner)
	{
		m_osOwner = owner;
	}

	@Override
	public void draw(Canvas spriteBatch) 
	{
		// TODO Auto-generated method stub
		m_pxExpBar.draw(spriteBatch);
	}
	/*
	public void setStart(double start)
	{
		m_nStartSize = start;
	}
	public void setEnd(double end)
	{
		m_dEndSize = end;
	}
*/
	@Override
	public void update(double gameTime) 
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void initializeEntity() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleEvent(Event event) {
		// TODO Auto-generated method stub
		m_pxExpBar.handleEvent(event);
	}
	
}
