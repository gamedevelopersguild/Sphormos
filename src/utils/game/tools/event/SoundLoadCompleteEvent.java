package utils.game.tools.event;

import android.media.SoundPool;
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
