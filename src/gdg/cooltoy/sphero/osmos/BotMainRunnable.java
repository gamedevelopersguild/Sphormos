package gdg.cooltoy.sphero.osmos;


import gdg.atthack.sphormos.GameThread;
import gdg.atthack.sphormos.events.game.BackOne;
import gdg.atthack.sphormos.events.game.LevelStartEvent;
import gdg.atthack.sphormos.events.game.MoveEvent;
import gdg.atthack.sphormos.events.game.PlayerChangeColor;
//import android.app.Activity;
//import android.content.Context; 
//import android.content.DialogInterface.OnClickListener;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PowerManager;
//import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.TextView;
//import android.os.PowerManager;
//import android.widget.TextView;
import orbotix.robot.app.StartupActivity;
import orbotix.robot.base.*;
import orbotix.robot.sensor.DeviceSensorsData;
import utils.game.tools.event.Event;
import utils.game.tools.event.activity.ActivityPauseEvent;
import utils.game.tools.event.activity.ActivityStopEvent;
import utils.game.tools.event.activity.StartActivityEvent;
import utils.game.tools.main.EventDispatcher;
import utils.game.tools.main.GameActivity;
import utils.game.tools.main.GameActivityNotifier;
import utils.game.tools.main.MainView;

//import gdg.cooltoy.sphero.R;
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
public class BotMainRunnable extends GameActivity implements OnMenuItemClickListener//Activity 
{	
	
	public static final String PREFS_NAME = "KP_???_PrefsFile";
	
	MainView m_gameView;
	GameThread m_gameThread;
	private boolean m_bIsActivityFinishing = false;
	TextView debug;
	
	
	// Game Code Vars End //
	
	int colorNum = 0;
	
	float SENSITIVITY = 4.5f;
	
	boolean moving = false;
	
	int MAX_COLOR = 3; // no longer a constant

    private static final int REQUEST_STARTUP = 101;
    
    PowerManager pm;
    PowerManager.WakeLock wl;
  //  float[] lastData = new float[3]; // Might be useful, Laz

    private Robot mRobot;
    
    private final DeviceMessenger.AsyncDataListener mDataListener = new DeviceMessenger.AsyncDataListener() 
    {
        @Override
        public void onDataReceived(DeviceAsyncData data) 
        { 
            if (data instanceof DeviceSensorsAsyncData) 
            {
                DeviceSensorsData ballData = ((DeviceSensorsAsyncData)data).getAsyncData().get(0);

                float[] sensorData = new float[3];
                sensorData[0] = (float) ballData.getAccelerometerData().getFilteredAcceleration().x;
                sensorData[1] = (float) ballData.getAccelerometerData().getFilteredAcceleration().y;
                sensorData[2] = (float) ballData.getAccelerometerData().getFilteredAcceleration().z;
                
                shakeTester(sensorData); // Check if user is shaking device. 
                
                /*sensorData[0] = (float)ballData.getGyroData().getRotationRateFiltered().x;
                sensorData[1] = (float)ballData.getGyroData().getRotationRateFiltered().y;
                sensorData[2] = (float)ballData.getGyroData().getRotationRateFiltered().z; 
                */
                EventDispatcher.getInstance().dispatchEvent(new MoveEvent(-1 * sensorData[0],  -1 * sensorData[1]));
                debug.setText("x:" + sensorData[0] + " y: " + sensorData[1] + " z: " + sensorData[2]);
                //System.out.println("x: " + sensorData[0] + "y: " + sensorData[1] + "z: " + sensorData[2]);
             //   RGBLEDOutputCommand.sendCommand(mRobot, r, g, b);
            }
        }
    };
    
    // Allows you to change color, if you shake your device.
    public void shakeTester(float [] sensorData)
    {
    	if (sensorData[0] >= SENSITIVITY || sensorData[0] <= -SENSITIVITY 
    	 || sensorData[1] >= SENSITIVITY || sensorData[1] <= -SENSITIVITY 
         || sensorData[2] >= SENSITIVITY || sensorData[2] <= -SENSITIVITY)
    	{
    		getNextColor();
        }
    }
    
    public void getNextColor()
    {
    	colorNum++;
    	
    	if (colorNum > MAX_COLOR)
    		colorNum = 0; // Reset back to 0;
    	
    	setColor();
    }
    
    public void setColor()
    {
    	//String temp = color.toLowerCase();
    	
    	if (colorNum == 0) // magenta
    	{
    		RGBLEDOutputCommand.sendCommand(mRobot, 175, 0, 255);
    		EventDispatcher.getInstance().dispatchEvent(new PlayerChangeColor(0));
    	}
    	
    	else if (colorNum == 1) // Green
    	{
    		RGBLEDOutputCommand.sendCommand(mRobot, 0, 255, 0);
    		EventDispatcher.getInstance().dispatchEvent(new PlayerChangeColor(1));
    	
    	}
    	
    	else if (colorNum == 3) // Yellow
    	{
    		RGBLEDOutputCommand.sendCommand(mRobot, 255, 255, 0);
    		EventDispatcher.getInstance().dispatchEvent(new PlayerChangeColor(3));
    	
    	}
    	
    	else if (colorNum == 2) // Blue
    	{
    		RGBLEDOutputCommand.sendCommand(mRobot, 0, 0, 255);
    		EventDispatcher.getInstance().dispatchEvent(new PlayerChangeColor(2));
    	
    	}
    	
    	//else if (colorNum == 4) // Cyan
    	//{
    		//RGBLEDOutputCommand.sendCommand(mRobot, 0, 255, 200);
    	//}
    	
    	else
    	{
    		RGBLEDOutputCommand.sendCommand(mRobot, 0, 0, 0);
    	}
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        m_gameView = (MainView) findViewById(R.id.maincanvas); 
         
    	GameActivityNotifier.getInstance().registerGameActivity(this);
    	
    }

    @Override
    protected void onStart() 
    {
        super.onStart();
        
        //Intent startupIntent = new Intent(this, StartupActivity.class);
        //startActivityForResult(startupIntent, REQUEST_STARTUP);
        
        m_gameThread = m_gameView.getThread();
        //StabilizationCommand.sendCommand(mRobot, false);
        debug = (TextView) findViewById(R.id.textView1);
    }

    @Override
    protected void onStop() 
    {
    	super.onStop();
    	
        // stop the streaming data when we leave
        SetDataStreamingCommand.sendCommand(mRobot, 0, 0, SetDataStreamingCommand.DATA_STREAMING_MASK_OFF, 0);

        // unregister the async data listener to prevent a memory leak.
        DeviceMessenger.getInstance().removeAsyncDataListener(mRobot, mDataListener);

        
        // pause here for a second to allow the previous commands to go through before we shutdown
        // the connection to the ball
        try 
        {
            Thread.sleep(100);//Original was 100.
        }
        
        catch (InterruptedException e) 
        {
            e.printStackTrace();
        }
        
        // disconnect from the ball
        RobotProvider.getDefaultProvider().disconnectControlledRobots();
        
     	stopApplication();
        
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
    protected void onResume() 
    {
        super.onResume();
        
        /*
        if(mRobot == null)
        {
        	finish();
        	return;
        }
        if(!mRobot.isConnected())
        {
        	finish();
        	return; 
        }
        */
        pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
        
        if(wl != null)
        	wl.acquire();
        
        StabilizationCommand.sendCommand(mRobot, false);
        FrontLEDOutputCommand.sendCommand(mRobot,1.0f);
        m_gameThread = m_gameView.getThread(); 
    	m_gameView.startThread();
    	SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
	   	m_gameThread.restoreGame(settings);  
	   	
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
    protected void onPause() 
    {
    	super.onPause();

    	//Log.d("TAG_KP", "On Pause Activity");
    	
    	if(wl != null)
    		wl.release();
    	//wl.release();
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
    

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        
        switch (requestCode)
        {
            case REQUEST_STARTUP:
                if (resultCode == RESULT_OK)
                {
                    // get the robot
                    mRobot = RobotProvider.getDefaultProvider().findRobot(data.getStringExtra(StartupActivity.EXTRA_ROBOT_ID));

                    // register the async data listener
                    DeviceMessenger.getInstance().addAsyncDataListener(mRobot, mDataListener);

                    // turn data streaming on for the specific types we want
                    //
                    // Parameters:
                    // (1) mRobot   - the robot from which we want the data
                    // (2) 2        - this is the divisor applied to the maximum data rate of 400Hz coming back from the
                    //                ball we want the data to come back at 200Hz so we use 2 (400Hz/2)
                    // (3) 1        - this is how many sensor 'snapshots' are delivered every time we get a data packet
                    //                from the ball. In this case, we only want 1, but if you don't need to process data
                    //                in real time, you could slow down the data rate and increase the number of
                    //                snapshots returned. The snapshots are always returned in an
                    //                ArrayList<DeviceSensorData> in the order they were created.
                    // (4) mask     - these are the different sensor values we would like to have returned. All of the
                    //                available sensors are listed in SetDataStreamingCommand
                    // (4) 0        - this is the total number of packets we want returned. If you just wanted a small
                    //                window of sensor data from the ball, you could set this to a specific number of
                    //                packets to cover that time period based on the divisor and snapshot count set
                    //                in the previous parameters. You can also set this to 0 for infinite packets. This
                    //                will stream information back to the phone until it is stopped (by sending 0 in the
                    //                divisor parameter) or the ball shuts down.
                    //
                    SetDataStreamingCommand.sendCommand(mRobot, 4, 1,
                    		SetDataStreamingCommand.DATA_STREAMING_MASK_ACCELEROMETER_X_FILTERED |
                            SetDataStreamingCommand.DATA_STREAMING_MASK_ACCELEROMETER_Y_FILTERED |
                            SetDataStreamingCommand.DATA_STREAMING_MASK_ACCELEROMETER_Z_FILTERED |
                            SetDataStreamingCommand.DATA_STREAMING_MASK_GYRO_X_FILTERED |
                            SetDataStreamingCommand.DATA_STREAMING_MASK_GYRO_Y_FILTERED |
                            SetDataStreamingCommand.DATA_STREAMING_MASK_GYRO_Z_FILTERED, 0);
                } 
                else 
                {
                    mRobot = null;
                }
                break;
        }
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
		else if(event instanceof LevelStartEvent)
		{
			// Set the color
			setColor();
		}
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onMenuItemClick(MenuItem item) {
		// TODO Auto-generated method stub
		return false;
	}
}
