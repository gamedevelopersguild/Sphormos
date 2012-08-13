package gdg.atthack.sphormos;

import gdg.atthack.sphormos.events.game.BackOne;
import gdg.cooltoy.sphero.osmos.R;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import utils.game.tools.event.Event;
import utils.game.tools.event.activity.ActivityPauseEvent;
import utils.game.tools.event.activity.ActivityStopEvent;
import utils.game.tools.event.activity.StartActivityEvent;
import utils.game.tools.main.EventDispatcher;
import utils.game.tools.main.GameActivity;
import utils.game.tools.main.GameActivityNotifier;
import utils.game.tools.main.MainView;


public class SphormosMainActivity extends GameActivity implements OnMenuItemClickListener
{
    
	public static final String PREFS_NAME = "KP_???_PrefsFile";
	
	MainView m_gameView;
	GameThread m_gameThread;
	
	private boolean m_bIsActivityFinishing = false;
	private SensorManager sensorMgr; 
	private Sensor mAccelerometer = null;
	//private GameAccelerometerListener m_accEvent; 
	
	public SphormosMainActivity()
	{
	}
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        
        //Log.d("TAG_KP", "On Created Activity");
    	
        setContentView(R.layout.main);        
        m_gameView = (MainView) findViewById(R.id.maincanvas); 
        
    	GameActivityNotifier.getInstance().registerGameActivity(this);
    }
    
    @Override
    protected void onPause() 
    {
    	super.onPause();

    	//Log.d("TAG_KP", "On Pause Activity");
    	
    	SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
       	SharedPreferences.Editor editor = settings.edit();
       	
       	//SensorHandler.getInstance().uninitialize();
       	//m_gameView.stopThread();
       	m_gameThread = m_gameView.getThread();
       	
       	m_gameView.stopThread();
       	
       	
       	// if player wants to quit then reset the game
    	if (isFinishing()) 
    	{
    		m_gameThread.resetGame();
    		m_bIsActivityFinishing = true;
        }
    	else 
    	{	    	
    		m_gameThread.pause();
    		m_bIsActivityFinishing = false;
        }
    	
       	m_gameThread.saveGame(editor);
       	
       	/*
       	// if player wants to quit then reset the game
    	if (isFinishing()) 
    	{
    		m_gameThread.handleEvent(new ActivityStopEvent());
        }
    	else 
    	{	    	
    		m_gameThread.handleEvent(new ActivityPauseEvent());
        }*/
       	
       	
    }
    /**
     * Helper method to handle to safe pausing of 
     * the activity.
     */
    private void pauseActivity()
    {
    	SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
       	SharedPreferences.Editor editor = settings.edit();

       	m_gameThread = m_gameView.getThread();
       	m_gameView.stopThread();
       	
    	m_gameThread.pause();
		m_bIsActivityFinishing = false;
    
	   	m_gameThread.saveGame(editor);
	    
    }
    /**
     * Helper method to handle the safe stopping of
     * the activity.
     */
    private void stopActivity()
    {
    	SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
       	SharedPreferences.Editor editor = settings.edit();

       	m_gameThread = m_gameView.getThread();
       	m_gameView.stopThread();
       	
		m_gameThread.resetGame();
		m_bIsActivityFinishing = true;
    
	   	m_gameThread.saveGame(editor);
	    
    	
    }
    
    @Override
    protected void onResume() 
    {
    	//Log.d("TAG_KP", "On Resume Activity");
    	super.onResume();  
    	
    	// restore game
    	m_gameThread = m_gameView.getThread(); 
    	m_gameView.startThread();
    	SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
	   	m_gameThread.restoreGame(settings);  
	   	//SensorHandler.getInstance().resume();
    }
    
    @Override
    protected void onStop() 
    {
    	stopApplication();
    }
    /**
     * Helper method to safely shut down the 
     * application.
     */
    private void stopApplication()
    {
    	super.onStop();
    	//Log.d("TAG_KP", "onStop");
    	if(m_bIsActivityFinishing)
    	{
    		System.exit(RESULT_OK);
    	}
    	
    }
    @Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.game_menu, menu);
		
		return true;
		
	}
    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
    	System.out.println("Menu Pressed");
    	m_gameThread.game.handleEvent(new BackOne());
    
    	return super.onOptionsItemSelected(item);
    }
    
    @Override
	public boolean onMenuItemClick(MenuItem arg0) 
	{
		// TODO Auto-generated method stub
    	//EventDispatcher.getInstance().dispatchEvent(new BackOne());
		return false;
	}
    
	@Override
	public boolean handleEvent(Event event) 
	{
		if(event instanceof StartActivityEvent)
		{
			StartActivityEvent startEvent = (StartActivityEvent)event;
			
			startActivity(startEvent.activityIntent);
			return true;
		}
		else if (event instanceof ActivityPauseEvent)
		{
			pauseActivity();
		}
		else if (event instanceof ActivityStopEvent)
		{
			stopActivity();
		}
		// TODO Auto-generated method stub
		return false;
	}
	

}