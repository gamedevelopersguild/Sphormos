package gdg.atthack.sphormos.managers;

import java.util.ArrayList;
import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import utils.game.tools.event.Event;
import utils.game.tools.main.Entity;

public class SphormosColorManager extends Entity
{
	
	private static SphormosColorManager m_Instance = null;
	
	private ArrayList<Paint>m_ActiveColors;
	
	private Random rand;
	private SphormosColorManager ()
	{
		m_ActiveColors = new ArrayList<Paint>();
		
		
		m_ActiveColors.add(createPaint(Color.MAGENTA));
		m_ActiveColors.add(createPaint(Color.GREEN));
		m_ActiveColors.add(createPaint(Color.BLUE));
		m_ActiveColors.add(createPaint(Color.YELLOW));
		m_ActiveColors.add(createPaint(Color.RED));
		
		rand = new Random();
		
	}
	
	public static SphormosColorManager getInstance()
	{
		if(m_Instance == null)
			m_Instance = new SphormosColorManager();
		
		return m_Instance;
	}
	public Paint createPaint(int color)
	{
		Paint temp = new Paint();
		temp.setColor(color);
		temp.setStrokeWidth(2);
		
		return temp;
				
	}
	
	public int getRandomColorIndex(int maxIndex)
	{
		
		return rand.nextInt(m_ActiveColors.size()) % (maxIndex+1) ;
	}
	public void dispose()
	{
		
	}

	public void addColor(int c)
	{
		if(m_ActiveColors.contains(c))
			return;
		
		m_ActiveColors.add(createPaint(c));
	}
	public int getColorCount()
	{
		if(m_ActiveColors == null)
			return -1;
		
		return m_ActiveColors.size();
	}
	
	public Paint getColor(int index)
	{
		if(index >= m_ActiveColors.size())
			return m_ActiveColors.get(0);
		
		return m_ActiveColors.get(index);
	}
	@Override
	public void draw(Canvas spriteBatch) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(double gameTime) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initializeEntity() 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleEvent(Event event) 
	{
		// TODO Auto-generated method stub
		
	}

}
