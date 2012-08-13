package gdg.atthack.sphormos.main;

import gdg.atthack.sphormos.Game;
import gdg.cooltoy.sphero.osmos.R;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class SoundContainer 
{
	private static SoundContainer m_Instance = null;
	private SoundPool m_soundPool;
	private MediaPlayer m_mediaPlayer;
	
	private static final int MAX_SOUNDS = 4;
	
	private int m_nBGMusicID; 
	private int m_nSFX_XPUpID;
	private int m_nSFX_XPDownID;
	private int m_nSFX_LvlUPID;
	
	private boolean m_bPlaySounds = true;
	
	private SoundContainer()
	{
		m_soundPool = new SoundPool(MAX_SOUNDS, AudioManager.STREAM_MUSIC, 0);
		
	}
	
	public static SoundContainer getInstance()
	{
		if(m_Instance == null)
			m_Instance = new SoundContainer();
		
		return m_Instance;
	}
	
	
	public void loadSounds()
	{
		//m_nBGMusicID = m_soundPool.load(Game.getGameContext(),R.raw.kp_bg_music ,0);
		m_mediaPlayer = MediaPlayer.create(Game.getGameContext(), R.raw.kp_bg_music );
		m_mediaPlayer.setLooping(true);
		
		m_nSFX_LvlUPID = m_soundPool.load(Game.getGameContext(), R.raw.lvl_up, 0);
		m_nSFX_XPDownID = m_soundPool.load(Game.getGameContext(),R.raw.xp_down,0);
		m_nSFX_XPUpID = m_soundPool.load(Game.getGameContext(), R.raw.xp_up, 0);
		
	}
	/**
	 * This method is used to release all 
	 * the sound resources used in the game.
	 */
	public void releaseResources()
	{
		if(!m_bPlaySounds)
			return; 
		
		if(m_mediaPlayer.isPlaying())
			m_mediaPlayer.stop();
		
		m_mediaPlayer.release();
		
		m_soundPool.release();
		
		m_Instance = null;
	}
	public void playBGMusic()
	{
		if(!m_bPlaySounds)
			return; 
		
		if(!m_mediaPlayer.isPlaying())
		{
			m_mediaPlayer.start();
		}
	}
	public void stopBGMusic()
	{
		if(m_mediaPlayer.isPlaying())
		{
			m_mediaPlayer.pause();
		}
	}
	public void playXPUpSound()
	{
		if(!m_bPlaySounds)
			return; 
		
		if(m_soundPool != null)
		{
			m_soundPool.play(m_nSFX_XPUpID, 1, 1, 0, 0, 1);
		
		}
	}
	public void playXPDownSound()
	{
		if(!m_bPlaySounds)
			return; 
		
		if(m_soundPool != null)
		{
			m_soundPool.play(m_nSFX_XPDownID, 1, 1, 0, 0, 1);
		}
	}
	public void playLevelUpSound()
	{
		if(!m_bPlaySounds)
			return; 
		
		if(m_soundPool != null)
		{
			m_soundPool.play(m_nSFX_LvlUPID, 1, 1, 0, 0, 1);
		}
	}
	
	public void setPlaySound(boolean shouldPlay)
	{
		m_bPlaySounds = shouldPlay;
	}
}
