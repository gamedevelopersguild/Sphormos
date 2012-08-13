package utils.game.tools.event;


public class AccelerometerChangedEvent extends Event 
{
	public float m_fX;
	public float m_fY;
	public float m_fZ;
	
	public AccelerometerChangedEvent(float x, float y, float z )
	{
		m_fX = x;
		m_fY = y;
		m_fZ = z;
	}
}
