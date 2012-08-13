package gdg.atthack.sphormos.entities;

import gdg.atthack.sphormos.events.game.MoveEvent;
import gdg.atthack.sphormos.managers.SphormosColorManager;
import android.graphics.Canvas;
import utils.game.tools.event.Event;
import utils.game.tools.main.EventDispatcher;
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
public class EnemyEntity extends SphormosEntity
{
	
	public EnemyEntity(int x, int y, double health, int colorIndex, double speed)
	{
		m_currX = ScreenSizeManager.getInstance().getCorrectedX(x);
		m_currY = ScreenSizeManager.getInstance().getCorrectedY(y);
		
		m_dHealth = health;
		m_nCurrentColorIndex = colorIndex;
		m_dSpeed = speed;
		m_dRadius = getRadius();
		
	}

	@Override
	public void draw(Canvas spriteBatch) 
	{
		//if(m_currX <=0 || m_currY <= 0)
		//	return; 
		
		// TODO Auto-generated method stub 
		spriteBatch.drawCircle((int)m_currX, (int)m_currY, (int)m_dRadius, SphormosColorManager.getInstance().getColor(m_nCurrentColorIndex));
		 
		
	}

	@Override
	public void update(double gameTime) { 
		// TODO Auto-generated method stub
		//if(m_dHealth <=0)
		//	EventDispatcher.getInstance().unregisterSubscriber(this);
	}

	@Override
	public void initializeEntity() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleEvent(Event event) 
	{
		// TODO Auto-generated method stub
		if(event instanceof MoveEvent)
		{
			MoveEvent playerMover = (MoveEvent)event;
			/*
			if(m_currX < 0 && playerMover.m_dXdelta <0)
				m_currX += playerMover.m_dXdelta;
			else
				m_currX -= playerMover.m_dXdelta;
			
			if(m_currY < 0 && playerMover.m_dYdelta <0)
				m_currY += playerMover.m_dYdelta;
			else
				m_currY -= playerMover.m_dYdelta;
			*/
			m_currX += -1*playerMover.m_dXdelta;
			m_currY += playerMover.m_dYdelta;
			
			//m_currX += playerMover.m_dXdelta;
			//m_currY += playerMover.m_dYdelta;
			
					
		}
		
	}

	
}
