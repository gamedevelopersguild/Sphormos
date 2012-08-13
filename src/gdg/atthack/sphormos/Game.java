package gdg.atthack.sphormos;

import gdg.atthack.sphormos.main.BitmapContainer;
import gdg.atthack.sphormos.main.SoundContainer;
import gdg.atthack.sphormos.managers.EnemyManager;
import gdg.atthack.sphormos.managers.LevelManager;
import gdg.atthack.sphormos.managers.SphormosColorManager;
import gdg.atthack.sphormos.states.game.GameMenuScreen;
import gdg.atthack.sphormos.states.game.GameScreen;
import gdg.atthack.sphormos.states.game.GameSplashScreen;

import java.util.Random;


import utils.game.tools.event.Event;
import utils.game.tools.event.SaveEvent;
import utils.game.tools.event.ScreenResizeEvent;
import utils.game.tools.event.activity.ActivityPauseEvent;
import utils.game.tools.event.activity.ActivityResumeEvent;
import utils.game.tools.fsm.GameStateMachine;
import utils.game.tools.main.EventDispatcher;
import utils.game.tools.main.GameActivityNotifier;
import utils.game.tools.main.ScreenSizeManager;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

/**
 * Game class taken from Donald E. Llopis workshop on android 
 * game development. Modified for the purpose of this game. 
 * 
 * 
 * @author Donald E. Llopis, CodeMinion
 *
 */
public class Game 
{

	private static Context m_context = null;
	private GameStateMachine<Game> m_stateMachine = null;
	
	private static int m_nScreenW;
	private static int m_nScreenH;
	
	// Used to clear screen every draw
	private Paint m_clearPaint;
	
	private SoundPool soundPool;
	private int m_nTitle_screen_music;
	
	//Compared to 480x800 phone 
	public static float m_fWidthRatio =1; 
	public static float m_fHeightRatio =1;
	
	private static final float MAX_WIDTH = 800;
	private static final float MAX_HEIGHT = 480;
	
	public Game(Context context) 
	{
		m_context = context;
		m_stateMachine = new GameStateMachine<Game>(this);
		
		m_clearPaint = new Paint();
		m_clearPaint.setARGB(255, 0, 0, 0);

		// Load All resources. Images & Sounds
		loadResources(context);
		
		m_stateMachine.changeState(new GameSplashScreen());
		//m_stateMachine.changeState(new GameMenuScreen());
		//m_stateMachine.changeState(new GameScreen());

		//soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
		//soundPool.setOnLoadCompleteListener(new GameOnLoadCompleteListener(this));
		
	
		
		//m_preLoadedLevel = new GameBikeLevelScreen();
	}
	
	public GameStateMachine<Game> getStateMachine()
	{
		return m_stateMachine;
	}
	
	public void setScreenSize(int width, int height) 
	{
		this.m_nScreenH = height;
		this.m_nScreenW = width;
		
		m_fWidthRatio =(float)(width/ MAX_WIDTH);
		m_fHeightRatio =(float)(height/ MAX_HEIGHT); 
		
		ScreenSizeManager.getInstance().setScreenSize(width, height);
		//ScreenSizeManager.getInstance().setPrefferedScreenSize(2400, 1600);
		
		handleEvent(new ScreenResizeEvent(width, height));
		

	}
	public static int getScreenWidth()
	{
		return m_nScreenW;
	}
	public static int getScreenHeight()
	{
		return m_nScreenH;
	}
	
	public void run(Canvas canvas) 
	{
		m_stateMachine.onUpdate();
		// Clear he screen. 
		canvas.drawPaint(m_clearPaint);
		// Draw Scenes
		m_stateMachine.onDraw(canvas);
		
	}
	
	
	public void doTouch() 
	{		
		//playerTap = true;
	}

	public void resetGame() 
	{
		// Release all resources and reset the singletons.
		//handleEvent(new ResetGameEvent());
		BitmapContainer.getInstance().releaseResources();
		EventDispatcher.getInstance().restDispatcher();
		SoundContainer.getInstance().releaseResources();
		ScreenSizeManager.getInstance().releaseResource();
		GameActivityNotifier.getInstance().releaseResources();
		
		EnemyManager.getInstance().dispose();
		SphormosColorManager.getInstance().dispose();
		LevelManager.getInstance().dispose();
		
		
	}
	
	private void loadSounds(Context context) 
	{
		SoundContainer.getInstance().loadSounds();
		//m_nTitle_screen_music = soundPool.load(context, R.raw.title_screen, 0);
		//m_nTitle_screen_music = soundPool.load(context, R.raw.droidjump, 0);
		//m_nTitle_screen_music = soundPool.load(context, R.raw.title_screen, 0);
		//droidEatPastrySnd = soundPool.load(context, R.raw.eatpastry, 1);
		//droidJumpSnd = soundPool.load(context, R.raw.droidjump, 1);		
	}
	
	private void loadImages(Context context) 
	{
		Resources res = context.getResources();
		BitmapContainer.getInstance().loadImages(context);
		
	}
	
	public void loadResources(Context context)
	{
		loadImages(context);
		loadSounds(context);
		
	}
	private void gamePause(Canvas canvas) 
	{
		
	}
	
	public void pause() 
	{
		handleEvent(new ActivityPauseEvent());
		
	}
		
	private void doScore(Canvas canvas) 
	{
	}
	public void restore(SharedPreferences savedState) 
	{
		handleEvent(new ActivityResumeEvent(savedState));	
	}
	
	public void save(SharedPreferences.Editor map) 
	{
		handleEvent(new SaveEvent(map));
	}
	
	/**
	 * This method returns the bitmap for the 
	 * splash screen.
	 * @return
	 */
	public Bitmap getSplashScreenImage()
	{
		return BitmapContainer.getInstance().getSplashScreenImage();
	}
	
	public Bitmap getGameLogo()
	{
		return BitmapContainer.getInstance().getGameLogo();
	}
	public Bitmap getStartMenu()
	{
		return BitmapContainer.getInstance().getStartMenu();
	}
	
	public SoundPool getSoundPool()
	{
		return soundPool;
	}
	
	public int getTitleMusicID()
	{
		return m_nTitle_screen_music;
	}
	
	public Bitmap getAreaBackgroundSky()
	{
		return BitmapContainer.getInstance().getAreaBackgroundSky();
	}
	
	public static Context getGameContext()
	{
		return m_context;
	}
	/**
	 * This method delegates the handling of the event to 
	 * the state machine. 
	 * @param event event to be handled by the state machine.
	 */
	public void handleEvent(Event event)
	{
		m_stateMachine.handleEvent(event);
	}

}
