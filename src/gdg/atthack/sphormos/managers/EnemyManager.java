package gdg.atthack.sphormos.managers;

import gdg.atthack.sphormos.entities.EnemyEntity;
import gdg.atthack.sphormos.entities.PlayerEntity;
import gdg.atthack.sphormos.entities.SphormosEntity;

import java.util.ArrayList;
import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Rect;
import utils.game.tools.event.Event;
import utils.game.tools.main.Entity;
import utils.game.tools.main.EventDispatcher;
import utils.game.tools.main.ScreenSizeManager;

public class EnemyManager extends Entity
{

	private ArrayList<EnemyEntity> m_enemyBatch;
	
	private static EnemyManager m_Instance = null;
	private Random rand;
	private EnemyManager()
	{
		m_enemyBatch = new ArrayList<EnemyEntity>();
		rand = new Random();
		
	}
	public void dispose()
	{
		m_Instance = null;
	}
	
	public static EnemyManager getInstance()
	{
		if(m_Instance == null)
			m_Instance = new EnemyManager();
		
		return m_Instance;
	}
	/**
	 * This method tries to generate a set of enemies in the level. If an enemy would colide with
	 * other enemies or the player it gets skipped from the generation. 
	 * @param batchSize
	 * @param bounds
	 * @param player
	 */
	public void generateEnemyBatch(int batchSize, Rect bounds, PlayerEntity player, int maxIndex, int maxEnemySize)
	{
		m_enemyBatch = new ArrayList<EnemyEntity>();
		
		EnemyEntity temp;
		int x, y;
		
		int lastSize = 0;
		for(int i = 0; i < batchSize; i++)
		{
			x = rand.nextInt(bounds.left);
			y = rand.nextInt(bounds.top);
			lastSize = rand.nextInt(50);
			temp = new EnemyEntity(x, 
					y, 
					rand.nextInt(maxEnemySize), 
					SphormosColorManager.getInstance().getRandomColorIndex(maxIndex), 
					lastSize);
			
			if(!isCollidingWithOthers(temp) && !temp.isCollidingWith(player))
			{
				m_enemyBatch.add(temp);
			
				EventDispatcher.getInstance().registerSubscriber(temp);
			}
			//else
			//	i-=1;
			
		}
	}
	
	/**
	 * Checks with the given entity is colliding with any of the elements 
	 * in the enemy list.
	 * @param curr
	 * @return
	 */
	public boolean isCollidingWithOthers(SphormosEntity curr)
	{
		for(int i = 0; i < m_enemyBatch.size(); i++)
		{
			if(m_enemyBatch.get(i).isCollidingWith(curr))
				return true;
		}
		
		return false;
	}
	
	@Override
	public void draw(Canvas spriteBatch) 
	{
		// TODO Auto-generated method stub
		for(int i = 0; i < m_enemyBatch.size(); i++)
		{
			m_enemyBatch.get(i).draw(spriteBatch);
			
		}
	}

	@Override
	public void update(double gameTime) 
	{
		// TODO Auto-generated method stub
		checkCollisions();
	}

	@Override
	public void initializeEntity() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleEvent(Event event) 
	{
		for(int i =0; i < m_enemyBatch.size(); i++)
		{
			m_enemyBatch.get(i).handleEvent(event);
		}
		
	}
	/**
	 * Checks for collision within the list. That is, collisions within
	 * each enemy.
	 */
	public void checkCollisions()
	{
		for(int i = 0; i < m_enemyBatch.size(); i++)
		{
			for(int j = i+1; j < m_enemyBatch.size(); j++)
			{
				m_enemyBatch.get(i).collideWithOther(m_enemyBatch.get(j));
			}
		}
	}
	
	/**
	 * This method checks the player for collisions with the enemies.
	 * @param player
	 */
	public void checkPlayerCollision(PlayerEntity player)
	{
		for(int i =0; i < m_enemyBatch.size(); i++)
		{
			player.collideWithOther(m_enemyBatch.get(i));
		}
	}

}
