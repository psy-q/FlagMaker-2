package flagmaker;

public class Ratio
{
	public int Width;
	public int Height;
	
	public Ratio (int width, int height)
	{
		Width = width;
		Height = height;
	}
	
	public String ToString()
	{
		return Height + ":" + Width;
	}
}
