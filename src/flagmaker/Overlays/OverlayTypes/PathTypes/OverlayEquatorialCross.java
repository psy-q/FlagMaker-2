package flagmaker.Overlays.OverlayTypes.PathTypes;

import flagmaker.Vector;

public class OverlayEquatorialCross extends OverlayPath
{
	private final String Path = "M 3,10 3,3 10,3 10,-3 3,-3 3,-10 -3,-10 -3,-3 -10,-3 -10,3 -3,3 -3,10 Z";
	private final Vector PathSize = new Vector(20, 20);
	
	public OverlayEquatorialCross(int maximumX, int maximumY)
	{
		super("equatorial cross", maximumX, maximumY);
		Constructor(Path, PathSize);
	}	
}
