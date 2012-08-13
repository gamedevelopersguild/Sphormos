package gdg.atthack.sphormos.entities;

import gdg.atthack.sphormos.events.game.MoveEvent;
import gdg.atthack.sphormos.events.game.PlayerChangeColor;
import gdg.atthack.sphormos.managers.LevelManager;
import gdg.atthack.sphormos.managers.SphormosColorManager;

import java.util.Vector;

import utils.game.tools.event.Event;
import utils.game.tools.main.ScreenSizeManager;
import android.graphics.Canvas;

public class PlayerEntity extends SphormosEntity
{

	private double targetX, targetY;
	public PlayerEntity(int xPos, int yPos, double health)
	{
		m_currX = ScreenSizeManager.getInstance().getCorrectedX(xPos);
		m_currY = ScreenSizeManager.getInstance().getCorrectedY(yPos);
		m_nCurrentColorIndex = 0;
		m_dHealth = health;
		m_dRadius = getRadius();
		targetX = m_currX;
		targetY = -1*m_currY;
		
	}
	@Override
	public void draw(Canvas spriteBatch) 
	{
		//float x = m_currX;
		//float y = m_currY;
		// TODO Auto-generated method stub
		spriteBatch.drawCircle((int)m_currX, (int)m_currY, (int)m_dRadius, SphormosColorManager.getInstance().getColor(m_nCurrentColorIndex));
		
		//spriteBatch.drawLine((int)m_currX, (int)m_currY, (float)(m_currX+targetX), (float)(m_currY+targetY), SphormosColorManager.getInstance().getColor(4));
		
	}
	@Override
	public void addLife(double lifeDelta) 
	{
		super.addLife(lifeDelta);
		LevelManager.getInstance().addLife(lifeDelta);
	}

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
	public void handleEvent(Event event) 
	{
		// TODO Auto-generated method stub
		if(event instanceof PlayerChangeColor)
		{
			PlayerChangeColor colorEvent = (PlayerChangeColor)event;
			m_nCurrentColorIndex = colorEvent.colorIndex;
		}
		if(event instanceof MoveEvent)
		{
			MoveEvent move = (MoveEvent)event;
			targetX = move.m_dXdelta *10;
			targetY = move.m_dYdelta *10;
			
		}
	}
	
	
}
