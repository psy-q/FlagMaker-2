package flagmaker.Overlays.OverlayTypes.PathTypes;

import flagmaker.Vector;

public class OverlayStar extends OverlayPath
{
	private final String Path = "m 0,-100 24,68 H 96 L 40,12 60,80 0,40 -60,80 -40,12 -96,-32 h 72 z";
	private final Vector PathSize = new Vector(192, 180);
	
	public OverlayStar(int maximumX, int maximumY)
	{
		super(maximumX, maximumY);
		Constructor("star", Path, PathSize);
	}	
}
