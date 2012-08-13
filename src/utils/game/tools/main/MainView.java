package utils.game.tools.main;

import gdg.atthack.sphormos.Game;
import gdg.atthack.sphormos.GameThread;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources.Theme;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

public class MainView extends SurfaceView implements SurfaceHolder.Callback 
{

	private GameThread thread;
	
	private Context context;
	private Game game;
	private SurfaceHolder holder;
	
	public boolean viewIsCreated = false;
	
	//public static float density = 0;
	public MainView(Context context, AttributeSet attrs)
	{		
		super(context, attrs);
		
		holder = getHolder();
		holder.addCallback(this);
		
		Display display = ((WindowManager)context.getSystemService(context.WINDOW_SERVICE)).getDefaultDisplay();
		
		int height = display.getHeight();
		int width = display.getWidth();
		Log.d("SIZE", "Height: " + height + ", Width: "+width);
		
		//height = 700;
		//width = 1280;
		if(height > width)
		{
			// Then swap 
			int temp = width;
			width = height;
			height = temp;
		}
		ScreenSizeManager.getInstance().setScreenSize(width, height);
		
		DisplayMetrics metrics = new DisplayMetrics();
		display.getMetrics(metrics);
		
		//density = metrics.density;
		
		// Set the preferred screen size for the app.
		//ScreenSizeManager.getInstance().setPrefferedScreenSize(640, 427);

		
		this.context = context;
		game = new Game(context);			
		
		thread = null;
	
		setFocusable(true);	
		
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) 
	{
		//Log.d("TAG_KP", "Surface Changed : "+ width +"," +height);
		if(thread != null)
		{	
			if(height > width)
			{
				int temp = width;
				width = height;
				height = temp;
			}
			thread.setSurfaceSize(width, height);
		
		}
	
	}
	
	public void surfaceCreated(SurfaceHolder holder) 
	{
		//SensorHandler.getInstance().initialize();
		viewIsCreated = true;
		startThread();
		//Log.d("TAG_KP", "Surface Created");
		
	}
	
	
	public void surfaceDestroyed(SurfaceHolder holder) 
	{
		stopThread();
		viewIsCreated = false;
		
		//holder = getHolder();
		//holder.removeCallback(this);
	
	}
	
	public void startThread()
	{
		if(thread != null && !thread.isRunning() && viewIsCreated)
		{
			thread.setRunning(true);
			thread.start();
		}	
	}
	public void stopThread()
	{
		//Log.d("TAG_KP", "Stoping Thread Destroyed");
		
		if(thread == null)
			return; 
		
		boolean retry = true;
		thread.setRunning(false);
		
		
		while (retry) 
		{
			try 
			{
				thread.join();
				retry = false;
			} 
			catch (InterruptedException e) 
			{
				
			}
		}

		thread = null;
	}
	/**
	 * This method delegates the touch event to the 
	 * thread.
	 * @return It returns false if the thread is null, otherwise true. 
	 */
	public boolean onTouchEvent(MotionEvent event) 
	{
		if(thread != null)
			return thread.doTouchEvent(event);
		
		return false;
	}
	
	public GameThread getThread() 
	{
		//Log.d("TAG_KP", "Get Thread");

		if (thread == null) 
		{
			//Log.d("TAG_KP", "Creating New Thread");

			thread = new GameThread(holder, context, game);
		}
		return thread;
	}
	
	
	
}
