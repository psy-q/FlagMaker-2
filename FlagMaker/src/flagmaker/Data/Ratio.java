package flagmaker.Data;

public class Ratio
{
	public int Width;
	public int Height;
	
	public Ratio (int width, int height)
	{
		Width = width;
		Height = height;
	}
	
	public Ratio (String asString)
	{
		String[] parts = asString.split(":");
		Height = Integer.parseInt(parts[0]);
		Width = Integer.parseInt(parts[1]);
	}
	
	public String ToString()
	{
		return Height + ":" + Width;
	}
}
