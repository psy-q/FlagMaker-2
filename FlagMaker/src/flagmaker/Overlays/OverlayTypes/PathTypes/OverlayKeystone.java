package flagmaker.Overlays.OverlayTypes.PathTypes;

import flagmaker.Data.Vector;

public class OverlayKeystone extends OverlayPath
{
	private final String Path = "m -90.189954,-114.36858 21.81,48.630011 H -120 l 60.370046,180.309989 h 119 L 119.74,-65.738569 H 68.120046 l 21.809981,-48.630011 z";
	private final Vector PathSize = new Vector(240, 230);
	
	public OverlayKeystone(int maximumX, int maximumY)
	{
		super("keystone", maximumX, maximumY);
		Constructor(Path, PathSize);
	}	
}
