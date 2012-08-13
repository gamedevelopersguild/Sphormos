package gdg.atthack.sphormos;

import gdg.atthack.sphormos.main.BitmapContainer;
import utils.game.tools.event.AppClosedEvent;
import utils.game.tools.event.Event;
import utils.game.tools.event.TouchDownEvent;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;


/**
 * Game Thread class taken from Donald E. Llopis workshop on android 
 * game development. 
 * 
 * https://github.com/machinezilla/DroidRunJump/downloads
 * 
 * It has been modified to match the need of this game.
 * 
 * @author Donald E. Llopis 
 * 
 * Modified by:
 * @author Frank E.Hernandez
 * 
 * Modifeid the doTouchEvent method.
 *
 */
public class GameThread extends Thread 
{
	
	private SurfaceHolder surfaceHolder;
	boolean run;
	public Game game;
	
	public GameThread(SurfaceHolder surfaceHolder, Context context, Game game) 
	{
		run = false;
		this.surfaceHolder = surfaceHolder;
		this.game = game;
	}
	
	public void setSurfaceSize(int width, int height) 
	{
		synchronized (surfaceHolder) 
		{
			game.setScreenSize(width, height);
		}
	}
	
	public void setRunning(boolean b) 
	{
		run = b;
	}
	public boolean isRunning()
	{
		return run;
	}
	
	@Override
	public void run() 
	{
		//
		// game loop
		//
		while (run) 
		{
			Canvas c = null;				
			try 
			{
				c = surfaceHolder.lockCanvas(null);
				synchronized (surfaceHolder) 
				{
					game.run(c);
				}
			} 
			finally 
			{
				if (c != null) 
				{
					surfaceHolder.unlockCanvasAndPost(c);
				}
			}
		}
	}
	
	public boolean doTouchEvent(MotionEvent event) 
	{
		boolean handled = false;

		synchronized (surfaceHolder) 
		{
			switch (event.getAction()) 
			{
			
				// This must be changed to signal
				// game events.
				case MotionEvent.ACTION_DOWN:
					game.handleEvent(new TouchDownEvent((int)event.getX(), (int)event.getY()));
					
					//game.doTouch();
					
					handled = true;
					break;
			}
		}
		
		return handled;			
	}
	
	public void pause() 
	{
		synchronized (surfaceHolder) 
		{				
			game.pause();
			run = false;
			//Log.d("TAG_KP", "Thread Pause");
	    	
		}
	}
	
	public void resetGame() 
	{
		synchronized (surfaceHolder) 
		{
			game.resetGame();
			//run = false;
			
		}
	}
	
	public void restoreGame(SharedPreferences savedInstanceState) 
	{
		synchronized (surfaceHolder) 
		{
			game.restore(savedInstanceState);
		}
	}

	public void saveGame(SharedPreferences.Editor editor) 
	{
		synchronized (surfaceHolder) 
		{
			game.save(editor);
		}
	}
	
	public void handleEvent(Event e)
	{
		synchronized (surfaceHolder) 
		{
			game.handleEvent(e);
		}
	}
}
