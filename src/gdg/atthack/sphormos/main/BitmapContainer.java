package gdg.atthack.sphormos.main;


import java.io.IOException;
import java.io.InputStream;

import utils.game.tools.main.ScreenSizeManager;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

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

public class BitmapContainer 
{
	private static BitmapContainer m_Instance = null;
	
	private Bitmap m_splashScreen = null;
	private Bitmap m_gameLogo = null;
	private Bitmap m_gameStartMenu = null;
	private Bitmap m_bmpMenuImage = null;
	private Bitmap m_bmpCreditsImage = null;
	
	private Bitmap m_bmpLevelPickImage = null;
	
	private Bitmap[] m_bmpExpBar;
	private int MAX_EXP_SPRITES = 2;
	
	private Bitmap m_bShadow;
	
	private Bitmap m_bmpYouWinImage = null;
	private Bitmap m_bmpYouLoseImage = null;
	
	
	private Paint m_drawPaint;
	private Paint m_drawQuestionColor;
	private Paint m_drawQuestionAnswerColor;
	private Paint m_drawExpPaint;
	private Paint m_drawTestPaint;

	private BitmapContainer()
	{
		m_drawPaint = new Paint();
		m_drawPaint.setARGB(255, 255, 255, 255);
		m_drawPaint.setAntiAlias(true);
		m_drawPaint.setTextSize(40.0f);
		m_drawPaint.setStyle(Paint.Style.FILL);
		
		m_drawQuestionColor = new Paint();
		m_drawQuestionColor.setARGB(255, 255, 255, 255);
		m_drawQuestionColor.setAntiAlias(true);
		m_drawQuestionColor.setTextSize(40.0f);
		m_drawQuestionColor.setStyle(Paint.Style.FILL);

		m_drawQuestionAnswerColor = new Paint();
		m_drawQuestionAnswerColor.setARGB(255, 255, 255, 255);
		m_drawQuestionAnswerColor.setAntiAlias(true);
		m_drawQuestionAnswerColor.setTextSize(40.0f);
		m_drawQuestionAnswerColor.setStyle(Paint.Style.FILL);
		
		m_drawExpPaint = new Paint();
		m_drawExpPaint.setARGB(255, 0, 0, 0);
		m_drawExpPaint.setAntiAlias(true);
		m_drawExpPaint.setTextSize(40);
		m_drawExpPaint.setStyle(Paint.Style.FILL);
		
		m_drawTestPaint = new Paint();
		m_drawTestPaint.setARGB(255, 127, 127, 127);
		m_drawTestPaint.setAntiAlias(true);
		m_drawTestPaint.setTextSize(40);
		m_drawTestPaint.setStyle(Paint.Style.FILL);

	}
	
	public void loadImages(Context context)
	{
	
		//Log.d("knowledgepet" , "Loaded the Images That We needed...");
		Resources res = context.getResources();
		AssetManager assetManager = context.getAssets();

		m_splashScreen = loadBitmap(assetManager, "gdg_low_res.png");
		
		
		
		m_bmpMenuImage = loadBitmap(assetManager, "main_menu.png");
		m_bmpCreditsImage = loadBitmap(assetManager, "credits_screen.png");
		
		// Set the preferred screen size to the same as the background, after all this image
		// should be the same size as the desired screen.
		ScreenSizeManager.getInstance().setPrefferedScreenSize(m_bmpMenuImage.getWidth(), 
						m_bmpMenuImage.getHeight());

		m_bmpLevelPickImage = loadBitmap(assetManager, "pick_level.png");
		
		m_bmpExpBar = new Bitmap[MAX_EXP_SPRITES];
		m_bmpExpBar[0] = loadBitmap(assetManager, "exp_bar_inner.png");
		m_bmpExpBar[1] = loadBitmap(assetManager, "exp_bar_outline.png");
		
		m_bmpYouLoseImage = loadBitmap(assetManager, "you_lose.png");
		m_bmpYouWinImage = loadBitmap(assetManager, "you_win.png");
		
		
	}
	/**
	 * Reads the specified image from the assets folder. 
	 * @param assetsPath
	 * @param assetManager
	 * @return
	 */
	private Bitmap loadBitmap(AssetManager assetManager, String assetsPath)
	{
		InputStream imageIn;
		Bitmap loadedBitmap = null;
		try 
		{
		    imageIn = assetManager.open(assetsPath, AssetManager.ACCESS_BUFFER);
		    loadedBitmap = BitmapFactory.decodeStream(imageIn);
		} 
		catch (IOException e) 
		{
		    Log.e("BitmapContaier","Could not open images: "+assetsPath,e);
		}

		return loadedBitmap;
	}
	
	public Bitmap getYouWinImage()
	{
		return m_bmpYouWinImage;
	}
	public Bitmap getYouLoseImage()
	{
		return m_bmpYouLoseImage;
	}
	/**
	 * Used to prevent memory leaks in the android.
	 * Should be close as the application is signaled 
	 * to close.
	 */
	public void releaseResources()
	{
		if(m_bShadow != null && !m_bShadow.isRecycled())
			m_bShadow.recycle();
		
		if(m_gameLogo != null && !m_gameLogo.isRecycled())
			m_gameLogo.recycle();
		
		
		for(int i = 0; i < m_bmpExpBar.length; i++)
		{
			if(m_bmpExpBar[i] != null && !m_bmpExpBar[i].isRecycled())
				m_bmpExpBar[i].recycle();
		}
		
		if(m_bmpMenuImage != null && m_bmpMenuImage.isRecycled())
			m_bmpMenuImage.recycle();

		if(m_bmpCreditsImage != null && m_bmpCreditsImage.isRecycled())
			m_bmpCreditsImage.recycle();
		
		if(m_bmpYouLoseImage != null && m_bmpYouLoseImage.isRecycled())
			m_bmpYouLoseImage.recycle();
		
		if(m_bmpYouWinImage != null && m_bmpYouWinImage.isRecycled())
			m_bmpYouWinImage.recycle();
		
			// Release the singleton also
		m_Instance = null;
		
	}
	public static BitmapContainer getInstance()
	{
		if(m_Instance == null)
			m_Instance = new BitmapContainer();
		
		return m_Instance;
	}
	
	public Bitmap getSplashScreenImage()
	{
		return m_splashScreen;
	}
	
	public Bitmap getGameLogo()
	{
		return m_gameLogo;
	}
	public Bitmap getStartMenu()
	{
		return m_gameStartMenu;
	}
	
	public Bitmap getLevelPick()
	{
		return m_bmpLevelPickImage;
	}
	
	public Bitmap[] getExpBarSprites()
	{
		return m_bmpExpBar;
	}
	
	public Bitmap getShadow()
	{
		return m_bShadow;
	}
	
	/**
	 * This method draws only if the bitmap has not been recycled.
	 * @param canvas
	 * @param image
	 * @param source
	 * @param dest
	 */
	public void drawSafely(Canvas canvas, Bitmap image, Rect source, Rect dest, Paint paint)
	{
		if(image == null)
			return;
		
		if(image.isRecycled())
			return;
		
		if(canvas == null)
			return;
		
		canvas.drawBitmap(image, source, dest, paint);
	}

	public Paint getPaint()
	{
		return m_drawPaint;
	}
	public Paint getQuestionPaint()
	{
		return m_drawQuestionColor;
	}
	public Paint getQuestionAnswerPaint()
	{
		return m_drawQuestionAnswerColor;
	}
	
	public Bitmap getMainMenuImage()
	{
		return m_bmpMenuImage;
	}
	
	public Bitmap getCreditsImage()
	{
		return m_bmpCreditsImage;
	}
	
	public Paint getExpPaint()
	{
		return m_drawExpPaint;
	}
	public Paint getTestPain()
	{
		return m_drawTestPaint;
	}
	
	
}
