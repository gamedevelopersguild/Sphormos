package gdg.atthack.sphormos.entities;

import android.graphics.Canvas;
import utils.game.tools.event.Event;
import utils.game.tools.main.Entity;
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

public abstract class SphormosEntity extends Entity
{

	protected double m_dSpeed;
	protected double m_dHealth;
	protected double m_dRadius;
	protected double m_dOrientation;
	
	protected int m_nCurrentColorIndex;
	protected double m_currX;
	protected double m_currY;
	
	protected double m_dAbsorbDelay  = 300; // delay in milliseconds.
	protected double m_dNextAbsorbTime = 0;
	protected int m_nAbsrobRate = 2;
	
	
	public boolean isAlive()
	{
		return m_dHealth >0;
	}
	/**
	 * If this entiy is colliding with other and their colors are the
	 * same. Then draw life unless the health of one or the other is <=0
	 * @param other
	 */
	public void collideWithOther(SphormosEntity other) 
	{
		if(other.m_nCurrentColorIndex != this.m_nCurrentColorIndex)
			return;
		
		if(other.m_dHealth<=0 || m_dHealth <=0)
			return; 
		
		if(!isCollidingWith(other))
			return;
		
		double currTime = System.currentTimeMillis();
		if(m_dNextAbsorbTime <= currTime)
		{
			
			
			if(m_dRadius >= other.m_dRadius)
			{
				//m_dHealth += m_nAbsrobRate;
				//other.m_dHealth -= m_nAbsrobRate;
				addLife(m_nAbsrobRate);
				other.addLife(-m_nAbsrobRate);
				
			}
			else
			{
				//m_dHealth -= m_nAbsrobRate;
				//other.m_dHealth += m_nAbsrobRate;
				addLife(-m_nAbsrobRate);
				other.addLife(m_nAbsrobRate);
			
			}
			
			m_dNextAbsorbTime = System.currentTimeMillis() + m_dAbsorbDelay;
		}
	}
	/**
	 * This calculates the distance between the two centers of the circles. If the distance between
	 * centers is less than or equal to the sum of their radius then we have a collision.
	 * @param other
	 * @return
	 */
	public boolean isCollidingWith(SphormosEntity other)
	{
		double distance = Math.sqrt(Math.pow(m_currX - other.m_currX, 2) + Math.pow(m_currY - other.m_currY, 2));
		
		//if(m_currX <=0 || m_currY <= 0)
		//	return false; 
		
		
		return distance <= (m_dRadius + other.m_dRadius);
	}
	
	public void addLife(double lifeDelta)
	{
		m_dHealth += lifeDelta;
		
		m_dRadius = getRadius();
	}
	
	public double getRadius()
	{
		//return Math.sqrt(m_dHealth/Math.PI);
		return m_dHealth/2;
	
	}
	
	
}
