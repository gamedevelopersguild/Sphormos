package utils.game.tools.main;


/***
 * This class handles the generation of 
 * positions and sizes for the different screen sizes 
 * of multiple android devices. 
 * 
 * Helper Class.
 * 
 * @author Frank Hernandez
 * Date: 03/06/2012
 *
 */
public class ScreenSizeManager 
{
	private static ScreenSizeManager m_Instance = null;
	
	// Keep track of the screen sizes.
	private int m_nScreenWidth;
	private int m_nScreenHeight;
	
	// Preferred width and height for this application. 
	private int m_nPreferredWidth = 640;//480;
	private int m_nPreferredHeight = 427;//320;
	
	private int m_nPreferredDensity;
	
	private ScreenSizeManager(){}
	
	/**
	 * Singleton implementation. 
	 * @return single instance for this class.
	 */
	public static ScreenSizeManager getInstance()
	{
		if(m_Instance == null)
			m_Instance = new ScreenSizeManager();
		
		return m_Instance;
		
	}
	
	/**
	 * This method updates the current screen width 
	 * and screen height. Should be called every time 
	 * the screen is resized.
	 * 
	 * @param width new width.
	 * @param height new height
	 */
	public void setScreenSize(int width, int height)
	{
		m_nScreenWidth = width;
		m_nScreenHeight = height;
	}
	
	/**
	 * Sets the preferred screen width and height.
	 * This is the based height and width used to 
	 * calculated the new height of the images.
	 * 
	 * @param width new preferred width 
	 * @param height new preferred height
	 */
	public void setPrefferedScreenSize(int width, int height)
	{
		m_nPreferredWidth = width;
		m_nPreferredHeight = height;
	}
	
	/**
	 * Helper method for calculating the resize ratio for the width.
	 * @return return new resize ratio. 
	 */
	private double getWidthRatio()
	{
		return 1.0* m_nScreenWidth/m_nPreferredWidth;
	}
	
	/**
	 * Helper method for calculating the resize ratio for the height.
	 * @return return new resize ratio. 
	 */
	private double getHeightRatio()
	{
		return 1.0 * m_nScreenHeight/m_nPreferredHeight;
	}
	
	/**
	 * Given the current X position it will resize it 
	 * if needed to the new location on the new screen 
	 * size. 
	 * @param currentX x position in the preferred screen size. 
	 * @return the corrected x value. 
	 */
	public int getCorrectedX(int currentX)
	{
		return (int)Math.round(currentX * getWidthRatio());
	}
	
	/**
	 * Given the current Y position it will resize it 
	 * if needed to the new location on the new screen 
	 * size. 
	 * @param currentX Y position in the preferred screen size. 
	 * @return the corrected x value. 
	 */
	public int getCorrectedY(int currentY)
	{
		return (int)Math.round(currentY * getHeightRatio());
	}
	
	public void releaseResource()
	{
		m_Instance = null;
	}
	
	
}
