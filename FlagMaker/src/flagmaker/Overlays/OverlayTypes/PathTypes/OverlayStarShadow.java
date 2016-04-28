package flagmaker.Overlays.OverlayTypes.PathTypes;

import flagmaker.Data.Vector;

public class OverlayStarShadow extends OverlayPath
{
	private final String Path = "M 0 -104.1875 L -25 -33.34375 L 0 -0.21875 L 0 -104.1875 z M 0 -0.21875 L 100.03125 -33.34375 L 25 -33.34375 L 0 -0.21875 z M 0 -0.21875 L 62.53125 83.375 L 41.6875 12.5 L 0 -0.21875 z M 0 -0.21875 L -62.53125 83.375 L 0 41.6875 L 0 -0.21875 z M 0 -0.21875 L -100.03125 -33.34375 L -41.6875 12.5 L 0 -0.21875 z";
	private final Vector PathSize = new Vector(200, 188);
	
	public OverlayStarShadow(int maximumX, int maximumY)
	{
		super("star shadow", maximumX, maximumY);
		Constructor(Path, PathSize);
	}	
}
