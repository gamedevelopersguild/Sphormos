package gdg.atthack.sphormos.entities;


import gdg.atthack.sphormos.main.BitmapContainer;
import android.graphics.Canvas;
import android.graphics.Rect;
import utils.game.tools.event.Event;
import utils.game.tools.main.Entity;
import utils.game.tools.main.ScreenSizeManager;

/**
 * This class handles the drawing of the 
 * experience bar. It deals with all the logic
 * of drawing the right of bar along for the current 
 * experience based on the maximum experience.
 * @author CodeMinion
 *
 */
public class PlayerExpBar extends Entity
{
	private Rect m_rectSrc;
	private Rect m_rectSrc2;
	private Rect m_rectDest;
	private Rect m_rectDestXP;
	private Rect m_rectDestXPOrig;
	
	
	public PlayerExpBar()
	{
				
	
	}
	@Override
	public void draw(Canvas spriteBatch) 
	{

		BitmapContainer.getInstance().drawSafely(spriteBatch, 
				BitmapContainer.getInstance().getExpBarSprites()[1], 
				m_rectSrc, m_rectDest, BitmapContainer.getInstance().getPaint());

		BitmapContainer.getInstance().drawSafely(spriteBatch,
				BitmapContainer.getInstance().getExpBarSprites()[0], 
				m_rectSrc2, m_rectDestXP, BitmapContainer.getInstance().getPaint());
		
	}
	@Override
	public void update(double gameTime) 
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void initializeEntity() 
	{
		if(BitmapContainer.getInstance().getExpBarSprites() == null 
				|| BitmapContainer.getInstance().getExpBarSprites()[1] == null 
				|| BitmapContainer.getInstance().getExpBarSprites()[0]== null)
			return; 
		
		m_rectSrc = new Rect(0, 0, 
				BitmapContainer.getInstance().getExpBarSprites()[1].getWidth(), 
				BitmapContainer.getInstance().getExpBarSprites()[1].getHeight());
		
		m_rectSrc2 = new Rect(0, 0, 
				BitmapContainer.getInstance().getExpBarSprites()[0].getWidth(), 
				BitmapContainer.getInstance().getExpBarSprites()[0].getHeight());
		
	
		ScreenSizeManager screenMan = ScreenSizeManager.getInstance();
		m_rectDest = new Rect(0, 
							screenMan.getCorrectedY(0),
							0, 0);
		
		
	
		m_rectDest.right = m_rectDest.left + screenMan.getCorrectedX(m_rectSrc.right);
		m_rectDest.bottom = m_rectDest.top + screenMan.getCorrectedY(m_rectSrc.bottom);
		
		m_rectDestXP = new Rect(screenMan.getCorrectedX(24), 
								screenMan.getCorrectedY(25),
								0, 0);
		
		m_rectDestXP.right = m_rectDestXP.left + screenMan.getCorrectedX(m_rectSrc2.right);
		m_rectDestXP.bottom = m_rectDestXP.top + screenMan.getCorrectedY(m_rectSrc2.bottom);
		
		m_rectDestXPOrig = new Rect(m_rectDestXP.left, m_rectDestXP.top, m_rectDestXP.right, m_rectDestXP.bottom);

		
	}
	
	public void resizeExpBar(double ratio) 
	{
		if(m_rectDestXP == null)
			return;
		
		m_rectDestXP.left = (int)(m_rectDestXPOrig.left);
		ScreenSizeManager screenMan = ScreenSizeManager.getInstance();
		double newWidth = screenMan.getCorrectedX(BitmapContainer.getInstance().getExpBarSprites()[0].getWidth())*(ratio);
		m_rectDestXP.top = (int)(m_rectDestXPOrig.top);
		m_rectDestXP.right = (int)(m_rectDestXPOrig.right- newWidth);
		m_rectDestXP.bottom = (int)(m_rectDestXPOrig.bottom);
	}
	@Override
	public void handleEvent(Event event) 
	{
		// TODO Auto-generated method stub
		
	}
}
