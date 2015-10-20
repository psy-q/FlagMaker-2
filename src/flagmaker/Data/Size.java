package flagmaker.Data;

public class Size
{
	public int X;
	public int Y;
	
	public Size(int x, int y)
	{
		X = x;
		Y = y;
	}
	
	public String ToString()
	{
		return Y + ":" + X;
	}
}
