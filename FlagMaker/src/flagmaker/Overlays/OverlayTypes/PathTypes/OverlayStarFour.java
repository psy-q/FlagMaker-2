package flagmaker.Overlays.OverlayTypes.PathTypes;

import flagmaker.Data.Vector;

public class OverlayStarFour extends OverlayPath
{
	private final String Path = "M 30,30 145,0 30,-30 0,-145 -30,-30 -145,0 -30,30 0,145 z";
	private final Vector PathSize = new Vector(290, 290);

	public OverlayStarFour(int maximumX, int maximumY)
	{
		super("star four", maximumX, maximumY);
		Constructor(Path, PathSize);
	}
}
