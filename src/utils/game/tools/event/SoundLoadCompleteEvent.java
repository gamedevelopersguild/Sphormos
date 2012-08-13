package utils.game.tools.event;

import android.media.SoundPool;

public class SoundLoadCompleteEvent extends Event 
{
	public SoundPool m_sSoundPool;
	public int m_nSampleID;
	public int m_nStatus;
	
	public SoundLoadCompleteEvent(SoundPool soundPool, int sampleId, int status)
	{
		m_sSoundPool = soundPool;
		m_nSampleID = sampleId;
		m_nStatus = status;
	}
}
