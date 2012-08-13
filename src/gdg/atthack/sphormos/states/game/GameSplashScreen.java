package gdg.atthack.sphormos.states.game;

import gdg.atthack.sphormos.Game;
import gdg.atthack.sphormos.main.BitmapContainer;
import utils.game.tools.event.Event;
import utils.game.tools.event.ScreenResizeEvent;
import utils.game.tools.event.TouchDownEvent;
import utils.game.tools.fsm.GameState;
import utils.game.tools.main.ScreenSizeManager;
import android.R;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * This class contains all the logic and implementation for
 * the Splash Screen of the game. 
 * 
 * @author Frank E. Hernandez
 *
 */
public class GameSplashScreen extends GameState<Game> 
{

	private Paint m_drawPaint;
	private Paint m_clearPaint;

	private float m_drawLeft;
	private float m_drawTop;
	
	private int m_stopAnimTime = 500;
	private int m_currAnimTime = 0;
	
	private double m_dSplashLeave; 
	private double m_dSplashDelay = 3000;
	
	private Rect m_rectDest;
	private Rect m_rectSrc;
	
	@Override
	public void onUpdate(Game owner) 
	{
	
		long now = System.currentTimeMillis();
		// TODO Auto-generated method stub
		if(m_currAnimTime < m_stopAnimTime)
		{
			m_currAnimTime +=20; //((m_currAnimTime+10) % m_stopAnimTime);
			m_currAnimTime = Math.min(m_currAnimTime, 255);
			m_drawPaint.setAlpha(m_currAnimTime);
		}
		else if(now > m_dSplashLeave)
		{
		
			//owner.getStateMachine().changeState(new GameTitleMainScreen());
			owner.handleEvent(new TouchDownEvent(0, 0));
		}
		//m_drawLeft += m_currAnimTime;
	}

	@Override
	public void onDraw(Game owner, Canvas canvas) 
	{
		
		// Draw the spalsh screen.
		//canvas.drawBitmap(owner.getSplashScreenImage(), m_drawLeft, m_drawTop, m_drawPaint);
		BitmapContainer.getInstance().drawSafely(canvas, 
				owner.getSplashScreenImage(), m_rectSrc, m_rectDest, 
				m_drawPaint);
		
		//canvas.drawText("Curr Time: "+m_currAnimTime, 0, m_drawPaint.getTextSize(), m_drawPaint);
	}

	@Override
	public void onEnter(Game owner) 
	{
		m_drawPaint = new Paint();
		m_drawPaint.setARGB(0, 255, 255, 255);
		m_drawPaint.setAntiAlias(true);
		m_drawPaint.setTextSize(42.0f);
		m_drawPaint.setStyle(Paint.Style.FILL);
		
		m_currAnimTime = 100;
		
		m_rectSrc = new Rect(0, 0, 
				BitmapContainer.getInstance().getSplashScreenImage().getWidth(), 
				BitmapContainer.getInstance().getSplashScreenImage().getHeight());
		
		m_rectDest = new Rect(0, 0, 0, 0);
		ScreenSizeManager screenMan = ScreenSizeManager.getInstance();
		m_rectDest.right = m_rectDest.left + screenMan.getCorrectedX(m_rectSrc.right);
		m_rectDest.bottom = m_rectDest.top + screenMan.getCorrectedY(m_rectSrc.bottom);
		
		//calculateGDGPosition(owner);
		// Prepare splash leave time
		m_dSplashLeave = System.currentTimeMillis() + m_dSplashDelay;
		
	}

	@Override
	public void onExit(Game owner) 
	{
		// TODO Auto-generated method stub
	
	}

	@Override
	public void handleEvent(Game owner, Event event) 
	{
		// If the player taps the screen then we 
		// need to move to the main screen.
		if(event instanceof TouchDownEvent)
		{
			//owner.getStateMachine().changeState(new GameTitleMainScreen());
			//owner.getStateMachine().changeState(new GameScreen());
			owner.getStateMachine().changeState(new GameMenuScreen());
			
		}
		// If there is a change in the size of the
		// screen, then recalculate the position of the 
		// GDG Logo.
		else if(event instanceof ScreenResizeEvent)
		{
			calculateGDGPosition(owner);
		}
	}

	/**
	 * This method calculates the coordinates to 
	 * center the GDG logo in the device screen.
	 * @param owner
	 */
	private void calculateGDGPosition(Game owner)
	{
		m_rectDest.left = (int)((owner.getScreenWidth() - ScreenSizeManager.getInstance().getCorrectedX(owner.getSplashScreenImage().getWidth())) /2.0);
		m_rectDest.top = (int)((owner.getScreenHeight() - ScreenSizeManager.getInstance().getCorrectedY(owner.getSplashScreenImage().getHeight())) /2.0);
	
	}
}
